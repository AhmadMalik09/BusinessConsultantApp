package pk.edu.uiit.businessconsultant;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class crypto_consultancy extends AppCompatActivity {
    TextView crypto,platforms,profit_loss,signals,Technical_terms;
    TextView  crypto_ans,platforms_ans,profit_loss_ans,signals_ans,Technical_terms_ans;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crpyto_consultancny);
        initialize();
        performance();
        crypto_ans.setVisibility(View.GONE);
        platforms_ans.setVisibility(View.GONE);
        profit_loss_ans.setVisibility(View.GONE);
        signals_ans.setVisibility(View.GONE);
        Technical_terms_ans.setVisibility(View.GONE);
    }
    public void performance(){
        crypto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crypto_ans.setVisibility(View.VISIBLE);
                platforms_ans.setVisibility(View.GONE);
                profit_loss_ans.setVisibility(View.GONE);
                signals_ans.setVisibility(View.GONE);
                Technical_terms_ans.setVisibility(View.GONE);
            }
        });
        platforms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crypto_ans.setVisibility(View.GONE);
                platforms_ans.setVisibility(View.VISIBLE);
                profit_loss_ans.setVisibility(View.GONE);
                signals_ans.setVisibility(View.GONE);
                Technical_terms_ans.setVisibility(View.GONE);
            }
        });
        profit_loss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crypto_ans.setVisibility(View.GONE);
                platforms_ans.setVisibility(View.GONE);
                profit_loss_ans.setVisibility(View.VISIBLE);
                signals_ans.setVisibility(View.GONE);
                Technical_terms_ans.setVisibility(View.GONE);
            }
        });
        signals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crypto_ans.setVisibility(View.GONE);
                platforms_ans.setVisibility(View.GONE);
                profit_loss_ans.setVisibility(View.GONE);
                signals_ans.setVisibility(View.VISIBLE);
                Technical_terms_ans.setVisibility(View.GONE);
            }
        });
        Technical_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crypto_ans.setVisibility(View.GONE);
                platforms_ans.setVisibility(View.GONE);
                profit_loss_ans.setVisibility(View.GONE);
                signals_ans.setVisibility(View.GONE);
                Technical_terms_ans.setVisibility(View.VISIBLE);
            }
        });
    }
    public void initialize(){
        crypto=(TextView) findViewById(R.id.about_crypto);
        platforms=(TextView) findViewById(R.id.crypto_platforms);
        profit_loss=(TextView) findViewById(R.id.profit_loss_crypto);
        signals=(TextView) findViewById(R.id.crypto_signals);
        Technical_terms=(TextView)findViewById(R.id.Technical_terms) ;
        crypto_ans=(TextView) findViewById(R.id.crypto_ans);
        platforms_ans=(TextView) findViewById(R.id.crypto_platforms_answer);
        profit_loss_ans=(TextView) findViewById(R.id.crypto_profit_loss_answer);
        signals_ans=(TextView) findViewById(R.id.crypto_signals_ans);
        Technical_terms_ans=(TextView) findViewById(R.id.Technical_terms_ans);
    }

}
