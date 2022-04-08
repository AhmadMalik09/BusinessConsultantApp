package pk.edu.uiit.businessconsultant.Activites;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pk.edu.uiit.businessconsultant.R;

public class stock_market_consultancy extends AppCompatActivity {
    TextView stock_market,stock_platforms,stock_profit,trading,Technical_terms;
    TextView  stock_market_ans,stock_platforms_ans,stock_profit_ans,trading_ans,Technical_terms_ans;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_market_consultancy);
        initialize();
        performance();
        stock_market_ans.setVisibility(View.GONE);
        stock_platforms_ans.setVisibility(View.GONE);
        stock_profit_ans.setVisibility(View.GONE);
        trading_ans.setVisibility(View.GONE);
        Technical_terms_ans.setVisibility(View.GONE);
    }
    public void performance(){
        stock_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stock_market_ans.setVisibility(View.VISIBLE);
                stock_platforms_ans.setVisibility(View.GONE);
                stock_profit_ans.setVisibility(View.GONE);
                trading_ans.setVisibility(View.GONE);
                Technical_terms_ans.setVisibility(View.GONE);
            }
        });
        stock_platforms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stock_market_ans.setVisibility(View.GONE);
                stock_platforms_ans.setVisibility(View.VISIBLE);
                stock_profit_ans.setVisibility(View.GONE);
                trading_ans.setVisibility(View.GONE);
                Technical_terms_ans.setVisibility(View.GONE);
            }
        });
        stock_profit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stock_market_ans.setVisibility(View.GONE);
                stock_platforms_ans.setVisibility(View.GONE);
                stock_profit_ans.setVisibility(View.VISIBLE);
                trading_ans.setVisibility(View.GONE);
                Technical_terms_ans.setVisibility(View.GONE);
            }
        });
        trading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stock_market_ans.setVisibility(View.GONE);
                stock_platforms_ans.setVisibility(View.GONE);
                stock_profit_ans.setVisibility(View.GONE);
                trading_ans.setVisibility(View.VISIBLE);
                Technical_terms_ans.setVisibility(View.GONE);
            }
        });
        Technical_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stock_market_ans.setVisibility(View.GONE);
                stock_platforms_ans.setVisibility(View.GONE);
                stock_profit_ans.setVisibility(View.GONE);
                trading_ans.setVisibility(View.GONE);
                Technical_terms_ans.setVisibility(View.VISIBLE);
            }
        });
    }
    public void initialize(){
        stock_market=(TextView) findViewById(R.id.about_s_market);
        stock_platforms=(TextView) findViewById(R.id.stock_platforms);
        stock_profit=(TextView) findViewById(R.id.profit_loss_stock);
        trading=(TextView) findViewById(R.id.stock_trading);
        Technical_terms=(TextView)findViewById(R.id.stock_Technical_terms) ;
        stock_market_ans=(TextView) findViewById(R.id.stock_ans);
        stock_platforms_ans=(TextView) findViewById(R.id.stock_platforms_answer);
        stock_profit_ans=(TextView) findViewById(R.id.stock_profit_loss_answer);
        trading_ans=(TextView) findViewById(R.id.stock_trading_ans);
        Technical_terms_ans=(TextView) findViewById(R.id.Technical_terms_dtl);
    }
}
