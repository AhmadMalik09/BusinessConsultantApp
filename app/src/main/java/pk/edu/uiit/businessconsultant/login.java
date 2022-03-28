package pk.edu.uiit.businessconsultant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    EditText email,password;
    Button btnlogin;
    TextView register , ForgetPass;
    FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    //DatabaseHelper databaseHelper;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        intialize();
        perform_action();
        register_now();
        forgetPassword();
    }

private void perform_action(){
    btnlogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String Email = email.getText().toString();
            String Password = password.getText().toString();
            if(TextUtils.isEmpty(Email)){
                email.setError("Email should not be empty");
                email.requestFocus();
            }
            else if(TextUtils.isEmpty(Password)){
                password.setError("Password should not be empty");
                password.requestFocus();
            }
            else {
                progressDialog.setMessage("Logging In...");
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(Email,Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        checkUserType();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed Logging In
                                progressDialog.dismiss();
                                Toast.makeText(login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


            }

        }


        private void checkUserType() {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.orderByChild("uid").equalTo(mAuth.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                           for (DataSnapshot ds: snapshot.getChildren()){
                                String accountType = ""+ds.child("accountType").getValue();
                                if (accountType.equals("Consultant"))
                                {
                                    progressDialog.dismiss();
                                    // User Is Consultant
                                    startActivity(new Intent(login.this, user_dashboard.class));
                                    finish();
                                }
                                if (accountType.equals("User"))
                                {
                                    progressDialog.dismiss();
                                    // User
                                    startActivity(new Intent(login.this, Consultant_Dashboard.class));
                                    finish();
                                }

                            }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(login.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    });
}
private void register_now(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this,register.class);
                login.this.startActivity(intent);
            }
        });
}
    public void forgetPassword(){
        ForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this,forget_Pass.class);
                startActivity(intent);
            }
        });
    }

private void intialize(){
       // databaseHelper =new DatabaseHelper(this);
        email=(EditText)findViewById(R.id.editTextEmail);
        password=(EditText) findViewById((R.id.editTextPassword));
        btnlogin=(Button) findViewById((R.id.btnlgn));
        register=(TextView) findViewById(R.id.reg);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        ForgetPass= (TextView) findViewById(R.id.forget_Pass);

}
}
