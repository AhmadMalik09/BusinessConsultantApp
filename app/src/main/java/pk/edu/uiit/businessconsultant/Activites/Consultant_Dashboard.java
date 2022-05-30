package pk.edu.uiit.businessconsultant.Activites;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import pk.edu.uiit.businessconsultant.ModelClasses.FirebaseHelper;
import pk.edu.uiit.businessconsultant.ModelClasses.loading_reviews;
import pk.edu.uiit.businessconsultant.ModelClasses.loading_users;
import pk.edu.uiit.businessconsultant.R;

public class Consultant_Dashboard extends AppCompatActivity{
    BottomNavigationView bottomNavigationView;
    TextView Consultant_Name,Expertise;
    FirebaseAuth mAuth;
    ImageButton logout;
    Button view_profile,Chat_Box,Add_Data,Review,Portfolio,Settings;
    RatingBar ratingBar;
   FirebaseHelper users;
   CircleImageView profilePicture;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultant_dashboard);
      //  performAction();
        initialize();
        performAction();
        bottomNagivationBar();
        subscribeToTopic();
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(Consultant_Dashboard.this,login.class));
        }
        if(user!=null){
            loadMyInfo();
           loadMyRatings();
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
                            String name = ""+ds.child("name").getValue();
                            String Expertee = ""+ds.child("field").getValue();
                            String profileImage = ""+ds.child("imageURL").getValue();

                            // Set Data To Main Seller Activity Views
                            Consultant_Name.setText(name);
                            Expertise.setText(Expertee);
                            try {
                                    //Picasso.get().load(users.profileImage).placeholder(R.drawable.profile).into(profilePic);
                                   Picasso.get().load(profileImage).fit().centerCrop().placeholder(R.drawable.profile).into(profilePicture);
                            }
                            catch (Exception exception){
                                   // profilePicture.setImageResource(R.drawable.profile);
                                Toast.makeText(Consultant_Dashboard.this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void initialize(){
    Consultant_Name=(TextView) findViewById(R.id.consultName);
    Expertise=(TextView) findViewById(R.id.expertise);
    logout=(ImageButton)findViewById(R.id.logOutB);
    mAuth=FirebaseAuth.getInstance();
    ratingBar=(RatingBar)findViewById(R.id.ratingBar);
    view_profile=(Button) findViewById(R.id.consult_profile);
    Settings=(Button)findViewById(R.id.settings);
    users=new FirebaseHelper();
    profilePicture=(CircleImageView) findViewById(R.id.profilePic);
    Chat_Box=(Button) findViewById(R.id.chat_box);
    Add_Data=(Button) findViewById(R.id.add_data);
    Review=(Button) findViewById(R.id.consult_ratings);
    Portfolio=(Button) findViewById(R.id.consult_portfolio);
    bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottomNavigationView);
    }

    public void performAction(){
        view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Consultant_Dashboard.this, View_profile.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(Consultant_Dashboard.this);
                builder.setTitle("Confirm Exit...");
                builder.setIcon(R.drawable.icon_lgout);
                    builder.setMessage("Are you sure, want to Exit ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent=new Intent(Consultant_Dashboard.this,login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                Consultant_Dashboard.this.startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });
        //Button for Opening Chat
        Chat_Box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(Consultant_Dashboard.this, loading_users.class);
               startActivity(intent);
            }
        });
        //Button For Adding Business data into Users Dashboard
        Add_Data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Consultant_Dashboard.this, Add_Data.class);
                startActivity(intent);
            }
        });
        //Button for checking Reviews and Ratings
        Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Consultant_Dashboard.this, loading_reviews.class);
                startActivity(intent);
            }
        });
        Portfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Consultant_Dashboard.this, consultantPortfolio.class);
                startActivity(intent);
            }
        });
        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Consultant_Dashboard.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void bottomNagivationBar(){
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.chat:
                        startActivity(new Intent(getApplicationContext(),loading_users.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.review:
                        startActivity(new Intent(getApplicationContext(),loading_reviews.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext(),SettingActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    float  ratingSum = 0;
    public void loadMyRatings(){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.child(mAuth.getUid()+"~Consultant~").child("Ratings")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ratingSum = 0;
                        for (DataSnapshot ds: snapshot.getChildren()){
                            float rating = Float.parseFloat(""+ds.child("ratings").getValue()); // e.g. 4.5
                            ratingSum = ratingSum + rating; // For Average, Add(Addition Of) All Ratings, Later Will Divide It By Number Of Reviews
                        }
                        long numberOfReviews = snapshot.getChildrenCount();
                        float avgRating = ratingSum/numberOfReviews;
                        ratingBar.setRating(avgRating);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void subscribeToTopic(){
        FirebaseMessaging.getInstance().subscribeToTopic(mAuth.getUid());
    }
    
}
