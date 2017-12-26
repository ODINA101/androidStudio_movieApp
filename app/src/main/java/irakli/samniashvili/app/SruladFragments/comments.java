package irakli.samniashvili.app.SruladFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.concurrent.Executor;

import irakli.samniashvili.app.FacebookLoginActivity;
import irakli.samniashvili.app.MainActivity;
import irakli.samniashvili.app.R;

/**
 * Created by irakli on 12/23/2017.
 */

public class comments extends Fragment {
    private FirebaseAuth mAuth;
    private String TAG = "faceboookSDK";
    private CallbackManager mCallbackManager;
    private LoginButton loginButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.comments, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = view.findViewById( R.id.login_button1 );
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.setFragment( this );
        LoginManager.getInstance().registerCallback( mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d( TAG, "facebook:onSuccess:" + loginResult );
                        handleFacebookAccessToken( loginResult.getAccessToken() );
                    }

                    @Override
                    public void onCancel() {
                        Log.d( TAG, "facebook:onCancel" );
                        // ...
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


    public void updateUI(FirebaseUser user) {
        if(user != null) {
            Toast.makeText( getContext(),"შესულიხარ ",Toast.LENGTH_LONG).show();
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
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        }







}
