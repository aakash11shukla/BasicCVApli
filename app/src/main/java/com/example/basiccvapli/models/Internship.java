package com.example.basiccvapli.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Internship implements Parcelable {

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    private String iid;
    private String companyName;
    private String industry;private String designation;
    private String location;
    private String fromDate;
    private String toDate;

    public Internship() {
    }

    protected Internship(Parcel in) {
        iid = in.readString();
        companyName = in.readString();
        industry = in.readString();
        designation = in.readString();
        location = in.readString();
        fromDate = in.readString();
        toDate = in.readString();
    }

    public static final Creator<Internship> CREATOR = new Creator<Internship>() {
        @Override
        public Internship createFromParcel(Parcel in) {
            return new Internship(in);
        }

        @Override
        public Internship[] newArray(int size) {
            return new Internship[size];
        }
    };

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iid);
        dest.writeString(companyName);
        dest.writeString(industry);
        dest.writeString(designation);
        dest.writeString(location);
        dest.writeString(fromDate);
        dest.writeString(toDate);
    }
}
