package com.pushpa.mycampusrecruitment.Processor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class PostJobExtractData {
    public Map<String, ArrayList<Set<String>>> init(String data, String companyName) {
        //  System.out.println("data: " + data);
        String split[]= data.split("Name:");
        Map<String, ArrayList<Set<String>>> mapCompanyToDetails= new LinkedHashMap<>();
        Set<String> companyDetails= null;
        ArrayList<Set<String>> a1= new ArrayList<Set<String>>();
        for (String s: split) {
            companyDetails= new LinkedHashSet<>();
            if (!s.trim().isEmpty()) {
                s= s.trim();
                //  System.out.println("split: " + s);
                if (s.trim().contains(companyName.trim())) {
                    System.out.println("split: " + s);
                    if (s.contains("Twelvth:")) {
                        companyDetails.add(s.substring(s.indexOf("Twelvth:"), s.indexOf("CurrentAcademic:")).replace(",","").trim());
                    }
                    if (s.contains("CurrentAcademic:")) {
                        companyDetails.add(s.substring(s.indexOf("CurrentAcademic:"), s.indexOf("selectedGrad:")).replace(",","").trim());
                    }
                    if (s.contains("selectedGrad:")) {
                        companyDetails.add(s.substring(s.indexOf("selectedGrad:"), s.indexOf("Tenth:")).replace(",","").trim());
                    }
                    if (s.contains("Tenth:")) {
                        companyDetails.add(s.substring(s.indexOf("Tenth:"), s.indexOf("KeySkills:")).replace(",","").trim());
                    }
                    if (s.contains("KeySkills:")) {
                        companyDetails.add(s.substring(s.indexOf("KeySkills:"), s.indexOf("Key:")).replace(",","").trim());
                    }
                    a1.add(companyDetails);
                }
            }
        }

       // System.out.println("a1: " + a1);

        mapCompanyToDetails.put(companyName.trim(), a1);

      //  System.out.println("mapCompanyToDetails: " + mapCompanyToDetails);

        return mapCompanyToDetails;
    }
}