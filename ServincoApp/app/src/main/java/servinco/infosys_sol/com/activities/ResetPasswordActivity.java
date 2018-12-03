package servinco.infosys_sol.com.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import servinco.infosys_sol.com.R;
import servinco.infosys_sol.com.singleton.AppSingleton;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String URL = "https://servincoapi.herokuapp.com/user/reset_password";


    EditText edtTxtEmail,edtTxtOldPassword,edtTxtNewPassword;
    Button btnResetDone;
    String userEmail,userOldPassword,userNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initViews();
    }

    private void initViews(){
        edtTxtEmail = findViewById(R.id.edtTxtEmail);
        edtTxtOldPassword = findViewById(R.id.edtTxtOldPassword);
        edtTxtNewPassword = findViewById(R.id.edtTxtNewPassword);
        btnResetDone = findViewById(R.id.btnResetDone);

        btnResetDone.setOnClickListener(ResetPasswordActivity.this);


    }

    private void userResetPassword(){
        String REQUEST_TAG = "com.example.umair.usercredentialapp.ResetPasswordActivity";
        userEmail = edtTxtEmail.getText().toString();
        userOldPassword = edtTxtOldPassword.getText().toString();
        userNewPassword = edtTxtNewPassword.getText().toString();
        if(TextUtils.isEmpty(userEmail)) {
            edtTxtEmail.setError("Please enter your email");
            edtTxtEmail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(userOldPassword)) {
            edtTxtOldPassword.setError("Please enter your email");
            edtTxtOldPassword.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(userNewPassword)) {
            edtTxtNewPassword.setError("Please enter your email");
            edtTxtNewPassword.requestFocus();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("status").equals("200")){
                        String successMsg = jsonObject.getString("success");
                        Toast.makeText(ResetPasswordActivity.this, successMsg, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPasswordActivity.this,SignInActivity.class));
                        finish();
                    }else if(jsonObject.getString("status").equals("404")){
                        String failedMsg = jsonObject.getString("success");
                        Toast.makeText(ResetPasswordActivity.this, failedMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ResetPasswordActivity.this, "Internet is not connected", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_email",userEmail);
                params.put("user_password",userOldPassword);
                params.put("new_password",userNewPassword);

                return params;
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest,REQUEST_TAG);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnResetDone:{
               userResetPassword();
                break;
            }
        }
    }
}
