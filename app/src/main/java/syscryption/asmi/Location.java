package syscryption.asmi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;


public class Location extends AppCompatActivity {
    private String UPLOAD_URL ="http://ASMITRANSLINK.COM/webservices/insert.php";
    AlertDialog.Builder builder;
        TextView t1;
    int selectedId;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button b;
    private String text,role;
    private String KEY_id = "id";
    private String KEY_token="token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        builder= new AlertDialog.Builder(Location.this);
        t1=(TextView)findViewById(R.id.location);
        b=(Button)findViewById(R.id.btn_signup);

        text = FirebaseInstanceId.getInstance().getToken();

        radioGroup=(RadioGroup)findViewById(R.id.radio) ;


        // find the radiobutton by returned id

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                role= (String) radioButton.getText();
              uploadImage();
            }
        });

    }

    public void findPlace(View view) {
        try {

            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());

                ((TextView)findViewById(R.id.location)).setText(place.getAddress() +"\n" + place.getPhoneNumber());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
    private void uploadImage() {
        String location = t1.getText().toString();
        if (location.toString().equals("")) {
            Toast.makeText(Location.this, "Please Select Location", Toast.LENGTH_LONG).show();
        } else if (role.toString().equals("")) {
            Toast.makeText(Location.this, "Please Select Role", Toast.LENGTH_LONG).show();
        } else {
            final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();

                            if (response.toString().trim().equalsIgnoreCase("Inserted Successfully")) {
                                builder.setTitle("");
                                builder.setMessage(response);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                Intent i = new Intent(Location.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } else if (response.toString().trim().equalsIgnoreCase("Insertion Failed")) {
                                builder.setTitle("");
                                builder.setMessage(response);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.dismiss();
                    error.printStackTrace();
                    //Showing toast
                    Toast.makeText(Location.this, error.getMessage(), Toast.LENGTH_LONG).show();

                }
            }) {


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    text = FirebaseInstanceId.getInstance().getToken();
                    //Getting Image Name
                    String name = text.toString();
                    String location = t1.getText().toString();
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", name);
                    params.put("location", location);
                    params.put("role", role);

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingletone.getmInstance(Location.this).addToRequestQueue(stringRequest);
        }
    }
}
