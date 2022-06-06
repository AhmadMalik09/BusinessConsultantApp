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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
    String chatID,senderID;
    String name; //Name of the person who message you
   //Here Firebase Messaging Service Variable starts
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        Initialization();
        getUserName();
        performAction();
        sendMessges();
        backButtonPressed();
        goForRating();
    }

    @Override
    protected void onStart() {
        super.onStart();


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
        // Get Data From Intent
      //  senderRoom= getIntent().getStringExtra("chatID");
      //  senderID= getIntent().getStringExtra("senderUid");
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
                        //this name will be use for a person who message you
                        name=snapshot.child("name").getValue().toString();
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
                                    sentNotification(Message);
                            }
                        });

                    }
                });

            }

        });

    }

    private void sentNotification(String message) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("to","/topics/"+consultantUID);
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("title","Message From "+name);
            jsonObject1.put("body",message);
            jsonObject1.put("senderUid",firebaseAuth.getUid());
            jsonObject1.put("chatID",senderID);
            jsonObject.put("notification",jsonObject1);
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,FCM_API, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                 //   Toast.makeText(Chat.this, "Notification sent", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                  Toast.makeText(Chat.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
             /*       NetworkResponse response = error.networkResponse;
                    String errorMsg = "";
                    if(response != null && response.data != null){
                        String errorString = new String(response.data);
                        Log.i("log error", errorString);  } */

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String>map=new HashMap<>();
                    map.put("content-type","application/json");
                    map.put("authorization","key=AAAAdq4Pl4c:APA91bGUShicc2EjdAHZZv0yBp3JOLSLh7IDg1LbchM-tPKFrnP12nlK4Mo8CoCt81BljmMdQk9tZefl-LgbHiX49db0_AwgLXjFA-nAXMUEAJkInZWD0EgEFBcFxzm0bnPhUi_ZMxHg");
                    return map;
                }
            };
            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
private void getUserName(){
    DatabaseReference reference=database.getReference().child("Users");
    reference.orderByChild("uid").equalTo(firebaseAuth.getUid())
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        //this name will be use for a person who message you
                        name = ds.child("name").getValue().toString();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
}

  public void backButtonPressed(){
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
        backButton=(ImageButton) findViewById(R.id.ChatBckButton);
        requestQueue= Volley.newRequestQueue(this);
    }
}