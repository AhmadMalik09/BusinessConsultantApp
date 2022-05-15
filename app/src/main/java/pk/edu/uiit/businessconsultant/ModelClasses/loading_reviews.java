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

import pk.edu.uiit.businessconsultant.Activites.Consultant_Dashboard;
import pk.edu.uiit.businessconsultant.Activites.login;
import pk.edu.uiit.businessconsultant.Adapters.review_Adapter;
import pk.edu.uiit.businessconsultant.R;

public class loading_reviews extends AppCompatActivity {
    ImageView backBtn;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    review_Adapter adapter;
    public  static String consultantUID;
    ArrayList<Reviews> reviewArrayList; // Will Contains List Of All Reviews
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviews);
        initialize();
        performAction();
    }
    public void initialize(){

        backBtn=(ImageView) findViewById(R.id.reviewBckBtn);
        // Initialization Of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
    }
    public void performAction(){
        consultantUID=firebaseAuth.getUid();
        reviewArrayList=new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()+"~Consultant~").child("Ratings")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Clear List Before Adding Data Into It
                           reviewArrayList.clear();
                        //     ratingSum = 0;
                        for (DataSnapshot ds: snapshot.getChildren()){
                            Reviews reviewList = ds.getValue(Reviews.class);
                            reviewArrayList.add(reviewList);
                        }
                           adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        recyclerView=(RecyclerView) findViewById(R.id.reviewLst);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new review_Adapter(loading_reviews.this,reviewArrayList);

        recyclerView.setAdapter(adapter);
        if(firebaseAuth.getCurrentUser()== null){
            startActivity(new Intent(loading_reviews.this, login.class));
        }
        // Back Button Click Listerner
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loading_reviews.this, Consultant_Dashboard.class));
            }
        });
    }


}
