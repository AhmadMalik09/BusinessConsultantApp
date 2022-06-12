package pk.edu.uiit.businessconsultant.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import pk.edu.uiit.businessconsultant.R;

public class update_profile extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView email,field,Rating;
    EditText name,phone,education,specification,experience,Award;
    ImageButton backBtn;
    CircleImageView ProfilePic;
    FloatingActionButton btnUpload;
    Button Update_Info;
    String Name,Phone,Education,Specification,Experience,Awards;
    private ProgressDialog progressDialog;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        initialize();
        performAction();

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(update_profile.this, login.class));
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
        //load Experience and Awards of Consultant
        writeExperienceAndAwards();
//Load average Rating of COnsultant
        ratingCount();

    }
    public void performAction(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();

            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(update_profile.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        Update_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDate();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUri= data.getData();
        ProfilePic.setImageURI(imageUri);
    }
    private void inputDate() {
        Name=name.getText().toString().trim();
        Phone=phone.getText().toString().trim();
        Education=education.getText().toString().trim();
        Specification=specification.getText().toString().trim();
        Experience=experience.getText().toString().trim();
        Awards=Award.getText().toString().trim();
        //Set Validation
        if (TextUtils.isEmpty(Name))
        {
            Toast.makeText(this, "Enter Full Name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty( Phone)) {
            Toast.makeText(update_profile.this, "Enter Phone Number!", Toast.LENGTH_SHORT).show();
            return;
        }
        if( Phone.length()!=11){
            Toast.makeText(this, "PhoneNo should be 11 digit", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(Education))
        {
            Toast.makeText(this, "Type Your Degree!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Specification))
        {
            Toast.makeText(this, "Type Your Certifications!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Experience))
        {
            Toast.makeText(this, "Type Your Experience!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Awards))
        {
            Toast.makeText(this, "Write No Awards", Toast.LENGTH_SHORT).show();
            return;
        }
        updateData();
        updateExpAndAwards();
    }

    private void updateData() {
        progressDialog.setMessage("Updating Profile...");
        progressDialog.show();
        if (imageUri==null){
            //Update Without Image
            //Setup Data to Update
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", "" + Name);
            hashMap.put("phone", "" + Phone);
            hashMap.put("qualification",""+Education);
            hashMap.put("specification",""+Specification);

            //Update to DB
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(mAuth.getUid()+"~Consultant~").updateChildren(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            //Updated
                            progressDialog.dismiss();
                            Toast.makeText(update_profile.this, "Data Updated...", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Failed to  Update
                            progressDialog.dismiss();
                            Toast.makeText(update_profile.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else{
            // Update With Image
            /*----------UpLoad Image First----------*/
            String filePathAndName = "profile_images/" + ""+ mAuth.getUid();
            // Get Storage reference
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
            storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Image Upload, get url of uploaded image
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while(!uriTask.isSuccessful());
                            Uri downloadImageUri = uriTask.getResult();
                            if(uriTask.isSuccessful())
                            {
                                //Image url received, now update DB
                                //Setup Data to Update
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("name", "" + Name);
                                hashMap.put("phone", "" + Phone);
                                hashMap.put("qualification",""+Education);
                                hashMap.put("specification",""+Specification);
                                hashMap.put("imageURL", "" + downloadImageUri);
                                //Update to DB
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                                reference.child(mAuth.getUid()+"~Consultant~").updateChildren(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                //Updated
                                                progressDialog.dismiss();
                                                Toast.makeText(update_profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //Failed to  Update
                                                progressDialog.dismiss();
                                                Toast.makeText(update_profile.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Failed to update
                            progressDialog.dismiss();
                            Toast.makeText(update_profile.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
    private void updateExpAndAwards(){
        progressDialog.setMessage("Updating Profile...");
        progressDialog.show();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("experience", "" + Experience);
        hashMap.put("no_Of_Awards", "" + Awards);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.child(mAuth.getUid()+"~Consultant~").child("Portfolio").updateChildren(hashMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

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
                        String Experience=""+snapshot.child("experience").getValue();
                        String no_Of_Awards=""+snapshot.child("no_Of_Awards").getValue();

                        experience.setText(Experience);
                        Award.setText(no_Of_Awards);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

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
                        Rating.setText(avgRating);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    public void initialize(){
        name=(EditText) findViewById(R.id.name);
        email=(TextView) findViewById(R.id.email);
        phone=(EditText) findViewById(R.id.phone);
        field=(TextView) findViewById(R.id.Field);
        Rating=(TextView)findViewById(R.id.avgRating) ;
        experience=(EditText) findViewById(R.id.updtExperience);
        Award=(EditText) findViewById(R.id.award) ;
        education=(EditText) findViewById(R.id.education);
        specification=(EditText) findViewById(R.id.certificate);
        ProfilePic =(CircleImageView)findViewById(R.id.consultantProfile);
        mAuth=FirebaseAuth.getInstance();
        backBtn=(ImageButton) findViewById(R.id.backBtn);
        btnUpload=(FloatingActionButton) findViewById(R.id.floatingActionButton);
        Update_Info=(Button) findViewById(R.id.updateProfile);
        // Initialization Of Progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait ...");
        progressDialog.setCanceledOnTouchOutside(false);
    }
}