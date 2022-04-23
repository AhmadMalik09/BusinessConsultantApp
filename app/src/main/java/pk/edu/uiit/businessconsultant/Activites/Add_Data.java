package pk.edu.uiit.businessconsultant.Activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    Spinner fieldSpinner;
    EditText Question,Answer;
    Button btnAddData;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    String Choices[];
    String Ques,Ans;
    String Field;


    private ProgressDialog progressDialog;
    BusinessInfo info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        initialize();
        performAction();

    }
    public void performAction(){
        Choices=getResources().getStringArray(R.array.Field_Selection);
        fieldSpinner=(Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,Choices);
        fieldSpinner.setAdapter(adapter);
        btnAddData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Saving Data...");
                progressDialog.show();
                Ques=Question.getText().toString();
                Ans=Answer.getText().toString();
                Field=fieldSpinner.getSelectedItem().toString().trim();
                info=new BusinessInfo(Ques,Ans,Field);
                if(TextUtils.isEmpty(Question.getText().toString())){
                    Toast.makeText(Add_Data.this, "Please Add Data", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(Answer.getText().toString())){
                    Toast.makeText(Add_Data.this, "Ans is must!!", Toast.LENGTH_SHORT).show();
                }
                Question.setText("");
                Answer.setText(" ");
             /*   DatabaseReference reference=database.getReference().child("Users");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                            String accountType = ""+dataSnapshot.child("accountType").getValue();
                            String Field=" "+dataSnapshot.child("field").getValue();
                            if(accountType.equals("Consultant")) {
                                if(Field.equals("Real-Estate")){
                                    RealQues=Question.getText().toString();
                                    RealAns=Answer.getText().toString();
                                    info=new BusinessInfo(RealQues,RealAns);
                                }
                                else if(Field.equals("Stock-Market")){
                                    StockQues=Question.getText().toString();
                                    StockAns=Answer.getText().toString();
                                    info=new BusinessInfo(StockQues,StockAns);

                                }
                                else if(Field.equals("Crypto-Currency")){
                                    CryptoQues=Question.getText().toString();
                                    CryptoAns=Answer.getText().toString();
                                    info=new BusinessInfo(CryptoQues,CryptoAns);
                                }
                                else if(Field.equals("E-Commerce")){
                                    ECommQues=Question.getText().toString();
                                    ECommAns=Answer.getText().toString();
                                    info=new BusinessInfo(ECommQues,ECommAns);
                                }
                                else if(Field.equals("Agriculture")){
                                    AgriQues=Question.getText().toString();
                                    AgriAns=Answer.getText().toString();
                                    info=new BusinessInfo(AgriQues,AgriAns);
                                }
                                else if(Field.equals("Farming")){
                                    FrmQues=Question.getText().toString();
                                    FrmAns=Answer.getText().toString();
                                   info= new BusinessInfo(FrmQues,FrmAns);
                                }
                                else if(Field.equals("IT")){
                                    IT_Q=Question.getText().toString();
                                    IT_ANS=Answer.getText().toString();
                                     info=new BusinessInfo(IT_Q,IT_ANS);
                                }
                                else if(Field.equals("Enterpreneur")){
                                    startUpQues =Question.getText().toString();
                                    startUpAns=Answer.getText().toString();
                                     info= new BusinessInfo(startUpQues,startUpAns);
                                }
                                else {
                                    Toast.makeText(Add_Data.this, "Error", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });  */
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