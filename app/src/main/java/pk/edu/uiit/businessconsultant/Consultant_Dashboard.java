package pk.edu.uiit.businessconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class Consultant_Dashboard extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    TextView Consultant_Name,Expertise;
    ImageView profilePic;
    FirebaseAuth mAuth;
    ImageButton logout;
    Button view_profile;
   RatingBar ratingBar;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultant_dashboard);
      //  performAction();
        initialize();
        performAction();
    }
  /*  @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(Consultant_Dashboard.this,login.class));
        }
        if(user!=null){
            loadMyInfo();
        }
    }
    private void loadMyInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.orderByChild("uid").equalTo(mAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            // Get Data From Database (Firebase)
                            String name = ""+ds.child("Name").getValue();
                            String Expertee = ""+ds.child("Field").getValue();
                            String profileImage = ""+ds.child("profileImage").getValue();

                            // Set Data To Main Seller Activity Views
                            Consultant_Name.setText(name);
                            Expertise.setText(Expertee);
                            try {
                            //    Picasso.get().load(profileImage).placeholder(R.drawable.profile).into(profilePic);
                            }
                            catch (Exception exception){
                               // profilePic.setImageResource(R.drawable.ic_store_gray);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    } */
    private void initialize(){
    Consultant_Name=(TextView) findViewById(R.id.consultName);
    Expertise=(TextView) findViewById(R.id.expertise);
    profilePic=(ImageView) findViewById(R.id.profilePic);
    logout=(ImageButton)findViewById(R.id.logOutB);
    mAuth=FirebaseAuth.getInstance();
    ratingBar=(RatingBar)findViewById(R.id.ratingBar);
    view_profile=(Button) findViewById(R.id.consult_profile);
    }
  /*  public void performAction(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(Consultant_Dashboard.this,login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                Consultant_Dashboard.this.startActivity(intent);
            }
        });
     /*   view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Consultant_Dashboard.this,View_profile.class);
                Consultant_Dashboard.this.startActivity(intent);
            }
        });
    } */

    public void performAction(){
        view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Consultant_Dashboard.this,View_profile.class);
                startActivity(intent);
            }
        });
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.chat:
                Toast.makeText(Consultant_Dashboard.this,"Here Chatting will be showed",Toast.LENGTH_LONG).show();
                return true;

            case R.id.notification:
                Toast.makeText(Consultant_Dashboard.this,"Here Chatting notification will be appeared",Toast.LENGTH_LONG).show();
                return true;

            case R.id.user:
                Toast.makeText(Consultant_Dashboard.this,"Consultant Profile",Toast.LENGTH_LONG).show();
                return true;
        }
        return false;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
}
