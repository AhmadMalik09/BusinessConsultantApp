package pk.edu.uiit.businessconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class real_estate extends AppCompatActivity {
    Button btnsubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.real_state_form);
        initialize();
        perform_action();
    }
    public void perform_action(){
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(real_estate.this,real_estate_consultancy.class);
                real_estate.this.startActivity(intent);
            }
        });
    }
    public void initialize(){
        btnsubmit=(Button) findViewById(R.id.btn_estate_submit);
    }

}
