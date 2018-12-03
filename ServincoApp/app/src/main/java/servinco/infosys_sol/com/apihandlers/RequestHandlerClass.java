package servinco.infosys_sol.com.apihandlers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RequestHandlerClass {

    public  static Request<String> RunningQueue(Context mContext, StringRequest mStringRequest){

        return Volley.newRequestQueue(mContext).add(mStringRequest);
    }

}
