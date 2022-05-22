package pk.edu.uiit.businessconsultant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import pk.edu.uiit.businessconsultant.ModelClasses.FirebaseHelper;
import pk.edu.uiit.businessconsultant.R;

public class profileFragment extends Fragment
{
    ImageView consultantImg;
    TextView name,nationality,hobby,goals,languages;
    FirebaseHelper firebaseHelper;
    String UID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_profile, container, false);
        loadData();
        return rootView;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            UID = bundle.getString("key");
        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        consultantImg = (ImageView) getView().findViewById(R.id.consultantProfile);
        name=(TextView) getView().findViewById(R.id.name);
        nationality=(TextView) getView().findViewById(R.id.Nationality);
        hobby=(TextView) getView().findViewById(R.id.Hobbies);
        goals=(TextView) getView().findViewById(R.id.Goals);
        languages=(TextView) getView().findViewById(R.id.language);
  //      UID=getArguments().getString("key");

    }
    private void loadData(){
        firebaseHelper=new FirebaseHelper();
        //Loading Consultant Image and Name
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(UID+"~Consultant~")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // Get Data From Database (Firebase)
                            String Name = ""+snapshot.child("name").getValue();
                            String profileImage = ""+snapshot.child("imageURL").getValue();

                            // Set Data To Profile Activity Views
                            name.setText(Name);
                            try {
                                Picasso.get().load(profileImage).placeholder(R.drawable.profile).into(consultantImg);
                            }
                            catch (Exception exception){
                                consultantImg.setImageResource(R.drawable.profile);
                            }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        // Loadind Other Info from portfolio
        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("Users");
        reference1.child(UID+"~Consultant~").child("Portfolio")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        //Get Value from FirebaseDatabase
                        String AreaOfInterest=""+snapshot.child("areaOfInterest").getValue();
                        String Goals=""+snapshot.child("goals").getValue();
                        String Languages=""+snapshot.child("languages").getValue();
                        String Nationality=""+snapshot.child("nationality").getValue();
                        nationality.setText(Nationality);
                        hobby.setText(AreaOfInterest);
                        goals.setText(Goals);
                        languages.setText(Languages);

                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}