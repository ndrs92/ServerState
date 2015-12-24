package es.uvigo.esei.dm1516.p17;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ndrs on 10/11/15.
 */
public class Server {
    private String name;
    private String address;

    public Server(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Server saveToDatabase(SQLiteDatabase db) {
        db.execSQL("INSERT INTO server VALUES('" + this.name + "', '" + this.address + "')");
        return this;
    }

    public Server deleteFromDatabase(SQLiteDatabase db){
        db.execSQL("DELETE FROM server WHERE address = '" + this.address + "'");
        return this;
    }

    public static void deleteAllFromDatabase(SQLiteDatabase db){
        db.execSQL("DELETE FROM server");
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String toString() {
        return this.name + ": " + this.address;
    }

}
