package jose.carlex.testvolley;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Carlex on 5/2/2017.
 */
public class BackgroundTaskTutor{

    Context context;
    ArrayList<TutorPost> arrayList = new ArrayList<>();
    String json_url = "http://192.168.43.93/fetchtutors.php";//"http://192.168.254.101/fetchtutors.php";

    public BackgroundTaskTutor (Context context)
    {
        this.context = context;
    }

    public ArrayList<TutorPost> getList()
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, json_url, (String) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count = 0;
                        while(count<response.length())
                        {
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);
                                TutorPost tutorPost = new TutorPost(jsonObject.getString("Date"),jsonObject.getString("Subject"),jsonObject.getString("Email"),jsonObject.getString("Price"),jsonObject.getString("Description"),jsonObject.getString("Status"));
                                arrayList.add(tutorPost);
                                count++;


                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error...",Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });

        MySingletonTutor.getInstance(context).addToRequestQueue(jsonArrayRequest);



        return  arrayList;
    }
}
