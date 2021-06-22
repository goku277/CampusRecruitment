package com.pushpa.mycampusrecruitment.Processor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class GetData1 {
    public static  Map<String, ArrayList<String>> map2= null;
    public ArrayList<Map<String, Set<ArrayList<String>>>> init(String data) {
        ArrayList<Map<String, Set<ArrayList<String>>>> a1= new ArrayList<>();
        String split[]= data.split("Name:");
        Set<String> CompanyName= new LinkedHashSet<>();
        for (String s: split) {
            s= s.replace("[","");
            if (!s.trim().isEmpty()) {
                CompanyName.add(s.substring(s.indexOf(s.charAt(0)), s.indexOf("CurrentAcademic:")).replace(",","").trim());
            }
        }
        Map<String, Set<ArrayList<String>>> map1= new LinkedHashMap<>();
        map2= new LinkedHashMap<>();
        for (String s: CompanyName) {
            Set<ArrayList<String>> aList= new LinkedHashSet<ArrayList<String>>();
            ArrayList<String> imageUrlList= new ArrayList<>();
            for (String s1: split) {
                ArrayList<String> bList= new ArrayList<>();
                if (s1.contains(s)) {
                    if (s1.contains("Requirements:")) {
                        bList.add(s1.substring(s1.indexOf("Requirements:"), s1.indexOf("Tenth:")).replace(",","").trim());
                    }
                    if (s1.contains("CurrentAcademic:")) {
                        bList.add(s1.substring(s1.indexOf("CurrentAcademic:"), s1.indexOf("selectedGrad:")).replace(",","").trim());
                    }
                    if (s1.contains("selectedGrad:")) {
                        bList.add(s1.substring(s1.indexOf("selectedGrad:"), s1.indexOf("j:")).replace(",","").trim());
                    }
                    if (s1.contains("KeySkills:")) {
                        bList.add(s1.substring(s1.indexOf("KeySkills:"), s1.indexOf("Qualification:")).replace(",","").trim());
                    }
                    if (s1.contains("Qualification")) {
                        bList.add(s1.substring(s1.indexOf("Qualification"), s1.indexOf("Requirements:")).replace(",","").trim());
                    }
                    if (s1.contains("Tenth:")) {
                        bList.add(s1.substring(s1.indexOf("Tenth:"), s1.indexOf("Twelvth:")).replace(",","").trim());
                    }
                    if (s1.contains("Twelvth:")) {
                        bList.add(s1.substring(s1.indexOf("Twelvth:"), s1.indexOf("Gap:")).replace(",", "").trim());
                    }
                    if (s1.contains("Expected CTC:"))  {
                        bList.add(s1.substring(s1.indexOf("Expected CTC:")));
                    }
                    if (s1.contains("imageUri:")) {
                        String imageStr= s1.substring(s1.indexOf("imageUri:"), s1.indexOf("Expected CTC:")).replace(",","").trim();
                        if (imageUrlList.isEmpty()) {
                            imageUrlList.add(imageStr);
                        }
                    }
                }
                if (!bList.isEmpty()) {
                    aList.add(bList);
                }
                if (!imageUrlList.isEmpty()) {
                    map2.put(s, imageUrlList);
                }
                if (!aList.isEmpty()) {
                    map1.put(s, aList);
                }
            }
        }
        System.out.println("map2: " + map2);
        a1.add(0, map1);
        setImage();
        return a1;
    }

    public Map<String, ArrayList<String>> setImage() {
        return map2;
    }
}