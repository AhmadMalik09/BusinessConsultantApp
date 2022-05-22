package pk.edu.uiit.businessconsultant.Adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import pk.edu.uiit.businessconsultant.ModelClasses.Reviews;
import pk.edu.uiit.businessconsultant.R;

public class review_Adapter extends RecyclerView.Adapter<review_Adapter.viewHolder> {
    Context loading_reviews;
    ArrayList<Reviews> reviewArrayList ;
    Context reviewsFragment;

    public review_Adapter(Context loading_reviews, ArrayList<Reviews> reviewArrayList) {
        this.loading_reviews = loading_reviews;
        this.reviewArrayList = reviewArrayList;
    }

    public review_Adapter(ArrayList<Reviews> reviewArrayList) {
        this.reviewArrayList = reviewArrayList;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_adapter,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Reviews reviews = reviewArrayList.get(position);
        String ratings = reviews.getRatings();

        // We Also Need Info (Profile, Name) Of User Who Wrote The Review: We Can Do It Using uid Of User
     loadUserDetail(reviews, holder);

     Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(reviews.getTimestamp()));
        String dateFormat = DateFormat.format("dd/MM/yyyy", calendar).toString();
        // Set Data
        holder.ratingBar.setRating(Float.parseFloat(ratings));
        holder.Review.setText(reviews.getReview());
        holder.dataTime.setText(dateFormat);

    }
   private void loadUserDetail(Reviews reviews, viewHolder holder) {
        // uid Of User Who Wrote Review
        String uid = reviews.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(uid+"~User~")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Get User Info, Use Same Key As In Firebase
                        String name = ""+snapshot.child("name").getValue();
                        String profileImage = ""+snapshot.child("profileImage").getValue();

                        // Set Data
                        holder.Name.setText(name);
                        try {
                            Picasso.get().load(profileImage).fit().centerCrop().into(holder.userProfile);
                        }
                        catch (Exception exception){
                            holder.userProfile.setImageResource(R.drawable.profile);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
            CircleImageView userProfile;
            TextView Name, dataTime,Review;
            RatingBar ratingBar;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            userProfile=itemView.findViewById(R.id.Usrprofile);
            Name=itemView.findViewById(R.id.userName);
            dataTime=itemView.findViewById(R.id.dateTv);
            Review=itemView.findViewById(R.id.reviewTv);
           ratingBar=itemView.findViewById(R.id.ratingBAR);
        }
    }
}
