package pk.edu.uiit.businessconsultant.Activites;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pk.edu.uiit.businessconsultant.R;

public class Email extends AppCompatActivity {
    EditText to,subject,message;
    Button Btnsend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);
        initialize();
        performAction();
    }
    public void initialize(){
        to=(EditText) findViewById(R.id.emailAddress);
        subject=(EditText) findViewById(R.id.writeSubject);
        message=(EditText) findViewById(R.id.Message);
        Btnsend=(Button) findViewById(R.id.btnSent);
    }
    public void performAction(){

        Btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=to.getText().toString();
                String Subject=subject.getText().toString();
                String Message=message.getText().toString();
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{email});
                intent.putExtra(Intent.EXTRA_SUBJECT,Subject);
                intent.putExtra(Intent.EXTRA_TEXT,Message);
               try {
                    startActivity(intent);
               }
               catch (Exception e){
                   Toast.makeText(Email.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

}
