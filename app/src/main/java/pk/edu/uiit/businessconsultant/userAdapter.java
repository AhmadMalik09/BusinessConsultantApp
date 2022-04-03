package pk.edu.uiit.businessconsultant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_design,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        FirebaseHelper users= usersArrayList.get(position);
        holder.ConsultantName.setText(users.name);
        holder.Field.setText(users.Field);
        Picasso.get().load(users.profileImage).into(holder.circleImageView);
    }



    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }
    class viewholder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView ConsultantName, Field;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            circleImageView= itemView.findViewById(R.id.consult_img);
            ConsultantName= itemView.findViewById(R.id.CName);
            Field= itemView.findViewById(R.id.CField);
        }
    }
}
