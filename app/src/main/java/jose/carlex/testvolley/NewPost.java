package jose.carlex.testvolley;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Carlex on 5/1/2017.
 */
public class NewPost extends Fragment {
    String email,name,subject,price,description,status, date;
    Button post_button;
    EditText text_subject, text_price, text_description;
    long poster;
    String post_url = "";
    String post_tutor_url = "http://192.168.43.93/posttutor.php";
    String post_tutee_url = "http://192.168.43.93/posttutee.php";
    AlertDialog.Builder builder;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        email = bundle.getString("email");

        builder = new AlertDialog.Builder(getActivity());
        post_button = (Button)view.findViewById(R.id.btn_post);
        text_subject = (EditText)view.findViewById(R.id.edit_subject);
        text_price = (EditText)view.findViewById(R.id.edit_price);
        text_description = (EditText)view.findViewById(R.id.edit_description);

        getActivity().setTitle("Create New Post");
        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                poster = adapterView.getSelectedItemId();
                //Toast.makeText(getActivity(), ""+adapterView.getSelectedItemId(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        post_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                subject = text_subject.getText().toString();
                price = text_price.getText().toString();
                description = text_description.getText().toString();
                status = "1";


                if(subject.equals("")||price.equals("")||description.equals(""))
                {
                    builder.setTitle("Something went wrong...");
                    displayAlert("Fill all fields.");
                }
                else {

                    if(poster == 0) post_url = post_tutor_url;
                    else            post_url = post_tutee_url;

                    Calendar dt = Calendar.getInstance();
                    date = dt.get(Calendar.YEAR) + ":" + dt.get(Calendar.MONTH) + ":" + dt.get(Calendar.DAY_OF_MONTH)+ " " + dt.get(Calendar.HOUR_OF_DAY) + ":" + dt.get(Calendar.MINUTE) + ":" + dt.get(Calendar.SECOND);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, post_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        if (code.equals("login_failed")) {
                                            builder.setTitle("Login Error.");
                                            displayAlert(jsonObject.getString("message"));
                                        } else {
                                            name = jsonObject.getString("name");
                                            builder.setTitle("Post Created");
                                            if(poster == 0)
                                                displayAlert("You have offered to teach '" + subject + "' for ₱" + price + ".");
                                            else
                                                displayAlert("You have inquired for a tutor for '" + subject + "' for ₱" + price + ".");
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                            error.printStackTrace();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", email);
                            params.put("date", date);
                            params.put("subject", subject);
                            params.put("price", price);
                            params.put("description", description);
                            params.put("status", status);
                            return params;
                        }
                    };
                    MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_post, container, false);
    }

    public void displayAlert(String message)
    {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
