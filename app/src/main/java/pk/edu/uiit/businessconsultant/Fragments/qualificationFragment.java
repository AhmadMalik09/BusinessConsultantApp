package pk.edu.uiit.businessconsultant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pk.edu.uiit.businessconsultant.R;

public class qualificationFragment extends Fragment {
TextView degree,specification,institute,AwardsCount;
String UID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View rootView= inflater.inflate(R.layout.fragment_qualification, container, false);
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
        degree=(TextView) getView().findViewById(R.id.degree);
        specification=(TextView) getView().findViewById(R.id.specifications);
        institute=(TextView) getView().findViewById(R.id.insitutes);
       AwardsCount=(TextView) getView().findViewById(R.id.awardsNumbers);
    }
    private void loadData(){
        //Loading Consultant Image and Name
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(UID+"~Consultant~")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // Get Data From Database (Firebase)
                        String Degree = ""+snapshot.child("qualification").getValue();
                        String Specification = ""+snapshot.child("specification").getValue();

                        // Set Data To Profile Activity Views
                        degree.setText(Degree);
                        specification.setText(Specification);


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
                        String Institute=""+snapshot.child("educationalInstitute").getValue();
                        String No_Of_Awards=""+snapshot.child("no_Of_Awards").getValue();
                        institute.setText(Institute);
                        AwardsCount.setText(No_Of_Awards);

                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}