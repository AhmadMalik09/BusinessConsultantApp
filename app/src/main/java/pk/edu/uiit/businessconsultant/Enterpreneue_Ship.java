package pk.edu.uiit.businessconsultant;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Enterpreneue_Ship extends AppCompatActivity {
    TextView entrepreneurship,new_startup,new_setup,new_Team_Building;
    TextView entrepreneurship_dtl,new_startup_ans,new_setup_ans,new_Team_Building_ans;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrepreneurship);
        initialize();
        performance();
        entrepreneurship_dtl.setVisibility(View.GONE);
        new_startup_ans.setVisibility(View.GONE);
        new_setup_ans.setVisibility(View.GONE);
        new_Team_Building_ans.setVisibility(View.GONE);
    }
    public void performance(){
        entrepreneurship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entrepreneurship_dtl.setVisibility(View.VISIBLE);
                new_startup_ans.setVisibility(View.GONE);
                new_setup_ans.setVisibility(View.GONE);
                new_Team_Building_ans.setVisibility(View.GONE);
            }
        });
        new_startup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entrepreneurship_dtl.setVisibility(View.GONE);
                new_startup_ans.setVisibility(View.VISIBLE);
                new_setup_ans.setVisibility(View.GONE);
                new_Team_Building_ans.setVisibility(View.GONE);
            }
        });
        new_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entrepreneurship_dtl.setVisibility(View.GONE);
                new_startup_ans.setVisibility(View.GONE);
                new_setup_ans.setVisibility(View.VISIBLE);
                new_Team_Building_ans.setVisibility(View.GONE);
            }
        });
        new_Team_Building.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entrepreneurship_dtl.setVisibility(View.GONE);
                new_startup_ans.setVisibility(View.GONE);
                new_setup_ans.setVisibility(View.GONE);
                new_Team_Building_ans.setVisibility(View.VISIBLE);
            }
        });
    }
    public void initialize(){
        entrepreneurship=(TextView) findViewById(R.id.enterpreneurship);
        new_startup=(TextView) findViewById(R.id.new_startup);
        new_setup=(TextView) findViewById(R.id.new_setup_location);
        new_Team_Building=(TextView) findViewById(R.id.enterpreneur_team);
        entrepreneurship_dtl=(TextView) findViewById(R.id.enterpreneurship_Dtl);
        new_startup_ans=(TextView) findViewById(R.id.new_startup_dtl);
        new_setup_ans=(TextView) findViewById(R.id.new_setup_dtl);
        new_Team_Building_ans=(TextView) findViewById(R.id.enterpreneur_team_building);
    }

}
