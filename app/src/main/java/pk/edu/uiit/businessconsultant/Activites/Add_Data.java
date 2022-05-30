package pk.edu.uiit.businessconsultant.Activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pk.edu.uiit.businessconsultant.ModelClasses.BusinessInfo;
import pk.edu.uiit.businessconsultant.R;

public class Add_Data extends AppCompatActivity {
    EditText Question,Answer;
    Button btnAddData;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    String Ques,Ans;
    String consultantField;
    TextView field;


    private ProgressDialog progressDialog;
    BusinessInfo info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        initialize();
        performAction();
        getField();
    }
    public void performAction(){
        btnAddData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Saving Data...");
                progressDialog.show();
                Ques=Question.getText().toString();
                Ans=Answer.getText().toString();
                consultantField=field.getText().toString();
                info=new BusinessInfo(Ques,Ans,consultantField);
                if(TextUtils.isEmpty(Question.getText().toString())){
                    Toast.makeText(Add_Data.this, "Please Add Data", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(Answer.getText().toString())){
                    Toast.makeText(Add_Data.this, "Ans is must!!", Toast.LENGTH_SHORT).show();
                }
                Question.setText("");
                Answer.setText(" ");
                database=FirebaseDatabase.getInstance();
                database.getReference().child("BusinessInfo")
                        .child("Info")
                        .push()
                        .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(Add_Data.this, "Data Added", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
    public void getField(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            // Get Data From Database (Firebase)
                            String Field = ""+ds.child("field").getValue();
                            // Set Data To Main Seller Activity Views
                            field.setText(Field);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public void initialize(){
        Question=(EditText) findViewById(R.id.QuestionBlock);
        Answer=(EditText) findViewById(R.id.AnswerBlock);
        btnAddData=(Button) findViewById(R.id.btnAddData);
        firebaseAuth = FirebaseAuth.getInstance();
        field=(TextView)findViewById(R.id.Consultantfield);
        // Initialization Of Progress Dialog
        progressDialog = new ProgressDialog(Add_Data.this);
        progressDialog.setTitle("Please Wait ...");
        progressDialog.setCanceledOnTouchOutside(false);

    }
}