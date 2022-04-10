package pk.edu.uiit.businessconsultant.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pk.edu.uiit.businessconsultant.Activites.ECommerce;
import pk.edu.uiit.businessconsultant.ModelClasses.BusinessInfo;
import pk.edu.uiit.businessconsultant.R;

public class dataAdapter extends RecyclerView.Adapter<dataAdapter.viewHolder> {
    Context ecommerce;
    ArrayList<BusinessInfo> infoArrayList ;

    public dataAdapter(ECommerce ecommerce, ArrayList<BusinessInfo> infoArrayList) {
        this.ecommerce = ecommerce;
        this.infoArrayList = infoArrayList;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_adapter,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        BusinessInfo info=infoArrayList.get(position);
        holder.Question.setText(info.getQuestion());
        holder.answer.setText(info.getAnswers());

    }


    @Override
    public int getItemCount() {
        return infoArrayList.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder {
        TextView Question,answer ;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Question= itemView.findViewById(R.id.questions);
           answer= itemView.findViewById(R.id.answers);
        }
    }
}
