package com.pushpa.mycampusrecruitment.Processor;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ShortListStudentsData {

    public Map<String, Set<String>> init(String data) {
        System.out.println("data: " + data);
        String split[]= data.split("university");
        Map<String, Set<String>> mapKeyToDetails= new LinkedHashMap<>();
        Set<String> keySet= null;
        for (String s: split) {
            keySet= new LinkedHashSet<>();
            String key="";
            if (!s.trim().isEmpty()) {
                s= s.trim();
                System.out.println("split: " + s);
                if (s.contains("key:")) {
                    key= s.substring(s.indexOf("key:")).replace("key:","").replace(",","").trim();
                }
                if (s.contains("ten:")) {
                    keySet.add(s.substring(s.indexOf("ten:"), s.indexOf("twelve:")).replace(",","").trim());
                }
                if (s.contains("twelve:")) {
                    keySet.add(s.substring(s.indexOf("twelve:"), s.indexOf("graduation:")).replace(",","").trim());
                }
                if (s.contains("graduation:")) {
                    keySet.add(s.substring(s.indexOf("graduation:"), s.indexOf("currentacademic:")).replace(",","").trim());
                }
                if (s.contains("currentacademic:")) {
                    keySet.add(s.substring(s.indexOf("currentacademic:"), s.indexOf("keyskills:")).replace(",","").trim());
                }
                if (s.contains("uriPath:")) {
                    keySet.add(s.substring(s.indexOf("uriPath:"), s.indexOf("key:")).replace(",", "").trim());
                }
                if (s.contains("keyskills:")) {
                    keySet.add(s.substring(s.indexOf("keyskills:"), s.indexOf("uriPath:")).replace(",","").trim());
                }
                mapKeyToDetails.put(key, keySet);
            }
        }
        System.out.println("mapKeyToDetails: " + mapKeyToDetails);
        return mapKeyToDetails;
    }
}
