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
import com.pushpa.mycampusrecruitment.Main.DisplayCorrespondingShortListedStudent;
import com.pushpa.mycampusrecruitment.Model.ShortlistedStudentsData;
import com.pushpa.mycampusrecruitment.R;

import java.util.ArrayList;
import java.util.Map;

public class ShortlistedCandidatesAdapter extends RecyclerView.Adapter<ShortlistedCandidatesAdapter.ViewHolder>{

    Context context;

    ArrayList<ShortlistedStudentsData> shortlistedStudentsDataArrayList;

    Map<String, String> mapImageUrlToCompany;

    Map<String, String> mapImageUrlToStudentDetails;

    String users;

    RecyclerView recyclerView11;

    public ShortlistedCandidatesAdapter(Context context, ArrayList<ShortlistedStudentsData> shortlistedStudentsDataArrayList, Map<String, String> mapImageUrlToCompany, Map<String, String> mapImageUrlToStudentDetails, String users) {
        this.shortlistedStudentsDataArrayList= shortlistedStudentsDataArrayList;
        this.mapImageUrlToCompany= mapImageUrlToCompany;
        this.mapImageUrlToStudentDetails= mapImageUrlToStudentDetails;
        this.users= users;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Glide.with(context).load(shortlistedStudentsDataArrayList.get(position).getStudentUrlImage()).centerCrop().into(holder.imageView);
       // holder.textView.setText(shortlistedStudentsDataArrayList.get(position).getStudentName());

        holder.textView.setText(users);

        System.out.println("From ShortlistedCandidatesAdapter mapImageUrlToCompany is: " + mapImageUrlToCompany);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(context, "Clicked on: " + shortlistedStudentsDataArrayList.get(holder.getAdapterPosition()).getStudentUrlImage(), Toast.LENGTH_SHORT).show();

                String getImageUrl= shortlistedStudentsDataArrayList.get(holder.getAdapterPosition()).getStudentUrlImage().trim();

                System.out.println("From ShortlistedCandidatesAdapter getImageUrl is: " + getImageUrl);

                String getSpecificStudentDetails="";

                for (Map.Entry<String, String> e1: mapImageUrlToStudentDetails.entrySet()) {
                    String val= e1.getValue().trim();
                  //  System.out.println("From ShortlistedCandidatesAdapter e1.getValue() is: " + val);
                    if (val.replace("[","").trim().equals(getImageUrl.trim())) {
                        getSpecificStudentDetails= e1.getKey().trim();
                    }
                }

                System.out.println("From ShortlistedCandidatesAdapter getSpecificStudentDEtails are: " + getSpecificStudentDetails);

                String getCompanySelected= mapImageUrlToCompany.get(getSpecificStudentDetails);

             /*   for (Map.Entry<String, String> e1: mapImageUrlToCompany.entrySet()) {
                    System.out.println(getSpecificStudentDetails + "\t\t" + e1.getKey());
                    if (e1.getKey().trim().equals(getSpecificStudentDetails)) {
                        getCompanySelected= e1.getValue();
                    }
                }    */

                System.out.println("From ShortlistedCandidatesAdapter getCompanySelected: " + getCompanySelected);



            //    String fetchedImageurl= mapImageUrlToStudentDetails.get(shortlistedStudentsDataArrayList.get(holder.getAdapterPosition()).getStudentUrlImage());

           //     System.out.println("From ShortlistedCandidatesAdapter fetchedImageurl: " + fetchedImageurl);

                String fetchedCompany= mapImageUrlToCompany.get(shortlistedStudentsDataArrayList.get(holder.getAdapterPosition()).getStudentUrlImage());

                System.out.println("From ShortlistedCandidatesAdapter fetchedCompany: " + fetchedCompany);

                Intent sendData= new Intent(context, DisplayCorrespondingShortListedStudent.class);

                sendData.putExtra("studentdetails", getSpecificStudentDetails);
                sendData.putExtra("companyselected", getCompanySelected);

                context.startActivity(sendData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shortlistedStudentsDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        Button view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.img_id);
            textView= (TextView) itemView.findViewById(R.id.university_id);
            view= (Button) itemView.findViewById(R.id.view_btn_id);
        }
    }
}