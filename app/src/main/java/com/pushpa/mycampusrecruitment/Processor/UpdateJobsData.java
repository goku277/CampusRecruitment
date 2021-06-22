package com.pushpa.mycampusrecruitment.Processor;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class UpdateJobsData {

    public Map<String, Set<String>> init(String data, String companyName) {
        String split[]= data.split("Name:");
        Set<String> refId= new LinkedHashSet<>();
        for (String s: split) {
            if (!s.trim().isEmpty()) {
                if (s.contains(companyName)) {
                    s = s.trim();
                    if (s.contains("ReferenceId:") && s.contains("Key:")) {
                        refId.add(s.substring(s.indexOf("ReferenceId:"), s.indexOf("Key:")).trim());
                    }
                }
            }
        }
        Map<String, Set<String>> mapReferenceIdToCompanyDetails= new LinkedHashMap<>();
        Set<String> set1= null;
        for (String s: refId) {
            set1= new LinkedHashSet<>();
            for (String s1: split) {
                if (s1.contains(companyName)) {
                    if (s1.contains(s)) {
                        if (s1.contains("imageUri:")) {
                            set1.add(s1.substring(s1.indexOf(s1.charAt(0)), s1.indexOf("imageUri:")).trim());
                        }
                        if (s1.contains("imageUri:")) {
                            set1.add(s1.substring(s1.indexOf("imageUri:"), s1.indexOf("Qualification:")).trim());
                        }
                        if (s1.contains("Qualification:")) {
                            set1.add(s1.substring(s1.indexOf("Qualification:"), s1.indexOf("Requirements:")).trim());
                        }
                        if (s1.contains("j:")) {
                            set1.add(s1.substring(s1.indexOf("j:"), s1.indexOf("KeySkills:")).replace(",","").trim());
                        }
                        if (s1.contains("KeySkills:")) {
                            set1.add(s1.substring(s1.indexOf("KeySkills:"), s1.indexOf("Gap:")).replace(",","").trim());
                        }
                        if (s1.contains("Gap:")) {
                            set1.add(s1.substring(s1.indexOf("Gap:"), s1.indexOf("Tenth:")).replace(",","").trim());
                        }
                        if (s1.contains("Tenth:")) {
                            set1.add(s1.substring(s1.indexOf("Tenth:"), s1.indexOf("Twelvth:")).trim());
                        }
                        if (s1.contains("Twelvth:")) {
                            set1.add(s1.substring(s1.indexOf("Twelvth:"), s1.indexOf("selectedGrad:")).trim());
                        }
                        if (s1.contains("CurrentAcademic:")) {
                            set1.add(s1.substring(s1.indexOf("CurrentAcademic:"), s1.indexOf("ReferenceId:")).trim());
                        }
                        if (s1.contains("Key:")) {
                            set1.add(s1.substring(s1.indexOf("Key:"), s1.indexOf("Expected CTC:")).trim());
                        }
                        if (s1.contains("Expected CTC:")) {
                            set1.add(s1.substring(s1.indexOf("Expected CTC:")).trim());
                        }
                    }
                }
            }
            mapReferenceIdToCompanyDetails.put(s, set1);
        }
        return mapReferenceIdToCompanyDetails;
    }
}