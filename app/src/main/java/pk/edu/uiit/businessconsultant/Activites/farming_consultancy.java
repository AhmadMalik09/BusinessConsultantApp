package pk.edu.uiit.businessconsultant.Activites;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pk.edu.uiit.businessconsultant.R;

public class farming_consultancy extends AppCompatActivity {
    TextView farming,equipment,profit,methodology;
    TextView farming_ans,equipment_dtl,profit_ans,methodology_ans;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farming_consultancy);
        initialize();
        performance();
        farming_ans.setVisibility(View.GONE);
        equipment_dtl.setVisibility(View.GONE);
        profit_ans.setVisibility(View.GONE);
        methodology_ans.setVisibility(View.GONE);
    }
    public void performance(){
        farming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                farming_ans.setVisibility(View.VISIBLE);
                equipment_dtl.setVisibility(View.GONE);
                profit_ans.setVisibility(View.GONE);
                methodology_ans.setVisibility(View.GONE);
            }
        });
        equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                farming_ans.setVisibility(View.GONE);
                equipment_dtl.setVisibility(View.VISIBLE);
                profit_ans.setVisibility(View.GONE);
                methodology_ans.setVisibility(View.GONE);
            }
        });
        profit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                farming_ans.setVisibility(View.GONE);
                equipment_dtl.setVisibility(View.GONE);
                profit_ans.setVisibility(View.VISIBLE);
                methodology_ans.setVisibility(View.GONE);
            }
        });
        methodology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                farming_ans.setVisibility(View.GONE);
                equipment_dtl.setVisibility(View.GONE);
                profit_ans.setVisibility(View.GONE);
                methodology_ans.setVisibility(View.VISIBLE);
            }
        });
    }
    public void initialize(){
        farming=(TextView) findViewById(R.id.about_farming);
        equipment=(TextView) findViewById(R.id.equipment);
        profit=(TextView) findViewById(R.id.farming_profit);
        methodology=(TextView) findViewById(R.id.farming_methodology);
        farming_ans=(TextView) findViewById(R.id.farming_details);
        equipment_dtl=(TextView) findViewById(R.id.equipment_answer);
        profit_ans=(TextView) findViewById(R.id.farming_profit_ans);
        methodology_ans=(TextView) findViewById(R.id.farming_methodology_ans);
    }

}
