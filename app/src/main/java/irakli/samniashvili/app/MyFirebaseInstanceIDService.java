package irakli.samniashvili.app;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by irakli on 12/24/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
         String token = FirebaseInstanceId.getInstance().getToken();
         Log.d("irakliTOKEN",token);

    }
}
