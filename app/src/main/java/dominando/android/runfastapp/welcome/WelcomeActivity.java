package dominando.android.runfastapp.welcome;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import dominando.android.runfastapp.MainActivity;
import dominando.android.runfastapp.R;

public class WelcomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
   // private PrefManager prefManager;
    private LinearLayout dotsLayout;
    private int[] layouts;
    private TextView[] pontos;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*prefManager = new PrefManager(this);
        if (!prefManager.isFirstTime()) {
            ChamaTelaPrincipal();
        } */

        //printKeyHash();

        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutPontos);
        loginButton = (LoginButton) findViewById(R.id.loginButton);

        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_friends"));
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ChamaTelaPrincipal();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
                builder.setMessage(R.string.error_facebook);

                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loginButton.performClick();
                    }
                });

                builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alerta = builder.create();
                alerta.show();

            }
        });

        layouts = new int[]{R.layout.welcome_slider1,
                R.layout.welcome_slider2,
                R.layout.welcome_slider3,
                R.layout.welcome_slider4};


        addBottomDots(0);

        myViewPagerAdapter = new MyViewPagerAdapter(layouts,WelcomeActivity.this);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getItem() {
        return viewPager.getCurrentItem();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null){
            ChamaTelaPrincipal();
        }
    }

    private void ChamaTelaPrincipal() {
        //prefManager.setFirstTime(false);
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

    /*private void printKeyHash() {
        try {

            PackageInfo info = getPackageManager().getPackageInfo("dominando.android.runfastapp", PackageManager.GET_SIGNATURES);

            for (Signature signature: info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("keyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    } */

    private void addBottomDots(int currentPage) {
        pontos = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < pontos.length; i++) {
            pontos[i] = new TextView(this);
            pontos[i].setText(Html.fromHtml("&#8226;"));
            pontos[i].setTextSize(35);
            pontos[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(pontos[i]);
        }

        if (pontos.length > 0)
            pontos[currentPage].setTextColor(colorsActive[currentPage]);
    }

}
