package irakli.samniashvili.app.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import irakli.samniashvili.app.MainActivity;
import irakli.samniashvili.app.R;
import irakli.samniashvili.app.sruladActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by irakli on 12/21/2017.
 */

public class page2 extends Fragment implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    public ArrayList<MyData> M_list = new ArrayList<MyData>();
    public ArrayList<String> myarr = new ArrayList<String>();
    public ProgressDialog dialog;

    private CustomAdapter adapter;
    private GridLayoutManager gridLayoutManager;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.page2_layout, container, false );
        setHasOptionsMenu( true );
        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("იტვირთება");
        dialog.show();
        get_Data_from_server( 0 );
        recyclerView = view.findViewById( R.id.mRecycler );
        adapter = new CustomAdapter( getContext(), M_list );
        gridLayoutManager = new GridLayoutManager( getContext(), 1 );
        recyclerView.setLayoutManager( gridLayoutManager );

        recyclerView.setAdapter( adapter );

    }

    private void get_Data_from_server(int i) {
        @SuppressLint("StaticFieldLeak") AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {


            @Override
            protected Void doInBackground(Integer... integers) {
                OkHttpClient client = new OkHttpClient();
                Bundle args = getArguments();
                Request request = new Request.Builder().url( args.getString( "url" ) ).build();

                try {
                    Response response = client.newCall( request ).execute();
                    JSONArray jsonArray = new JSONArray( response.body().string() );
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject( i );
                        MyData data = new MyData( jsonObject.getString( "name" ),
                                jsonObject.getString( "photo" ),
                                jsonObject.getString( "description" ),

                                jsonObject.getString( "videoHD" ),
                                jsonObject.getString( "videoSD" ) );
                        M_list.add( data );
                        Collections.reverse( M_list );
                        dialog.dismiss();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();

            }
        };
        task.execute( i );
    }




    static class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private Context context;
        private List<MyData> my_data;

        public CustomAdapter(Context context, List<MyData> my_data) {
            this.context = context;
            this.my_data = my_data;
        }
        public void setFilter(List<MyData> countryModels) {
            my_data = new ArrayList<>();
            my_data.addAll(countryModels);
            notifyDataSetChanged();
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.one_item, parent, false );
            return new ViewHolder( itemView );
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.name.setText( my_data.get( position ).getName() );
            Picasso.with( context ).load( my_data.get( position ).getImg() ).into( holder.img );
            holder.name.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alertDialog = new AlertDialog.Builder( context ).create();
                    alertDialog.setTitle( "სათაური" );
                    alertDialog.setMessage( my_data.get( position ).getName() );
                    alertDialog.setButton( AlertDialog.BUTTON_NEUTRAL, "დახურვა",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            } );
                    alertDialog.show();
                }
            } );
            holder.desBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alertDialog = new AlertDialog.Builder( context ).create();
                    alertDialog.setTitle( "აღწერა" );
                    alertDialog.setMessage( my_data.get( position ).getdes() );
                    alertDialog.setButton( AlertDialog.BUTTON_NEUTRAL, "დახურვა",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            } );
                    alertDialog.show();
                }
            } );

            holder.sruladbtn.setOnClickListener( new View.OnClickListener() {
                JSONObject myo = new JSONObject();
                String arr[] = {my_data.get( position ).getName(),
                        my_data.get( position ).getImg(),
                        my_data.get( position ).getdes(),
                        my_data.get( position ).getVideohd(),
                        my_data.get( position ).getVideosd()};

                @Override
                public void onClick(View v) {


                        /*
                        myo.put(  "name",my_data.get(position).getName());
                        myo.put("img",my_data.get( position ).getImg());
                        myo.put("des",my_data.get( position ).getdes());
                        myo.put("videoHD",my_data.get( position ).getVideohd());
                        myo.put( "videoSD",my_data.get(position).getVideosd());
*/
                    Intent srulad = new Intent( context, sruladActivity.class );
                    srulad.putExtra( "data", arr );
                    context.startActivity( srulad );

                }
            } );


        }

        @Override
        public int getItemCount() {
            return my_data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView name;
            public ImageView img;
            public TextView des;
            private Button sruladbtn;
            private Button desBtn;

            public ViewHolder(View itemView) {
                super( itemView );
                name = itemView.findViewById( R.id.list_item );
                img = itemView.findViewById( R.id.list_img );
                sruladbtn = itemView.findViewById( R.id.list_srulad );
                desBtn = itemView.findViewById( R.id.des_btn );

            }
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
        final List<MyData> filteredModelList = filter(M_list, newText);

        adapter.setFilter(filteredModelList);
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<MyData> filter(List<MyData> models, String query) {
        query = query.toLowerCase();final List<MyData> filteredModelList = new ArrayList<>();
        for (MyData model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}