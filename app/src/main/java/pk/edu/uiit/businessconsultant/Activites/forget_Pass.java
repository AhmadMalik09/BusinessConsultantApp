package pk.edu.uiit.businessconsultant.Activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import pk.edu.uiit.businessconsultant.R;

public class forget_Pass extends AppCompatActivity {
  Button ForgetBtn;
  EditText email;
  ImageView back_btn;
  String Email;
    // FirebaseAuth
    private FirebaseAuth firebaseAuth;

    // Progress Dialog
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        initialize();
        performAction();
    }
    public void initialize(){
        ForgetBtn=(Button)findViewById(R.id.btnFrgt);
        email=(EditText) findViewById(R.id.eTEmail);
        back_btn=(ImageView) findViewById(R.id.forget_password_back_btn);
        // Initialization Of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialization Of Progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait ...");
        progressDialog.setCanceledOnTouchOutside(false);
    }
    public void performAction(){
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        ForgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        Email=email.getText().toString().trim();
        // Validation
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
        {
            Toast.makeText(forget_Pass.this, "Invalid Email!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Sending Instructions to Reset Password!");
        progressDialog.show();

        firebaseAuth.sendPasswordResetEmail(Email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Instructions Sent
                        progressDialog.dismiss();
                        Toast.makeText(forget_Pass.this, "Password Reset Instructions Sent to Your Email!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Failed sending Instructions
                        progressDialog.dismiss();
                        Toast.makeText(forget_Pass.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}