package pk.edu.uiit.businessconsultant.Activites;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import pk.edu.uiit.businessconsultant.Adapters.messagesAdapter;
import pk.edu.uiit.businessconsultant.ModelClasses.Messages;
import pk.edu.uiit.businessconsultant.Notifications.FcmNotificationsSender;
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
    FcmNotificationsSender notificationsSender;
    String postUrl = "https://fcm.googleapis.com/fcm/send";
    String fcmServerKey ="AAAAdq4Pl4c:APA91bGUShicc2EjdAHZZv0yBp3JOLSLh7IDg1LbchM-tPKFrnP12nlK4Mo8CoCt81BljmMdQk9tZefl-LgbHiX49db0_AwgLXjFA-nAXMUEAJkInZWD0EgEFBcFxzm0bnPhUi_ZMxHg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        notificationsSender=new FcmNotificationsSender();
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

            //   getToken(Message,consultantUID,senderRoom);
              //  preparedNotificationMessage(senderRoom,Message);
            }

        });
    }
/*    private void preparedNotificationMessage(String chatID, String message){
        // When User seller Change The Order Status In Progress/Completed/Cancelled, Sand Notification To Buyer

        // Prepare Data For Notification
        String NOTIFICATION_TOPIC = "/topics/" + notificationsSender.FCM_TOPIC; // Must Be Same Subscribed By User
        String NOTIFICATION_TITLE = "Business Consultant";
        String NOTIFICATION_MESSAGE = ""+message;

        // Prepare JSON (What To Send And Where To Sand)
        JSONObject notificationJo = new JSONObject();
        JSONObject notificationBodyJo = new JSONObject();

        try {

            // What To Sand
            notificationBodyJo.put("hisID", consultantUID);
            // Since We Are Logged In As Seller To Change Order Status So Current User uid Is Seller uid
            notificationBodyJo.put("uid", firebaseAuth.getUid());
            notificationBodyJo.put("chatID",chatID);
            notificationBodyJo.put("notificationTitle", NOTIFICATION_TITLE);
            notificationBodyJo.put("notificationMessage", NOTIFICATION_MESSAGE);

            // Where To Sand
            notificationJo.put("to", NOTIFICATION_TOPIC); // To All who Subscribed To This Topic
            notificationJo.put("data", notificationBodyJo);
        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        sendFcmNotification(notificationJo);
    }

    private void sendFcmNotification(JSONObject notificationJo) {
        // Send Volley Request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://fcm.googleapis.com/fcm/send", notificationJo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Notification Sent

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Notification Failed
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                // Put Required Headers
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");
                headers.put("Authorization","key" + notificationsSender.FCM_TOPIC);
                return headers;
            }
        };

        // Enque The Volley Request
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
    //2nd Method
 */
 /*   private void getToken(String message, String hisID, String chatID) {
        String Title="Business Consultant";
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid());
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String token = snapshot.child("token").getValue().toString();


                JSONObject to = new JSONObject();
                JSONObject data = new JSONObject();
                try {
                    data.put("title", Title);
                    data.put("message", message);
                    data.put("hisID", hisID);
                    data.put("chatID", chatID);
                    to.put("to", token);
                    to.put("data", data);

                    sendNotification(to);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendNotification(JSONObject to) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, postUrl, to, response -> {
            Log.d("notification", "sendNotification: " + response);
        }, error -> {
            Log.d("notification", "sendNotification: " + error);
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "key=" + fcmServerKey);
                map.put("Content-Type", "application/json");
                return map;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }  */
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