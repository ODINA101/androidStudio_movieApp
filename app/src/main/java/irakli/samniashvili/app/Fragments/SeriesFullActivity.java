package irakli.samniashvili.app.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import irakli.samniashvili.app.MainActivity;
import irakli.samniashvili.app.R;

public class SeriesFullActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_full);
        RecyclerView recyclerView =  findViewById(R.id.myRecycler);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
       getSupportActionBar().setTitle(getIntent().getExtras().getString("name"));
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager(mLayoutManager );

        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        Picasso.with(this).load(getIntent().getExtras().getString("photo")).into((ImageView) findViewById(R.id.serialPhoto));
        TextView des;
        des = findViewById(R.id.des);
        des.setText(getIntent().getExtras().getString("des"));


        FirebaseRecyclerAdapter<seriesModel2,seriesModelView> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<seriesModel2, seriesModelView>(
                seriesModel2.class,
                R.layout.single_serie,
                seriesModelView.class,
                FirebaseDatabase.getInstance().getReference().child("series").child(getIntent().getExtras().getString("ref")).child("parts")

        ) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void populateViewHolder(seriesModelView viewHolder, seriesModel2 model, int position) {

                viewHolder.serieNum.setText("სერია #" + (position+1));

            }
        };
    recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


 public static class seriesModelView extends RecyclerView.ViewHolder {
     CardView cardView;
TextView serieNum;
        public seriesModelView(View itemView) {
         super(itemView);
       cardView = itemView.findViewById(R.id.serieCard);
serieNum = itemView.findViewById(R.id.serienum);


      }





 }

}
