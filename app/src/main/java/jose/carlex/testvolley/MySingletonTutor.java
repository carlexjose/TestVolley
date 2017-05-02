package jose.carlex.testvolley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Carlex on 4/30/2017.
 */
public class MySingletonTutor {
    private static MySingletonTutor mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private MySingletonTutor(Context context)
    {
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingletonTutor getInstance(Context context)
    {
        if(mInstance==null)
        {
            mInstance = new MySingletonTutor(context);
        }
        return mInstance;
    }

    public <T>void addToRequestQueue(Request<T> request)
    {
        requestQueue.add(request);
    }
}
