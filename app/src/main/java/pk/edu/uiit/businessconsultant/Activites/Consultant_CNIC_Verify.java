package pk.edu.uiit.businessconsultant.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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

public class Consultant_CNIC_Verify  extends AppCompatActivity {
    ImageView CNIC_Frnt,CNIC_Bck;
    FloatingActionButton CNICFrntBtn, CNICBckBtn;
    ImageButton move_next;

    Uri uri_CNIC_Frnt, uri_CNIC_Bck;
    private FirebaseAuth firebaseAuth;
    // Progress Dialog
    private ProgressDialog progressDialog;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultant_cnic_verify);
        initialize();
        perform_action();
    }
    private void perform_action(){

        move_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCNIC_Front_ToFirbase();
                saveCNIC_Back_ToFirebase();
                //Intent intent = new Intent(Consultant_CNIC_Verify.this, Consultant_Dashboard.class);
               // Consultant_CNIC_Verify.this.startActivity(intent);
            }
        });
        CNICFrntBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(Consultant_CNIC_Verify.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(123);
            }

        });
        CNICBckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(Consultant_CNIC_Verify.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(321);
            }
        });
    }


    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123){
            uri_CNIC_Frnt= data.getData();
            CNIC_Frnt.setImageURI(uri_CNIC_Frnt);
        }
        if(requestCode==321){
            uri_CNIC_Bck= data.getData();
            CNIC_Bck.setImageURI(uri_CNIC_Bck);
        }
    }
//Save CNIC info in Database
private void  saveCNIC_Front_ToFirbase() {
    progressDialog.setMessage("Savind CNIC Data...");
    progressDialog.show();
    progressDialog.setTitle("~~~CNIC DATA~~~");
    //save Data with Profile Image
    // Name and path of image
    String CNIC_Front = "CNIC_Images/" + "" +firebaseAuth.getUid()+"(~CNIC_FRONT~)";
    StorageReference storageReference= FirebaseStorage.getInstance().getReference(CNIC_Front);
    //Uploading Degree Image
    storageReference.putFile(uri_CNIC_Frnt)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get URL Of Uploaded Image
                    Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    Uri downloadImageUri1=uriTask.getResult();
                    if(uriTask.isSuccessful()){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("CNIC_Front", "" + downloadImageUri1); // URL Of Uploaded Image

                        //Save Data in Database
                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
                        reference.child(firebaseAuth.getUid()+"~Consultant~").child("CNIC").child("CNIC-FRONT").setValue(hashMap)
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
                                        Toast.makeText(Consultant_CNIC_Verify.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Consultant_CNIC_Verify.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


}
private void  saveCNIC_Back_ToFirebase(){
    //save Data with Profile Image
    // Name and path of image
    String CNIC_Back = "CNIC_Images/" + "" +firebaseAuth.getUid()+"(~CNIC_BACK~) ";
    StorageReference storageReference= FirebaseStorage.getInstance().getReference(CNIC_Back);
    //Uploading Specific Consultant Certificate Images
    storageReference.putFile(uri_CNIC_Bck)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get URL Of Uploaded Image
                    Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    Uri downloadImageUri=uriTask.getResult();
                    if(uriTask.isSuccessful()){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("CNIC_Back", "" + downloadImageUri); // URL Of Uploaded Image
                        //Save Data in Database
                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
                        reference.child(firebaseAuth.getUid()+"~Consultant~").child("CNIC").child("CNIC-BACK").setValue(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.dismiss();
                                        Toast.makeText(Consultant_CNIC_Verify.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Consultant_CNIC_Verify.this,login.class));
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Failed Updating DB
                                        progressDialog.dismiss();
                                        Toast.makeText(Consultant_CNIC_Verify.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Consultant_CNIC_Verify.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
}

    private void initialize(){
        CNIC_Frnt=(ImageView) findViewById(R.id.cnicFrnt_img);
        CNIC_Bck=(ImageView)findViewById(R.id.cnicBck_img);
        move_next=(ImageButton) findViewById(R.id.next_dashboard);
        CNICFrntBtn=(FloatingActionButton) findViewById(R.id.CNICFrntBtn);
        CNICBckBtn=(FloatingActionButton) findViewById(R.id.CNICBckBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialization Of Progress Dialog
        progressDialog = new ProgressDialog(Consultant_CNIC_Verify.this);
        progressDialog.setTitle("Please Wait ...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

}
