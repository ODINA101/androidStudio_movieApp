package irakli.samniashvili.app;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import irakli.samniashvili.app.Fragments.page2;
import irakli.samniashvili.app.Fragments.series;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
private Fragment ffragment;
    private static final String TAG = "MainActivity";

    private Handler mHandler;       // Handler to display the ad on the UI thread
    private Runnable displayAd;
    Toolbar toolbar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        MultiDex.install(this);
         toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

 checkwifi();






    if(getIntent().getExtras() != null) {
        for(String key : getIntent().getExtras().keySet())
        {
            if(key.equals( "title" )) {
                Toast.makeText( this,getIntent().getExtras().getString( key ),Toast.LENGTH_LONG ).show();
            }else if(key.equals( "message" )) {
                Toast.makeText( this,getIntent().getExtras().getString( key ),Toast.LENGTH_LONG ).show();

            }
        }
    }
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        getSupportActionBar().setTitle("მთავარი");

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ffragment = new page2();
        final Bundle url = new Bundle();

        url.putString("url", "მთავარი");
        ffragment.setArguments( url );

        ft.replace(R.id.main_content,ffragment);
        ft.commit();

    }

    @SuppressLint("CutPasteId")
    public void checkwifi() {
        final ConnectionDetector cd;
        final View parentLayout =  findViewById( R.id.main_content );

        cd = new ConnectionDetector(this);
        final Snackbar snackBar = Snackbar.make(findViewById(R.id.main_content), "გთხოვთ დაუკავშირდეთ ინტერნეტს", Snackbar.LENGTH_LONG);

        if (!cd.isConnected()) {


           snackBar.setAction("შემოწმება", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   checkwifi();
                }
            }).setActionTextColor(getResources().getColor(android.R.color.holo_red_light)).show();
        }else{
            snackBar.dismiss();
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }



    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id;
        id = item.getItemId();
      switch (id) {
          case R.id.nav_home:
              Bundle bundle = new Bundle();

              bundle.putString("url", "მთავარი");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_animaciuri:
                 bundle = new Bundle();

              bundle.putString("url", "ანიმაცია");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_series:
              fragment = new series();
              break;
          case R.id.nav_sashineleba:
              bundle = new Bundle();

              bundle.putString("url", "საშინელება");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_satavgadasavlo:
              bundle = new Bundle();

              bundle.putString("url", "სათავგადასავლო");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_istoriuli:
              bundle = new Bundle();

              bundle.putString("url", "ისტორიული");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_komediuri:
              bundle = new Bundle();

              bundle.putString("url", "კომედია");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_fantastika:
              bundle = new Bundle();

              bundle.putString("url", "ფანტასტიკა");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_drama:
              bundle = new Bundle();

              bundle.putString("url", "დრამა");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;

          case R.id.nav_boeviki:
              bundle = new Bundle();

              bundle.putString("url", "ბოევიკი");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_biografiuli:
              bundle = new Bundle();

              bundle.putString("url", "ბიოგრაფიული");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_mdzafrsiujetiani:
              bundle = new Bundle();

              bundle.putString("url", "მძაფრსიუჟეტიანი");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
         case R.id.nav_trileri:
              bundle = new Bundle();

              bundle.putString("url", "თრილერი");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_mistika:
              bundle = new Bundle();

              bundle.putString("url", "მისტიკური");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_kriminaluri:
              bundle = new Bundle();

              bundle.putString("url", "კრიმინალური");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;

      }






        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.main_content,fragment);
        ft.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

}
