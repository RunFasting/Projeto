package dominando.android.runfastapp.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dominando.android.runfastapp.MainActivity;
import dominando.android.runfastapp.R;
import dominando.android.runfastapp.config.PrefManager;
import dominando.android.runfastapp.welcome.WelcomeActivity;

public class SplashActivity extends AppCompatActivity {

    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        prefManager = new PrefManager(this);
        final boolean ChamaTelaBoasVindas = prefManager.isFirstTime();


        setContentView(R.layout.activity_splash);

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ChamaTelaBoasVindas)
                    ChamaTelaBoasVindas();
                else
                    ChamaTelaPrincipal();
            }
        }, 2000);
    }

    private void ChamaTelaBoasVindas() {
        Intent intent = new Intent(SplashActivity.this,
                WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void ChamaTelaPrincipal(){
        Intent intent = new Intent(SplashActivity.this,
                MainActivity.class);
        startActivity(intent);
        finish();
    }

}
