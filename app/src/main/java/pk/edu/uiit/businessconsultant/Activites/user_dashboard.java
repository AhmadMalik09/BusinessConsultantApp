package pk.edu.uiit.businessconsultant.Activites;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import pk.edu.uiit.businessconsultant.R;

public class user_dashboard extends AppCompatActivity {
    Button real_estate,stock_market,crypto,e_commerce,agriculture,farming,IT,new_startup;
    ImageView logout;
    TextView Name;
    FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_dashboard);

        initialize();
        performAction();
        subscribeToTopic();
    }
    @Override
   public void onStart(){
        super.onStart();

        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount!=null){
             Name.setText(signInAccount.getDisplayName());

        }
        FirebaseUser user=mAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(user_dashboard.this,login.class));
        }
        if(user!=null){
            loadMyInfo();
        }

    }
    private void loadMyInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.orderByChild("uid").equalTo(mAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            // Get Data From Database (Firebase)
                            String name = ""+ds.child("name").getValue();
                            // Set Data To  UserDashboard
                            Name.setText(name);

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void  performAction(){
        real_estate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(user_dashboard.this,real_estate_consultancy.class);
                user_dashboard.this.startActivity(intent);
            }
        });
        e_commerce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(user_dashboard.this,ECommerce.class);
                user_dashboard.this.startActivity(intent);
            }
        });
        agriculture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(user_dashboard.this,agriculture_consultancy.class);
                user_dashboard.this.startActivity(intent);
            }
        });
        farming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(user_dashboard.this,farming.class);
                user_dashboard.this.startActivity(intent);
            }
        });
        IT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(user_dashboard.this,IT_Consultancy.class);
                user_dashboard.this.startActivity(intent);
            }
        });
        new_startup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(user_dashboard.this, Enterpreneur_Ship.class);
                user_dashboard.this.startActivity(intent);
            }
        });
        crypto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(user_dashboard.this,crypto_consultancy.class);
                user_dashboard.this.startActivity(intent);
            }
        });
        stock_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(user_dashboard.this,stock_market_consultancy.class);
                user_dashboard.this.startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(user_dashboard.this);
                builder.setTitle("Confirm Exit...");
                builder.setIcon(R.drawable.icon_lgout);
                builder.setMessage("Are you sure , want to Exit ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent=new Intent(user_dashboard.this,login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                user_dashboard.this.startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();

            }
        });
    }
    private void subscribeToTopic(){
        FirebaseMessaging.getInstance().subscribeToTopic(mAuth.getUid());
    }
    private void initialize(){
        real_estate=(Button) findViewById(R.id.real_estate);
        stock_market=(Button) findViewById(R.id.stock_market);
        crypto=(Button) findViewById(R.id.crptocurrency);
        e_commerce=(Button) findViewById(R.id.Ecommerce);
        agriculture=(Button) findViewById(R.id.agriculture);
        farming=(Button) findViewById(R.id.farming);
        IT=(Button) findViewById(R.id.IT);
        new_startup=(Button) findViewById(R.id.enterpreneur);
        logout=(ImageView) findViewById(R.id.logoutBtn);
        Name=(TextView) findViewById(R.id.useName);
        mAuth=FirebaseAuth.getInstance();
        // Initialization Of Progress Dialog
        progressDialog = new ProgressDialog(this);


    }
}
