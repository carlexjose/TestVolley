package jose.carlex.testvolley;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button login_button;
    EditText Email,Password;
    String email, password;
    String login_url = "http://192.168.254.101/login.php";
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.reg_txt);
        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });
        builder = new AlertDialog.Builder(MainActivity.this);
        login_button = (Button)findViewById(R.id.bn_login);
        Email = (EditText)findViewById(R.id.login_email);
        Password = (EditText)findViewById(R.id.login_password);
        login_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                email = Email.getText().toString();
                password = Password.getText().toString();

                if(email.equals("")||password.equals(""))
                {
                    builder.setTitle("Something went wrong");
                    displayAlert("Enter a valid email and password");
                }
                else
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                            new Response.Listener<String>(){
                                @Override
                                public void onResponse (String response){
                                    try{
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        if(code.equals("login_failed"))
                                        {
                                            builder.setTitle("Login Error.");
                                            displayAlert(jsonObject.getString("message"));
                                        }
                                        else
                                        {
                                            Intent intent = new Intent(MainActivity.this,LoginSuccess.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString ("name", jsonObject.getString("name"));
                                            bundle.putString ("email",jsonObject.getString("email"));
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }
                                    } catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
                            error.printStackTrace();

                        }
                    })
                    {
                        @Override
                        protected Map<String,String> getParams()throws AuthFailureError{
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("email",email);
                            params.put("password",password);
                            return params;
                        }
                    };
                    MySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
                }
            }
        });
    }

    public void displayAlert(String message)
    {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Email.setText("");
                Password.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
