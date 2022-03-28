package pk.edu.uiit.businessconsultant;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ecommerce_consultancy extends AppCompatActivity {
    TextView product,profit,store,prod_marketing;
    TextView product_ans,profit_ans,store_ans,prod_marketing_ans;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_commerce_consultancy);
        initialize();
        performance();
        product_ans.setVisibility(View.GONE);
        profit_ans.setVisibility(View.GONE);
        store_ans.setVisibility(View.GONE);
        prod_marketing_ans.setVisibility(View.GONE);
    }
    public void performance(){
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_ans.setVisibility(View.VISIBLE);
                profit_ans.setVisibility(View.GONE);
                store_ans.setVisibility(View.GONE);
                prod_marketing_ans.setVisibility(View.GONE);
            }
        });
        profit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_ans.setVisibility(View.GONE);
                profit_ans.setVisibility(View.VISIBLE);
                store_ans.setVisibility(View.GONE);
                prod_marketing_ans.setVisibility(View.GONE);
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_ans.setVisibility(View.GONE);
                profit_ans.setVisibility(View.GONE);
                store_ans.setVisibility(View.VISIBLE);
                prod_marketing_ans.setVisibility(View.GONE);
            }
        });
        prod_marketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_ans.setVisibility(View.GONE);
                profit_ans.setVisibility(View.GONE);
                store_ans.setVisibility(View.GONE);
                prod_marketing_ans.setVisibility(View.VISIBLE);
            }
        });
    }
    public void initialize(){
        product=(TextView) findViewById(R.id.Ecom_product);
        profit=(TextView) findViewById(R.id.ecomm_profit);
        store=(TextView) findViewById(R.id.ecomm_store);
        prod_marketing=(TextView) findViewById(R.id.product_marketing);
        product_ans=(TextView) findViewById(R.id.abt_product_answer);
        profit_ans=(TextView) findViewById(R.id.ecomm_profit_answer);
        store_ans=(TextView) findViewById(R.id.store_detail);
        prod_marketing_ans=(TextView) findViewById(R.id.product_marketing_ans);
    }

}
