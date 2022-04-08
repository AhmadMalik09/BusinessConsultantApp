package pk.edu.uiit.businessconsultant.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pk.edu.uiit.businessconsultant.R;

public class splash extends AppCompatActivity {
    Animation top_animation , bottom_animation;
    ImageView image;
    TextView logo1,logo2,slogan;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        intialize();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash.this,login.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
    private void intialize(){
        top_animation= AnimationUtils.loadAnimation(this,R.anim.splash_animation);
        bottom_animation=AnimationUtils.loadAnimation(this,R.anim.splash_bottom);
        image=(ImageView) findViewById(R.id.logoImage);
        logo1=(TextView) findViewById(R.id.l1);
        logo2=(TextView) findViewById(R.id.l2);
        slogan=(TextView) findViewById(R.id.slogan);
        image.setAnimation(top_animation);
        logo1.setAnimation(bottom_animation);
        logo2.setAnimation(bottom_animation);
        slogan.setAnimation(bottom_animation);

    }

}
