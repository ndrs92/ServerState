package es.uvigo.esei.dm1516.p17;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by ndrs on 10/11/15.
 */
public class Details extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setElevation(0);


        Bundle extras = getIntent().getExtras();
        TextView name = (TextView) findViewById(R.id.detailName);
        TextView address = (TextView) findViewById(R.id.detailServer);
        String extrasName = extras.getString("name");
        String extrasAddress = extras.getString("address");
        name.setText(extrasName);
        address.setText(extrasAddress);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}