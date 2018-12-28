package syscryption.asmi;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class FcmMessagingService extends FirebaseMessagingService {
    String type="";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData().size()>0){
            type="json";

            sendNotification(remoteMessage.getData().toString());
        }
        if(remoteMessage.getData() !=null){
            type="message";
            sendNotification(remoteMessage.getNotification().getBody());
        }

        //super.onMessageReceived(remoteMessage);

    }

    private void sendNotification(String messageBody) {
        String id="",message="",title="";

        if(type.equals("json")) {
            try {
                JSONObject jsonobject = new JSONObject(messageBody);
                id = jsonobject.getString("id");
                message = jsonobject.getString("message");
                title = jsonobject.getString("title");

            } catch (JSONException e) {
                //e.printStackTrace();
            }
        }
            else if(type.equals("message")){
                message=messageBody;
            }

            Intent intent=new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent PendindIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this);
            notificationBuilder.setContentTitle(getString(R.string.app_name));
            notificationBuilder.setContentText(message);
            Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationBuilder.setSound(soundUri);
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
            notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.mipmap.ic_launcher));
            notificationBuilder.setAutoCancel(true);
            Vibrator v=(Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(1000);
            notificationBuilder.setContentIntent(PendindIntent);
            NotificationManager notificationmanager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationmanager.notify(0,notificationBuilder.build());


        }



    }

