package irakli.samniashvili.app.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import irakli.samniashvili.app.MainActivity;
import irakli.samniashvili.app.R;

/**
 * Created by root on 3/16/18.
 */

public class series extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.series,container,false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity())
                .setActionBarTitle("სერიალები");

        RecyclerView recyclerView = view.findViewById(R.id.seriesRecycler);
       LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize( true );

        recyclerView.setLayoutManager(mLayoutManager );

        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        FirebaseRecyclerOptions<seriesModel> options = new FirebaseRecyclerOptions.Builder<seriesModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("series"),seriesModel.class)
                .build();
        FirebaseRecyclerAdapter<seriesModel,seriesModelHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<seriesModel, seriesModelHolder>(
 options


                //                seriesModel.class,
//                R.layout.one_item,
//                seriesModelHolder.class,
//                FirebaseDatabase.getInstance().getReference().child("series")


        ) {
            @NonNull
            @Override
            public seriesModelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = getLayoutInflater().inflate(R.layout.one_item, parent,false);
                return new seriesModelHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull seriesModelHolder viewHolder, final int position, @NonNull final seriesModel model) {
                viewHolder.name.setText(model.getName());
                viewHolder.setPhoto(model.getPhoto());
                viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent srulad = new Intent(view.getContext(),SeriesFullActivity.class);
                        srulad.putExtra("ref",getRef(position).getKey());
                        srulad.putExtra("photo",model.getPhoto());
                        srulad.putExtra("des",model.getDes());
                        srulad.putExtra("name",model.getName());

                        startActivity(srulad);
                    }
                });
            }


        };
recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }



    public static class seriesModelHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView name;
        ImageView photo;
        ProgressBar progressBar;
        public seriesModelHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar2);
            cardView = itemView.findViewById(R.id.cardView22);
            name = itemView.findViewById(R.id.list_item);
            photo = itemView.findViewById(R.id.list_img);

        }

        public void setPhoto(String data) {
            Picasso.get().load(data).into(photo, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }


            });
        }



    }

}
