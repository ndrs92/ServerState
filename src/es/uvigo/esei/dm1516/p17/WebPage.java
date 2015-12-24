package es.uvigo.esei.dm1516.p17;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ndrs on 24/12/15.
 */
public class WebPage extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webpage);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setElevation(0);

        WebView wb = (WebView) findViewById(R.id.webView);
        wb.setWebViewClient( new WebViewClient() );
        wb.loadUrl(checkURL(getIntent().getExtras().getString("address")));

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
}