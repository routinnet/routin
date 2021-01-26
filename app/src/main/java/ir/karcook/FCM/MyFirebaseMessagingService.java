package ir.karcook.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import ir.karcook.MainActivity;
import ir.karcook.R;
import ir.karcook.Tools.Shared_Prefrences;
import ir.karcook.ViewPresenter.SecondActivity.SecondActivity;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onNewToken(String token) {

        SharedPreferences.Editor editor = Shared_Prefrences.getInstance(getApplicationContext()).getSp().edit();
        editor.putString(getString(R.string.fcm_token), token);
        editor.apply();


    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        try {
            Log.e("ffffff" , remoteMessage.getData().toString());
            JSONObject jsonObject = new JSONObject(remoteMessage.getData());
            sendNotification(jsonObject.getString("title"),
                    jsonObject.getString("message"),
                    jsonObject.getInt("course"),
                    jsonObject.getInt("coursePackage"));


//            Intent intent = new Intent("pushReceived");
//            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void sendNotification(String title, String messageBody ,int course , int coursePackage) {


        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("isPush", true);
        intent.putExtra("course", course);
        intent.putExtra("coursePackage", coursePackage);
//        startActivity(intent);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0
                //   Request code
                , intent,
                PendingIntent.FLAG_ONE_SHOT);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "myChannel")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setWhen(System.currentTimeMillis())
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);




        Random random = new Random();

        notificationManager.notify(random.nextInt()/*ID of notification*/, notificationBuilder.build());

    }

}
