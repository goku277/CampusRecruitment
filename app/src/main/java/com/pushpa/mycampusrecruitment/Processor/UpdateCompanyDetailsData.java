package com.pushpa.mycampusrecruitment.Processor;

import java.util.LinkedHashSet;
import java.util.Set;

public class UpdateCompanyDetailsData {
    public Set<String> init(String data, String user) {
        Set<String> companyDetails= new LinkedHashSet<>();
        String split[]= data.split("name");
        for (String s: split) {
            if (!s.trim().isEmpty()) {
                if (s.contains(user)) {
                    System.out.println("split: " + s);
                    if (s.contains("1:")) {
                        companyDetails.add(s.substring(s.indexOf("1:"), s.indexOf("yos:")).replace(",","").trim());
                    }
                    if (s.contains("yos:")) {
                        companyDetails.add(s.substring(s.indexOf("yos:"), s.indexOf("total:")).replace(",","").trim());
                    }
                    if (s.contains("total:") && s.contains("location:")) {
                        companyDetails.add(s.substring(s.indexOf("total:"), s.indexOf("location:")).replace(",","").trim());
                    }
                    if (s.contains("Acheivements:")) {
                        companyDetails.add(s.substring(s.indexOf("Acheivements:"), s.indexOf("uriPath:")).replace(",","").trim());
                    }
                    if (s.contains("uriPath:")) {
                        companyDetails.add(s.substring(s.indexOf("uriPath:"), s.indexOf("referenceId:")).replace(",","").trim());
                    }
                    if (s.contains("referenceId:")) {
                        companyDetails.add(s.substring(s.indexOf("referenceId:"), s.indexOf("key:")).replace(",","").trim());
                    }
                    if (s.contains("key:")) {
                        companyDetails.add(s.substring(s.indexOf("key:")).trim());
                    }
                    if (s.contains("location:") && s.contains("Acheivements:")) {
                        companyDetails.add(s.substring(s.indexOf("location:"), s.indexOf("Acheivements:")).replace(",","").trim());
                    }
                }
            }
        }
        return companyDetails;
    }
}