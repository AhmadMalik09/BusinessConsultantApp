package pk.edu.uiit.businessconsultant.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import pk.edu.uiit.businessconsultant.R;

public class View_profile extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView name,email,phone,education,specification,field;
    Button updateinfo;
    ImageButton backBtn;
    CircleImageView ProfilePic;
    TextView ratingCount,Experience,AwardCount;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile);
        initialize();
        performAction();
        ratingCount();                  //show Rating on Profile
        writeExperienceAndAwards();     //show awards and experience on profile
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(View_profile.this, login.class));
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
                            String Name = ""+ds.child("name").getValue();
                            String Email=""+ ds.child("email").getValue();
                            String Expertee = ""+ds.child("field").getValue();
                            String profileImage = ""+ds.child("imageURL").getValue();
                            String Education=""+ds.child("qualification").getValue();
                            String Specification=""+ds.child("specification").getValue();
                            String Phone=""+ds.child("phone").getValue();
                            // Set Data To Profile Activity Views
                            name.setText(Name);
                            field.setText(Expertee);
                            education.setText(Education);
                            specification.setText(Specification);
                            phone.setText(Phone);
                            email.setText(Email);

                            try {
                                  Picasso.get().load(profileImage).placeholder(R.drawable.profile).into( ProfilePic);
                            }
                            catch (Exception exception){
                                ProfilePic.setImageResource(R.drawable.profile);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    public void performAction(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(View_profile.this, Consultant_Dashboard.class);
                startActivity(intent);
                finish();

            }
        });
        //update Info Button
        updateinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(View_profile.this,update_profile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
    }
    //Get Rating from Database and show it in Rating Section of Profile
    float  ratingSum = 0;
    public void ratingCount(){
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
                        float average = ratingSum/numberOfReviews;
                        String avgRating=Float.toString(average);
                        ratingCount.setText(avgRating);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    public void writeExperienceAndAwards(){
        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("Users");
        reference1.child(mAuth.getUid()+"~Consultant~").child("Portfolio")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            //Get Value from FirebaseDatabase
                            String experience=""+snapshot.child("experience").getValue();
                            String no_Of_Awards=""+snapshot.child("no_Of_Awards").getValue();

                            Experience.setText(experience);
                            AwardCount.setText(no_Of_Awards);


                        }



                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
    public void initialize(){
    name=(TextView) findViewById(R.id.name);
    email=(TextView) findViewById(R.id.email);
    phone=(TextView) findViewById(R.id.phone);
    field=(TextView) findViewById(R.id.Field);
    education=(TextView) findViewById(R.id.education);
    specification=(TextView) findViewById(R.id.certificate);
    ratingCount=(TextView)findViewById(R.id.ratings);
    Experience=(TextView)findViewById(R.id.expInYears);
    AwardCount=(TextView)findViewById(R.id.awardsCount);
    ProfilePic =(CircleImageView)findViewById(R.id.consultantProfile);
    mAuth=FirebaseAuth.getInstance();
    backBtn=(ImageButton) findViewById(R.id.backBtn);
    updateinfo=(Button) findViewById(R.id.updateInfo);
    }

}
