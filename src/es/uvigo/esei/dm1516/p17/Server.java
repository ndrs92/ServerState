package es.uvigo.esei.dm1516.p17;

import android.database.sqlite.SQLiteDatabase;

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

    public void saveToDatabase(SQLiteDatabase db) {
        db.execSQL("INSERT INTO server VALUES('" + this.name + "', '" + this.address + "')");
    }

    public void deleteFromDatabase(SQLiteDatabase db){
        db.execSQL("DELETE FROM server WHERE address = '" + this.address + "'");
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
