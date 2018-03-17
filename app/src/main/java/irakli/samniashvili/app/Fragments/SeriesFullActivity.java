package irakli.samniashvili.app.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import irakli.samniashvili.app.MainActivity;
import irakli.samniashvili.app.R;

public class SeriesFullActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_full);

        String seson = "seson1";
        final List<String> spinnerArray = new ArrayList<String>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sItems = (Spinner) findViewById(R.id.spinner);
        sItems.setAdapter(adapter);


        FirebaseDatabase.getInstance().getReference().child("series").child(getIntent().getExtras().getString("ref")).child("parts").addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        spinnerArray.clear();

        for(int i=0;i<dataSnapshot.getChildrenCount();i++) {
            spinnerArray.add("სეზონი " + (i+1));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});



   sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
       @Override
       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

           refreshData(sItems.getSelectedItem().toString());
       }

       @Override
       public void onNothingSelected(AdapterView<?> parent) {

       }
   });

        Picasso.with(this).load(getIntent().getExtras().getString("photo")).into((ImageView) findViewById(R.id.serialPhoto));
        TextView des;
        des = findViewById(R.id.des);
        des.setText(getIntent().getExtras().getString("des"));




    }



        public void refreshData(String seson) {
            FirebaseRecyclerAdapter<seriesModel2, seriesModelView> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<seriesModel2, seriesModelView>(
                    seriesModel2.class,
                    R.layout.single_serie,
                    seriesModelView.class,
                    FirebaseDatabase.getInstance().getReference().child("series").child(getIntent().getExtras().getString("ref")).child("parts").child(seson)

            ) {
                @SuppressLint("SetTextI18n")
                @Override
                protected void populateViewHolder(seriesModelView viewHolder, seriesModel2 model, int position) {

                    viewHolder.serieNum.setText(getRef(position).getKey());

                }
            };
        RecyclerView recyclerView = findViewById(R.id.myRecycler);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("name"));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);

        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
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





