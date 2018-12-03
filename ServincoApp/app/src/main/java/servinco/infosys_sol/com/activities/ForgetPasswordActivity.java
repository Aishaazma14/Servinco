package servinco.infosys_sol.com.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import servinco.infosys_sol.com.R;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnForgetPassword,btnShowSettings;
    EditText edtTxtForgetPassword;
    AnimationDrawable smileyAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initViews();
    }

    private void initViews(){

        btnForgetPassword =  findViewById(R.id.btnForgetPassword);
        edtTxtForgetPassword =  findViewById(R.id.edtTxtForgetPassword);


        btnForgetPassword.setOnClickListener(ForgetPasswordActivity.this);
        btnShowSettings.setOnClickListener(ForgetPasswordActivity.this);
       /* //animationDrawable();*/
        }





    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnForgetPassword:{
                Toast.makeText(this, "Reset Your password now ", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

   /* private void animationDrawable(){

        imgSmiley.setBackgroundResource(R.drawable.smiley_animation);
        smileyAnimation = (AnimationDrawable) imgSmiley.getBackground();
        smileyAnimation.start();

    }*/
}
