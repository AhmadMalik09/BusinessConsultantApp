package pk.edu.uiit.businessconsultant.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import pk.edu.uiit.businessconsultant.R;

public class Consultant_degree_verify extends AppCompatActivity {
    ImageView degreeImg , consultCertificate;
    Button select_img;
    FloatingActionButton Edu, EduCertification;
    ImageButton move_next;
    Uri uri_degree, uri_consult_certificate;
    private FirebaseAuth firebaseAuth;
    // Progress Dialog
    private ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultant_degree_verify);
        initialize();
        perform_action();
    }
    private void perform_action(){

        move_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQualificationDataToFirbase();
                saveSpecificationsData();
               // Intent intent = new Intent(Consultant_degree_verify.this, Consultant_CNIC_Verify.class);
               // Consultant_degree_verify.this.startActivity(intent);
            }
        });
        Edu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(Consultant_degree_verify.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(10);
            }
        });
        EduCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(Consultant_degree_verify.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(20);

            }
        });

    }



    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            uri_degree= data.getData();
            degreeImg.setImageURI(uri_degree);
        }
        if(requestCode==20){
            uri_consult_certificate= data.getData();
            consultCertificate.setImageURI(uri_consult_certificate);
        }

    }
//Saving Data to Firbase
private void saveQualificationDataToFirbase() {
    progressDialog.setMessage("Saving Qualification Info...");
    progressDialog.show();
    progressDialog.setTitle("~~~Qualification DATA~~~");
    //save Data with Profile Image
    // Name and path of image
    String Qualification = "Degree_Images/" + "" +firebaseAuth.getUid()+"(~Qualification~)";
    StorageReference storageReference= FirebaseStorage.getInstance().getReference(Qualification);
    //Uploading Degree Image
    storageReference.putFile(uri_degree)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get URL Of Uploaded Image
                    Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    Uri downloadImageUri1=uriTask.getResult();
                    if(uriTask.isSuccessful()){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("DegreeImg", "" + downloadImageUri1); // URL Of Uploaded Image
                        //Save Data in Database
                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Consultant_Qualifications");
                        reference.child(firebaseAuth.getUid()+"~Degree~").setValue(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.dismiss();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Failed Updating DB
                                        progressDialog.dismiss();
                                        Toast.makeText(Consultant_degree_verify.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Consultant_degree_verify.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

}

private void saveSpecificationsData(){
    //save Data with Profile Image
    // Name and path of image
    String Specification = "Degree_Images/" + "" +firebaseAuth.getUid()+"(~Specification_Certificate~) ";
    StorageReference storageReference= FirebaseStorage.getInstance().getReference(Specification);
    //Uploading Specific Consultant Certificate Images
    storageReference.putFile(uri_consult_certificate)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get URL Of Uploaded Image
                    Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    Uri downloadImageUri=uriTask.getResult();
                    if(uriTask.isSuccessful()){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("Consultant_Certificate", "" + downloadImageUri); // URL Of Uploaded Image
                        //Save Data in Database
                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Consultant_Qualifications");
                        reference.child(firebaseAuth.getUid()+"~Certificate~").setValue(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.dismiss();
                                        Toast.makeText(Consultant_degree_verify.this, "Save and Next", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Consultant_degree_verify.this, Consultant_CNIC_Verify.class));
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Failed Updating DB
                                        progressDialog.dismiss();
                                        Toast.makeText(Consultant_degree_verify.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Consultant_degree_verify.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


}


    private void initialize(){
        degreeImg=(ImageView) findViewById(R.id.degreeImg);
        consultCertificate=(ImageView) findViewById(R.id.EduSpecification);
        Edu=(FloatingActionButton)findViewById(R.id.DegreeBtn);
        EduCertification=(FloatingActionButton)findViewById(R.id.SpecificationBtn) ;
        move_next=(ImageButton) findViewById(R.id.next_dashboard);
        // Initialization Of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialization Of Progress Dialog
        progressDialog = new ProgressDialog(Consultant_degree_verify.this);
        progressDialog.setTitle("Please Wait ...");
        progressDialog.setCanceledOnTouchOutside(false);
    }



}
