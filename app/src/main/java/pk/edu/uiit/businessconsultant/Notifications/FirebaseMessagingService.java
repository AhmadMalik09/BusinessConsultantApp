package pk.edu.uiit.businessconsultant.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import pk.edu.uiit.businessconsultant.Activites.Chat;
import pk.edu.uiit.businessconsultant.R;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String NOTIFICATION_CHANNEL_ID = "MY_NOTIFICATION_CHANNEL_ID"; // Required For Android 0 or above

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        // All notifications will be received here

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // Get Data From Notification

        if (message.getData().size()>0){
            String Title = message.getData().get("title");
            String Message = message.getData().get("message");
            String hisID = message.getData().get("hisID");
            String chatID = message.getData().get("chatID");

            if (firebaseUser !=null){
                // User Is Signed In And Is Same User To Which Notification Is Sent
                showNotification(Title,Message, hisID, chatID);
            }
        }
    }
    @Override
    public void onNewToken(@NonNull String s) {
        updateToken(s);
        super.onNewToken(s);
    }

    private void updateToken(String token) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid());
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            databaseReference.updateChildren(map);
        }

    }

    private void showNotification(String Title, String Message, String hisID, String chatID) {
        // Notifications
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Id for Notification, Random
        int notificationID = new Random().nextInt(3000);

        // Check If Android Version Is Oreo/O or Above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            setupNotificationChannel(notificationManager);
        }

        // Handle Notification click, Start Order Activity
        Intent intent = null;
            // Open Chat Activity
            intent = new Intent(this, Chat.class);
             intent.putExtra("chatID", chatID);
             intent.putExtra("hisID", hisID);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // Large Icon
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_baseline_notifications_active_24);

        // Sound Of Notification
        Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setLargeIcon(largeIcon)
                .setContentTitle(Title)
                .setContentText(Message)
                .setSound(notificationSoundUri)
                .setAutoCancel(true) // Cancel/Dismiss when Clicked
                .setContentIntent(pendingIntent); // Add Intent
        // Show Notification
        notificationManager.notify(notificationID, notificationBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupNotificationChannel(NotificationManager notificationManager) {
        CharSequence channelName = "Some Sample Text";
        String channelDescription = "Channel Description Here";

        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription(channelDescription);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
        notificationChannel.enableVibration(true);
        if (notificationManager != null){
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}


