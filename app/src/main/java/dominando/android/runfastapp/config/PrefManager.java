package dominando.android.runfastapp.config;

import android.content.Context;
import android.content.SharedPreferences;

import dominando.android.runfastapp.constantes.Constantes;

public class PrefManager {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PrefManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constantes.PREF_NAME,context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setFirstTime(boolean isFirstTime) {
        editor.putBoolean(Constantes.IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTime() {
        return sharedPreferences.getBoolean(Constantes.IS_FIRST_TIME_LAUNCH, true);
    }
}
