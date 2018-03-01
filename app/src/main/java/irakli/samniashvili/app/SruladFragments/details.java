package irakli.samniashvili.app.SruladFragments;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import irakli.samniashvili.app.PLayer2Activity;
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


                            downloadbtn.setOnClickListener( new View.OnClickListener() {
                               @Override
                              public void onClick(View v) {
                                   new Downloader().execute(videohd1 );
                                }
                            } );

                        }else {
                            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
                            long bytesAvailable;
                            if (android.os.Build.VERSION.SDK_INT >=
                                    android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                                bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
                            }
                            else {
                                bytesAvailable = (long)stat.getBlockSize() * (long)stat.getAvailableBlocks();
                            }
                            long megAvailable = bytesAvailable / (1024 * 1024);

                            if(megAvailable < 1500) {
                                Log.e("irakli", "Available MB : " + megAvailable);
                            }else {
                                new Downloader().execute(videosd1);
                            }


                         }
                       return false;

                    }
                 }).show();



    }


    public class Downloader extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mBuilder.setProgress(100, 0, false);
            mNotifyManager.notify(id, mBuilder.build());

        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;

            try {
                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)  + name1 + ".mp4");

                byte data[] = new byte[8192];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {}
            return null;

        }
        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC",progress[0]);
            mBuilder.setProgress(100, Integer.parseInt(progress[0]), false);
            mNotifyManager.notify(id, mBuilder.build());
        }

        @Override
        protected void onPostExecute(String unused) {
            mBuilder.setContentText("ფილმის გადმოწერა დასრულდა");
            // Removes the progress bar
            mBuilder.setProgress(0, 0, false);
            mNotifyManager.notify(id, mBuilder.build());
        }
    }



    public void downloader(String url) {
        downloadManager = (DownloadManager) getContext().getSystemService( Context.DOWNLOAD_SERVICE);
        Uri uri  = Uri.parse( url );

        DownloadManager.Request request = new DownloadManager.Request( uri );
        Long reference = downloadManager.enqueue( request );

        request.setNotificationVisibility( DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED );
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