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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pk.edu.uiit.businessconsultant.R;
import pk.edu.uiit.businessconsultant.Activites.agriculture_consultancy;
import pk.edu.uiit.businessconsultant.Activites.login;

public class loading_Consultants extends AppCompatActivity {
    ImageView backBtn;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    userAdapter adapter;
    FirebaseDatabase database;
    ArrayList<FirebaseHelper> usersArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_consultants);
        backBtn=(ImageView)findViewById(R.id.chatBckBtn);
        firebaseAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        usersArrayList=new ArrayList<>();
        DatabaseReference reference=database.getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    String accountType = ""+dataSnapshot.child("accountType").getValue();
                    if(accountType.equals("Consultant")) {
                        FirebaseHelper users = dataSnapshot.getValue(FirebaseHelper.class);
                        usersArrayList.add(users);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView=(RecyclerView) findViewById(R.id.contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new userAdapter(loading_Consultants.this,usersArrayList);
        recyclerView.setAdapter(adapter);
        if(firebaseAuth.getCurrentUser()== null){
            startActivity(new Intent(loading_Consultants.this, login.class));
        }
        // Back Button Click Listerner
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loading_Consultants.this, agriculture_consultancy.class));
            }
        });
    }

}