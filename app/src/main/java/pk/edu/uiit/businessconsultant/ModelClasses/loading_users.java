package pk.edu.uiit.businessconsultant.ModelClasses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pk.edu.uiit.businessconsultant.Adapters.consultantAdapter;
import pk.edu.uiit.businessconsultant.Activites.Consultant_Dashboard;
import pk.edu.uiit.businessconsultant.R;
import pk.edu.uiit.businessconsultant.Activites.login;

public class loading_users extends AppCompatActivity {

    ImageView backBtn;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    consultantAdapter adapter;
    FirebaseDatabase database;
    FirebaseUser firebaseUser;
    ArrayList<Users> usersArrayList;
    String senderID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_users);
        backBtn=(ImageView)findViewById(R.id.chatBckBtn);
        firebaseAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        usersArrayList=new ArrayList<>();
   /*     DatabaseReference reference=database.getReference().child("Chats").child("messages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                        senderID = "" + ds.child("senderID").getValue();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/
        DatabaseReference referenceUser=database.getReference().child("Users");
        referenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    Messages messages=ds.getValue(Messages.class);
                    String accountType = ""+ds.child("accountType").getValue();
                    if(accountType.equals("User")) {
                        //   if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderID())){
                        Users users = ds.getValue(Users.class);
                        usersArrayList.add(users);
                    // }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView=(RecyclerView) findViewById(R.id.loadindUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new consultantAdapter(loading_users.this,usersArrayList);
        recyclerView.setAdapter(adapter);
        if(firebaseAuth.getCurrentUser()== null){
            startActivity(new Intent(loading_users.this, login.class));
        }
        // Back Button Click Listerner
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loading_users.this, Consultant_Dashboard.class));
            }
        });

    }

 /*   public void initialize(){
        recyclerView=(RecyclerView) findViewById(R.id.loadindUsers);
    }  */
}