package irakli.samniashvili.app.SruladFragments;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;

import irakli.samniashvili.app.R;
import me.toptas.fancyshowcase.FancyShowCaseView;
import ru.whalemare.sheetmenu.SheetMenu;

/**
 * Created by irakli on 12/23/2017.
 */

@SuppressLint("ValidFragment")
public class details extends Fragment {

    public TextView mName;
    public ImageView mImage;
    private String getDetails1;
    private String name1;
    private String img1;
    private String des;
    private String videohd1;
    private String videosd1;
    private InterstitialAd interstitial;

    private FloatingActionButton playMovie;

    public details(String name, String img1, String des1, String videohd, String videosd) {
        this.name1 = name;
        this.img1 = img1;
        this.des = des1;
        this.videohd1 = videohd;
        this.videosd1 = videosd;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.srulad_det, container, false );


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        mName = view.findViewById( R.id.des );
        mName.setText( des );
        mImage = view.findViewById( R.id.movie_img );
        Picasso.with( getContext() ).load( img1 ).into( mImage );
        interstitial = new InterstitialAd( getContext() );
        interstitial.setAdUnitId( "ca-app-pub-6370427711797263/8829887578" );

        AdRequest adRequest = new AdRequest.Builder()

                // Add a test device to show Test Ads
                .addTestDevice( AdRequest.DEVICE_ID_EMULATOR )
                .addTestDevice( "CC5F2C72DF2B356BBF0DA198" )
                .build();

        // Load ads into Banner Ads

        // Load ads into Interstitial Ads
        interstitial.loadAd( adRequest );

        // Prepare an Interstitial Ad Listener
        interstitial.setAdListener( new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                displayInterstitial();
            }
        } );
    }

    public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }

      final View parentV = getView();

        playMovie = getView().findViewById( R.id.playMovie );
        playMovie.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensheet();
            }
        } );


    }

    public void opensheet() {



        SheetMenu.with(getContext())
                .setTitle( "ხარისხი" )
                .setMenu(R.menu.sheet_menu)
                .setAutoCancel(false)
                .setClick(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.magali) {
                               Intent movie = new Intent(getContext(),MovieStreamActivty.class);
                            movie.putExtra("datahd",videohd1);
                            movie.putExtra( "name",name1 );
                            movie.putExtra( "hd",true );

                            startActivity( movie );

                        }else {
                            Intent movie2 = new Intent(getContext(),MovieStreamActivty.class);
                            movie2.putExtra("datasd",videosd1);
                            movie2.putExtra( "name",name1 );
                            movie2.putExtra( "hd",false );

                            startActivity( movie2 );

                        }
                        return false;

                    }
                }).show();
    }

}
