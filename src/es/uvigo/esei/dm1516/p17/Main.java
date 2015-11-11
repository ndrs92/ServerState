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
        getActionBar().setElevation(0);
        database = openOrCreateDatabase("serverstate", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS server (name TEXT, address TEXT)");

        ListView listaServers = (ListView) findViewById(R.id.listaServers);
        listaServers.setLongClickable(true);

        this.servers = new ArrayList<Server>();
        this.serversAdapter = new ArrayAdapter<Server>(getApplicationContext(), R.layout.list_layout , this.servers) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) this.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rowView = inflater.inflate(R.layout.list_layout, parent, false);
                ImageView icon = (ImageView) rowView.findViewById(R.id.list_icon);
                TextView serverName = (TextView) rowView.findViewById(R.id.list_title);
                TextView serverAddress = (TextView) rowView.findViewById(R.id.list_subtitle);
                ImageView goIcon = (ImageView) rowView.findViewById(R.id.list_goicon);

                serverName.setText(getItem(position).getName());
                serverAddress.setText(getItem(position).getAddress());

                if( ((int) (Math.random()*2+1)) == 1){
                    icon.setImageDrawable(getDrawable(R.drawable.ic_up));

                }else{
                    icon.setImageDrawable(getDrawable(R.drawable.ic_down));

                }



                return rowView;
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
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.mainMenuRefresh:
                serversAdapter.notifyDataSetChanged();
                break;
            case R.id.mainMenuTestData:
                createTestData();
                break;

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

    public void createTestData(){
        Server.deleteAllFromDatabase(Main.database);
        serversAdapter.clear();
        serversAdapter.add(new Server("Google", "www.google.es").saveToDatabase(Main.database));
        serversAdapter.add(new Server("Facebook", "www.facebook.com").saveToDatabase(Main.database));
        serversAdapter.add(new Server("Twitter", "www.twitter.com").saveToDatabase(Main.database));
        serversAdapter.add(new Server("Universidade de Vigo", "www.uvigo.es").saveToDatabase(Main.database));
        serversAdapter.add(new Server("ESEI", "www.esei.uvigo.es").saveToDatabase(Main.database));
        serversAdapter.add(new Server("Faitic Uvigo", "www.faitic.uvigo.es").saveToDatabase(Main.database));
        serversAdapter.add(new Server("Reddit", "www.reddit.com").saveToDatabase(Main.database));
        serversAdapter.add(new Server("Infraesctructura ESEI", "www.infraesctructura.ei.uvigo.es").saveToDatabase(Main.database));
        serversAdapter.add(new Server("Google +", "plus.google.com").saveToDatabase(Main.database));
        serversAdapter.add(new Server("Spotify", "www.spotify.com").saveToDatabase(Main.database));
        serversAdapter.add(new Server("Stack Overflow", "www.stackoverflow.com").saveToDatabase(Main.database));
        serversAdapter.add(new Server("Android Web", "www.android.com").saveToDatabase(Main.database));
        serversAdapter.add(new Server("Android Developers", "developer.android.com").saveToDatabase(Main.database));
        serversAdapter.add(new Server("Java", "www.java.com").saveToDatabase(Main.database));
        serversAdapter.add(new Server("Microsoft", "www.microsoft.com").saveToDatabase(Main.database));
        serversAdapter.add(new Server("Apple", "www.apple.com").saveToDatabase(Main.database));
    }

}
