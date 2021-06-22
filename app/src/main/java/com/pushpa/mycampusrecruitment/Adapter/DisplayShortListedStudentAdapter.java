package com.pushpa.mycampusrecruitment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.pushpa.mycampusrecruitment.Model.DisplaySpecificShortListedStudentData;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;

public class DisplayShortListedStudentAdapter extends RecyclerView.Adapter<DisplayShortListedStudentAdapter.ViewHolder>{

    Context context;
    ArrayList<DisplaySpecificShortListedStudentData> displaySpecificShortListedStudentData= new ArrayList<>();
    String companySelected;

    public DisplayShortListedStudentAdapter(Context context, ArrayList<DisplaySpecificShortListedStudentData> displaySpecificShortListedStudentData, String companySelected) {
        this.context= context;
        this.companySelected= companySelected;
        this.displaySpecificShortListedStudentData= displaySpecificShortListedStudentData;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.display_shortlisted_students_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        System.out.println("From DisplayShortListedStudentAdapter displaySpecificShortListedStudentData.get(position).getSelectionincompanies() is: " + displaySpecificShortListedStudentData.get(position).getSelectionincompanies());
        holder.companyselected.setText(displaySpecificShortListedStudentData.get(position).getSelectionincompanies());
        holder.keyskills.setText(displaySpecificShortListedStudentData.get(position).getKeyskills());
        holder.grad.setText(displaySpecificShortListedStudentData.get(position).getGraduation());
        holder.ten.setText(displaySpecificShortListedStudentData.get(position).getTen());
        holder.twelve.setText(displaySpecificShortListedStudentData.get(position).getTwelve());
        holder.reg.setText(displaySpecificShortListedStudentData.get(position).getRegistrationnumber());
        holder.currentacademic.setText(displaySpecificShortListedStudentData.get(position).getCurrentacademic());
    }

    @Override
    public int getItemCount() {
        return displaySpecificShortListedStudentData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView companyselected, reg, ten, twelve, grad, currentacademic, keyskills;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            companyselected= (TextView) itemView.findViewById(R.id.selected_companies_id);
            reg= (TextView) itemView.findViewById(R.id.registration_number_id);
            ten= (TextView) itemView.findViewById(R.id.ten_percentage_id);
            twelve= (TextView) itemView.findViewById(R.id.twelve_percentage_id);
            grad= (TextView) itemView.findViewById(R.id.graduation_percentage_id);
            currentacademic= (TextView) itemView.findViewById(R.id.current_academic_id);
            keyskills= (TextView) itemView.findViewById(R.id.key_skills_id);
        }
    }
}
