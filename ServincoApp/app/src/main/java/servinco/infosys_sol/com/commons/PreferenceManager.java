package servinco.infosys_sol.com.commons;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private static final String FILE_NAME = "Servinco welcome";
    private static final String IS_FIRST_TIME_LAUNCH= "Is first time launch";

    private static final int PRIVATE_MODE = 0 ;

    public PreferenceManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(FILE_NAME,PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void setIsFirstTimeLaunch(Boolean isFirstTimeLaunch){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH,isFirstTimeLaunch);
        editor.commit();
    }

    public boolean isFirstTimeLaunch(){
        return preferences.getBoolean(IS_FIRST_TIME_LAUNCH,true);
    }
}
