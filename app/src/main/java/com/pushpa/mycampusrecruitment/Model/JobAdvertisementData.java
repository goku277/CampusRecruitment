package com.pushpa.mycampusrecruitment.Model;

public class JobAdvertisementData {
    private String jobImageUrl, jobName;

    public JobAdvertisementData(String jobImageUrl, String jobName) {
        this.jobImageUrl = jobImageUrl;
        this.jobName = jobName;
    }

    public String getJobImageUrl() {
        return jobImageUrl;
    }

    public void setJobImageUrl(String jobImageUrl) {
        this.jobImageUrl = jobImageUrl;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
