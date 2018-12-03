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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SignUpActivity";
    private static final String URL = "https://servincoapi.herokuapp.com/user/signup";


    Button btnSignUpCont,btnSignUp,btnSignIn;
    EditText edtTxtRegEmail,edtTxtRegUsername,edtTxtRegPassword;
    String userEmail , userPassword, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
    }

    private void initViews() {

        btnSignUpCont = findViewById(R.id.btnSignUpContin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);
        edtTxtRegEmail = findViewById(R.id.edtTxtRegEmail);
        edtTxtRegUsername = findViewById(R.id.edtTxtRegUsername);
        edtTxtRegPassword = findViewById(R.id.edtTxtRegPassword);

        btnSignUpCont.setOnClickListener(SignUpActivity.this);
        btnSignIn.setOnClickListener(SignUpActivity.this);

    }


    @Override
    public void onClick(View incomingView) {
        switch(incomingView.getId()){

            case R.id.btnSignUpContin: {
                userSignUp();
                break;
            } case R.id.btnSignIn :{
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                break;
            }
        }
    }
    private void userSignUp(){
        String REQUEST_TAG = "com.example.umair.usercredentialapp.SignUpactivity";

        userEmail = edtTxtRegEmail.getText().toString();
        userName = edtTxtRegUsername.getText().toString();
        userPassword = edtTxtRegPassword.getText().toString();

        if(TextUtils.isEmpty(userEmail)) {
            edtTxtRegEmail.setError("Please enter your email");
            edtTxtRegEmail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(userName)) {
            edtTxtRegUsername.setError("Please enter your email");
            edtTxtRegUsername.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(userPassword)) {
            edtTxtRegPassword.setError("Please enter your email");
            edtTxtRegPassword.requestFocus();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("200")){
                        String successMsg = jsonObject.getString("success");
                        Toast.makeText(SignUpActivity.this, successMsg, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                        finish();
                    }else if(jsonObject.getString("status").equals("404")){
                        Toast.makeText(SignUpActivity.this, "The user already exists", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpActivity.this, "Error "+ error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_email",userEmail);
                params.put("firstName",userName);
                params.put("user_password",userPassword);

                return params;
            }
        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest,REQUEST_TAG);

    }
}






































