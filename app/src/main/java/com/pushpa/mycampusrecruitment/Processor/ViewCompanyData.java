package com.pushpa.mycampusrecruitment.Processor;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ViewCompanyData {
    public Map<String, Set<String>> init(String data) {
        String split[]= data.split("name1:");
        Set<String> companyName= new LinkedHashSet<>();
        for (String s: split) {
            if (!s.trim().isEmpty()) {
                s= s.trim();
                if (s.contains("yos:")) {
                    s= s.substring(s.indexOf(s.charAt(0)), s.indexOf("yos:")).trim();
                    companyName.add(s);
                }
            }
        }
        Map<String, Set<String>> mapCompanyToDetails= new LinkedHashMap<>();
        Set<String> set1= null;
        for (String s: companyName) {
            set1= new LinkedHashSet<>();
            for (String s1: split) {
                if (s1.contains(s)) {
                    if (s1.contains("yos:") && s1.contains("total:")) {
                        set1.add(s1.substring(s1.indexOf("yos:"), s1.indexOf("total:")).trim());
                    }
                    if (s1.contains("total:") && s1.contains("location:")) {
                        set1.add(s1.substring(s1.indexOf("total:"), s1.indexOf("location:")).trim());
                    }
                    if (s1.contains("location:") && s1.contains("Acheivements:")) {
                        set1.add(s1.substring(s1.indexOf("location:"), s1.indexOf("Acheivements:")).trim());
                    }
                    if (s1.contains("Acheivements:") && s1.contains("uriPath:")) {
                        set1.add(s1.substring(s1.indexOf("Acheivements:"), s1.indexOf("uriPath:")).trim());
                    }
                    if (s1.contains("uriPath:") && s1.contains("referenceId:")) {
                        set1.add(s1.substring(s1.indexOf("uriPath:"), s1.indexOf("referenceId:")).trim());
                    }
                    if (s1.contains("referenceId:") && s1.contains("key:")) {
                        set1.add(s1.substring(s1.indexOf("referenceId:"), s1.indexOf("key:")).trim());
                    }
                    if (s1.contains("key:")) {
                        set1.add(s1.substring(s1.indexOf("key:")).trim());
                    }
                }
            }
            mapCompanyToDetails.put(s,set1);
        }
        return mapCompanyToDetails;
    }
}