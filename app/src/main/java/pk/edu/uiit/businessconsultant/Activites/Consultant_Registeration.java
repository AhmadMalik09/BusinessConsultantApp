package pk.edu.uiit.businessconsultant.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;
import pk.edu.uiit.businessconsultant.ModelClasses.FirebaseHelper;
import pk.edu.uiit.businessconsultant.R;

public class Consultant_Registeration extends AppCompatActivity {
    Spinner spinner;
    EditText name,email,password,Phone,CNIC,Qualification,specification;
    Button btnNext;
    String Choices[];
    CircleImageView profile;
    String consultantField;
    String Name;
    String Email;
    String Password;
    String Phone_No;
    String CNIC_No;
    String Degree;
    String Specifitication;
    String profileImage;
    String  accountType;
    FloatingActionButton uploadProfilePic;
    Uri Imageuri;
    // FirebaseAuth
    private FirebaseAuth firebaseAuth;
    // Progress Dialog
    private ProgressDialog progressDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultant_registeration);
        initialize();
        perform_action();
    }
    private void perform_action(){
        Choices=getResources().getStringArray(R.array.Field_Selection);
        spinner=(Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,Choices);
        spinner.setAdapter(adapter);

        //Button Use to Move Next Screen
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
               // Intent intent = new Intent(Consultant_Registeration.this, Consultant_degree_verify.class);
               // Consultant_Registeration.this.startActivity(intent);
            }
        });
        uploadProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(Consultant_Registeration.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Imageuri= data.getData();
        profile.setImageURI(Imageuri);
    }
 //Save Consultant Registration data to Firebase
 private void inputData() {
        //Setting Validations on Data
        consultantField= spinner.getSelectedItem().toString().trim();
        Name = name.getText().toString().trim();
        Email = email.getText().toString().trim();
        Password= password.getText().toString().trim();
        Phone_No= Phone.getText().toString().trim();
        CNIC_No=CNIC.getText().toString().trim();
        Degree= Qualification.getText().toString().trim();
        Specifitication = specification.getText().toString().trim();
        accountType="Consultant";
        profileImage=Imageuri.toString();
     if (TextUtils.isEmpty(Name)) {
         Toast.makeText(Consultant_Registeration.this, "Enter Full Name!", Toast.LENGTH_SHORT).show();
         return;
     }
     if (TextUtils.isEmpty(Phone_No)) {
         Toast.makeText(Consultant_Registeration.this, "Enter Phone Number!", Toast.LENGTH_SHORT).show();
         return;
     }
     if(Phone_No.length()!=11){
         Toast.makeText(this, "PhoneNo should be 11 digit", Toast.LENGTH_SHORT).show();
     }
     if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
         Toast.makeText(Consultant_Registeration.this, "Please Enter Valid Email!", Toast.LENGTH_SHORT).show();
         return;
     }
     if (Password.length() <= 6) {
         Toast.makeText(Consultant_Registeration.this, "Password Must be atleast 6 characters long!", Toast.LENGTH_SHORT).show();
         return;
     }
     if (TextUtils.isEmpty(CNIC_No)) {
         Toast.makeText(Consultant_Registeration.this, "Enter CNIC!", Toast.LENGTH_SHORT).show();
         return;
     }
     if (TextUtils.isEmpty(Degree)) {
         Toast.makeText(Consultant_Registeration.this, "Enter Your Qualification!", Toast.LENGTH_SHORT).show();
         return;
     }
     if (TextUtils.isEmpty(Specifitication)) {
         Toast.makeText(Consultant_Registeration.this, "Enter Your Certification!", Toast.LENGTH_SHORT).show();
         return;
     }


     createAccount();

 }

    private void createAccount() {
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        //Create Account In Database
        firebaseAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // Account Created
                        saverFirebaseData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Consultant_Registeration.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void saverFirebaseData() {
        progressDialog.setTitle("Saving Account Information...");
        String timeStamp = "" + System.currentTimeMillis();
        //Saving Data Without Image
        if(Imageuri==null){
            FirebaseHelper firebaseHelper=new FirebaseHelper(firebaseAuth.getUid(),consultantField,Name,Email,Phone_No,Password,CNIC_No,Degree,Specifitication,profileImage,accountType);
         /*   HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("uid"," "+ firebaseAuth.getUid());
            hashMap.put("Field", "" + consultantField);
            hashMap.put("Name", "" + Name);
            hashMap.put("Email", "" + Email);
            hashMap.put("Phone_No", "" + Phone_No);
            hashMap.put("Password", "" + Password);
            hashMap.put("CNIC", "" + CNIC_No);
            hashMap.put("Qualification", "" + Degree);
            hashMap.put("Specification",""+ Specifitication);
            hashMap.put("accountType", "Consultant");
            hashMap.put("ProfileImage","");  */
            //Save Data in Database
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
            reference.child(firebaseAuth.getUid()+"~Consultant~").setValue(firebaseHelper)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressDialog.dismiss();
                            Toast.makeText(Consultant_Registeration.this, "Save and Next", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Consultant_Registeration.this, Consultant_degree_verify.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Failed Updating DB
                            progressDialog.dismiss();
                            Toast.makeText(Consultant_Registeration.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
        else{
            //save Data with Profile Image
            // Name and path of image
            String filePathAndName = "profile_images/" + "" +firebaseAuth.getUid();
            StorageReference storageReference= FirebaseStorage.getInstance().getReference(filePathAndName);
            storageReference.putFile(Imageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get URL Of Uploaded Image
                            Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri downloadImageUri=uriTask.getResult();
                            if(uriTask.isSuccessful()){
                                FirebaseHelper firebaseHelper=new FirebaseHelper(firebaseAuth.getUid(),consultantField,Name,Email,Phone_No,Password,CNIC_No,Degree,Specifitication,profileImage,accountType);
                          /*      HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("uid"," "+ firebaseAuth.getUid());
                                hashMap.put("Field", "" + consultantField);
                                hashMap.put("Name", "" + Name);
                                hashMap.put("Email", "" + Email);
                                hashMap.put("Phone_No", "" + Phone_No);
                                hashMap.put("Password", "" + Password);
                                hashMap.put("CNIC", "" + CNIC_No);
                                hashMap.put("Qualification", "" + Degree);
                                hashMap.put("Specification",""+ Specifitication);
                                hashMap.put("accountType", "Consultant");
                                hashMap.put("profileImage", "" + downloadImageUri); // URL Of Uploaded Image  */
                                //Save Data in Database
                                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
                                reference.child(firebaseAuth.getUid()+"~Consultant~").setValue(firebaseHelper)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressDialog.dismiss();
                                                Toast.makeText(Consultant_Registeration.this, "Save and Next", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(Consultant_Registeration.this, Consultant_degree_verify.class));
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Failed Updating DB
                                                progressDialog.dismiss();
                                                Toast.makeText(Consultant_Registeration.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Consultant_Registeration.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }

    }

    private void initialize(){
        name=(EditText) findViewById(R.id.consultant_Name);
        email=(EditText) findViewById(R.id.consultant_Email);
        password=(EditText) findViewById(R.id.consultant_Password);
        Phone=(EditText)findViewById(R.id.consultant_Phone);
        CNIC=(EditText) findViewById(R.id.consultant_CNIC);
        Qualification=(EditText) findViewById(R.id.consultant_edu);
        specification=(EditText) findViewById(R.id.specification);
        btnNext=(Button) findViewById(R.id.nextBtn);
        profile=(CircleImageView) findViewById(R.id.profile_image);
        uploadProfilePic=(FloatingActionButton) findViewById(R.id.floatingActionButton);
        // Initialization Of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialization Of Progress Dialog
        progressDialog = new ProgressDialog(Consultant_Registeration.this);
        progressDialog.setTitle("Please Wait ...");
        progressDialog.setCanceledOnTouchOutside(false);
    }
}
