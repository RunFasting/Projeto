package dominando.android.runfastapp.welcome;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import dominando.android.runfastapp.R;
import dominando.android.runfastapp.config.PrefManager;

public class WelcomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private PrefManager prefManager;
    private LinearLayout dotsLayout;
    private int[] layouts;
    private TextView[] pontos;
    private MyViewPagerAdapter myViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager = new PrefManager(this);
        /*if (!prefManager.isFirstTime()) {
            ChamaTelaPrincipal();
        } */

        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutPontos);

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

    /*private void ChamaTelaPrincipal() {
        prefManager.setFirstTime(false);
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
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
