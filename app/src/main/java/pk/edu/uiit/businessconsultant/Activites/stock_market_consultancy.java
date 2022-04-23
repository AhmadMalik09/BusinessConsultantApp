package pk.edu.uiit.businessconsultant.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import pk.edu.uiit.businessconsultant.ModelClasses.loading_Consultants;
import pk.edu.uiit.businessconsultant.R;

public class stock_market_consultancy extends AppCompatActivity {
    RecyclerView recyclerView;
    Button goForChat;
    ;
    dataAdapter adapter;
    ArrayList<BusinessInfo> infoArrayList;
    Add_Data data;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_market_consultancy);
        initialize();
        performance();
        goForChat();
    }

    public void performance() {
        infoArrayList = new ArrayList<>();
        data = new Add_Data();
        DatabaseReference reference = database.getReference().child("BusinessInfo").child("Info");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String Field = "" + dataSnapshot.child("field").getValue();
                    if (Field.equals("Stock-Market")) {
                        BusinessInfo info = dataSnapshot.getValue(BusinessInfo.class);
                        infoArrayList.add(info);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.QA);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new dataAdapter(stock_market_consultancy.this, infoArrayList);
        recyclerView.setAdapter(adapter);
    }

    public void goForChat() {
        goForChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stock_market_consultancy.this, loading_Consultants.class);
                startActivity(intent);
            }
        });
    }

    public void initialize() {
        goForChat = (Button) findViewById(R.id.stock_market_consultant);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }
}
