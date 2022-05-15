package pk.edu.uiit.businessconsultant.Activites;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import pk.edu.uiit.businessconsultant.ModelClasses.Reviews;
import pk.edu.uiit.businessconsultant.R;

public class feedback extends AppCompatActivity {
    TextView experience;
    RatingBar ratingBar;
    EditText reveiw;
    Button feedback;
    String consultantUID ;
    private FirebaseAuth firebaseAuth;
    public static String reviewerUid;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        consultantUID=getIntent().getStringExtra("consultantUID");
        //Will use this ID Loading Review in Consultant Dashboard
    //    reviewerUid=firebaseAuth.getUid();
        initialize();
        performAction();
    }
    public void performAction(){
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating==0){
                    experience.setText("Worst");
                }
                else if(rating==1){
                    experience.setText("Bad");
                }
                else if(rating==2){
                    experience.setText("Fair");
                }
                else if(rating==3){
                    experience.setText("Good");
                }
                else if(rating==4){
                    experience.setText("Very Good");
                }
                else if(rating==5){
                    experience.setText("Exellent");
                }

            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
            }

        });
    }
    public void inputData(){


        String ratings = ""+ratingBar.getRating();
        String Review = reveiw.getText().toString();
        String timestamp = ""+System.currentTimeMillis();
        // Setup Data In Hashmap
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Rating",""+ratings);
        hashMap.put("Review",""+Review);
        hashMap.put("timestamp", ""+ timestamp);
        hashMap.put("uid",firebaseAuth.getUid());
       Reviews reviews=new Reviews(ratings,Review,firebaseAuth.getUid(),timestamp);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(consultantUID+"~Consultant~").child("Ratings").child(firebaseAuth.getUid()).setValue(reviews)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(feedback.this, "Thanks Your Review Added", Toast.LENGTH_SHORT).show();
                    }
                });


    }
    public void initialize(){

        // Initialization Of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        experience=(TextView) findViewById(R.id.ratingText);
        ratingBar=(RatingBar) findViewById(R.id.ratingbar);
        reveiw=(EditText) findViewById(R.id.feedback);
        feedback=(Button) findViewById(R.id.btnFeedback);
    }


}
