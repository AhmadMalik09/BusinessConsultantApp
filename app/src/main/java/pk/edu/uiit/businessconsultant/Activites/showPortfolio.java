package pk.edu.uiit.businessconsultant.Activites;

import static pk.edu.uiit.businessconsultant.R.color.app_background;
import static pk.edu.uiit.businessconsultant.R.color.blackTextColor;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

import pk.edu.uiit.businessconsultant.Fragments.fieldFragment;
import pk.edu.uiit.businessconsultant.Fragments.profileFragment;
import pk.edu.uiit.businessconsultant.Fragments.qualificationFragment;
import pk.edu.uiit.businessconsultant.Fragments.reviewsFragment;
import pk.edu.uiit.businessconsultant.R;

public class showPortfolio extends AppCompatActivity {
    TextView profile,qualification,field,review;
    FrameLayout fl;
    FragmentManager fragmentManager;
    profileFragment profileFrg;
    qualificationFragment qualificationFrg;
    fieldFragment fieldFrg;
    reviewsFragment reviewsFrg;
    FirebaseAuth firebaseAuth;
    String consultantUID;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_portfolio);
        initialization();
         performAction();
        fragmentManager=getSupportFragmentManager();
        consultantUID=getIntent().getStringExtra("uid");
        Bundle bundle = new Bundle();
        bundle.putString("key", consultantUID);
        FragmentTransaction ft= fragmentManager.beginTransaction();
        ft.add(R.id.flfragment,profileFrg,"PR");
        ft.commit();
        profileFrg.setArguments(bundle);
        profile.setTextColor(getResources().getColor(app_background));;
    }
    public void performAction(){
        consultantUID=getIntent().getStringExtra("uid");
        Bundle bundle = new Bundle();
        bundle.putString("key", consultantUID);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.flfragment,profileFrg);
                ft.commit();
                profileFrg.setArguments(bundle);
                profile.setTextColor(getResources().getColor(app_background));
                qualification.setTextColor(getResources().getColor(blackTextColor));
                field.setTextColor(getResources().getColor(blackTextColor));
                review.setTextColor(getResources().getColor(blackTextColor));
            }
        });
        qualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.flfragment,qualificationFrg);
                ft.commit();
                qualificationFrg.setArguments(bundle);
                qualification.setTextColor(getResources().getColor(app_background));
                field.setTextColor(getResources().getColor(blackTextColor));
                review.setTextColor(getResources().getColor(blackTextColor));
                profile.setTextColor(getResources().getColor(blackTextColor));

            }
        });
        field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.flfragment,fieldFrg);
                ft.commit();
                fieldFrg.setArguments(bundle);
                field.setTextColor(getResources().getColor(R.color.app_background));
                review.setTextColor(getResources().getColor(blackTextColor));
                profile.setTextColor(getResources().getColor(blackTextColor));
                qualification.setTextColor(getResources().getColor(blackTextColor));

            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.flfragment,reviewsFrg);
                ft.commit();
                reviewsFrg.setArguments(bundle);
                review.setTextColor(getResources().getColor(R.color.app_background));
                profile.setTextColor(getResources().getColor(blackTextColor));
                qualification.setTextColor(getResources().getColor(blackTextColor));
                field.setTextColor(getResources().getColor(blackTextColor));
            }
        });
    }
    public void initialization(){
        profile=(TextView)findViewById(R.id.profileID);
        qualification=(TextView) findViewById(R.id.eduID);
        field=(TextView) findViewById(R.id.fieldID);
        review=(TextView) findViewById(R.id.reviewID);
        fl=(FrameLayout) findViewById(R.id.flfragment);
        //fragment initialization in showProfile Activity
        profileFrg=new profileFragment();
        qualificationFrg=new qualificationFragment();
        fieldFrg=new fieldFragment();
        reviewsFrg=new reviewsFragment();
        firebaseAuth=FirebaseAuth.getInstance();
        //Sending UID to fragments

    }
}
