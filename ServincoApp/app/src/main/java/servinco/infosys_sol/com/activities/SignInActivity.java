package servinco.infosys_sol.com.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import static servinco.infosys_sol.com.apihandlers.RequestHandlerClass.RunningQueue;
import static servinco.infosys_sol.com.commons.Constants.BASE_URL;



public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final String URL = "https://servincoapi.herokuapp.com/user/signin";

    //    the views
    Button btnSignIn, btnForgetPassword, btnSignInContin,btnSignUp,btnSigninResetPassword;

    EditText edtTxtUserEmail, edtTxtUserPassword;

    String userName,password;


    String apiEndPoint = "users/signin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initViews();


        signTheUserIn(apiEndPoint);
    }

    private void signTheUserIn(String apiEndPoint) {

        StringRequest mStringRequest = new StringRequest(1, BASE_URL + apiEndPoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
// FIXME here take the response and see if its the user

                // FIXME here take the response and see if its the user
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
// TODO    here handle if user is unable to reach the url

                // TODO    here handle if user is unable to reach the url


            }
        });

        RunningQueue(SignInActivity.this, mStringRequest);

    }


//this will handle all the view registration processes

    private void initViews() {
        btnSignIn = findViewById(R.id.btnSignIn);
        edtTxtUserEmail = findViewById(R.id.edtTxtUserEmail);
        edtTxtUserPassword = findViewById(R.id.edtTxtUserPassword);
        btnForgetPassword = findViewById(R.id.btnSigninForgotPassword);
        btnSignInContin = findViewById(R.id.btnSignInContin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSigninResetPassword = findViewById(R.id.btnSigninResetPassword);

        btnSignIn.setOnClickListener(SignInActivity.this);
        btnForgetPassword.setOnClickListener(SignInActivity.this);
        btnSignInContin.setOnClickListener(SignInActivity.this);
        btnSignUp.setOnClickListener(SignInActivity.this);
        btnSigninResetPassword.setOnClickListener(SignInActivity.this);


    }


//    this will handle all the click on clickable views

    @Override
    public void onClick(View incomingView) {

        switch (incomingView.getId()) {

            case R.id.btnSignIn: {
                Toast.makeText(this, "hello signin", Toast.LENGTH_SHORT).show();
//                TODO form Validation
//                TODO check if user has internet connection
//                TODO after validation call the api url to check user in DB
                break;
            }
            case R.id.btnSignInContin:{
                userLogin();
                break;

            }
            case R.id.btnSigninForgotPassword:{
                Toast.makeText(this, "Forget Password", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignInActivity.this,ForgetPasswordActivity.class));
                break;
            }
            case R.id.btnSignUp:{
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
                break;
            }
            case R.id.btnSigninResetPassword:{
                startActivity(new Intent(SignInActivity.this,ResetPasswordActivity.class));
                break;
            }

        }
    }
    private void userLogin(){
        String Request_login= "servinco.infosys_sol.com.activities.Login";

        userName = edtTxtUserEmail.getText().toString();
        password = edtTxtUserPassword.getText().toString();

        if(TextUtils.isEmpty(userName)){
            edtTxtUserEmail.setError("Please enter your username");
            edtTxtUserEmail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            edtTxtUserPassword.setError("Please enter your password");
            edtTxtUserPassword.requestFocus();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("status").equals("200")){
                        startActivity(new Intent(SignInActivity.this,MainActivity.class));
                        finish();
                    }else if(jsonObject.getString("status").equals("404")) {
                        String error = jsonObject.getString("data");
                        Toast.makeText(SignInActivity.this, error , Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignInActivity.this, "The error is "+ error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("user_email",userName);
                params.put("user_password",password);
                return params;
            }
        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest,Request_login);

    }
}





