package pk.edu.uiit.businessconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ECommerce extends AppCompatActivity {
    Button btnsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_commerce);
        initialize();
        perform_action();
    }

    public void perform_action() {
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ECommerce.this, ecommerce_consultancy.class);
                ECommerce.this.startActivity(intent);
            }
        });
    }
        public void initialize(){
            btnsubmit = (Button) findViewById(R.id.btnSend_Ecomm);
        }
    }

