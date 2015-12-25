package es.uvigo.esei.dm1516.p17;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
        LinearLayout accessButton = (LinearLayout) findViewById(R.id.detailsfab);



        String extrasName = extras.getString("name");
        String extrasAddress = extras.getString("address");
        name.setText(extrasName);
        address.setText(extrasAddress);

        final Server target = new Server(extrasName, extrasAddress);

        accessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toWeb = new Intent(Details.this, WebPage.class);
                toWeb.putExtra("address", target.getAddress());
                toWeb.putExtra("name", target.getName());
                startActivity(toWeb);
            }
        });

        //Launch ConnChecker. It would change the FAB color by itself
        ConnChecker cc = new ConnChecker(this);
        cc.execute(target);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}