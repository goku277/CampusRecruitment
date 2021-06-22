package com.pushpa.mycampusrecruitment.Processor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class NewDataParse {
    public static class Node {
        String data;
        Node left, right;
        public Node(String data) {
            this.data= data;
            left= right= null;
        }
    }
    Node head, tail;
    public static void insert(String data, NewDataParse n1) {
        Node new_node= new Node(data);
        if (n1.head== null && n1.tail== null) {
            n1.head= new_node;
            n1.tail= new_node;
            n1.head.left= null;
            n1.tail.right= null;
        }
        else {
            Node temp= n1.tail;
            n1.tail.right= new_node;
            n1.tail= new_node;
            n1.tail.left= temp;
        }
    }
    public static ArrayList<ArrayList<String>> checkAndParse(NewDataParse n1) {
        if (n1.head==null && n1.tail==null) return null;
        else {
            ArrayList<ArrayList<String>> setToList= new ArrayList<>();
            Set<ArrayList<String>> bigData= new LinkedHashSet<>();
            Node curr= n1.head;
            while (curr!=null) {
                ArrayList<String> tempData = new ArrayList<>();
                while (curr!=null && !curr.data.equals("End of data")) {
                    if (!curr.data.trim().equals("End of data")) {
                        tempData.add(curr.data.trim());
                    }
                    else {
                        if (!bigData.contains(tempData) || !tempData.isEmpty()) {
                            if (!tempData.isEmpty()) {
                                bigData.add(tempData);
                                break;
                            }
                        }
                    }
                    curr = curr.right;
                }
            }
            setToList.addAll(bigData);
            return setToList;
        }
    }
    public ArrayList<ArrayList<String>> init(String data) {
        System.out.println("data: " + data);
        String split[]= data.split(",");
        NewDataParse n1= new NewDataParse();
        for (String s: split) {
            insert(s, n1);
        }
        ArrayList<ArrayList<String>> a1= checkAndParse(n1);
        return a1;
    }
}