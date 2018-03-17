package irakli.samniashvili.app;

import android.app.job.JobInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import irakli.samniashvili.app.SruladFragments.comments;
import irakli.samniashvili.app.SruladFragments.details;

public class sruladActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    private Toolbar toolbar;
    private static final String TAG = "MainActivity";

    private JobInfo mJob;
    private SectionsPageAdapter mSectionsPageAdapter;
   private String dat[];
    private ViewPager mViewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_srulad );
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6370427711797263/8829887578");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        dat =  getIntent().getExtras().getStringArray("data");

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {

        }


        Bundle bundle = new Bundle();
        bundle.putString("params", "My String data");
        details myObj = new details(dat[0],dat[1],dat[2],dat[3],dat[4]);
       comments comments = new comments( dat[0] );

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );



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
        SectionsPageAdapter adapter = new SectionsPageAdapter( getSupportFragmentManager() );
        adapter.addFragment( new details( dat[0], dat[1], dat[2], dat[3], dat[4] ), "ფილმი" );
        adapter.addFragment( new comments( dat[0] ), "კომენტარები" );

        viewPager.setAdapter( adapter );

    }
    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:



                finish();
                break;

        }
        return true;

    }





    }



