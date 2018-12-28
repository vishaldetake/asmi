package syscryption.asmi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    DrawerLayout drawerLayout;

    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        this.webView = (WebView) findViewById(R.id.webview);


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if (ProjectUtilities.isConnectingToInternet(this)) {
                  /* WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(webViewClient);*/

            final ProgressDialog pd = ProgressDialog.show(this, "", "Loading...", true);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                }
            });

            webView.loadUrl("http://asmitranslink.com/");
        }
        else {
            Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show();
            finish();
        }


    }


 @Override
   /* public void onBackPressed() {
       *//* //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                //finish();
                if(webView.canGoBack()){
                    webView.goBack();
                }

            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();*//*

        if(webView.canGoBack()){
            webView.goBack();
        }
    }*/
 /*public void onBackPressed() {
     if(webView.getVisibility()== View.VISIBLE){
         // dont pass back button action
         if(webView.canGoBack()){
             webView.goBack();
         }
         return;
     }else{
         // pass back button action
         super.onBackPressed();
     }
 }*/
 public void onBackPressed() {

     if(webView.canGoBack()) {

         webView.goBack();

     } else {
         // Let the system handle the back button
         super.onBackPressed();
     }
 }


   // @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
}


