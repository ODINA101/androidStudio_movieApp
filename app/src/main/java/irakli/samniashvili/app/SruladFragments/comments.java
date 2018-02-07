package irakli.samniashvili.app.SruladFragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import irakli.samniashvili.app.R;

/**
 * Created by irakli on 12/23/2017.
 */

@SuppressLint("ValidFragment")
public class comments extends Fragment {
    private FirebaseAuth mAuth;
    private String TAG = "faceboookSDK";
    private CallbackManager mCallbackManager;
    private LinearLayoutManager mLayoutManager;
    private LoginButton loginButton;
    TextView commentText;
    AccessTokenTracker accessTokenTracker;
    Button publish;
    DatabaseReference database;
    DatabaseReference myRef;
    private String movie;
    ProgressDialog progressDialog;
    private DatabaseReference mUsersDatabase;
    private RecyclerView mUsersList;
    private FirebaseDatabase database2;
    private String necom;
    @SuppressLint("ValidFragment")
    public comments(String movie) {
        this.movie = movie;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.comments, container, false );


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        FacebookSdk.sdkInitialize(getContext());
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = view.findViewById( R.id.login_button );
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.setFragment( this );
        progressDialog.setTitle( "მიმდინარეობს ინფორმაციის დამუშავება" );
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("movies").child(movie);
        mUsersList = view.findViewById(R.id.movies_list);
        mUsersList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager( getContext() );
        mUsersList.setLayoutManager(mLayoutManager);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd( true );
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    commentText.setVisibility( View.GONE );
                    publish.setVisibility( View.GONE );
                    LoginManager.getInstance().logOut();

                }
            }
        };

        LoginManager.getInstance().registerCallback( mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d( TAG, "facebook:onSuccess:" + loginResult );
                        handleFacebookAccessToken( loginResult.getAccessToken() );
                    }

                    @Override
                    public void onCancel() {
                        Log.d( TAG, "facebook:onCancel" );
                        LoginManager.getInstance().logOut();
                        updateUI( null );

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d( TAG, "facebook:onError", error );
                        // ...
                    }
                } );
            }


    @SuppressWarnings("ConstantConditions")
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Task<AuthResult> authResultTask = mAuth.signInWithCredential( credential )
                .addOnCompleteListener( getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d( TAG, "signInWithCredential:success" );
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI( user );
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w( TAG, "signInWithCredential:failure", task.getException() );
                            Toast.makeText( getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT ).show();
                            updateUI( null );
                        }

                        // ...
                    }
                } );
    }


    public void updateUI(final FirebaseUser user) {
        commentText = getView().findViewById(R.id.comment);
        publish = getView().findViewById( R.id.publish );
        if(user != null) {

           commentText.setVisibility( View.VISIBLE );
           publish.setOnClickListener( new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   progressDialog.show();
                   necom = commentText.getText().toString();
                    database = FirebaseDatabase.getInstance().getReference().child("movies").child(movie);

                   HashMap<String,String> dataMap = new HashMap<>();
                   dataMap.put("name",user.getDisplayName());
                   dataMap.put("photo",user.getPhotoUrl().toString());
                   dataMap.put("comment",necom);

                 database.push().setValue(dataMap);
                  commentText.setText( null );
                   progressDialog.dismiss();














               }
           } );

           commentText.addTextChangedListener( new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {

               }

               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {
                   check();

               }

               private void check() {
                   if(commentText.length() != 0) {
                   publish.setVisibility( View.VISIBLE );

                   }else{
                       publish.setVisibility( View.GONE );


                   }
               }

               @Override
               public void afterTextChanged(Editable s) {

               }

           } );


        }else {
            commentText.setVisibility( View.GONE);
            publish.setVisibility( View.GONE );


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

        @Override
        public void onStart() {
            super.onStart();
            FirebaseRecyclerAdapter<movies,moviesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<movies,moviesViewHolder>(
                    movies.class,
                    R.layout.single_comment,
                    moviesViewHolder.class,
                    mUsersDatabase
            ) {

                @Override
                public void populateViewHolder(moviesViewHolder moviesViewHolder, movies movies, int i) {
                    moviesViewHolder.setName(movies.getName());
                    moviesViewHolder.setphoto( movies.getPhoto() );
                    moviesViewHolder.setComment( movies.getComment() );



                }

            };
            mUsersList.setAdapter(firebaseRecyclerAdapter);

            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        }


    public static class moviesViewHolder extends RecyclerView.ViewHolder {
        View mView;
        Context context;

        public moviesViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        void setName(String name) {
            TextView userNameView = mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);


        }
        void setComment(String name) {
            TextView userNameView1 = mView.findViewById(R.id.user_single_comment);
            userNameView1.setText(name);


        }
        void setphoto(String url) {
            CircleImageView userImageView = mView.findViewById( R.id.user_single_image );
            Picasso.with(context).load(url).into(userImageView);
        }
    }



}
