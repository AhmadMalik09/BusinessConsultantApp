package pk.edu.uiit.businessconsultant.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import pk.edu.uiit.businessconsultant.Adapters.messagesAdapter;
import pk.edu.uiit.businessconsultant.ModelClasses.Messages;
import pk.edu.uiit.businessconsultant.Notifications.MySingleton;
import pk.edu.uiit.businessconsultant.R;

public class Chat extends AppCompatActivity {
    String consultantPicture,consultantName,consultantUID;
    CircleImageView profileImage;
    TextView Name;
    CardView sendBtn;
    EditText textMessage;
    ImageView starBtn;
    RecyclerView recyclerView;
    String UserID;
    String Message;
    ImageButton backButton;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    public  static  String  userProfileImage;
    public  static  String  consultantImage;
    String senderRoom;
    String receiverRoom;
    ArrayList<Messages>messagesArrayList;
    messagesAdapter adapter;
   //Here Firebase Messaging Service Variable starts
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey ="AAAAdq4Pl4c:APA91bGUShicc2EjdAHZZv0yBp3JOLSLh7IDg1LbchM-tPKFrnP12nlK4Mo8CoCt81BljmMdQk9tZefl-LgbHiX49db0_AwgLXjFA-nAXMUEAJkInZWD0EgEFBcFxzm0bnPhUi_ZMxHg";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
      //  FirebaseMessaging.getInstance().subscribeToTopic("all");
        Initialization();
        performAction();
        sendMessges();
        goForRating();
    }
    public void performAction(){
        consultantName= getIntent().getStringExtra("name");
        consultantPicture=getIntent().getStringExtra("profilePicture");
        consultantUID=getIntent().getStringExtra("uid");
        messagesArrayList=new ArrayList<>();
        // Get Image from Firebase
        try {
            Picasso.get().load(consultantPicture).fit().centerCrop().into(profileImage);
        }
        catch (Exception exception){
            profileImage.setImageResource(R.drawable.profile);
        }
        Name.setText(""+consultantName);
        UserID=firebaseAuth.getUid();
        senderRoom=UserID +  consultantUID;
        receiverRoom=consultantUID+UserID;
        // add linear layout manager
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new messagesAdapter(Chat.this,messagesArrayList);
        recyclerView.setAdapter(adapter);
        DatabaseReference Chatreference=database.getReference().child("Chats").child(senderRoom).child("messages");
        Chatreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Messages messages=dataSnapshot.getValue(Messages.class);
                    messagesArrayList.add(messages);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference reference=database.getReference().child("Users").child(firebaseAuth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for (DataSnapshot ds: snapshot.getChildren()){
                    String accountType = ""+ds.child("accountType").getValue();
                    if (accountType.equals("User")){
                        userProfileImage=snapshot.child("profileImage").getValue().toString();
                    }

                }
                consultantImage=consultantPicture;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void sendMessges(){
        //After message typed Send Button Clickted...
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Message= textMessage.getText().toString();
                if(Message.isEmpty()){
                    Toast.makeText(Chat.this, "Plese Enter Some Text", Toast.LENGTH_SHORT).show();
                    return;
                }
                textMessage.setText("");
                Date date= new Date();
          /*      String Title ="Business Consultant";
                FcmNotificationsSender notificationsSender=new FcmNotificationsSender("/topics/all",Title,textMessage.getText().toString()
                        ,getApplicationContext(),Chat.this);  */
                Messages messages=new Messages(Message, UserID,date.getTime());
                database=FirebaseDatabase.getInstance();
                database.getReference().child("Chats")
                        .child(senderRoom)
                        .child("messages")
                        .push()
                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        database.getReference().child("Chats")
                                .child(receiverRoom)
                                .child("messages")
                                .push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                    }
                });

            }

        });
        //Firebase Notification Service
        TOPIC = "/topics/userABC"; //topic must match with what the receiver subscribed to
        NOTIFICATION_TITLE = Name.getText().toString();
        NOTIFICATION_MESSAGE = textMessage.getText().toString();
        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", NOTIFICATION_TITLE);
            notifcationBody.put("message", NOTIFICATION_MESSAGE);

            notification.put("to", TOPIC);
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }
        sendNotification(notification);
    }
    private void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                        Name.setText("");
                        textMessage.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Chat.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();

            }
        });
    }
    public void goForRating(){
        //Set the visibility of Star Button
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                            String accountType = ""+ds.child("accountType").getValue();
                            if (accountType.equals("Consultant"))
                            {
                                starBtn.setVisibility(View.GONE);
                            }
                            if (accountType.equals("User"))
                            {
                                //Only User can give review and rating
                                starBtn.setVisibility(View.VISIBLE);
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        starBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Chat.this,feedback.class);
                intent.putExtra("consultantUID",consultantUID);
                startActivity(intent);
            }
        });
    }
    public void Initialization(){
        profileImage=(CircleImageView) findViewById(R.id.profileImg);
        Name=(TextView) findViewById(R.id.Consult_Name);
        sendBtn=(CardView)findViewById(R.id.sendBtn);
        textMessage=(EditText)findViewById(R.id.textMessage);
        recyclerView=(RecyclerView)findViewById(R.id.chatAdapter);
        starBtn=(ImageView)findViewById(R.id.star);
        database=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        backButton=(ImageButton) findViewById(R.id.backBtn);
    }
}