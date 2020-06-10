package app.wahenga.dettaglio;

import org.folg.gedcom.model.Submitter;
import app.wahenga.Dettaglio;
import app.wahenga.Podio;
import app.wahenga.R;
import app.wahenga.U;

public class Autore extends Dettaglio {

	Submitter a;

	@Override
	public void impagina() {
		setTitle( R.string.submitter );
		a = (Submitter) casta( Submitter.class );
		mettiBava( "SUBM", a.getId() );
		metti( getString(R.string.value), "Value", false, true );   // Value de che?
		metti( getString(R.string.name), "Name" );
		metti( getString(R.string.address), a.getAddress() );
		metti( getString(R.string.www), "Www" );
		metti( getString(R.string.email), "Email" );
		metti( getString(R.string.telephone), "Phone" );
		metti( getString(R.string.fax), "Fax" );
		metti( getString(R.string.language), "Language" );
		metti( getString(R.string.rin), "Rin", false, false );
		mettiEstensioni( a );
		U.cambiamenti( box, a.getChange() );
	}

	@Override
	public void elimina() {
		// Ricordiamo che almeno un autore deve essere specificato
		// non aggiorna la data di nessun record
		Podio.eliminaAutore( a );
	}
}
