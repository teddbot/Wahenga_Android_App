package app.wahenga.dettaglio;

import android.view.Menu;
import org.folg.gedcom.model.Change;
import org.folg.gedcom.model.DateTime;
import app.wahenga.Dettaglio;
import app.wahenga.R;
import app.wahenga.U;

public class Cambiamenti extends Dettaglio {

	Change c;

	@Override
	public void impagina() {
		setTitle( R.string.change_date );
		mettiBava( "CHAN" );
		c = (Change) casta(Change.class);
		DateTime dataTempo = c.getDateTime();
		U.metti( box, getString(R.string.value), dataTempo.getValue() );
		U.metti( box, getString(R.string.time), dataTempo.getTime() );
		mettiEstensioni( c );
		U.mettiNote( box, c, true );
	}

	// Qui non c'è bisogno di un menu
	@Override
	public boolean onCreateOptionsMenu( Menu m ) {
		return false;
	}
}
