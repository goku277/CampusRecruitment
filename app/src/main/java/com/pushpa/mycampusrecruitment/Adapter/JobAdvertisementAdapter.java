package com.pushpa.mycampusrecruitment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pushpa.mycampusrecruitment.Main.DisplayCorrespondingJob;
import com.pushpa.mycampusrecruitment.Model.JobAdvertisementData;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;
import java.util.Map;

public class JobAdvertisementAdapter extends RecyclerView.Adapter<JobAdvertisementAdapter.ViewHolder> {

    ArrayList<JobAdvertisementData> jobAdvertisementDataArrayList;

    ArrayList<String> requirementList;

    Map<String, ArrayList<ArrayList<String>>> receiveData;

    ArrayList<ArrayList<String>> getData= new ArrayList<>();

    Context context;

    public JobAdvertisementAdapter(Context context, ArrayList<JobAdvertisementData> jobAdvertisementDataArrayList, ArrayList<String> requirementList, Map<String, ArrayList<ArrayList<String>>> receiveData) {
        this.context= context;
        this.jobAdvertisementDataArrayList= jobAdvertisementDataArrayList;
        this.receiveData= receiveData;
        this.requirementList= requirementList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.check_job_layout_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Glide.with(context).load(jobAdvertisementDataArrayList.get(position).getJobImageUrl()).centerCrop().into(holder.imageView);
        holder.textView.setText(jobAdvertisementDataArrayList.get(position).getJobName());
        holder.req.setText(requirementList.get(position));


        holder.req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String company= jobAdvertisementDataArrayList.get(holder.getAdapterPosition()).getJobName();
                for (Map.Entry<String, ArrayList<ArrayList<String>>> map1: receiveData.entrySet()) {
                    if (map1.getKey().equals(company)) {
                        getData= map1.getValue();
                    }
                }
                System.out.println("From JobAdvertisementAdapter getData: " + getData);
                ArrayList<String> combo= new ArrayList<>();
                for (ArrayList<String> s: getData) {
                    for (int i=0;i<s.size();i++) {
                        combo.add(s.get(i));
                    }
                    combo.add("End of data");
                }
                System.out.println("From JobAdvertisementAdapter combo is: " + combo);
                Intent sendData= new Intent(context, DisplayCorrespondingJob.class);
                sendData.putStringArrayListExtra("comb", combo);
                context.startActivity(sendData);
            }
        });

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String company= jobAdvertisementDataArrayList.get(holder.getAdapterPosition()).getJobName();
                for (Map.Entry<String, ArrayList<ArrayList<String>>> map1: receiveData.entrySet()) {
                    if (map1.getKey().equals(company)) {
                        getData= map1.getValue();
                    }
                }
                System.out.println("From JobAdvertisementAdapter getData: " + getData);
                ArrayList<String> combo= new ArrayList<>();
                for (ArrayList<String> s: getData) {
                    for (int i=0;i<s.size();i++) {
                        combo.add(s.get(i));
                    }
                    combo.add("End of data");
                }
                System.out.println("From JobAdvertisementAdapter combo is: " + combo);
                Intent sendData= new Intent(context, DisplayCorrespondingJob.class);
                sendData.putStringArrayListExtra("comb", combo);
                context.startActivity(sendData);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Toast.makeText(context, "Clicked on: " + jobAdvertisementDataArrayList.get(holder.getAdapterPosition()).getJobName(), Toast.LENGTH_SHORT).show();
                String company= jobAdvertisementDataArrayList.get(holder.getAdapterPosition()).getJobName();
                for (Map.Entry<String, ArrayList<ArrayList<String>>> map1: receiveData.entrySet()) {
                    if (map1.getKey().equals(company)) {
                        getData= map1.getValue();
                    }
                }
                System.out.println("From JobAdvertisementAdapter getData: " + getData);
                ArrayList<String> combo= new ArrayList<>();
                for (ArrayList<String> s: getData) {
                    for (int i=0;i<s.size();i++) {
                        combo.add(s.get(i));
                    }
                    combo.add("End of data");
                }
                System.out.println("From JobAdvertisementAdapter combo is: " + combo);
                Intent sendData= new Intent(context, DisplayCorrespondingJob.class);
                sendData.putStringArrayListExtra("comb", combo);
                context.startActivity(sendData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobAdvertisementDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textView, req;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView= (CardView) itemView.findViewById(R.id.cardView2);
            imageView= (ImageView) itemView.findViewById(R.id.company_logo_id);
            textView= (TextView) itemView.findViewById(R.id.company_name_id);
            req= (TextView) itemView.findViewById(R.id.req_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String jobName= jobAdvertisementDataArrayList.get(getAdapterPosition()).getJobName();
                  //  Toast.makeText(context, "Clicked on: " + jobName, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}