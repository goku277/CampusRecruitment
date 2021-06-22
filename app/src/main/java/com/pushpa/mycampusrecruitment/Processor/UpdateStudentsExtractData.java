package com.pushpa.mycampusrecruitment.Processor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class UpdateStudentsExtractData {
      /*  public static void main(String[] args) {
            String data = "universityname:NIT Trichy, studentregistrationnumber: 123451, ten: 60, twelve: 60, graduation: 70, currentacademic: 90, keyskills: Java, Python, Machine Learning, Data Structures and Algorithms., uriPath: https://firebasestorage.googleapis.com/v0/b/online-recruitment-syste-976e4.appspot.com/o/Student%20Profile%20Picture%2F3b861b9b-4625-4f43-b232-aeba0e8e6d34?alt=media&token=1c209f55-69bd-42f0-9117-7516ca8b4a59, key: NIT Trichy 123451, universityname:NIT Trichy, studentregistrationnumber: 1234117, ten: 60, twelve: 60, graduation: 70, currentacademic: 90, keyskills: Java, c++, python, uriPath: https://firebasestorage.googleapis.com/v0/b/online-recruitment-syste-976e4.appspot.com/o/Student%20Profile%20Picture%2F48f8b200-0702-4338-b44f-44a21f783145?alt=media&token=e22f607d-4a20-450a-a58e-da2ae31909c9, key: NIT Trichy 1234117, universityname:NIT Trichy, studentregistrationnumber: 12311234, ten: 60, twelve: 60, graduation: 70, currentacademic: 60, keyskills: Java,c++,python, uriPath: https://firebasestorage.googleapis.com/v0/b/online-recruitment-syste-976e4.appspot.com/o/Student%20Profile%20Picture%2F87e807dd-cd77-4aa2-8e9f-9f76459eb915?alt=media&token=1653ef7d-e354-4799-b347-7f00fca1a9dc, key: NIT Trichy 12311234, universityname:NIT Trichy, studentregistrationnumber: 123457, ten: 60, twelve: 60, graduation: 60, currentacademic: 60, keyskills: Java, Python, MySql, Machine Learning, Deep Learning., uriPath: https://firebasestorage.googleapis.com/v0/b/online-recruitment-syste-976e4.appspot.com/o/Student%20Profile%20Picture%2Fcf0b7709-9328-45c4-97ae-e3d17276e75b?alt=media&token=52d2a9fe-c170-4cbd-954a-19a8d7803741, key: NIT Trichy 123457, universityname:Pondicherry University, studentregistrationnumber: 18352211, ten: 60, twelve: 60, graduation: 60, currentacademic: 60, keyskills: Java, Mysql, python, uriPath: https://firebasestorage.googleapis.com/v0/b/online-recruitment-syste-976e4.appspot.com/o/Student%20Profile%20Picture%2F046daa1f-f180-4b50-9c7f-4089273a7937?alt=media&token=1d4c8834-edb0-46a2-95ff-3da07099ad88, key: Pondicherry University 18352211, universityname:NIT Trichy, studentregistrationnumber: 123441, ten: 90, twelve: 90, graduation: 90, currentacademic: 90, keyskills: Java, python, c++, uriPath: https://firebasestorage.googleapis.com/v0/b/online-recruitment-syste-976e4.appspot.com/o/Student%20Profile%20Picture%2Fecb4491e-0644-4159-b7fe-602eb1945adc?alt=media&token=35faadfc-794a-4e07-8b8a-06a65b2a332b, key: NIT Trichy 123441, universityname:NIT Trichy, studentregistrationnumber: 123456, ten: 60, twelve: 60, graduation: 60, currentacademic: 60, keyskills: Java, Python, MySql, Machine Learning, Deep Learning., uriPath: https://firebasestorage.googleapis.com/v0/b/online-recruitment-syste-976e4.appspot.com/o/Student%20Profile%20Picture%2Fcf0b7709-9328-45c4-97ae-e3d17276e75b?alt=media&token=52d2a9fe-c170-4cbd-954a-19a8d7803741, key: NIT Trichy 123456, universityname:NIT Trichy, studentregistrationnumber: 123112, ten: 60, twelve: 70, graduation: 60, currentacademic: 90, keyskills: Java, c++, python, uriPath: https://firebasestorage.googleapis.com/v0/b/online-recruitment-syste-976e4.appspot.com/o/Student%20Profile%20Picture%2Fa0dc2798-8884-4ab1-8753-5105841f1d50?alt=media&token=c65f5efb-c042-40c4-b15e-d215cc05f05d, key: NIT Trichy 123112, universityname:Cachar College, studentregistrationnumber: 19, ten: 86, twelve: 95, graduation: 75, currentacademic: 78, keyskills: Software Engineer, uriPath: , key: Cachar College 19, universityname:Pondicherry University, studentregistrationnumber: 18352224, ten: 60, twelve: 60, graduation: 60, currentacademic: 80, keyskills: Python, Java, Mysql, Php, Machine Learning, Html, Css, uriPath: https://firebasestorage.googleapis.com/v0/b/online-recruitment-syste-976e4.appspot.com/o/Student%20Profile%20Picture%2F9c468ea1-5029-4465-879a-7bda980d92f9?alt=media&token=7a134bfb-3330-41a8-93fc-5794bf536bb8, key: Pondicherry University 18352224,";
            String university = "name:NIT Trichy";

            ArrayList<Map<String, Set<String>>> a1= init(data, university);
            System.out.println("From main() a1 is: " + a1);
        }    */

        public ArrayList<Map<String, Set<String>>> init(String data, String university) {

            ArrayList<Map<String, Set<String>>> a1= new ArrayList<Map<String, Set<String>>>();
            System.out.println("data: " + data);
            String split[] = data.split("universityname:");
            Set<String> reg = new LinkedHashSet<>();
            for (String s : split) {
                if (!s.trim().isEmpty()) {
                    if (s.contains(university)) {
                        System.out.println("split: " + s);
                        if (s.contains("studentregistrationnumber:") && s.contains("ten:")) {
                            reg.add(s.substring(s.indexOf("studentregistrationnumber:"), s.indexOf("ten:")).replace(",", "").trim());
                        }
                    }
                }
            }

            System.out.println("reg is: " + reg);

            Map<String, Set<String>> mapregTodetails = new LinkedHashMap<>();

            Map<String, Set<String>> mapregTokey= new LinkedHashMap<>();

            Set<String> details = null;

            Set<String> key= null;

            for (String s : reg) {
                details = new LinkedHashSet<>();
                key= new LinkedHashSet<>();
                for (String s1 : split) {
                    if (!s1.trim().isEmpty()) {
                        if (s1.contains(university)) {
                            if (s1.contains(s)) {
                                String str = s1.substring(s1.indexOf("studentregistrationnumber:"), s1.indexOf("ten:")).replace(",", "").trim();
                                if (str.trim().equals(s.trim())) {
                                    if (s1.contains("ten:") && s1.contains("twelve:")) {
                                        details.add(s1.substring(s1.indexOf("ten:"), s1.indexOf("twelve:")).replace(",", "").trim());
                                    }
                                    if (s1.contains("twelve:") && s1.contains("graduation:")) {
                                        details.add(s1.substring(s1.indexOf("twelve:"), s1.indexOf("graduation:")).replace(",", "").trim());
                                    }
                                    if (s1.contains("graduation:") && s1.contains("currentacademic:")) {
                                        details.add(s1.substring(s1.indexOf("graduation:"), s1.indexOf("currentacademic:")).replace(",", "").trim());
                                    }
                                    if (s1.contains("currentacademic:") && s1.contains("keyskills:")) {
                                        details.add(s1.substring(s1.indexOf("currentacademic:"), s1.indexOf("keyskills:")).replace(",", "").trim());
                                    }
                                    if (s1.contains("keyskills:") && s1.contains("uriPath:")) {
                                        details.add(s1.substring(s1.indexOf("keyskills:"), s1.indexOf("uriPath:")).replace(",", "").trim());
                                    }
                                    if (s1.contains("uriPath:") && s1.contains("key:")) {
                                        details.add(s1.substring(s1.indexOf("uriPath:"), s1.indexOf("key:")).replace(",", "").trim());
                                    }
                                    if (s1.contains("key:") && s1.contains("selectedBranch:")) {
                                        details.add(s1.substring(s1.indexOf("key:"), s1.indexOf("selectedBranch:")).replace(",", "").trim());
                                        key.add(s1.substring(s1.indexOf("key:"), s1.indexOf("selectedBranch:")).replace(",", "").trim());
                                    }
                                    if (s1.contains("selectedBranch:") && s1.contains("date:")) {
                                        if (!details.contains(s1.substring(s1.indexOf("selectedBranch:"), s1.indexOf("date:")).replace(",","").trim())) {
                                            details.add(s1.substring(s1.indexOf("selectedBranch:"), s1.indexOf("date:")).replace(",", "").trim());
                                        }
                                        if (!key.contains(s1.substring(s1.indexOf("selectedBranch:"), s1.indexOf("date:")).replace(",","").trim())) {
                                            key.add(s1.substring(s1.indexOf("selectedBranch:"), s1.indexOf("date:")).replace(",", "").trim());
                                        }
                                    }
                                    if (s1.contains("studentname:") && s1.contains("contactnumber:")) {
                                        if (!details.contains(s1.substring(s1.indexOf("studentname:"), s1.indexOf("contactnumber:")).replace(",","").trim())) {
                                            details.add(s1.substring(s1.indexOf("studentname:"), s1.indexOf("contactnumber:")).replace(",", "").trim());
                                        }
                                        if (!key.contains(s1.substring(s1.indexOf("studentname:"), s1.indexOf("contactnumber:")).replace(",","").trim())) {
                                            key.add(s1.substring(s1.indexOf("studentname:"), s1.indexOf("contactnumber:")).replace(",", "").trim());
                                        }
                                    }
                                    if (s1.contains("contactnumber:") && s1.contains("Semester:")) {
                                        if (!details.contains(s1.substring(s1.indexOf("contactnumber:"), s1.indexOf("Semester:")).replace(",","").trim())) {
                                            details.add(s1.substring(s1.indexOf("contactnumber:"), s1.indexOf("Semester:")).replace(",", "").trim());
                                        }
                                        if (!key.contains(s1.substring(s1.indexOf("contactnumber:"), s1.indexOf("Semester:")).replace(",","").trim())) {
                                            key.add(s1.substring(s1.indexOf("contactnumber:"), s1.indexOf("Semester:")).replace(",", "").trim());
                                        }
                                    }
                                    if (s1.contains("Semester:" ) && s1.contains("selectedBranch:")) {
                                        if (!details.contains(s1.substring(s1.indexOf("Semester:"), s1.indexOf("selectedBranch:")).replace("selectedBranch:", "").replace(",", "").trim()))
                                            details.add(s1.substring(s1.indexOf("Semester:")).replace("selectedBranch:", "").replace(",", "").trim());
                                        }
                                    if (!key.contains(s1.substring(s1.indexOf("Semester:")).replace("selectedBranch:","").replace(",","").trim())) {
                                        key.add(s1.substring(s1.indexOf("Semester:")).replace("selectedBranch:", "").replace(",", "").trim());
                                    }
                                    }
                                    if (s1.contains("selectedBranch:") && s1.contains("emailid:")) {
                                        if (!details.contains(s1.substring(s1.indexOf("selectedBranch:"), s1.indexOf("emailid:")).replace(",","").trim())) {
                                            details.add(s1.substring(s1.indexOf("selectedBranch:"), s1.indexOf("emailid:")).replace(",", "").trim());
                                        }
                                        if (!key.contains(s1.substring(s1.indexOf("selectedBranch:"), s1.indexOf("emailid:")).replace(",","").trim())) {
                                            key.add(s1.substring(s1.indexOf("selectedBranch:"), s1.indexOf("emailid:")).replace(",", "").trim());
                                        }
                                    }
                                    if (s1.contains("emailid:")) {
                                        if (!details.contains(s1.substring(s1.indexOf("emailid:")))) {
                                            details.add(s1.substring(s1.indexOf("emailid:")));
                                        }
                                        if (!key.contains(s1.indexOf("emailid:"))) {
                                            key.add(s1.substring(s1.indexOf("emailid:")));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    mapregTodetails.put(s, details);
                    mapregTokey.put(s, key);
                }

            System.out.println("mapregTodetails: " + mapregTodetails);
            System.out.println("mapregTokey: " + mapregTokey);

            a1.add(0, mapregTodetails);
            a1.add(1, mapregTokey);

            return a1;
        }
}