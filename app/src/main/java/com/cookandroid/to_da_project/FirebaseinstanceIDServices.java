package com.cookandroid.to_da_project;

import android.util.Log;




public class FirebaseinstanceIDServices extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseIIDService";



    @Override
    public void onNewToken(String s) {

        super.onNewToken(s);
        Log.e("NEW_TOKEN",s);

        sendRegistrationToServer(s);

    }

    private void sendRegistrationToServer (String token){

    }
}
