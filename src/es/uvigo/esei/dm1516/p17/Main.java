package es.uvigo.esei.dm1516.p17;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class Main extends Activity {
    /**
     * Called when the activity is first created.
     */

    private ArrayList<String> servers;
    private ArrayAdapter<String> serversAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ListView listaServers = (ListView) findViewById(R.id.listaServers);


        this.servers = new ArrayList<String>();
        this.serversAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, this.servers){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView toRet = (TextView) super.getView(position, convertView, parent);
                toRet.setTextColor(R.color.text_color);
                return toRet;
            }
        };



        listaServers.setAdapter(serversAdapter);

        serversAdapter.add("Test");

        LinearLayout addButton = (LinearLayout) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serversAdapter.add("Test");
            }
        });

    }


}
