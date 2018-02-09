package irakli.samniashvili.app.SruladFragments;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import irakli.samniashvili.app.R;
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
    public FloatingActionButton downloadbtn;
public DownloadManager downloadManager;
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
        downloadbtn = view.findViewById( R.id.download );


        downloadbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensheet2();
            }
        } );


        final View parentV = getView();

        playMovie = parentV.findViewById( R.id.playMovie );
        playMovie.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensheet();
            }



        });
 }
    public void opensheet2() {



        SheetMenu.with(getContext())
                .setTitle( "გადმოწერა" )
                .setMenu(R.menu.sheet_menu2)
                .setAutoCancel(false)
                .setClick(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.magali) {


                            downloadbtn.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                   downloader( videohd1 );

                                }
                            } );

                        }else {

                            downloader( videosd1 );



                        }
                        return false;

                    }
                }).show();
    }

    public void downloader(String url) {
        downloadManager = (DownloadManager) getContext().getSystemService( Context.DOWNLOAD_SERVICE);
        Uri uri  = Uri.parse( url );

        DownloadManager.Request request = new DownloadManager.Request( uri );
        Long reference = downloadManager.enqueue( request );

        request.setNotificationVisibility( DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED );
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