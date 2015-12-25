package es.uvigo.esei.dm1516.p17;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

/**
 * Created by ndrs on 24/12/15.
 */
public class ConnChecker extends AsyncTask<Server, Double, Boolean>{
    private Details activity;
    private String ipResult;

    public ConnChecker(Details activity){
        this.activity = activity;
    }

    @Override
    public void onPreExecute(){
        ipResult = "invalid";
        LinearLayout button = (LinearLayout) activity.findViewById(R.id.detailsfab);
        button.setBackground(activity.getResources().getDrawable(R.drawable.detailsfab_loading));
        button.setClickable(false);
        activity.findViewById(R.id.detailsfab_image).setBackground(activity.getResources().getDrawable(R.drawable.spinner_16_inner_holo));
        TextView tvIp = (TextView) activity.findViewById(R.id.tvIp);
        TextView tvIpContent = (TextView) activity.findViewById(R.id.tvIpContent);
        tvIpContent.setVisibility(View.INVISIBLE);
        tvIp.setText("...");
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
                        //Try to resolve ip address
                        address = address.replace("http://", "");
                        address = address.replace("https://", "");
                        if(address.indexOf("/") != -1){

                            address = address.substring(0,address.indexOf("/"));

                        }

                        //Sometimes it does not get any address, dunno why
                        InetAddress getIP = InetAddress.getByName(address);
                        ipResult = getIP.getHostAddress();

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
        LinearLayout l = (LinearLayout) activity.findViewById(R.id.detailsfab);
        ImageView i = (ImageView) activity.findViewById(R.id.detailsfab_image);
        TextView servicesText = (TextView) activity.findViewById(R.id.servicesText);
        TextView tvIp = (TextView) activity.findViewById(R.id.tvIp);
        TextView tvIpContent = (TextView) activity.findViewById(R.id.tvIpContent);

        if(connected){
            servicesText.setText("Servidor disponible");
            l.setBackground(activity.getResources().getDrawable(R.drawable.detailsfab_up_selector));
            i.setBackground(activity.getResources().getDrawable(R.drawable.globe_icon));
            l.setClickable(true);
            tvIp.setText("IP: ");
            tvIpContent.setVisibility(View.VISIBLE);

            if(ipResult != "invalid"){
                tvIpContent.setText(ipResult);
            }else{
                tvIpContent.setText("Inaccesible");
            }
        } else {
            servicesText.setText("Servidor desconectado");
            l.setBackground(activity.getResources().getDrawable(R.drawable.detailsfab_down_selector));
            i.setBackground(activity.getResources().getDrawable(R.drawable.ic_down));
            l.setClickable(false);
            tvIp.setText("No hay datos para mostrar :(");
        }
    }
}
