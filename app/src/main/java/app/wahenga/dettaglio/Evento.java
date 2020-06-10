package app.wahenga.dettaglio;

import org.folg.gedcom.model.EventFact;
import org.folg.gedcom.model.PersonFamilyCommonContainer;
import java.util.Arrays;
import app.wahenga.Dettaglio;
import app.wahenga.Memoria;
import app.wahenga.R;
import app.wahenga.U;

public class Evento extends Dettaglio {

	EventFact e;
	// Lista di tag di eventi utili per evitare di mettere il Value dell'EventFact
	String[] tagEventi = { "BIRT","CHR","DEAT","BURI","CREM","ADOP","BAPM","BARM","BASM","BLES", // Eventi di Individuo
			"CHRA","CONF","FCOM","ORDN","NATU","EMIG","IMMI","CENS","PROB","WILL","GRAD","RETI",
			"ANUL","DIV","DIVF","ENGA","MARB","MARC","MARR","MARL","MARS" }; // eventi di Famiglia

	@Override
	public void impagina() {
		setTitle( R.string.event );
		e = (EventFact) casta( EventFact.class );
		setTitle( e.getDisplayType() );
		mettiBava( e.getTag() );
		if( Arrays.asList(tagEventi).contains(e.getTag()) ) // è un evento (senza Value)
			metti( getString(R.string.value), "Value", false, true );
		else // tutti gli altri casi, solitamente attributi (con Value)
			metti( getString(R.string.value), "Value", true, true );
		metti( getString(R.string.type), "Type", false, false ); // troppo raffinato per un'app generalista
		metti( getString(R.string.date), "Date" );
		metti( getString(R.string.place), "Place" );
		metti( getString(R.string.address), e.getAddress() );
		if( e.getTag() != null && e.getTag().equals("DEAT") )
			metti( getString(R.string.cause), "Cause" );
		else
			metti( getString(R.string.cause), "Cause", false, false );
		metti( getString(R.string.www), "Www", false, false );
		metti( getString(R.string.email), "Email", false, false );
		metti( getString(R.string.telephone), "Phone", false, false );
		metti( getString(R.string.fax), "Fax", false, false );
		metti( getString(R.string.rin), "Rin", false, false );
		metti( getString(R.string.user_id), "Uid", false, false );
		//altriMetodi = { "WwwTag", "EmailTag", "UidTag" };
		mettiEstensioni( e );
		U.mettiNote( box, e, true );
		U.mettiMedia( box, e, true );
		U.citaFonti( box, e );
	}

	@Override
	public void elimina() {
		((PersonFamilyCommonContainer)Memoria.oggettoContenitore()).getEventsFacts().remove( e );
		U.aggiornaDate( Memoria.oggettoCapo() );
		Memoria.annullaIstanze(e);
	}

	// Elimina i principali tag vuoti e eventualmente aggiunge la Y
	public static void ripulisciTag( EventFact ef ) {
		if( ef.getDate()!=null && ef.getDate().isEmpty() ) ef.setDate( null );
		if( ef.getPlace()!=null && ef.getPlace().isEmpty() ) ef.setPlace( null );

		if( ef.getTag()!=null && ( ef.getTag().equals("BIRT") || ef.getTag().equals("CHR") || ef.getTag().equals("DEAT") ) ) {
			if( ef.getDate() == null && ef.getPlace() == null )
				ef.setValue( "Y" );
			else
				ef.setValue( null );
		}
		if( ef.getValue()!=null && ef.getValue().isEmpty() ) ef.setValue( null );
	}
}
