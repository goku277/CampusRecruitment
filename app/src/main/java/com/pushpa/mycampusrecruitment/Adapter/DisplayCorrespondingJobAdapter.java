package com.pushpa.mycampusrecruitment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.pushpa.mycampusrecruitment.Model.DisplayCorrespondingJobData;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;

public class DisplayCorrespondingJobAdapter extends RecyclerView.Adapter<DisplayCorrespondingJobAdapter.ViewHolder> {

     ArrayList<DisplayCorrespondingJobData> criteriaList= new ArrayList<>();
     Context context;

    public DisplayCorrespondingJobAdapter(Context context, ArrayList<DisplayCorrespondingJobData> criteriaList) {
        this.criteriaList = criteriaList;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.display_corresponding_job_layout_list_item, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayCorrespondingJobAdapter.ViewHolder holder, int position) {
        holder.Requirements.setText(criteriaList.get(position).getRequirements());
        holder.Expected_CTC.setText(criteriaList.get(position).getExpected_ctc());
        holder.Twelvth.setText(criteriaList.get(position).getTwelvth());
        holder.Tenth.setText(criteriaList.get(position).getTenth());
        holder.Qualification.setText(criteriaList.get(position).getQualification());
        holder.selectedGrad.setText(criteriaList.get(position).getGrad());
        holder.KeySkills.setText(criteriaList.get(position).getKeyskills());
        holder.CurrentAcademic.setText(criteriaList.get(position).getCurrentacademic());
    }

    @Override
    public int getItemCount() {
        return criteriaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Requirements, CurrentAcademic, selectedGrad, KeySkills, Qualification, Tenth, Twelvth, Expected_CTC;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Requirements = (TextView) itemView.findViewById(R.id.requirements_id);
            CurrentAcademic= (TextView) itemView.findViewById(R.id.currentacademic_id);
            selectedGrad= (TextView) itemView.findViewById(R.id.grad_id);
            KeySkills= (TextView) itemView.findViewById(R.id.keyskills_id);
            Qualification= (TextView) itemView.findViewById(R.id.qualification_id);
            Tenth= (TextView) itemView.findViewById(R.id.tenth_id);
            Twelvth= (TextView) itemView.findViewById(R.id.twelvth_id);
            Expected_CTC= (TextView) itemView.findViewById(R.id.expected_ctc_id);
        }
    }
}