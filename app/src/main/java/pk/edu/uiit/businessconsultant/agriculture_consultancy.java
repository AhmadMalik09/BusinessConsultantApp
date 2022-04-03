package pk.edu.uiit.businessconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class agriculture_consultancy extends AppCompatActivity {
    TextView crop,season,agri_profit,methedology;
    TextView crop_ans,season_ans,agri_profit_ans,methedology_ans;
    Button goForChat;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agriculture_consultancy);
        initialize();
        performance();
        crop_ans.setVisibility(View.GONE);
        season_ans.setVisibility(View.GONE);
        agri_profit_ans.setVisibility(View.GONE);
        methedology_ans.setVisibility(View.GONE);
    }
    public void performance(){
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crop_ans.setVisibility(View.VISIBLE);
                season_ans.setVisibility(View.GONE);
                agri_profit_ans.setVisibility(View.GONE);
                methedology_ans.setVisibility(View.GONE);
            }
        });
        season.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crop_ans.setVisibility(View.GONE);
                season_ans.setVisibility(View.VISIBLE);
                agri_profit_ans.setVisibility(View.GONE);
                methedology_ans.setVisibility(View.GONE);
            }
        });
        agri_profit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crop_ans.setVisibility(View.GONE);
                season_ans.setVisibility(View.GONE);
                agri_profit_ans.setVisibility(View.VISIBLE);
                methedology_ans.setVisibility(View.GONE);
            }
        });
        methedology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crop_ans.setVisibility(View.GONE);
                season_ans.setVisibility(View.GONE);
                agri_profit_ans.setVisibility(View.GONE);
                methedology_ans.setVisibility(View.VISIBLE);
            }
        });
        goForChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(agriculture_consultancy.this,loading_Consultants.class);
                startActivity(intent);
            }
        });
    }
    public void initialize(){
        crop=(TextView) findViewById(R.id.about_crop);
        season=(TextView) findViewById(R.id.season);
        agri_profit=(TextView) findViewById(R.id.agri_profit);
        methedology=(TextView) findViewById(R.id.plantination);
        crop_ans=(TextView) findViewById(R.id.abt_crop_answer);
        season_ans=(TextView) findViewById(R.id.abt_seasonr);
        agri_profit_ans=(TextView) findViewById(R.id.agri_profit_detail);
        methedology_ans=(TextView) findViewById(R.id.plantination_way);
        goForChat=(Button) findViewById(R.id.agri_consultant);
    }

}
