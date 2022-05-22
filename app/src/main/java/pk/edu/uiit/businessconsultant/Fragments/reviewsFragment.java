package pk.edu.uiit.businessconsultant.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pk.edu.uiit.businessconsultant.Adapters.review_Adapter;
import pk.edu.uiit.businessconsultant.ModelClasses.Reviews;
import pk.edu.uiit.businessconsultant.R;

public class reviewsFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Reviews> reviewArrayList;
    String UID;
    review_Adapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_reviews, container, false);
        recyclerView=rootView.findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewArrayList=new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(UID+"~Consultant~").child("Ratings")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Clear List Before Adding Data Into It
                        reviewArrayList.clear();
                        //     ratingSum = 0;
                        for (DataSnapshot ds: snapshot.getChildren()){
                            Reviews reviewList = ds.getValue(Reviews.class);
                            reviewArrayList.add(reviewList);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        adapter=new review_Adapter(reviewArrayList);
        recyclerView.setAdapter(adapter);
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

}