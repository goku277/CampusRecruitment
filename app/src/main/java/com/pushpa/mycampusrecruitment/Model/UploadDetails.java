package com.pushpa.mycampusrecruitment.Model;

public class UploadDetails {
    String name, email, address, contact, password, referenceid, role, username;

    public UploadDetails(String name, String email, String address, String contact, String password, String referenceid, String role, String username) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.contact = contact;
        this.password = password;
        this.referenceid= referenceid;
        this.role= role;
        this.username= username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UploadDetails() {

    }

    public String getReferenceid() {
        return referenceid;
    }

    public void setReferenceid(String referenceid) {
        this.referenceid = referenceid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}