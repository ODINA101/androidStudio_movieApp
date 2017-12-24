package irakli.samniashvili.app.SruladFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
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
  private FloatingActionButton playMovie;

    public details(String name,String img1, String des1,String videohd,String videosd) {
        this.name1 = name;
        this.img1 = img1;
        this.des = des1;
        this.videohd1 = videohd;
        this.videosd1 = videosd;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate( R.layout.srulad_det, container, false );


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        mName = view.findViewById( R.id.des );
        mName.setText( des );
        mImage = view.findViewById( R.id.movie_img );
        Picasso.with(getContext()).load(img1).into(mImage);

       playMovie = view.findViewById( R.id.playMovie );
       playMovie.setOnClickListener( new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               opensheet();
            /*   Intent movie = new Intent(getContext(),MovieStreamActivty.class);
               movie.putExtra("hd",videohd1);
               movie.putExtra( "sd",videosd1 );
               movie.putExtra( "name",name1 );
               startActivity( movie );
*/

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
