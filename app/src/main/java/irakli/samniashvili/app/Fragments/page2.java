package irakli.samniashvili.app.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import irakli.samniashvili.app.R;
import irakli.samniashvili.app.sruladActivity;

/**
 * Created by irakli on 12/21/2017.
 */

public class page2 extends Fragment implements SearchView.OnQueryTextListener {
    public DatabaseReference mUsersDatabase;
    public RecyclerView recyclerView;
   public Query firebaseSearchQuery;
    public ProgressDialog dialog;
    String des;
    String hd;
    String sd;
    FirebaseRecyclerAdapter<MyData, mydataViewHolder> firebaseRecyclerAdapter;
    public LinearLayoutManager mLayoutManager;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.page2_layout, container, false );
        setHasOptionsMenu( true );
        return view;
    }

    @SuppressLint("CutPasteId")
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child( "ALLmovies").child( getArguments().getString( "url" ));

        dialog = new ProgressDialog( getContext());

        dialog.setMessage( "იტვირთება" );
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById( R.id.mRecycler );
        recyclerView.setHasFixedSize( true );
        firebaseSearchQuery = mUsersDatabase;

        recyclerView.setLayoutManager(mLayoutManager );

        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);


    } 

    public void  recyclerviewRefresh() {
        FirebaseRecyclerAdapter<MyData, mydataViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MyData, mydataViewHolder>(

                MyData.class,
                R.layout.one_item,
                mydataViewHolder.class,
                firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(mydataViewHolder viewHolder, final MyData model, int position) {
                viewHolder.setName( model.getName() );
                viewHolder.setPhoto(model.getPhoto());

                viewHolder.userDesView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog alertDialog = new AlertDialog.Builder( getActivity() ).create();
                        alertDialog.setTitle( "აღწერა" );
                        alertDialog.setMessage( model.getDes() );
                        alertDialog.setButton( AlertDialog.BUTTON_NEUTRAL, "დახურვა",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                } );
                        alertDialog.show();
                    }
                } );
                viewHolder.userNameBtn.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog alertDialog = new AlertDialog.Builder( getContext() ).create();
                        alertDialog.setTitle( "სათაური" );
                        alertDialog.setMessage( model.getName() );
                        alertDialog.setButton( AlertDialog.BUTTON_NEUTRAL, "დახურვა",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                } );
                        alertDialog.show();
                    }
                } );


                viewHolder.sruladBtn.setOnClickListener( new View.OnClickListener() {
                    JSONObject myo = new JSONObject();
                    String arr[] = {model.getName(),
                            model.getPhoto(),
                            model.getDes(),
                            model.getHd(),
                            model.getSd()};

                    @Override
                    public void onClick(View v) {
                        Intent srulad = new Intent( getContext(), sruladActivity.class );
                        srulad.putExtra( "data", arr );
                        startActivity( srulad );

                    }

                } );

            }


        };

        recyclerView.setAdapter(firebaseRecyclerAdapter );
    }

    public void firebaseUserSearch(String searchText) {

        firebaseSearchQuery = mUsersDatabase.orderByChild( "name" ).startAt( searchText ).endAt( searchText + "\uf8ff" );
        recyclerviewRefresh();

    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseSearchQuery = mUsersDatabase;
        recyclerviewRefresh();


    }
     public static class mydataViewHolder extends RecyclerView.ViewHolder {
        Button userDesView;
        TextView userNameBtn;
        Button sruladBtn;
          View mView;

        public mydataViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            userDesView = mView.findViewById(R.id.des_btn);
            userNameBtn = mView.findViewById( R.id.list_item );
            sruladBtn = mView.findViewById( R.id.list_srulad);
        }


       void setName(String name) {

            TextView userNameView;
            userNameView = mView.findViewById( R.id.list_item);
            userNameView.setText(name);
        }
        void setPhoto(String photoName) {

            ImageView userImageView = mView.findViewById(R.id.list_img);
            Log.d("myphotourl",photoName);

            Picasso.with(mView.getContext()).load( Uri.parse(photoName)).placeholder( R.layout.progress_animation ).into(userImageView);
        }

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate( R.menu.search_menu, menu );

        final MenuItem item = menu.findItem( R.id.action_search );
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView( item );
        searchView.setOnQueryTextListener( this );


    }
   @Override
    public boolean onQueryTextChange(String newText) {
if(!newText.isEmpty()) {
    firebaseUserSearch( newText );
}else{
    onStart();
}
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }





}

