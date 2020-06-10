// Lista dei Media

package app.wahenga;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.theartofdev.edmodo.cropper.CropImage;
import org.folg.gedcom.model.Media;
import org.folg.gedcom.model.MediaContainer;
import org.folg.gedcom.model.MediaRef;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import app.wahenga.visita.ListaMediaContenitore;
import app.wahenga.visita.RiferimentiMedia;
import app.wahenga.visita.TrovaPila;
import static app.wahenga.Globale.gc;

public class Galleria extends Fragment {

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle bandolo ) {
		setHasOptionsMenu( true );
		View vista = inflater.inflate( R.layout.galleria, container, false );
		RecyclerView griglia = vista.findViewById( R.id.galleria );
		griglia.setHasFixedSize( true );
		if( gc != null ) {
			ListaMediaContenitore visitaMedia = new ListaMediaContenitore( gc, !getActivity().getIntent().getBooleanExtra("galleriaScegliMedia",false ) );
			gc.accept( visitaMedia );
			((AppCompatActivity)getActivity()).getSupportActionBar().setTitle( visitaMedia.listaMedia.size() + " " + getString(R.string.media).toLowerCase() );
			RecyclerView.LayoutManager gestoreLayout = new GridLayoutManager( getContext(), 2 );
			griglia.setLayoutManager( gestoreLayout );
			AdattatoreGalleriaMedia adattatore = new AdattatoreGalleriaMedia( visitaMedia.listaMedia, true );
			griglia.setAdapter( adattatore );
			vista.findViewById( R.id.fab ).setOnClickListener( v ->
					U.appAcquisizioneImmagine( getContext(), Galleria.this, 4546, null )
			);
		}
		return vista;
	}

	// Andandosene dall'attività resetta l'extra se non è stato scelto un media condiviso
	@Override
	public void onPause() {
		super.onPause();
		getActivity().getIntent().removeExtra("galleriaScegliMedia");
	}

	// todo bypassabile?
	static int popolarita( Media med ) {
		RiferimentiMedia riferiMedia = new RiferimentiMedia( gc, med, false );
		return riferiMedia.num;
	}

	static Media nuovoMedia( Object contenitore ){
		Media media = new Media();
		media.setId( U.nuovoId(gc,Media.class) );
		media.setFileTag("FILE"); // Necessario per poi esportare il Gedcom
		gc.addMedia( media );
		if( contenitore != null ) {
			MediaRef rifMed = new MediaRef();
			rifMed.setRef( media.getId() );
			((MediaContainer)contenitore).addMediaRef( rifMed );
		}
		return media;
	}

	// Scollega da un contenitore un media condiviso
	static void scollegaMedia( String idMedia, MediaContainer contenitore, View vista ) {
		Iterator<MediaRef> refi = contenitore.getMediaRefs().iterator();
		while( refi.hasNext() ) {
			MediaRef ref = refi.next();
			if( ref.getMedia( Globale.gc ) == null // Eventuale ref a un media inesistente
					|| ref.getRef().equals(idMedia) )
				refi.remove();
		}
		if( contenitore.getMediaRefs().isEmpty() )
			contenitore.setMediaRefs( null );
		vista.setVisibility( View.GONE );
	}

	// Elimina un media condiviso o locale e rimuove i riferimenti nei contenitori
	// Restituisce un array con i capostipiti modificati
	public static Object[] eliminaMedia( Media media, View vista ) {
		Set<Object> capi;
		if( media.getId() != null ) {	// media OBJECT
			gc.getMedia().remove( media );	// ok
			// Elimina i riferimenti in tutti i contenitori
			RiferimentiMedia eliminaMedia = new RiferimentiMedia( gc, media, true );
			capi = eliminaMedia.capostipiti;
		} else {	// media LOCALE
			new TrovaPila( gc, media ); // trova temporaneamente la pila del media per individuare il contenitore
			MediaContainer contenitore = (MediaContainer)Memoria.oggettoContenitore();
			contenitore.getMedia().remove( media );
			if( contenitore.getMedia().isEmpty() )
				contenitore.setMedia( null );
			capi = new HashSet<>(); // set con un solo Object capostipite
			capi.add( Memoria.oggettoCapo() );
			Memoria.arretra(); // elimina la pila appena creata
		}
		Memoria.annullaIstanze( media );
		if( vista != null )
			vista.setVisibility( View.GONE );
		return capi.toArray( new Object[0] );
	}

	// Il file pescato dal file manager diventa media condiviso
	@Override
	public void onActivityResult( int requestCode, int resultCode, Intent data ) {
		if( resultCode == Activity.RESULT_OK ) {
			if( requestCode == 4546 ) { // File preso da app fornitrice viene salvato in Media ed eventualmente ritagliato
				Media media = nuovoMedia( null );
				if( U.ritagliaImmagine( getContext(), this, data, media ) ) { // se è un'immagine (quindi ritagliabile)
					U.salvaJson( false, media );
							// Non deve scattare onRestart() + recreate() perché poi il fragment di arrivo non è più lo stesso
					return;
				}
			} else if( requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE ) {
				U.fineRitaglioImmagine( data );
			}
			U.salvaJson( true, Globale.mediaCroppato );
		} else if( requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE ) // se clic su freccia indietro in Crop Image
			Globale.editato = true;
		else
			Toast.makeText( getContext(), R.string.something_wrong, Toast.LENGTH_LONG ).show();
	}

	// Menu contestuale
	private Media media;
	@Override
	public void onCreateContextMenu( ContextMenu menu, View vista, ContextMenu.ContextMenuInfo info ) {
		media = (Media) vista.getTag( R.id.tag_oggetto );
		menu.add(0, 0, 0, R.string.delete );
	}
	@Override
	public boolean onContextItemSelected( MenuItem item ) {
		if( item.getItemId() == 0 ) {
			U.salvaJson( false, eliminaMedia( media, null ) );
			getActivity().recreate();
			return true;
		}
		return false;
	}
}