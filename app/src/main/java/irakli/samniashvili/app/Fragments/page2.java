package irakli.samniashvili.app.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.CardView;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import irakli.samniashvili.app.MainActivity;
import irakli.samniashvili.app.R;
import irakli.samniashvili.app.sruladActivity;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.holder.AnimateViewHolder;

/**
 * Created by irakli on 12/21/2017.
 */

public class page2 extends Fragment implements SearchView.OnQueryTextListener {
   public ProgressBar progressBar3;
    public RecyclerView recyclerView;
   public Query firebaseSearchQuery;
    public DatabaseReference mUsersDatabase;
    String des;
    String hd;
    String sd;
    public LinearLayoutManager mLayoutManager;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.page2_layout, container, false );
        setHasOptionsMenu( true );

        return view;
    }

    @SuppressLint("CutPasteId")
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated( view, savedInstanceState );


        ((MainActivity) getActivity())
                .setActionBarTitle(getArguments().getString( "url" ));


        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child( "ALLmovies").child( getArguments().getString( "url" ));
        progressBar3 = view.findViewById( R.id.progressBar3 );
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById( R.id.mRecycler );
        recyclerView.setHasFixedSize( true );
        firebaseSearchQuery = mUsersDatabase;

        recyclerView.setLayoutManager(mLayoutManager );

        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerviewRefresh();


    }

    public void  recyclerviewRefresh() {
        FirebaseRecyclerOptions<MyData> options = new FirebaseRecyclerOptions.Builder<MyData>()
                .setQuery(firebaseSearchQuery,MyData.class)
                .build();

        FirebaseRecyclerAdapter<MyData, mydataViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MyData, mydataViewHolder>(
 options
           /*     MyData.class,
               ,
                mydataViewHolder.class,
                firebaseSearchQuery*/
        ) {
            @NonNull
            @Override
            public mydataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = getLayoutInflater().inflate( R.layout.one_item, parent,false);
                return new mydataViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull mydataViewHolder viewHolder, int position, @NonNull final MyData model) {
                viewHolder.setName( model.getName() );
                viewHolder.setPhoto(model.getPhoto());
                progressBar3.setVisibility( View.GONE );



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
                        //    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation( (Activity) viewHolder.mView.getContext(),viewHolder.userImageView,"mIMG" );
                        srulad.putExtra( "data", arr );
                        startActivity( srulad  );

                    }

                } );
            }




        };
        SlideInBottomAnimationAdapter alphaAdapter = new SlideInBottomAnimationAdapter(firebaseRecyclerAdapter);
        alphaAdapter.setFirstOnly(false);
        recyclerView.setAdapter(alphaAdapter);
        firebaseRecyclerAdapter.startListening();
        recyclerView.setItemAnimator(new SlideInLeftAnimator());

    }

    public void firebaseUserSearch(String searchText) {

        firebaseSearchQuery = mUsersDatabase.orderByChild( "name" ).startAt( searchText ).endAt( searchText + "\uf8ff" );
        recyclerviewRefresh();

    }


     public static class mydataViewHolder extends RecyclerView.ViewHolder implements AnimateViewHolder {
        TextView userNameBtn;
       CardView sruladBtn;
          View mView;
          ProgressBar progressBar;
         ImageView userImageView;

        public mydataViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
           progressBar = itemView.findViewById( R.id.progressBar2 );
            userNameBtn = mView.findViewById( R.id.list_item );
            sruladBtn = mView.findViewById( R.id.cardView22 );
            userImageView  = mView.findViewById( R.id.list_img );
        }


       void setName(String name) {

            TextView userNameView;
            userNameView = mView.findViewById( R.id.list_item);
            userNameView.setText(name);
        }
        void setPhoto(String photoName) {

            Log.d("myphotourl",photoName);

            Picasso.get().load( Uri.parse(photoName)).placeholder( R.drawable.white).into( userImageView, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility( View.GONE );
                }

                @Override
                public void onError(Exception e) {

                }

            } );
        }

         @Override
         public void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
             ViewCompat.setTranslationY(itemView, -itemView.getHeight() * 0.3f);
             ViewCompat.setAlpha(itemView, 0);
         }

         @Override
         public void preAnimateRemoveImpl(RecyclerView.ViewHolder holder) {

         }

         @Override
         public void animateAddImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {
             ViewCompat.animate(itemView)
                     .translationY(0)
                     .alpha(1)
                     .setDuration(300)
                     .setListener(listener)
                     .start();
         }

         @Override
         public void animateRemoveImpl(RecyclerView.ViewHolder holder, ViewPropertyAnimatorListener listener) {

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
    firebaseSearchQuery = mUsersDatabase;
    recyclerviewRefresh();

}
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }





}

