package irakli.samniashvili.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.messaging.FirebaseMessaging;

import irakli.samniashvili.app.Fragments.page2;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
private Fragment ffragment;
    private static final String TAG = "MainActivity";
    private InterstitialAd interstitial;
    private AdView mAdView;
    private Handler mHandler;       // Handler to display the ad on the UI thread
    private Runnable displayAd;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
         toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );



        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
// Prepare the Interstitial Ad
        interstitial = new InterstitialAd(MainActivity.this);
        // Insert the Ad Unit ID
        interstitial.setAdUnitId("ca-app-pub-6370427711797263/8829887578");

        //Locate the Banner Ad in activity_main.xml
        AdView adView = (AdView) this.findViewById(R.id.adView);

  adRequest = new AdRequest.Builder()

                // Add a test device to show Test Ads
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("CC5F2C72DF2B356BBF0DA198")
                .build();

        // Load ads into Banner Ads
        adView.loadAd(adRequest);

        // Load ads into Interstitial Ads
        interstitial.loadAd(adRequest);

        // Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                displayInterstitial();
            }
        });
    }
    public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }

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

        url.putString("url", "http://samniashvili.online/api.php?q=home");
        ffragment.setArguments( url );

        ft.replace(R.id.main_content,ffragment);
        ft.commit();

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

              bundle.putString("url", "http://samniashvili.online/api.php?q=home");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_animaciuri:
                 bundle = new Bundle();

              bundle.putString("url", "http://samniashvili.online/api.php?q=animaciuri");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_sashineleba:
              bundle = new Bundle();

              bundle.putString("url", "http://samniashvili.online/api.php?q=sashineleba");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_satavgadasavlo:
              bundle = new Bundle();

              bundle.putString("url", "http://samniashvili.online/api.php?q=satavgadasavlo");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_istoriuli:
              bundle = new Bundle();

              bundle.putString("url", "http://samniashvili.online/api.php?q=istoriuli");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_komediuri:
              bundle = new Bundle();

              bundle.putString("url", "http://samniashvili.online/api.php?q=komediuri");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_fantastika:
              bundle = new Bundle();

              bundle.putString("url", "http://samniashvili.online/api.php?q=fantastika");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_drama:
              bundle = new Bundle();

              bundle.putString("url", "http://samniashvili.online/api.php?q=drama");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_dokumenturi:
              bundle = new Bundle();

              bundle.putString("url", "http://samniashvili.online/api.php?q=dokumenturi");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_boeviki:
              bundle = new Bundle();

              bundle.putString("url", "http://samniashvili.online/api.php?q=boeviki");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_biografiuli:
              bundle = new Bundle();

              bundle.putString("url", "http://samniashvili.online/api.php?q=biografiuli");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_mdzafrsiujetiani:
              bundle = new Bundle();

              bundle.putString("url", "http://samniashvili.online/api.php?q=mdzafrsiujetiani");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
         case R.id.nav_trileri:
              bundle = new Bundle();

              bundle.putString("url", "http://samniashvili.online/api.php?q=trileri");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_mistika:
              bundle = new Bundle();

              bundle.putString("url", "http://samniashvili.online/api.php?q=mistika");
              fragment = new page2();
              fragment.setArguments(bundle);
              break;
          case R.id.nav_kriminaluri:
              bundle = new Bundle();

              bundle.putString("url", "http://samniashvili.online/api.php?q=kriminaluri");
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
