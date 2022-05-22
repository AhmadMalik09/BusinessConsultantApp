package pk.edu.uiit.businessconsultant.Activites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pk.edu.uiit.businessconsultant.ModelClasses.Portfolio;
import pk.edu.uiit.businessconsultant.R;

public class consultantPortfolio extends AppCompatActivity {
    EditText EducationalInstitutes,awards,working_org,nationality,experience,areaOfInterest,languages,goals,NoOfAwards;
    String EducationalInstitute,Awards,Working_org,Nationality,Experience,AreaOfInterest,Languages,Goals,AwardsCount;
    Button submitBtn;
    ImageView bckBtn;
    Portfolio portfolio;
    FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_portfolio);
        initialization();
        performAction();
    }
    public void performAction(){
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Please Wait ...");
                progressDialog.setMessage("Saving Portfolio Information...");
                progressDialog.show();
                savingPortfolioInfo();
            }
        });
        bckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void savingPortfolioInfo(){
        EducationalInstitute=EducationalInstitutes.getText().toString();
        Awards=awards.getText().toString();
        Working_org=working_org.getText().toString();
        Nationality=nationality.getText().toString();
        Experience=experience.getText().toString();
        AreaOfInterest=areaOfInterest.getText().toString();
        Languages=languages.getText().toString();
        Goals=goals.getText().toString();
        AwardsCount=NoOfAwards.getText().toString();
        portfolio=new Portfolio(firebaseAuth.getUid(),EducationalInstitute,Awards,Working_org,Experience,AreaOfInterest,Languages,Goals,Nationality,AwardsCount);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()+"~Consultant~").child("Portfolio").setValue(portfolio)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(consultantPortfolio.this, "Portfolio Updated", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public void initialization(){
        EducationalInstitutes=(EditText) findViewById(R.id.edu_institutes);
        awards=(EditText) findViewById(R.id.awards);
        working_org=(EditText) findViewById(R.id.working_insitutes);
        nationality=(EditText)findViewById(R.id.nationality);
        experience=(EditText) findViewById(R.id.experience);
        areaOfInterest=(EditText) findViewById(R.id.hobbies);
        languages=(EditText) findViewById(R.id.languages);
        goals=(EditText) findViewById(R.id.goals);
        NoOfAwards=(EditText)findViewById(R.id.noOfAward);
        submitBtn=(Button) findViewById(R.id.btnSubmit);
        bckBtn=(ImageView)findViewById(R.id.portFolioBckBtn) ;
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(consultantPortfolio.this);
        progressDialog.setTitle("Please Wait ...");
        progressDialog.setCanceledOnTouchOutside(false);
    }
}
