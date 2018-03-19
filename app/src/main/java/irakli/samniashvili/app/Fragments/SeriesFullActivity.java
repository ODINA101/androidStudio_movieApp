package irakli.samniashvili.app.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import irakli.samniashvili.app.MainActivity;
import irakli.samniashvili.app.R;
import irakli.samniashvili.app.SruladFragments.MovieStreamActivty;

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



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



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

        Picasso.get().load(getIntent().getExtras().getString("photo")).into((ImageView) findViewById(R.id.serialPhoto));
        TextView des;
        des = findViewById(R.id.des);
        des.setText(getIntent().getExtras().getString("des"));




    }



        public void refreshData(String seson) {
            FirebaseRecyclerOptions<seriesModel2> options = new FirebaseRecyclerOptions.Builder<seriesModel2>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("series").child(getIntent().getExtras().getString("ref")).child("parts").child(seson),seriesModel2.class)
                    .build();
            FirebaseRecyclerAdapter<seriesModel2, seriesModelView> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<seriesModel2, seriesModelView>(
options

                    //                    seriesModel2.class,
//                    R.layout.single_serie,
//                    seriesModelView.class,
//                    FirebaseDatabase.getInstance().getReference().child("series").child(getIntent().getExtras().getString("ref")).child("parts").child(seson)

            ) {
                @NonNull
                @Override
                public seriesModelView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = getLayoutInflater().inflate(R.layout.single_serie, parent,false);
                    return new seriesModelView(view);
                }

                @Override
                protected void onBindViewHolder(@NonNull final seriesModelView holder, final int position, @NonNull final seriesModel2 model) {
                    holder.serieNum.setText("სერია " + (position+1));
                    holder.cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent movie2 = new Intent(holder.view.getContext(),MovieStreamActivty.class);
                            movie2 .putExtra("datasd",model.getSd());
                            movie2 .putExtra( "name",  "სერია" + (position+1));
                            movie2 .putExtra( "hd",false );
                            startActivity( movie2 );

                        }
                    });

                }


            };
        RecyclerView recyclerView = findViewById(R.id.myRecycler);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("name"));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);

            recyclerView.setAdapter(firebaseRecyclerAdapter);
            firebaseRecyclerAdapter.startListening();

        }

    public static class seriesModelView extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView serieNum;
        View view;

        public seriesModelView(View itemView) {
            super(itemView);
            view = itemView;
            cardView = itemView.findViewById(R.id.serieCard);
            serieNum = itemView.findViewById(R.id.serienum);


        }
    }
    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:



                finish();
                break;

        }
        return true;

    }
    }





