package pk.edu.uiit.businessconsultant.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import pk.edu.uiit.businessconsultant.ModelClasses.loading_users;
import pk.edu.uiit.businessconsultant.R;

public class Chat extends AppCompatActivity {
    String consultantPicture,consultantName,consultantUID,UserUID;
    CircleImageView profileImage;
    TextView Name;
    CardView sendBtn;
    EditText textMessage;
    RecyclerView recyclerView;
    ImageButton backButton;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    public  static  String  userProfileImage;
    public  static  String  consultantImage;
    String senderRoom;
    String receiverRoom;
    ArrayList<Messages>messagesArrayList;
    messagesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Initialization();
        performAction();
        sendMessges();

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
        UserUID=firebaseAuth.getUid();
        senderRoom=UserUID +  consultantUID;
        receiverRoom=consultantUID+UserUID;
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
                String Message= textMessage.getText().toString();
                if(Message.isEmpty()){
                    Toast.makeText(Chat.this, "Plese Enter Some Text", Toast.LENGTH_SHORT).show();
                    return;
                }
                textMessage.setText("");
                Date date= new Date();

                Messages messages=new Messages(Message, UserUID,consultantUID,date.getTime());
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
    }
    public void backPressed(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Chat.this, loading_users.class);
                startActivity(intent);
                finish();

            }
        });
    }
    public void Initialization(){
        profileImage=(CircleImageView) findViewById(R.id.profileImg);
        Name=(TextView) findViewById(R.id.Consult_Name);
        sendBtn=(CardView)findViewById(R.id.sendBtn);
        textMessage=(EditText)findViewById(R.id.textMessage);
        recyclerView=(RecyclerView)findViewById(R.id.chatAdapter);
        database=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        backButton=(ImageButton) findViewById(R.id.backBtn);
    }
}