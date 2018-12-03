package servinco.infosys_sol.com.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.intrusoft.library.FrissonView;

import servinco.infosys_sol.com.R;

public class SettingsActivity extends AppCompatActivity {
    TextView CleanTextVu,DataTextVu,PowerTextVu,SecurityTextVu,FilterTextVu,AppWareTextVu;
    ImageView backArrowImageVu;
    FrissonView frissonViewTop,frissonViewMid,frissonViewLow;
    Animation moveFrissHorizon,moveFrissVertical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        AnimationsForFrissions();
    }

    private void initViews() {
        backArrowImageVu = findViewById(R.id.backArrowImageVu);
        CleanTextVu = findViewById(R.id.CleanTextVu);
        DataTextVu = findViewById(R.id.DataTextVu);
        PowerTextVu = findViewById(R.id.PowerTextVu);
        SecurityTextVu = findViewById(R.id.SecurityTextVu);
        FilterTextVu= findViewById(R.id.FilterTextVu);
        AppWareTextVu = findViewById(R.id.AppWareTextVu);

        frissonViewTop = findViewById(R.id.frission_view_top);
        frissonViewMid = findViewById(R.id.frission_view_mid);
        frissonViewLow = findViewById(R.id.frission_view_low);
    }

    private void AnimationsForFrissions() {

        moveFrissHorizon = AnimationUtils.loadAnimation(this,R.anim.move_frission_horizon);
        frissonViewLow.startAnimation(moveFrissHorizon);

        moveFrissVertical = AnimationUtils.loadAnimation(this,R.anim.move_frission_vertical);
        frissonViewMid.startAnimation(moveFrissVertical);

    }

}

