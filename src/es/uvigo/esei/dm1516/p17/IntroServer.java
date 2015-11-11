package es.uvigo.esei.dm1516.p17;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ndrs on 10/11/15.
 */
public class IntroServer extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introserver);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setElevation(0);

        Button addButton = (Button) findViewById(R.id.createServer);
        final EditText edServerName = (EditText) findViewById(R.id.introserver_name);
        final EditText edServerAddress = (EditText) findViewById(R.id.introserver_address);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Server toAdd = new Server(edServerName.getText().toString(), edServerAddress.getText().toString());
                if (toAdd.getName().length() < 3) {
                    Toast.makeText(IntroServer.this, "Introduce un nombre con más de 3 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (toAdd.getAddress().length() < 5) {
                        Toast.makeText(IntroServer.this, "Introduce una dirección de más de 5 caracteres", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        toAdd.saveToDatabase(Main.database);
                        Toast.makeText(IntroServer.this, "Creado:\n"+toAdd.toString(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(IntroServer.this, Main.class);
                        startActivity(i);
                    }
                }
            }
        });
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