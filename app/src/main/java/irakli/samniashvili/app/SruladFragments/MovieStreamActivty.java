package irakli.samniashvili.app.SruladFragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import cn.jzvd.JZVideoPlayerStandard;
import irakli.samniashvili.app.R;

public class MovieStreamActivty extends AppCompatActivity {
private String myurl;

private JZVideoPlayerStandard jzVideoPlayerStandard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.activity_movie_stream_activty );

         jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);


      if(getIntent().getExtras().getBoolean( "hd" )) {
            myurl = getIntent().getExtras().getString( "datahd" );

      }else{
          myurl = getIntent().getExtras().getString( "datasd");

      }
        Log.d( "Error",myurl );
        jzVideoPlayerStandard.setUp(myurl
                , JZVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN,getIntent().getExtras().getString( "name" ));
        jzVideoPlayerStandard.backButton.setVisibility( View.GONE);




        jzVideoPlayerStandard.fullscreenButton.setVisibility( View.GONE );


    }

    public  void  onBackPressed() {
finish();
        jzVideoPlayerStandard.backButton.performClick();

Log.d("gamosvla","gamovidaaaaaaaaaaaaaaaaaa");
    }


}
