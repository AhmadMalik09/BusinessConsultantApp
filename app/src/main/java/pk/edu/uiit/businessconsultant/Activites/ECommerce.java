package pk.edu.uiit.businessconsultant.Activites;

import android.os.Bundle;
import android.widget.Button;

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

import pk.edu.uiit.businessconsultant.Adapters.dataAdapter;
import pk.edu.uiit.businessconsultant.ModelClasses.BusinessInfo;
import pk.edu.uiit.businessconsultant.R;

public class ECommerce extends AppCompatActivity {
    RecyclerView recyclerView;
    Button btnStartChat;
    dataAdapter adapter;
    ArrayList<BusinessInfo> infoArrayList;
    Add_Data data;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecommerce);

        initialize();
        performAction();
    }
    public void initialize(){
        btnStartChat=(Button) findViewById(R.id.real_estate_consultant);
        firebaseAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
    }
    public void performAction(){
        infoArrayList=new ArrayList<>();
        DatabaseReference reference=database.getReference().child("BusinessInfo");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                infoArrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                        BusinessInfo info=dataSnapshot.getValue(BusinessInfo.class);
                        infoArrayList.add(info);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView=(RecyclerView) findViewById(R.id.QA);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new dataAdapter(ECommerce.this,infoArrayList);
        recyclerView.setAdapter(adapter);

    }
}