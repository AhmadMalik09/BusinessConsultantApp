package pk.edu.uiit.businessconsultant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Farming extends AppCompatActivity
{
    Button btnsubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farming);
        initialize();
        perform_action();
    }

    public void perform_action() {
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Farming.this, farming_consultancy.class);
                Farming.this.startActivity(intent);
            }
        });
    }
    public void initialize(){
        btnsubmit = (Button) findViewById(R.id.frming_btnSend);
    }
}
