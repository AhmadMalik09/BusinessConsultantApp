package pk.edu.uiit.businessconsultant.Adapters;

import static pk.edu.uiit.businessconsultant.Activites.Chat.consultantImage;
import static pk.edu.uiit.businessconsultant.Activites.Chat.userProfileImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import pk.edu.uiit.businessconsultant.ModelClasses.Messages;
import pk.edu.uiit.businessconsultant.R;

public class messagesAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<Messages>messagesArrayList;

    public messagesAdapter(Context context, ArrayList<Messages> messagesArrayList) {
        this.context = context;
        this.messagesArrayList = messagesArrayList;
    }

    int itemSend=1;
    int itemReceive=2;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==itemSend){
            View view= LayoutInflater.from(context).inflate(R.layout.sender_layout_item,parent,false);
            return new senderViewHolder(view);
        }
        else {
            View view= LayoutInflater.from(context).inflate(R.layout.receiver_layout_item,parent,false);
            return new receiverVieeHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //Model Class
        Messages messages=messagesArrayList.get(position);
        if(holder.getClass()==senderViewHolder.class){
            senderViewHolder viewHolder=(senderViewHolder) holder;
            //set Incoming Messages
            viewHolder.msgText.setText(messages.getMessage());
            //now set ProfileImage of user(sender)
            Picasso.get().load(userProfileImage).fit().centerCrop().into(viewHolder.senderProfile);
        }
        else {
            receiverVieeHolder viewHolder=(receiverVieeHolder) holder;
            //set received Messages
            viewHolder.textMsg.setText(messages.getMessage());
            //now set ProfileImage of Consultant(receiver)
            Picasso.get().load(consultantImage).fit().centerCrop().into(viewHolder. receiverProfile);
        }

    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Messages messages=messagesArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderID())){
            return itemSend;
        }
        else {
            return itemReceive;
        }
    }

    class senderViewHolder extends RecyclerView.ViewHolder {
        CircleImageView senderProfile;
        TextView msgText;
        public senderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderProfile=itemView.findViewById(R.id.profile_image);
            msgText=itemView.findViewById(R.id.senderTxt);
        }
    }
    class receiverVieeHolder extends RecyclerView.ViewHolder {
        CircleImageView receiverProfile;
        TextView textMsg;
        public receiverVieeHolder(@NonNull View itemView) {
            super(itemView);
            receiverProfile=itemView.findViewById(R.id.receiver_image);
            textMsg=itemView.findViewById(R.id.receiverTxt);
        }
    }
}
