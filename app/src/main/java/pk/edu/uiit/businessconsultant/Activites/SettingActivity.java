package pk.edu.uiit.businessconsultant.Activites;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import pk.edu.uiit.businessconsultant.ModelClasses.loading_reviews;
import pk.edu.uiit.businessconsultant.ModelClasses.loading_users;
import pk.edu.uiit.businessconsultant.R;

public class SettingActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView updatePass,privacyPolicy,howToUse,logout,Help;
    TextView privacyPolicyAns,HowToUse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        initialization();
        bottomNagivationBar();
        performAction();
    }
    public void initialization(){
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottomNavigationView);
        updatePass=(TextView) findViewById(R.id.tv1);
        privacyPolicy=(TextView) findViewById(R.id.tv2);
        howToUse=(TextView) findViewById(R.id.tv3);
        logout=(TextView) findViewById(R.id.tv4);
        privacyPolicyAns=(TextView) findViewById(R.id.privacypolicy);
        HowToUse=(TextView) findViewById(R.id.how_to_use);
        Help=(TextView) findViewById(R.id.tv5);

    }
    public void performAction(){
        updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),forget_Pass.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("Confirm Exit...");
                builder.setIcon(R.drawable.icon_lgout);
                builder.setMessage("Are you sure, want to Exit ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent=new Intent(getApplicationContext(),login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                SettingActivity.this.startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });
        //Show Privacy Policy
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                privacyPolicyAns.setVisibility(View.VISIBLE);
                HowToUse.setVisibility(View.GONE);
                privacyPolicyAns.setMovementMethod(new ScrollingMovementMethod());
            }
        });
        howToUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HowToUse.setVisibility(View.VISIBLE);
                privacyPolicyAns.setVisibility(View.GONE);
            }
        });
        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Email.class));
            }
        });
    }
    public void bottomNagivationBar(){
        bottomNavigationView.setSelectedItemId(R.id.setting);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),Consultant_Dashboard.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.chat:
                        startActivity(new Intent(getApplicationContext(), loading_users.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.review:
                        startActivity(new Intent(getApplicationContext(), loading_reviews.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.setting:
                        return true;
                }
                return false;
            }
        });
    }
}