package pk.edu.uiit.businessconsultant.Activites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import pk.edu.uiit.businessconsultant.R;

public class splash extends AppCompatActivity {
  //  Animation top_animation , bottom_animation;
  //  ImageView image;
    TextView progress;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    int progressValue=0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        //intialize();
        progress=(TextView)findViewById(R.id.progress);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        firebaseAuth=FirebaseAuth.getInstance();
        Activity activity=splash.this;
        progressBar.setProgress(progressValue);
        progressBar.setMax(100);

       Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                progressValue = progressValue+1;
                progressBar.setProgress(progressValue);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setText(String.valueOf(progressValue+"%"));
                    }
                });
                if(progressBar.getProgress()>=100){
                    timer.cancel();
                    FirebaseUser user=firebaseAuth.getInstance().getCurrentUser();
                    if(user==null){
                        startActivity(new Intent(splash.this,login.class));
                        finish();
                    }
                    if(user!=null){
                        //It will check usertype then allow user to move their respected dashboard
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                        reference.orderByChild("uid").equalTo(firebaseAuth.getUid())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot ds: snapshot.getChildren()){
                                            String accountType = ""+ds.child("accountType").getValue();
                                            if (accountType.equals("Consultant"))
                                            {

                                                // User Is Consultant
                                                startActivity(new Intent(splash.this, Consultant_Dashboard.class));
                                                finish();
                                            }
                                            if (accountType.equals("User"))
                                            {

                                                // User
                                                startActivity(new Intent(splash.this, user_dashboard.class));
                                                finish();
                                            }

                                        }

                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(splash.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }

            }
        },1000,50);

   /*     Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               /* Intent intent = new Intent(splash.this,login.class);
                startActivity(intent);
                finish();
                FirebaseUser user=firebaseAuth.getInstance().getCurrentUser();
                if(user==null){
                    startActivity(new Intent(splash.this,login.class));
                    finish();
                }
                if(user!=null){
                    //It will check usertype then allow user to move their respected dashboard
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                        reference.orderByChild("uid").equalTo(firebaseAuth.getUid())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot ds: snapshot.getChildren()){
                                            String accountType = ""+ds.child("accountType").getValue();
                                            if (accountType.equals("Consultant"))
                                            {

                                                // User Is Consultant
                                                startActivity(new Intent(splash.this, Consultant_Dashboard.class));
                                                finish();
                                            }
                                            if (accountType.equals("User"))
                                            {

                                                // User
                                                startActivity(new Intent(splash.this, user_dashboard.class));
                                                finish();
                                            }

                                        }

                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(splash.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

            }

        },3000);  */


    }


    private void intialize(){
   //     top_animation= AnimationUtils.loadAnimation(this,R.anim.splash_animation);
   //     bottom_animation=AnimationUtils.loadAnimation(this,R.anim.splash_bottom);
        //    image.setAnimation(top_animation);
        progress=(TextView)findViewById(R.id.progress);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);


        firebaseAuth=FirebaseAuth.getInstance();
    }

}
