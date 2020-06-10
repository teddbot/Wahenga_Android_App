package app.wahenga.dettaglio;

import org.folg.gedcom.model.Address;
import app.wahenga.Dettaglio;
import app.wahenga.Memoria;
import app.wahenga.R;
import app.wahenga.U;

public class Indirizzo extends Dettaglio {

	Address a;

	@Override
	public void impagina() {
		setTitle( R.string.address );
		mettiBava( "ADDR" );
		a = (Address) casta( Address.class );
		metti( getString(R.string.value), "Value", false, true );	// Fortemente deprecato in favore dell'indirizzo frammentato
		metti( getString(R.string.name), "Name", false, false );	// _name non standard
		metti( getString(R.string.line_1), "AddressLine1" );
		metti( getString(R.string.line_2), "AddressLine2" );
		metti( getString(R.string.line_3), "AddressLine3" );
		metti( getString(R.string.postal_code), "PostalCode" );
		metti( getString(R.string.city), "City" );
		metti( getString(R.string.state), "State" );
		metti( getString(R.string.country), "Country" );
		mettiEstensioni( a );
	}

	@Override
	public void elimina() {
		eliminaIndirizzo( Memoria.oggettoContenitore() );
		U.aggiornaDate( Memoria.oggettoCapo() );
		Memoria.annullaIstanze(a);
	}
}
