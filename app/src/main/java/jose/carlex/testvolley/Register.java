package jose.carlex.testvolley;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class Register extends AppCompatActivity {
    Button reg_bn;
    EditText Name,Email,Number,Password,ConPassword;
    String name,email,number,password,conpass, emailDomain = "";
    AlertDialog.Builder builder;
    String reg_url = "http://192.168.254.101/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reg_bn = (Button)findViewById(R.id.bn_reg);
        Name = (EditText) findViewById(R.id.reg_name);
        Email = (EditText) findViewById(R.id.reg_email);
        Number = (EditText) findViewById(R.id.reg_number);
        Password = (EditText) findViewById(R.id.reg_password);
        ConPassword = (EditText) findViewById(R.id.reg_con_password);
        builder = new AlertDialog.Builder(Register.this);
        reg_bn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name = Name.getText().toString();
                email = Email.getText().toString();
                number = Number.getText().toString();
                password = Password.getText().toString();
                conpass = ConPassword.getText().toString();
                String[] emailSplit = email.split("@");

                try{
                    emailDomain = emailSplit[1];
                }catch (ArrayIndexOutOfBoundsException exception){
                    emailDomain = "";
                    exception.printStackTrace();
                }

                if(name.equals("")||email.equals("")||number.equals("")||password.equals("")||conpass.equals(""))
                {
                    builder.setTitle("Somethinng went wrong.");
                    builder.setMessage("Please fill all the fields");
                    displayAlert("input_error");
                }
                else if(!email.contains("@")||!(emailDomain.equals("obf.ateneo.edu")))
                {
                    builder.setTitle("Somethinng went wrong.");
                    builder.setMessage("Use a valid university email");
                    displayAlert("input_error");
                }
                else if(!(password.equals(conpass)))
                {
                    builder.setTitle("Somethinng went wrong.");
                    builder.setMessage("Password fields do not match");
                    displayAlert("input_error");
                }
                else
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, reg_url,
                            new Response.Listener<String>(){
                                @Override
                                public void onResponse (String response){
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        String message = jsonObject.getString("message");
                                        builder.setTitle("Server Response...");
                                        builder.setMessage(message);
                                        displayAlert(code);
                                    } catch (JSONException e){
                                        e.printStackTrace();
                                    }

                                }
                            },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){

                        }
                    })
                    {
                        @Override
                        protected Map <String, String> getParams() throws AuthFailureError{
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("name", name);
                            params.put("email", email);
                            params.put("number", number);
                            params.put("password", password);

                            return params;
                        }
                    };
                    MySingleton.getInstance(Register.this).addToRequestQueue(stringRequest);
                }
            }
        });
    }

    public void displayAlert(final String code)
    {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (code.equals("input_error"))
                {
                    Password.setText("");
                    ConPassword.setText("");
                }
                else if (code.equals("reg_success"))
                {
                    finish();
                }
                else if (code.equals("reg_failed"))
                {
                    Name.setText("");
                    Email.setText("");
                    Number.setText("");
                    Password.setText("");
                    ConPassword.setText("");
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
