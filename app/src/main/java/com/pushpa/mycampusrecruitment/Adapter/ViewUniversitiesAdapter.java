package com.pushpa.mycampusrecruitment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pushpa.mycampusrecruitment.Model.ViewUniversitiesModelData;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewUniversitiesAdapter extends RecyclerView.Adapter<ViewUniversitiesAdapter.ViewHolder>{

    ArrayList<ViewUniversitiesModelData> viewUniversities;
    Context context;

    public ViewUniversitiesAdapter(ArrayList<ViewUniversitiesModelData> viewUniversities, Context context) {
        this.viewUniversities = viewUniversities;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_university_layout_list_item, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(viewUniversities.get(position).getImageUrl()).into(holder.cig);

        holder.branch.setText("Branches available:\t"+ viewUniversities.get(position).getBranches());
        holder.id.setText("University id:\t" + viewUniversities.get(position).getUniversityId());
        holder.loc.setText("Location:\t" + viewUniversities.get(position).getLocation());
        holder.name.setText("University Name:\t" + viewUniversities.get(position).getUniversityName());
        holder.year.setText("Established Year:\t"+ viewUniversities.get(position).getYear());

    }

    @Override
    public int getItemCount() {
        return viewUniversities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView cig;
        TextView name, id, loc, branch, year;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cig= (CircleImageView) itemView.findViewById(R.id.image_id);
            name= (TextView) itemView.findViewById(R.id.text_id);
            id= (TextView) itemView.findViewById(R.id.textView);
            loc= (TextView) itemView.findViewById(R.id.location_id);
            branch= (TextView) itemView.findViewById(R.id.branches_id);
            year= (TextView) itemView.findViewById(R.id.year_of_establishment_id);
        }
    }
}
