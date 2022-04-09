package pk.edu.uiit.businessconsultant.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import pk.edu.uiit.businessconsultant.Activites.Chat;
import pk.edu.uiit.businessconsultant.ModelClasses.FirebaseHelper;
import pk.edu.uiit.businessconsultant.ModelClasses.Users;
import pk.edu.uiit.businessconsultant.ModelClasses.loading_users;
import pk.edu.uiit.businessconsultant.R;

public class consultantAdapter extends RecyclerView.Adapter<consultantAdapter.viewHolder> {
    Context loading_User;
    ArrayList<Users> usersArrayList ;
    private FirebaseHelper users;
    public consultantAdapter(loading_users loading_User, ArrayList<Users> usersArrayList) {
        this.loading_User = loading_User;
        this.usersArrayList = usersArrayList;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consultant_adapter,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
       Users users=usersArrayList.get(position);
        holder.UsertName.setText(users.name);
        try {
            Picasso.get().load(users.profileImage).fit().centerCrop().into(holder.circleImageView);
        }
        catch (Exception exception){
            holder.circleImageView.setImageResource(R.drawable.profile);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(loading_User, Chat.class);
                intent.putExtra("name",users.getName());
                intent.putExtra("profilePicture",users.profileImage);
                intent.putExtra("uid",users.getUid());
                loading_User.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView UsertName ;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView= itemView.findViewById(R.id.user_img);
            UsertName= itemView.findViewById(R.id.UName);
        }
    }

}
