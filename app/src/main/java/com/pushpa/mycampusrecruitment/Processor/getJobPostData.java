package com.pushpa.mycampusrecruitment.Processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class getJobPostData {
    static ArrayList<ArrayList<String>> aList= new ArrayList<ArrayList<String>>();
    private static void initialize() {
        for (int i=0;i<5;i++) {
            aList.add(new ArrayList<String>());
        }
    }

    public ArrayList<ArrayList<String>> init(String data, String name) {
        initialize();
        Set<Set<String>> setDetails= new LinkedHashSet<>();
        Set<String> set1= null;
        Set<String> ten= new HashSet<>();
        Set<String> twelve= new HashSet<>();
        Set<String> gradd= new HashSet<>();
        Set<String> academic= new HashSet<>();
        Map<String, String> map1= new LinkedHashMap<>();
        String split[]= data.split("Name:");
        for (String s: split) {
            if (!s.trim().isEmpty()) {
                if (s.contains(name)) {
                    set1= new HashSet<>();
                    String name1="", imageurl="", requirements= "", tenth="", twelvth="", grad="", curAcademic="", refId="";
                    s= s.trim();
                    if (s.contains("imageUri:")) {
                        name1= "Name: " + s.substring(s.indexOf(s.charAt(0)), s.indexOf("imageUri:")).trim();
                    }
                    if (s.contains("Requirements:") && s.contains("j:")) {
                        requirements= s.substring(s.indexOf("Requirements:"), s.indexOf("j:")).trim();
                    }
                    if (s.contains("imageUri:") && s.contains("Qualification:")) {
                        imageurl= s.substring(s.indexOf("imageUri:"), s.indexOf("Qualification")).trim();
                    }
                    if (s.contains("Tenth") && s.contains("Twelvth:")) {
                        tenth= s.substring(s.indexOf("Tenth:"), s.indexOf("Twelvth:")).trim();
                    }
                    if (s.contains("Twelvth:") && s.contains("selectedGrad:")) {
                        twelvth= s.substring(s.indexOf("Twelvth:"), s.indexOf("selectedGrad:")).trim();
                    }
                    if (s.contains("selectedGrad:") && s.contains("CurrentAcademic:")) {
                        grad= s.substring(s.indexOf("selectedGrad:"), s.indexOf("CurrentAcademic:")).trim();
                    }
                    if (s.contains("CurrentAcademic:") && s.contains("ReferenceId:")) {
                        curAcademic= s.substring(s.indexOf("CurrentAcademic:"), s.indexOf("ReferenceId:")).trim();
                    }
                    if (s.contains("ReferenceId:") && s.contains("Key:")) {
                        refId= s.substring(s.indexOf("ReferenceId:"), s.indexOf("Key:")).trim();
                    }
                    set1.add(name1);
                    set1.add(imageurl);
                    set1.add(tenth);
                    set1.add(twelvth);
                    set1.add(grad);
                    set1.add(curAcademic);
                    set1.add(refId);

                    String ten11= tenth.replace("Tenth:","").replace("%","").trim();
                    String twelve11= twelvth.replace("Twelvth:", "").replace("%","").trim();
                    String grad11= grad.replace("selectedGrad:","").replace("%","").trim();
                    String academic11= curAcademic.replace("CurrentAcademic:","").replace("%","").trim();

                    map1.put(ten11 + " " + twelve11 + " " + grad11 + " "  +academic11, requirements);

                    ten.add(ten11);
                    twelve.add(twelve11);
                    gradd.add(grad11);
                    academic.add(academic11);

                    setDetails.add(set1);
                }
            }
        }

        ArrayList<String> copyTen= new ArrayList<>();
        copyTen.addAll(ten);

        ArrayList<String> copyTwelve= new ArrayList<>();
        copyTwelve.addAll(twelve);

        ArrayList<String> copyGrad= new ArrayList<>();
        copyGrad.addAll(gradd);

        ArrayList<String> copyAcademic= new ArrayList<>();
        copyAcademic.addAll(academic);

        String min1= Collections.min(ten);
        String min11= Collections.min(twelve);
        String min12= Collections.min(gradd);
        String min122= Collections.min(academic);

        System.out.println("From getJobPostData ten: " + min1);
        System.out.println("From getJobPostData twelve: " + min11);
        System.out.println("From getJobPostData gradd: " + min12);
        System.out.println("From getJobPostData academic: " + min122);

        System.out.println("map1: " + map1);

        Map<String, String> map11= new LinkedHashMap<>();

        for (Map.Entry<String, String> e1: map1.entrySet()) {
            String k11= e1.getKey();
            String keys[]= e1.getKey().split("\\s+");
            int add=0;
            for (String s: keys) {
                add+= Integer.parseInt(s);
                map11.put(k11, String.valueOf(add));
            }
        }

        System.out.println("map11 is: " + map11);

        int minKey= Integer.MAX_VALUE;
        for (Map.Entry<String, String> e1: map11.entrySet()) {
            int keys= Integer.parseInt(e1.getValue());
            if (minKey > keys) {
                minKey= keys;
            }
        }

        String getKey1= "";

        for (Map.Entry<String, String> e1: map11.entrySet()) {
            String val= e1.getValue();

            if (val.equals(String.valueOf(minKey))) {
                getKey1= e1.getKey();
            }
        }

        System.out.println("minKey is: " + minKey);

        String splitKeys[]= getKey1.split("\\s+");

        String key1= splitKeys[0] + " " + splitKeys[1] + " " + splitKeys[2] + " " + splitKeys[3];

        System.out.println("key is: " + key1);

        aList.get(0).add(splitKeys[0]);
        aList.get(1).add(splitKeys[1]);
        aList.get(2).add(splitKeys[2]);
        aList.get(3).add(splitKeys[3]);
        aList.get(4).add(map1.get(key1));
        return aList;
    }
}