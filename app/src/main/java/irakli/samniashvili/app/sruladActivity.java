package irakli.samniashvili.app;

import android.app.job.JobInfo;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import irakli.samniashvili.app.SruladFragments.comments;
import irakli.samniashvili.app.SruladFragments.details;

public class sruladActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private InterstitialAd interstitial;
    private static final String TAG = "MainActivity";
    private JobInfo mJob;
    private SectionsPageAdapter mSectionsPageAdapter;
   private String dat[];
    private ViewPager mViewPager;

    /*
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_srulad );
        dat =  getIntent().getExtras().getStringArray("data");


/*
        Bundle bundleee = new Bundle();
        bundleee.putString("name", dat[0]);
        bundleee.putString("img", dat[1]);
        bundleee.putString("des", dat[2]);
        bundleee.putString("videoHD", dat[3]);
        bundleee.putString("videoSD", dat[4]);


        Fragment fragobj= new details();
        fragobj.setArguments(bundleee);

*/


        Bundle bundle = new Bundle();
        bundle.putString("params", "My String data");
// set MyFragment Arguments
        details myObj = new details(dat[0],dat[1],dat[2],dat[3],dat[4]);


        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle( dat[0] );

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new details(dat[0],dat[1],dat[2],dat[3],dat[4]), "ფილმი");
        adapter.addFragment(new comments(), "კომენტარები");

        viewPager.setAdapter(adapter);
        interstitial = new InterstitialAd(this);
        // Insert the Ad Unit ID
        interstitial.setAdUnitId("ca-app-pub-6370427711797263/8829887578");

        //Locate the Banner Ad in activity_main.xml
        AdRequest adRequest = new AdRequest.Builder().build();
        adRequest = new AdRequest.Builder()

                // Add a test device to show Test Ads
                .addTestDevice( AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("CC5F2C72DF2B356BBF0DA198")
                .build();

        // Load ads into Banner Ads

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

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                onBackPressed();

                finish();
                return true;

            default:
                return super.onOptionsItemSelected( item );
        }
    }





    }



