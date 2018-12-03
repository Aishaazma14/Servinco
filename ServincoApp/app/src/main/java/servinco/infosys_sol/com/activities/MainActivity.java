package servinco.infosys_sol.com.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import servinco.infosys_sol.com.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edtTxtSearch;
    Button btnRestaurant,btnLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews(){
        edtTxtSearch = findViewById(R.id.edtTxtSearch);
        btnLocation = findViewById(R.id.btnLocation);
       btnRestaurant = findViewById(R.id.btnRestaurant);
       btnLocation.setOnClickListener(MainActivity.this);
        btnRestaurant.setOnClickListener(MainActivity.this);
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLocation:{
                startActivity(new Intent(MainActivity.this,MapsActivity.class));
                break;
            }case R.id.btnRestaurant:{
                break;
            }
        }

    }
}
