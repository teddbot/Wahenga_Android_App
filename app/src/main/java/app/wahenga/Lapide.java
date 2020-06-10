package app.wahenga;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class Lapide extends AppCompatActivity {

	@Override
	protected void onCreate( Bundle bandolo ) {
		super.onCreate( bandolo );
		setContentView( R.layout.lapide );



		TextView collega = findViewById( R.id.lapide_link );
		collega.setPaintFlags( collega.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG );
		collega.setOnClickListener( v -> startActivity(
				new Intent(Intent.ACTION_VIEW, Uri.parse("https://wahenga.org")) )
		);
	}
}
