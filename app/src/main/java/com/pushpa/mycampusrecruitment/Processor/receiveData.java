package com.pushpa.mycampusrecruitment.Processor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class receiveData {
    static Map<String, ArrayList<ArrayList<String>>> map1= new LinkedHashMap<>();
    public Map<String, ArrayList<ArrayList<String>>> init(String data) {
        ///   System.out.println("data: " + data);
        String split[]= data.split("\\]\\],|\\[\\{|\\]\\]\\}\\]");
        Set<String> companyName= new LinkedHashSet<>();
        for (String s: split) {
            if (!s.trim().isEmpty()) {
                s= s.trim();
                if (s.contains("[[")) {
                    s= s.replace("[[","");
                }
                if (s.contains("]")) {
                    s= s.replace("]","");
                }
                //  System.out.println("split: " + s);
                if (s.contains("Requirements:")) {
                    s= s.substring(s.indexOf(s.charAt(0)), s.indexOf("Requirements:")).replace("=","").trim();
                    companyName.add(s);
                }
            }
        }
        //  System.out.println("companyName: " + companyName);
        for (String s: companyName) {
            String company="", details= "";
            for (String s1: split) {
                if (!s1.trim().isEmpty()) {
                    if (s1.contains(s)) {
                        company= s;
                        details= s1.replace(s+"=","").replace("[[","").replace("]","").replace("[","").trim();
                    }
                }
            }
            //  System.out.println(company + " " + details);
            map1(company, details);
            //  break;
        }
        System.out.println("map1 is: " + map1);
        return map1;
    }

    private static void map1(String company, String details) {
        String split[]= details.split(",");
        ArrayList<String> aList= new ArrayList<>();
        for (String s: split) {
            s= s.trim();
            System.out.println("split: " + s);
            aList.add(s);
        }
        ArrayList<ArrayList<String>> a11= slidingWindow(aList);

        //   System.out.println("From map1() a11 is: " + a11);

        map1.put(company, a11);
    }

    private static ArrayList<ArrayList<String>> slidingWindow(ArrayList<String> aList) {
        int start=0, end=0;
        ArrayList<ArrayList<String>> a1= new ArrayList<>();
        // System.out.println("a1: " + aList.get(start));
        while (end < aList.size()) {
            ArrayList<String> bList= new ArrayList<>();
            end++;
            if (end-start+1==8) {
                //  System.out.println("aList.get(end): " + aList.get(end));
                bList.add(aList.get(start));
                bList.add(aList.get(start+1));
                bList.add(aList.get(start+2));
                bList.add(aList.get(start+3));
                bList.add(aList.get(start+4));
                bList.add(aList.get(start+5));
                bList.add(aList.get(start+6));
                bList.add(aList.get(start+7));
                start= end+1;
                end++;
            }
            if (bList.size()== 8) {
                //  System.out.println("bList: " + bList);
                a1.add(bList);
            }
        }
        return a1;
    }
}