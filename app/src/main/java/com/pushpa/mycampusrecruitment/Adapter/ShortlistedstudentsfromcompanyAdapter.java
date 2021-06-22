package com.pushpa.mycampusrecruitment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pushpa.mycampusrecruitment.Main.DisplaySpecificShortListedStudent;
import com.pushpa.mycampusrecruitment.Model.Shortlistedstudentsfromcompanydata;
import com.pushpa.mycampusrecruitment.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ShortlistedstudentsfromcompanyAdapter extends RecyclerView.Adapter<ShortlistedstudentsfromcompanyAdapter.ViewHolder>{

    Map<String, Set<String>> shortListedStudentsMap= new LinkedHashMap<>();
    ArrayList<Shortlistedstudentsfromcompanydata> uploadShortListedStudentsDetailsDataArrayList= new ArrayList<>();

    Set<String> imageUrl= new LinkedHashSet<>();

    ArrayList<String> copyList= new ArrayList<>();

    Context context; Map<String, String> mapKeyToImageUrl;
    String reg;

    public ShortlistedstudentsfromcompanyAdapter(Map<String, String> mapKeyToImageUrl, ArrayList<Shortlistedstudentsfromcompanydata> uploadShortListedStudentsDetailsDataArrayList, Map<String, Set<String>> shortListedStudentsMap, Context context, String reg) {
        this.mapKeyToImageUrl= mapKeyToImageUrl;
        this.uploadShortListedStudentsDetailsDataArrayList = uploadShortListedStudentsDetailsDataArrayList;
        this.shortListedStudentsMap= shortListedStudentsMap;
        this.context = context;
        this.reg= reg;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shortlisted_students_from_company_list_item_layout, parent, false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.universityName.setText(uploadShortListedStudentsDetailsDataArrayList.get(position).getUniversityname());

        for (Map.Entry<String, String> e1: mapKeyToImageUrl.entrySet()) {
            imageUrl.add(e1.getValue());
        }

        copyList.addAll(imageUrl);

        Glide.with(context).load(copyList.get(position)).into(holder.cig);

        final Map<String, Set<String>> sortedStudents= new LinkedHashMap<>();

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Set<String>> aList= new ArrayList<Set<String>>();
                ArrayList<String> key= new ArrayList<>();
                Map<String, Set<String>> map1= new LinkedHashMap<>();
                String key1= "";
             //   Toast.makeText(context, "Clicked on: " + uploadShortListedStudentsDetailsDataArrayList.get(position).getUniversityname(), Toast.LENGTH_SHORT).show();
                String clickedUniversity= uploadShortListedStudentsDetailsDataArrayList.get(position).getUniversityname();
                for (Map.Entry<String, Set<String>> e1: shortListedStudentsMap.entrySet()) {
                    //  System.out.println("From ShortlistedstudentsfromcompanyAdapter onBindViewHolder() clickedUniversity: " + clickedUniversity + "\t\t e1.getKey(): " + e1.getKey() + "\t\te1.getValue(): " + e1.getValue());
                    if (e1.getKey().contains(clickedUniversity)) {
                        key.add(e1.getKey());
                        key1= e1.getKey();
                        System.out.println("From ShortlistedstudentsfromcompanyAdapter key is: " + key);
                        System.out.println("e1.getKey().contains(clickedUniversity)");
                        aList.add(e1.getValue());
                        map1.put(key1, e1.getValue());
                    }
                }
                System.out.println("From ShortlistedstudentsfromcompanyAdapter onBindViewHolder() aList: " + aList);

                String str= aList + "";

                System.out.println("From ShortlistedstudentsfromcompanyAdapter onBindViewHolder() key is: " + key);

                Intent sendData= new Intent(context, DisplaySpecificShortListedStudent.class);
                sendData.putExtra("str", str);
                sendData.putStringArrayListExtra("key",key);
                sendData.putExtra("reg", reg);
                sendData.putExtra("map1", (Serializable) map1);
                context.startActivity(sendData);
            }
        });

       // System.out.println("From ShortlistedstudentsfromcompanyAdapter onBindViewHolder() aList: " + aList);
    }

    @Override
    public int getItemCount() {
        return uploadShortListedStudentsDetailsDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cig;
        TextView universityName;
        Button view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            universityName= (TextView) itemView.findViewById(R.id.university_name_id);
            cig= itemView.findViewById(R.id.cig_id);
            view= (Button) itemView.findViewById(R.id.view_btn_id);
        }
    }
}