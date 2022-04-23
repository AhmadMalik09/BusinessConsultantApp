package pk.edu.uiit.businessconsultant.Adapters;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pk.edu.uiit.businessconsultant.Activites.ECommerce;
import pk.edu.uiit.businessconsultant.Activites.Enterpreneur_Ship;
import pk.edu.uiit.businessconsultant.Activites.IT_Consultancy;
import pk.edu.uiit.businessconsultant.Activites.agriculture_consultancy;
import pk.edu.uiit.businessconsultant.Activites.crypto_consultancy;
import pk.edu.uiit.businessconsultant.Activites.real_estate_consultancy;
import pk.edu.uiit.businessconsultant.Activites.stock_market_consultancy;
import pk.edu.uiit.businessconsultant.ModelClasses.BusinessInfo;
import pk.edu.uiit.businessconsultant.R;

public class dataAdapter extends RecyclerView.Adapter<dataAdapter.viewHolder> {
    Context ecommerce;
    ArrayList<BusinessInfo> infoArrayList ;
    Context farming;
    Context agriculture;
    Context cryptocurrency;
    Context enterpreneurShip;
    Context IT;
    Context realEstate;
    Context stockMarket;
    public dataAdapter(ECommerce ecommerce, ArrayList<BusinessInfo> infoArrayList) {
        this.ecommerce = ecommerce;
        this.infoArrayList = infoArrayList;
    }

    public dataAdapter(pk.edu.uiit.businessconsultant.Activites.farming farming, ArrayList<BusinessInfo> infoArrayList) {
        this.infoArrayList = infoArrayList;
        this.farming = farming;
    }

    public dataAdapter(agriculture_consultancy agriculture , ArrayList<BusinessInfo> infoArrayList) {
        this.infoArrayList = infoArrayList;
        this.agriculture = agriculture;
    }

    public dataAdapter(crypto_consultancy cryptocurrency, ArrayList<BusinessInfo> infoArrayList) {
        this.infoArrayList = infoArrayList;
        this.cryptocurrency = cryptocurrency;
    }

    public dataAdapter(Enterpreneur_Ship enterpreneurShip, ArrayList<BusinessInfo> infoArrayList) {
        this.infoArrayList = infoArrayList;
        this.enterpreneurShip = enterpreneurShip;
    }
    public dataAdapter(IT_Consultancy IT, ArrayList<BusinessInfo> infoArrayList) {
        this.infoArrayList = infoArrayList;
        this.IT = IT;
    }

    public dataAdapter(real_estate_consultancy realEstate, ArrayList<BusinessInfo> infoArrayList) {
        this.infoArrayList = infoArrayList;
        this.realEstate = realEstate;
    }

    public dataAdapter(stock_market_consultancy stockMarket , ArrayList<BusinessInfo> infoArrayList) {
        this.infoArrayList = infoArrayList;
        this.stockMarket = stockMarket;
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
        holder.Question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.answer.setVisibility(View.VISIBLE);
            }
        });


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
           answer.setVisibility(View.GONE);
           answer.setMovementMethod(new ScrollingMovementMethod());
        }
    }
}
