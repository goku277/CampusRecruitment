package com.pushpa.mycampusrecruitment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pushpa.mycampusrecruitment.Model.UniversityData;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;

public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.ViewHolder> {

    ArrayList<UniversityData> universityDataArrayList;
    Context context;

    public UniversityAdapter(Context context, ArrayList<UniversityData> universityDataArrayList) {
        this.universityDataArrayList= universityDataArrayList;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_admin_list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(universityDataArrayList.get(position).getImageUrl()).centerCrop().into(holder.imageView);
        holder.textView.setText(universityDataArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return universityDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.img_id);
           // textView= (TextView) itemView.findViewById(R.id.text_id);
        }
    }
}
