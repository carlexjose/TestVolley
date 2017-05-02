package jose.carlex.testvolley;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PosterDetails extends AppCompatActivity {

    TextView tx_price,tx_date,tx_subject,tx_description,tx_name, tx_email;
    Button contact_button;

    String getname_url = "http://192.168.43.93/getname.php";
    String name, email;

    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_details);

        builder = new AlertDialog.Builder(PosterDetails.this);
        contact_button = (Button)findViewById(R.id.contact_button);

        //
        email = getIntent().getStringExtra("email");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getname_url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse (String response){
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            if(code.equals("login_failed"))
                            {
                                builder.setTitle("Error");
                                displayAlert(jsonObject.getString("message"));
                            }
                            else
                            {
                                tx_name.setText("Name: " + jsonObject.getString("name"));
                                Toast.makeText(PosterDetails.this,jsonObject.getString("name"),Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(PosterDetails.this,"Error",Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        })
        {
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",email);
                return params;
            }
        };
        MySingleton.getInstance(PosterDetails.this).addToRequestQueue(stringRequest);

        //


        tx_date = (TextView)findViewById(R.id.Date);
        tx_price = (TextView)findViewById(R.id.price);
        tx_subject = (TextView)findViewById(R.id.subject);
        tx_description = (TextView)findViewById(R.id.description);
        tx_name = (TextView)findViewById(R.id.poster_name);
        tx_email = (TextView)findViewById(R.id.email);
        tx_date.setText("Date Posted: " + getIntent().getStringExtra("date"));
        tx_price.setText("Price: " + getIntent().getStringExtra("price"));
        //tx_name.setText("Name: " + name);
        tx_subject.setText("Subject/Course: " + getIntent().getStringExtra("subject"));
        tx_description.setText("Description: " + getIntent().getStringExtra("description"));

        contact_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Confirm Contact");
                displayAlert("You are about to exchange contact details with poster.");
            }
        });
    }

    public void displayAlert(String message)
    {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                tx_email.setText("Email: " + getIntent().getStringExtra("email"));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
