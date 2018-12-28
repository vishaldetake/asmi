package syscryption.asmi;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class Splash extends AppCompatActivity {
    private String UPLOAD_URL = "http://ASMITRANSLINK.COM/webservices/token_check.php";
    private String text, role;
    private String KEY_id = "id";
    private String KEY_token = "token";
    private String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new CountDownTimer(5000, 1000) {

            /** This method will be invoked on finishing or expiring the timer */
            @Override
            public void onFinish() {


                if (ProjectUtilities.isConnectingToInternet(Splash.this)) {
                    /** Creates an intent to start new activity */
                    token_check();
                } else {
                    Toast.makeText(Splash.this, "Please Connect Your Internet", Toast.LENGTH_SHORT).show();
                    finish();
                }


            }

            /** This method will be invoked in every 1000 milli seconds until
             * this timer is expired.Because we specified 1000 as tick time
             * while creating this CountDownTimer
             */
            @Override
            public void onTick(long millisUntilFinished) {

            }
        }.start();

    }

    public void token_check() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.toString().trim().equalsIgnoreCase("None")) {
                            Intent i = new Intent(Splash.this, Location.class);
                            startActivity(i);
                            finish();
                        } else if (response.toString().trim().equalsIgnoreCase("Token Already Exist")) {
                           Intent i = new Intent(Splash.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //Showing toast
                Toast.makeText(Splash.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                text = FirebaseInstanceId.getInstance().getToken();

                //Getting Image Name
                String name = text;
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingletone.getmInstance(Splash.this).addToRequestQueue(stringRequest);

    }


}