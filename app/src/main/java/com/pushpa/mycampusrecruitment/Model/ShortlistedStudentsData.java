package com.pushpa.mycampusrecruitment.Model;

public class ShortlistedStudentsData {
    String studentUrlImage, universityName, studentName;

    public ShortlistedStudentsData(String studentUrlImage) {
        this.studentUrlImage = studentUrlImage;
    }

    public String getStudentUrlImage() {
        return studentUrlImage;
    }

    public void setStudentUrlImage(String studentUrlImage) {
        this.studentUrlImage = studentUrlImage;
    }
}