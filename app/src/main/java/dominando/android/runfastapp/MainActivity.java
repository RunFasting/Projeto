package dominando.android.runfastapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import de.hdodenhof.circleimageview.CircleImageView;
import dominando.android.runfastapp.amigos.AmigosFragment;
import dominando.android.runfastapp.desafios.DesafiosFragment;
import dominando.android.runfastapp.metas.MetasFragment;
import dominando.android.runfastapp.perfil.PerfilFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AccessToken accessToken;
    private CircleImageView profileImg;
    private Handler handler = new Handler();
    private TextView username;
    private TextView DescEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentIniciar();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        username = (TextView)hView.findViewById(R.id.username);
        DescEmail = (TextView)hView.findViewById(R.id.email);
        profileImg = (CircleImageView) hView.findViewById(R.id.profile_image);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void fragmentIniciar(){
        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

    private void fragmentPerfil(){
        PerfilFragment fragment = new PerfilFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

    private void fragmentAmigos(){
        AmigosFragment fragment = new AmigosFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

    private void fragmentMetas(){
        MetasFragment fragment = new MetasFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

    private void fragmentsDesafios(){
        DesafiosFragment fragment = new DesafiosFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

    private void sair(){
        LoginManager.getInstance().logOut();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_iniciar) {
            fragmentIniciar();
        } else if (id == R.id.nav_eu) {
            fragmentPerfil();
        } else if (id == R.id.nav_metas) {
            fragmentMetas();
        } else if (id == R.id.nav_amigos) {
            fragmentAmigos();
        } else if (id == R.id.nav_desafios){
            fragmentsDesafios();
        } else if (id == R.id.nav_configuracoes){

        } else if (id == R.id.nav_sair){
            sair();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null){
            finish();
        }

        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String nome = object.getString("name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String caminhoImg = "https://graph.facebook.com/" +id + "/picture?type=large";

                    username.setText(nome);
                    DescEmail.setText(email);
                    loadImg(caminhoImg);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parametros = new Bundle();
        parametros.putString("fields", "id,name,email");
        request.setParameters(parametros);
        request.executeAsync();
    }

    private void loadImg(final String caminhoImg){

        new Thread(){

            public void run(){
                Bitmap img = null;

                try{
                    URL url = new URL(caminhoImg);
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                    InputStream input = conexao.getInputStream();
                    img = BitmapFactory.decodeStream(input);
                }
                catch(IOException e){}

                final Bitmap imgAux = img;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        profileImg.setImageBitmap(imgAux);
                    }
                });
            }

        }.start();

    }
}
