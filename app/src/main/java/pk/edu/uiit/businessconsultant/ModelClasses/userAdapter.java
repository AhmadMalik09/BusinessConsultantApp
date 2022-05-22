package pk.edu.uiit.businessconsultant.ModelClasses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import de.hdodenhof.circleimageview.CircleImageView;
import pk.edu.uiit.businessconsultant.Activites.Chat;
import pk.edu.uiit.businessconsultant.Activites.showPortfolio;
import pk.edu.uiit.businessconsultant.R;

public class userAdapter extends RecyclerView.Adapter<userAdapter.viewholder>{
    Context loading_consultants;
    ArrayList<FirebaseHelper> usersArrayList ;
    private FirebaseHelper users;
    public userAdapter(loading_Consultants loading_consultants, ArrayList<FirebaseHelper> usersArrayList) {
            this.loading_consultants=loading_consultants;
            this.usersArrayList = usersArrayList;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_adaper,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        FirebaseHelper users= usersArrayList.get(position);
        holder.ConsultantName.setText(users.name);
        holder.Field.setText(users.Field);
        try {
            Picasso.get().load(users.profileImage).fit().centerCrop().into(holder.circleImageView);
        }
        catch (Exception exception){
            holder.circleImageView.setImageResource(R.drawable.profile);
        }
        loadRatings(users,holder);
        //Creating Click Listener moving toward Chat activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(loading_consultants);
                builder.setTitle("Welcome Here...");
                builder.setIcon(R.drawable.welcome_icon);
                builder.setMessage("Here is choice for You !")
                        .setCancelable(false)
                        .setPositiveButton("Start Chat", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(loading_consultants, Chat.class);
                                intent.putExtra("name",users.getName());
                                intent.putExtra("profilePicture",users.profileImage);
                                intent.putExtra("uid",users.getUid());
                                loading_consultants.startActivity(intent);
                            }
                        })
                        .setNegativeButton("View Portfolio", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(loading_consultants, showPortfolio.class);
                                intent.putExtra("uid",users.getUid());
                                loading_consultants.startActivity(intent);
                            }
                        });

                AlertDialog alertDialog=builder.create();
                alertDialog.show();


            }
        });
    }
    float  ratingSum = 0;
    private void loadRatings(FirebaseHelper users,viewholder holder){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.child(users.getUid()+"~Consultant~").child("Ratings")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ratingSum = 0;
                        for (DataSnapshot ds: snapshot.getChildren()){
                            float rating = Float.parseFloat(""+ds.child("ratings").getValue()); // e.g. 4.5
                            ratingSum = ratingSum + rating; // For Average, Add(Addition Of) All Ratings, Later Will Divide It By Number Of Reviews
                        }
                        long numberOfReviews = snapshot.getChildrenCount();
                        float avgRating = ratingSum/numberOfReviews;
                        holder.ratingBar.setRating(avgRating);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



}


    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }
    class viewholder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView ConsultantName, Field;
        RatingBar ratingBar;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            circleImageView= itemView.findViewById(R.id.consult_img);
            ConsultantName= itemView.findViewById(R.id.CName);
            Field= itemView.findViewById(R.id.CField);
            ratingBar=itemView.findViewById(R.id.Avgrating);
        }
    }
}
