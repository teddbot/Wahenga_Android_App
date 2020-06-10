// Modello di Visitor che visita tutti i possibili contenitori del Gedcom distinguendo i capostipiti

package app.wahenga.visita;

import org.folg.gedcom.model.Change;
import org.folg.gedcom.model.EventFact;
import org.folg.gedcom.model.Family;
import org.folg.gedcom.model.Header;
import org.folg.gedcom.model.Media;
import org.folg.gedcom.model.Name;
import org.folg.gedcom.model.Note;
import org.folg.gedcom.model.Person;
import org.folg.gedcom.model.Repository;
import org.folg.gedcom.model.RepositoryRef;
import org.folg.gedcom.model.Source;
import org.folg.gedcom.model.SourceCitation;
import org.folg.gedcom.model.Submitter;
import org.folg.gedcom.model.Visitor;

public class VisitorTotale extends Visitor {

	private boolean visita( Object oggetto ) {
		return visita( oggetto, false );
	}

	boolean visita( Object oggetto, boolean capostipite ) {
		return true;
	}

	@Override
	public boolean visit( Header h ) {
		return visita( h, true );
	}
	@Override
	public boolean visit( Person p ) {
		return visita( p, true );
	}
	@Override
	public boolean visit( Family f ) {
		return visita( f, true );
	}
	@Override
	public boolean visit( Source s ) {
		return visita( s, true );
	}
	@Override
	public boolean visit( Repository r ) {
		return visita( r, true );
	}
	@Override
	public boolean visit( Submitter s ) {
		return visita( s, true );
	}
	@Override
	public boolean visit( Media m ) {
		return visita( m, m.getId()!=null );
	}
	@Override
	public boolean visit( Note n ) {
		return visita( n, n.getId()!=null );
	}
	@Override
	public boolean visit( Name n ) {
		return visita( n );
	}
	@Override
	public boolean visit( EventFact e ) {
		return visita( e );
	}
	@Override
	public boolean visit( SourceCitation s ) {
		return visita( s );
	}
	@Override
	public boolean visit( RepositoryRef r ) {
		return visita( r );
	}
	@Override
	public boolean visit( Change c ) {
		return visita( c );
	}
}
