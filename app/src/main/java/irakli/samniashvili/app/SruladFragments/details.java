package irakli.samniashvili.app.SruladFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import irakli.samniashvili.app.PLayer2Activity;
import irakli.samniashvili.app.R;
import okhttp3.Request;
import ru.whalemare.sheetmenu.SheetMenu;

/**
 * Created by irakli on 12/23/2017.
 */

@SuppressLint("ValidFragment")
public class details extends Fragment {
    private long enqueue;


    private DownloadManager dm;
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
    float megAvailable;
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
        Picasso.get( ).load( img1 ).into( mImage );
        downloadbtn = view.findViewById( R.id.download );

        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());

        File path = Environment.getDataDirectory();
        StatFs stat2 = new StatFs(path.getPath());
        long blockSize = stat2.getBlockSize();
        long availableBlocks = stat2.getAvailableBlocks();


System.out.print(Formatter.formatFileSize(getContext(), availableBlocks * blockSize));

       String gbs = Formatter.formatFileSize(getContext(), availableBlocks * blockSize).replace("GB", "");

       if(gbs.contains("MB")) {

          gbs = gbs.replace("MB","");
       }
  if(gbs.contains(",")) {
      megAvailable = Float.parseFloat(gbs.replace(",","."));

  }else{
      megAvailable = Float.parseFloat(gbs);

  }

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
                opensheet3();
            }



        });
 }
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    int id = 1;

    public void opensheet2() {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mNotifyManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(getContext());
   Intent mov = new Intent(getContext(),MovieStreamActivty.class);
        mov.putExtra("datasd",Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)  + name1 + ".mp4");
        mov.putExtra( "name",name1 );
        mov.putExtra( "hd",false );
        @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity(
                getContext(),
                0,
                mov
                ,
                Intent.FLAG_ACTIVITY_NEW_TASK);


        mBuilder.setContentTitle(name1)
                .setContentText("მიმდინარეობს გადმოწერა")
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_file_download);


        SheetMenu.with(getContext())
                .setTitle( "გადმოწერა" )
               .setMenu(R.menu.sheet_menu2)
                .setAutoCancel(false)
                .setClick(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.magali) {


                            if( megAvailable > 1.50) {

    dm = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
    final DownloadManager.Request request = new DownloadManager.Request(
            Uri.parse(videohd1));

    enqueue = dm.enqueue(request);
    request.setShowRunningNotification(true);
    request.setDestinationInExternalPublicDir(Environment.getExternalStorageDirectory().getAbsolutePath(), "Download");
    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            Log.e("INSIDE", "" + referenceId);
            PendingIntent intent2 = PendingIntent.getActivity(getContext(), 0,
                    new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS), 0);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(getContext())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(name1)
                            .setContentIntent(intent2)
                            .setContentText("გადმოწერა წარმატებით დასრულდა");

            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify((int) referenceId, mBuilder.build());

        }
    };
    getActivity().registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
}else {
                                /////////////////////


                                new LovelyInfoDialog(getContext())
                                        .setTopColorRes(R.color.common_google_signin_btn_text_dark_default)
                                        .setIcon(R.drawable.ic_error_black_24dp)
                                        //This will add Don't show again checkbox to the dialog. You can pass any ID as argument

                                        .setTitle("მოხდა შეცდომა")
                                        .setMessage("თქვენ არ გაქვთ საკმარისი მეხსიერება")
                                        .create()
                                        .show();




                            }
                        }else {
                          if( megAvailable > 1.50) {


                              dm = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                              final DownloadManager.Request request = new DownloadManager.Request(
                                      Uri.parse(videosd1));

                              enqueue = dm.enqueue(request);
                              request.setShowRunningNotification(true);
                              request.setDestinationInExternalPublicDir(Environment.getExternalStorageDirectory().getAbsolutePath(), "Download");
                              BroadcastReceiver onComplete = new BroadcastReceiver() {
                                  public void onReceive(Context ctxt, Intent intent) {
                                      long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                                      Log.e("INSIDE", "" + referenceId);
                                      PendingIntent intent2 = PendingIntent.getActivity(getContext(), 0,
                                              new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS), 0);
                                      NotificationCompat.Builder mBuilder =
                                              new NotificationCompat.Builder(getContext())
                                                      .setSmallIcon(R.mipmap.ic_launcher)
                                                      .setContentTitle(name1)
                                                      .setContentIntent(intent2)
                                                      .setContentText("გადმოწერა წარმატებით დასრულდა");

                                      NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                                      notificationManager.notify((int) referenceId, mBuilder.build());

                                  }
                              };
                              getActivity().registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


                          }else{

                              new LovelyInfoDialog(getContext())
                                      .setTopColorRes(R.color.common_google_signin_btn_text_dark_default)
                                      .setIcon(R.drawable.ic_error_black_24dp)
                                      //This will add Don't show again checkbox to the dialog. You can pass any ID as argument

                                      .setTitle("მოხდა შეცდომა")
                                      .setMessage("თქვენ არ გაქვთ საკმარისი მეხსიერება")
                                      .create()
                                      .show();


                          }



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
       // getActivity().registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void opensheet(final Intent movi, String type) {



        SheetMenu.with(getContext())
                .setTitle( "ხარისხი" )
                .setMenu(R.menu.sheet_menu)
                .setAutoCancel(false)
                .setClick(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.magali) {


                            movi.putExtra("datahd",videohd1);
                            movi.putExtra( "name",name1 );
                            movi.putExtra( "hd",true );
                            startActivity( movi );


                        }else {

                            movi.putExtra("datasd",videosd1);
                            movi.putExtra( "name",name1 );
                            movi.putExtra( "hd",false );
                            startActivity( movi );

                        }
                        return false;

                    }
                }).show();
    }


    public void opensheet3() {
        SheetMenu.with(getContext())
                .setTitle( "player" )
                .setMenu(R.menu.sheet_menu3)
                .setAutoCancel(false)
                .setClick(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.magali) {



                            Intent movie = new Intent(getContext(),PLayer2Activity.class);


                            opensheet(movie,"new");
                        }else {
                            Intent movie2 = new Intent(getContext(),MovieStreamActivty.class);


                            opensheet(movie2,"old");


                        }
                        return false;

                    }
                }).show();
    }

}