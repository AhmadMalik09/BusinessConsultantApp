package pk.edu.uiit.businessconsultant.Activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import pk.edu.uiit.businessconsultant.ModelClasses.BusinessInfo;
import pk.edu.uiit.businessconsultant.R;

public class Add_Data extends AppCompatActivity {
    EditText Question,Answer;
    Button btnAddData;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    public static  String questions,answers;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        initialize();
        performAction();
    }
    public void performAction(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Saving Data...");
                progressDialog.show();
                questions=Question.getText().toString();
                answers=Answer.getText().toString();
                if(questions.isEmpty()){
                    Toast.makeText(Add_Data.this, "Please Add Data", Toast.LENGTH_SHORT).show();
                }
                if(answers.isEmpty()){
                    Toast.makeText(Add_Data.this, "Ans is must!!", Toast.LENGTH_SHORT).show();
                }
                Question.setText("");
                Answer.setText(" ");
                BusinessInfo info=new BusinessInfo(firebaseAuth.getUid(),questions,answers);
                database=FirebaseDatabase.getInstance();
                database.getReference().child("BusinessInfo")
                        .child(firebaseAuth.getUid()+"~BusinessInfo~")
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


    public void initialize(){
        Question=(EditText) findViewById(R.id.QuestionBlock);
        Answer=(EditText) findViewById(R.id.AnswerBlock);
        btnAddData=(Button) findViewById(R.id.btnAddData);
        firebaseAuth = FirebaseAuth.getInstance();
        // Initialization Of Progress Dialog
        progressDialog = new ProgressDialog(Add_Data.this);
       progressDialog.setTitle("Please Wait ...");
       progressDialog.setCanceledOnTouchOutside(false);

    }
}