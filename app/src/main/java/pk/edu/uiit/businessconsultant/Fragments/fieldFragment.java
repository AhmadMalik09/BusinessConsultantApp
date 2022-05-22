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



public class fieldFragment extends Fragment {
    TextView field,certficates,experience,working_org;
    String UID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_field, container, false);
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
        field=(TextView) getView().findViewById(R.id.field);
        certficates=(TextView) getView().findViewById(R.id.certificates);
        experience=(TextView) getView().findViewById(R.id.experience);
        working_org=(TextView) getView().findViewById(R.id.working_org);
    }
    public void loadData(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(UID+"~Consultant~")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // Get Data From Database (Firebase)
                        String Field = ""+snapshot.child("field").getValue();


                        // Set Data To Profile Activity Views
                        field.setText(Field);


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
                        String Certificate=""+snapshot.child("awards").getValue();
                        String Experience=""+snapshot.child("experience").getValue();
                        String WorkingOrg=""+snapshot.child("working_org").getValue();
                        certficates.setText(Certificate);
                        experience.setText(Experience);
                        working_org.setText(WorkingOrg);
                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}