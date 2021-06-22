package com.pushpa.mycampusrecruitment.Processor;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class viewUniversitiesdata {
    public Map<String, Set<String>> init(String data) {
        String split[]= data.split("universityName:");
        Set<String> universityName= new LinkedHashSet<>();
        for (String s: split) {
            if (!s.trim().isEmpty()) {
                if (s.contains("location:")) {
                    universityName.add(s.substring(s.indexOf(s.charAt(0)), s.indexOf("location:")).trim());
                }
            }
        }
        Map<String, Set<String>> mapuniversityTostudentdetails= new LinkedHashMap<>();
        Set<String> detailsSet= null;
        for (String s: universityName) {
            detailsSet= new LinkedHashSet<>();
            for (String s1: split) {
                if (!s1.trim().isEmpty()) {
                    if (s1.contains(s)) {
                        if (s1.contains("location:")) {
                            detailsSet.add(s1.substring(s1.indexOf("location:"), s1.indexOf("universityId")).trim());
                        }
                        if (s1.contains("universityId")) {
                            detailsSet.add(s1.substring(s1.indexOf("universityId"), s1.indexOf("branches")).trim());
                        }
                        if (s1.contains("branches:")) {
                            detailsSet.add(s1.substring(s1.indexOf("branches:"), s1.indexOf("year:")).trim());
                        }
                        if (s1.contains("year:")) {
                            detailsSet.add(s1.substring(s1.indexOf("year:"), s1.indexOf("uriPath:")).trim());
                        }
                        if (s1.contains("uriPath:")) {
                            detailsSet.add(s1.substring(s1.indexOf("uriPath:"), s1.indexOf("key:")).trim());
                        }
                        if (s1.contains("key:")) {
                            detailsSet.add(s1.substring(s1.indexOf("key:")).trim());
                        }
                        System.out.println("detailsSet: " + detailsSet);
                    }
                }
            }
            mapuniversityTostudentdetails.put(s, detailsSet);
        }
        return mapuniversityTostudentdetails;
    }
}