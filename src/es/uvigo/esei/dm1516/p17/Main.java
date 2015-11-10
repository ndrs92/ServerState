package es.uvigo.esei.dm1516.p17;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

public class Main extends Activity {
    /**
     * Called when the activity is first created.
     */

    public static SQLiteDatabase database;

    private ArrayList<Server> servers;
    private ArrayAdapter<Server> serversAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        database = openOrCreateDatabase("serverstate", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS server (name TEXT, address TEXT)");

        ListView listaServers = (ListView) findViewById(R.id.listaServers);
        listaServers.setLongClickable(true);

        this.servers = new ArrayList<Server>();
        this.serversAdapter = new ArrayAdapter<Server>(getApplicationContext(), android.R.layout.simple_list_item_2, this.servers) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TwoLineListItem row;
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    row = (TwoLineListItem) inflater.inflate(android.R.layout.simple_list_item_2, null);
                } else {
                    row = (TwoLineListItem) convertView;
                }
                Server data = servers.get(position);
                row.getText1().setTextColor(getResources().getColor(R.color.text_color));
                row.getText1().setText(data.getName());
                row.getText2().setTextColor(getResources().getColor(R.color.text_color));
                row.getText2().setText(data.getAddress());

                return row;
            }
        };


        listaServers.setAdapter(serversAdapter);
        //Populate list with database info
        Cursor dbdata = database.rawQuery("Select * from server", new String[0]);
        while (dbdata.moveToNext()) {
            serversAdapter.add(new Server(dbdata.getString(dbdata.getColumnIndex("name")), dbdata.getString(dbdata.getColumnIndex("address"))));
        }


        listaServers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Main.this, Details.class);
                i.putExtra("name", serversAdapter.getItem(position).getName());
                i.putExtra("address", serversAdapter.getItem(position).getAddress());
                startActivity(i);
            }
        });

        listaServers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
                final Server toDel = serversAdapter.getItem(position);
                builder.setTitle("Eliminar");
                builder.setMessage("¿Desea eliminar: \n" + toDel.toString() + "?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        serversAdapter.remove(toDel);
                        toDel.deleteFromDatabase(Main.database);

                    }
                });
                builder.setNegativeButton("No", null);
                builder.create().show();

                return true;
            }
        });

        LinearLayout addButton = (LinearLayout) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, IntroServer.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu( menu );
        this.getMenuInflater().inflate( R.menu.main_menu, menu );
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch( menuItem.getItemId() ) {
            case R.id.mainMenuSettings:
                Toast.makeText(Main.this, "Aún no implementado", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mainMenuAbout:
                Toast.makeText(Main.this, "Aún no implementado", Toast.LENGTH_SHORT).show();
                break;
            default:

                break;
        }
        return true;
    }


}
