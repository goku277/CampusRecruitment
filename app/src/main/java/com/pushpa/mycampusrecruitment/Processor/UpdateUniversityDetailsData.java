package com.pushpa.mycampusrecruitment.Processor;

import java.util.LinkedHashSet;
import java.util.Set;

public class UpdateUniversityDetailsData {

    public Set<String> init(String data, String univsersity) {
        Set<String> set1= new LinkedHashSet<>();
        String split[]= data.split("universityName:");
        for (String s: split) {
            if (!s.trim().isEmpty()) {
                if (s.contains(univsersity)) {
                    if (s.contains("location:")) {
                        set1.add("universityName: " + s.substring(s.indexOf(s.charAt(0)), s.indexOf("location:")).trim());
                    }
                    if (s.contains("location:") && s.contains("universityId:")) {
                        set1.add(s.substring(s.indexOf("location:"), s.indexOf("universityId:")).trim());
                    }
                    if (s.contains("universityId:") && s.contains("branches:")) {
                        set1.add(s.substring(s.indexOf("universityId:"), s.indexOf("branches:")).trim());
                    }
                    if (s.contains("branches:") && s.contains("year:")) {
                        set1.add(s.substring(s.indexOf("branches:"), s.indexOf("year:")).trim());
                    }
                    if (s.contains("year:") && s.contains("uriPath:")) {
                        set1.add(s.substring(s.indexOf("year:"), s.indexOf("uriPath:")).replace(",","").trim());
                    }
                    if (s.contains("uriPath:") && s.contains("key:")) {
                        set1.add(s.substring(s.indexOf("uriPath:"), s.indexOf("key:")).replace(",","").trim());
                    }
                    if (s.contains("key:")) {
                        set1.add(s.substring(s.indexOf("key:")).trim());
                    }
                }
            }
        }
        System.out.println("From UpdateUniversityDetailsData set1 is: " + set1);
        return set1;
    }
}