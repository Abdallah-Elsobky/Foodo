package iti.student.foodo.data.datasource.local;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private static final String PREF_NAME = "FOODO_PREF";
    private static final String KEY_IS_FIRST_TIME = "IS_FIRST_TIME";



    private final SharedPreferences sharedPreferences;

    public PrefManager(Context context) {
        sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isFirstTime() {
        return sharedPreferences.getBoolean(KEY_IS_FIRST_TIME, true);
    }

    public void setFirstTime(boolean value) {
        sharedPreferences.edit()
                .putBoolean(KEY_IS_FIRST_TIME, value)
                .apply();
    }


    public void clear() {
        sharedPreferences.edit().clear().apply();
    }
}

