package pk.edu.uiit.businessconsultant.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pk.edu.uiit.businessconsultant.ModelClasses.loading_Consultants;
import pk.edu.uiit.businessconsultant.R;

public class IT_Consultancy extends AppCompatActivity {
    TextView IT_Business,IT_startup,IT_setup,Team_Building;
    TextView IT_Business_ans,IT_startup_ans,IT_setup_ans,Team_Building_ans;
    Button goForChat;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_tect_consultancy);
        initialize();
        performance();
        IT_Business_ans.setVisibility(View.GONE);
        IT_startup_ans.setVisibility(View.GONE);
        IT_setup_ans.setVisibility(View.GONE);
        Team_Building_ans.setVisibility(View.GONE);
    }
    public void performance(){
        IT_Business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IT_Business_ans.setVisibility(View.VISIBLE);
                IT_startup_ans.setVisibility(View.GONE);
                IT_setup_ans.setVisibility(View.GONE);
                Team_Building_ans.setVisibility(View.GONE);
            }
        });
        IT_startup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IT_Business_ans.setVisibility(View.GONE);
                IT_startup_ans.setVisibility(View.VISIBLE);
                IT_setup_ans.setVisibility(View.GONE);
                Team_Building_ans.setVisibility(View.GONE);
            }
        });
        IT_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IT_Business_ans.setVisibility(View.GONE);
                IT_startup_ans.setVisibility(View.GONE);
                IT_setup_ans.setVisibility(View.VISIBLE);
                Team_Building_ans.setVisibility(View.GONE);
            }
        });
        Team_Building.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IT_Business_ans.setVisibility(View.GONE);
                IT_startup_ans.setVisibility(View.GONE);
                IT_setup_ans.setVisibility(View.GONE);
                Team_Building_ans.setVisibility(View.VISIBLE);
            }
        });
        goForChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IT_Consultancy.this, loading_Consultants.class);
                startActivity(intent);
            }
        });
    }
    public void initialize(){
        IT_Business=(TextView) findViewById(R.id.IT_business);
        IT_startup=(TextView) findViewById(R.id.IT_startup);
        IT_setup=(TextView) findViewById(R.id.setup_location);
        Team_Building=(TextView) findViewById(R.id.IT_team);
        IT_Business_ans=(TextView) findViewById(R.id.IT_business_Dtl);
        IT_startup_ans=(TextView) findViewById(R.id.IT_startup_dtl);
        IT_setup_ans=(TextView) findViewById(R.id.setup_location_dtl);
        Team_Building_ans=(TextView) findViewById(R.id.team_solution);
        goForChat=(Button) findViewById(R.id.IT_consultant);
    }

}
