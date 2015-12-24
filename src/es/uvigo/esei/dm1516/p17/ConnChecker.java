package es.uvigo.esei.dm1516.p17;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ndrs on 24/12/15.
 */
public class ConnChecker extends AsyncTask<Server, Double, Boolean>{
    private Details activity;

    public ConnChecker(Details activity){
        this.activity = activity;
    }

    @Override
    public void onPreExecute(){
        activity.findViewById(R.id.detailColorLayout).setBackgroundColor(activity.getResources().getColor(R.color.gray));
    }

    @Override
    public Boolean doInBackground(Server... params) {
        try{
            ConnectivityManager cm = (ConnectivityManager) activity.getSystemService
                    (Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnected())
            {
                //Network is available but check if we can get access from the network.
                String address = checkURL(params[0].getAddress());
                Log.e("Checking: ", address);

                if(address == "invalid"){
                    return false;
                }

                URL url = new URL(address);

                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(2000); // Timeout 2 seconds.
                urlc.connect();

                if (urlc.getResponseCode() == 200)  //Successful response.
                {
                    return true;
                }
                else
                {

                    address = address.replace("http", "https");
                    url = new URL(address);

                    urlc = (HttpURLConnection) url.openConnection();
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(2000); // Timeout 2 seconds.
                    urlc.connect();

                    if (urlc.getResponseCode() == 200)  //Successful response.
                    {
                        return true;
                    }
                    else
                    {
                        Log.d("NO INTERNET", "NO INTERNET");
                        return false;
                    }

                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public String checkURL(String url){
        //Check if it has http:// and all that mess
        url = url.toLowerCase();
        if(!url.contains(".")){
            return "invalid";
        }

        if(!url.contains("http://")){
            url = "http://" + url;
        }

        return url;
    }

    @Override
    public void onPostExecute(Boolean connected){
        LinearLayout l = (LinearLayout) activity.findViewById(R.id.detailColorLayout);
        TextView servicesText = (TextView) activity.findViewById(R.id.servicesText);
        Button accessButton = (Button) activity.findViewById(R.id.accessButton);

        if(connected){
            servicesText.setText("Servidor disponible");
            l.setBackgroundColor(activity.getResources().getColor(R.color.server_up));
            accessButton.setVisibility(View.VISIBLE);
        }else{
            servicesText.setText("Servidor desconectado");
            l.setBackgroundColor(activity.getResources().getColor(R.color.server_down));
        }
    }
}
