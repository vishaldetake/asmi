package syscryption.asmi;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Nilima on 05-09-2018.
 */

public class MySingletone {
    private  static MySingletone mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private MySingletone(Context context)
    {
        mCtx=context;
        requestQueue=getRequestQueue();
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue == null)
        {
            requestQueue= Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return  requestQueue;
    }

    public static synchronized MySingletone getmInstance(Context context)
    {
        if(mInstance == null)
        {
            mInstance=new MySingletone(context);
        }
        return  mInstance;
    }
    public<T> void addToRequestQueue(Request<T> request)
    {
        requestQueue.add(request);
    }

}
