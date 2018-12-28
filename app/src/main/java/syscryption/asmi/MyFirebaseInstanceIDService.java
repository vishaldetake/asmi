package syscryption.asmi;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Vishal on 10/7/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static  final String TAG="MyFirebaseInstanceIDService";

    @Override
    public void onTokenRefresh() {

        String refreshedToken= FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"New Token:"+refreshedToken);

       // super.onTokenRefresh();
    }
}
