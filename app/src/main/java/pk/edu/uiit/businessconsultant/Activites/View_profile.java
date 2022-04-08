package pk.edu.uiit.businessconsultant.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    ImageButton backBtn;
    CircleImageView ProfilePic;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile);
        initialize();
        performAction();
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
                            // Set Data To Main Seller Activity Views
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
    }
    public void initialize(){
    name=(TextView) findViewById(R.id.name);
    email=(TextView) findViewById(R.id.email);
    phone=(TextView) findViewById(R.id.phone);
    field=(TextView) findViewById(R.id.Field);
    education=(TextView) findViewById(R.id.education);
    specification=(TextView) findViewById(R.id.certificate);
    ProfilePic =(CircleImageView)findViewById(R.id.consultantProfile);
    mAuth=FirebaseAuth.getInstance();
    backBtn=(ImageButton) findViewById(R.id.backBtn);
    }

}
