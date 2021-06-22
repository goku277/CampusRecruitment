package com.pushpa.mycampusrecruitment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pushpa.mycampusrecruitment.Model.DisplaySpecificShortListedStudentFromCompanyData;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DisplaySpecificShortListedStudentAdapter extends RecyclerView.Adapter<DisplaySpecificShortListedStudentAdapter.ViewHolder>{

    ArrayList<DisplaySpecificShortListedStudentFromCompanyData> disp;
    Context context;

    public DisplaySpecificShortListedStudentAdapter(ArrayList<DisplaySpecificShortListedStudentFromCompanyData> disp, Context context) {
        this.disp= disp;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.display_specific_shortlisted_students_from_company_item_list_layout, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(disp.get(position).getImageuri()).centerCrop().into(holder.cig);
        holder.ten.setText(disp.get(position).getTen());
        holder.twelve.setText(disp.get(position).getTwelve());
        holder.grad.setText(disp.get(position).getGraduation());
        holder.currentacademic.setText(disp.get(position).getCurrentacademic());
        holder.univ.setText("Institution Name: "+ disp.get(position).getUniversityname());
        holder.reg.setText("Reg.Id: " + disp.get(position).getRegistrationnumber());
        holder.keyskills.setText(disp.get(position).getKeyskills());
    }

    @Override
    public int getItemCount() {
        return disp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView cig;
        TextView univ, reg, ten, twelve, grad, currentacademic, keyskills;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cig= (CircleImageView) itemView.findViewById(R.id.cig_id);
            univ= (TextView) itemView.findViewById(R.id.univ_id);
            reg= (TextView) itemView.findViewById(R.id.reg_no_id);
            ten= (TextView) itemView.findViewById(R.id.ten_id);
            twelve= (TextView) itemView.findViewById(R.id.twelve_id);
            grad= (TextView) itemView.findViewById(R.id.grad_id);
            keyskills= (TextView) itemView.findViewById(R.id.keyskills_id);
            currentacademic= (TextView) itemView.findViewById(R.id.currentacademic_id);
        }
    }
}
