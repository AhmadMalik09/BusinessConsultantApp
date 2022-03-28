package pk.edu.uiit.businessconsultant;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class real_estate_consultancy extends AppCompatActivity {
  TextView real_estate,real_oppertunity,profit_loss,investment;
  TextView real_estate_ans,real_oppertunity_ans,profit_loss_ans,investment_ans;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.real_estate_consultancy);
        initialize();
        performance();
        real_estate_ans.setVisibility(View.GONE);
        real_oppertunity_ans.setVisibility(View.GONE);
        profit_loss_ans.setVisibility(View.GONE);
        investment_ans.setVisibility(View.GONE);
    }
    public void performance(){
        real_estate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                real_estate_ans.setVisibility(View.VISIBLE);
                real_oppertunity_ans.setVisibility(View.GONE);
                profit_loss_ans.setVisibility(View.GONE);
                investment_ans.setVisibility(View.GONE);
            }
        });
        real_oppertunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                real_estate_ans.setVisibility(View.GONE);
                real_oppertunity_ans.setVisibility(View.VISIBLE);
                profit_loss_ans.setVisibility(View.GONE);
                investment_ans.setVisibility(View.GONE);
            }
        });
        profit_loss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                real_estate_ans.setVisibility(View.GONE);
                real_oppertunity_ans.setVisibility(View.GONE);
                profit_loss_ans.setVisibility(View.VISIBLE);
                investment_ans.setVisibility(View.GONE);
            }
        });
        investment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                real_estate_ans.setVisibility(View.GONE);
                real_oppertunity_ans.setVisibility(View.GONE);
                profit_loss_ans.setVisibility(View.GONE);
                investment_ans.setVisibility(View.VISIBLE);
            }
        });
    }
    public void initialize(){
        real_estate=(TextView) findViewById(R.id.about_real_estate);
        real_oppertunity=(TextView) findViewById(R.id.options);
        profit_loss=(TextView) findViewById(R.id.profit_loss);
        investment=(TextView) findViewById(R.id.opportunity_against_investment);
        real_estate_ans=(TextView) findViewById(R.id.real_estate_answer);
        real_oppertunity_ans=(TextView) findViewById(R.id.investment_options_answer);
        profit_loss_ans=(TextView) findViewById(R.id.profit_loss_answer);
        investment_ans=(TextView) findViewById(R.id.opportunity_against_investment_ans);
    }

}
