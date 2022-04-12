package pk.edu.uiit.businessconsultant.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;
import pk.edu.uiit.businessconsultant.ModelClasses.Users;
import pk.edu.uiit.businessconsultant.R;


public class register extends AppCompatActivity {
    EditText etName, etEmail, etPass, etPhone;
    Button btnRegister;
    TextView register_consultant, login;
    String fullName, phoneNumber, email, password;
    String profileImage;
    ImageView Google, Facebook;
    CircleImageView Userprofile;
    FloatingActionButton uploadProfilePic;
    Uri Imageuri;
    String  accountType;
    // FirebaseAuth
    private FirebaseAuth firebaseAuth;
    // Progress Dialog
    private ProgressDialog progressDialog;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 123;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        initialize();
        perform_action();
        login_now();
        consultant_register();
        //Google Functions here....
        googleSiginRequest();

    }

    // Google SignIn Started From Here
    private void googleSiginRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id1))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(this, "Google SignIn Done", Toast.LENGTH_SHORT).show();
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {

                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        Imageuri= data.getData();
        Userprofile.setImageURI(Imageuri);
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(register.this, "signInWithCredential:success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(register.this, "signInWithCredential:failure", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            finish();
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user){
        Intent intent=new Intent(register.this,user_dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void perform_action() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Register User
                inputData();

            }
        });
        Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        Facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(register.this,Facebook_Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        uploadProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(register.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

//Firebase authentication with Email and Password started from here
            private void inputData() {

                // Get Data From Views
                fullName = etName.getText().toString();
                phoneNumber = etPhone.getText().toString();
                email = etEmail.getText().toString();
                password = etPass.getText().toString();
                accountType="User";
                profileImage=Imageuri.toString();

                // Set Validations of Data

                if (fullName.isEmpty()) {
                    Toast.makeText(register.this, "Enter Full Name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phoneNumber.isEmpty()) {
                    Toast.makeText(register.this, "Enter Phone Number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phoneNumber.length()!=11){
                    Toast.makeText(this, "Phone should be 11 digit", Toast.LENGTH_SHORT).show();
                }
                if(email.isEmpty()){
                    Toast.makeText(register.this, "Enter Your Email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(register.this, "Please Enter Valid Email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() <= 6) {
                    Toast.makeText(register.this, "Password Must be atleast 6 characters long!", Toast.LENGTH_SHORT).show();
                    return;
                }
                createAccount();
            }

            private void createAccount() {
                progressDialog.setMessage("Creating Account...");
                progressDialog.show();

                //Create Account In Database
                firebaseAuth.createUserWithEmailAndPassword(email, password)
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
                                // Failed creating Account
                                progressDialog.dismiss();
                                Toast.makeText(register.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            private void saverFirebaseData() {
                progressDialog.setTitle("Saving Account Information...");
                String timeStamp = "" + System.currentTimeMillis();
                //Saving Data Without Image
                if (Imageuri == null) {
                    Users user=new Users(firebaseAuth.getUid(),fullName,email,password,phoneNumber,accountType,profileImage);
                    // Setup Data to Save
                 /*   HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("uid", "" + firebaseAuth.getUid());
                    hashMap.put("email", "" + email);
                    hashMap.put("name", "" + fullName);
                    hashMap.put("Password", "" + password);
                    hashMap.put("phone", "" + phoneNumber);
                    hashMap.put("accountType", "User");
                    hashMap.put("ProfileImage","");
                    hashMap.put("online", "true"); */

                    // Save to DB
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    reference.child(firebaseAuth.getUid() + "~User~").setValue(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    // DB Updated
                                    progressDialog.dismiss();
                                    Toast.makeText(register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(register.this, login.class));
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Failed Updating DB
                                    progressDialog.dismiss();
                                    Toast.makeText(register.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                }
            else{
                    //save Data with Profile Image
                    // Name and path of image
                    String filePathAndName = "User_Profile/" + "" +firebaseAuth.getUid();
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
                                        Users user=new Users(firebaseAuth.getUid(),fullName,email,password,phoneNumber,accountType,profileImage);
                                       /* HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("uid", "" + firebaseAuth.getUid());
                                        hashMap.put("email", "" + email);
                                        hashMap.put("name", "" + fullName);
                                        hashMap.put("Password", "" + password);
                                        hashMap.put("phone", "" + phoneNumber);
                                        hashMap.put("accountType", "User");
                                        hashMap.put("profileImage", "" + downloadImageUri); // URL Of Uploaded Image
                                        hashMap.put("online", "true"); */
                                        //Save Data in Database
                                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
                                        reference.child(firebaseAuth.getUid()+"~User~").setValue(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(register.this, login.class));
                                                        finish();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Failed Updating DB
                                                        progressDialog.dismiss();
                                                        Toast.makeText(register.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(register.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
                private void login_now() {
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(register.this, login.class);
                        register.this.startActivity(intent);
                    }
                });
            }

            private void consultant_register() {
                register_consultant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(register.this, Consultant_Registeration.class);
                        register.this.startActivity(intent);
                    }
                });
            }

            private void initialize() {

                etName = findViewById(R.id.etName);
                etEmail = findViewById(R.id.etEmail);
                etPass = findViewById(R.id.etPass);
                etPhone = findViewById(R.id.etPhone);
                register_consultant = (TextView) findViewById(R.id.consultant_registeration);
                login = (TextView) findViewById(R.id.go_login);
                Userprofile=(CircleImageView) findViewById(R.id.userProfile);
                uploadProfilePic=(FloatingActionButton) findViewById(R.id.profileBtn);
                btnRegister = (Button) findViewById(R.id.btnRegister);
                Google = (ImageView) findViewById(R.id.googleSigIn);
                Facebook=(ImageView)findViewById(R.id.fbBtn);
                // Initialization Of FirebaseAuth
                firebaseAuth = FirebaseAuth.getInstance();

                // Initialization Of Progress Dialog
                progressDialog = new ProgressDialog(register.this);
                progressDialog.setTitle("Please Wait ...");
                progressDialog.setCanceledOnTouchOutside(false);
            }


}