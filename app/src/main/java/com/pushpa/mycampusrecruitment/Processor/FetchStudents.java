package com.pushpa.mycampusrecruitment.Processor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class FetchStudents {

    public Set<ArrayList<String>> init(String data, String university) {
        Set<String> getUniversityName= new LinkedHashSet<>();
        String split[]= data.split("universityname:");
        Set<ArrayList<String>> aSet= new LinkedHashSet<>();
        ArrayList<String> aList= null;
        for (String s: split) {
            aList= new ArrayList<>();
            if (s.contains("studentregistrationnumber:")) {
                if (!s.trim().isEmpty()) {
                    getUniversityName.add(s.substring(s.indexOf(s.charAt(0)), s.indexOf("studentregistrationnumber:")).replace(",", "").trim());
                }
            }
            if (!s.trim().isEmpty()) {
                if (!s.isEmpty()) {
                    if (s.contains(university)) {
                        if (s.contains("studentregistrationnumber:")) {
                            aList.add(s.substring(s.indexOf("studentregistrationnumber:"), s.indexOf("ten:")).replace(",", "").trim());
                        }
                        if (s.contains("ten:")) {
                            aList.add(s.substring(s.indexOf("ten:"), s.indexOf("twelve:")).replace(",", "").trim());
                        }
                        if (s.contains("twelve:")) {
                            aList.add(s.substring(s.indexOf("twelve:"), s.indexOf("graduation:")).replace(",","").trim());
                        }
                        if (s.contains("graduation:")) {
                            aList.add(s.substring(s.indexOf("graduation:"), s.indexOf("currentacademic:")).replace(",", "").trim());
                        }
                        if (s.contains("currentacademic:")) {
                            aList.add(s.substring(s.indexOf("currentacademic:"), s.indexOf("uriPath:")).replace(",", "").trim());
                        }
                        if (s.contains("uriPath:") && s.contains("contactnumber:")) {
                            aList.add(s.substring(s.indexOf("uriPath:"), s.indexOf("contactnumber:")).replace(",", "").trim());
                        }
                        if (s.contains("contactnumber:")) {
                            aList.add(s.substring(s.indexOf("contactnumber:")));
                        }
                    }
                }
            }
            if (!aList.isEmpty()) {
                aSet.add(aList);
            }
            System.out.println("From FetchStudents aSet is: " + aSet);
        }
        return aSet;
    }
}