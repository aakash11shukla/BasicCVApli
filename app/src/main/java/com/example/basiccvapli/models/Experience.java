package com.example.basiccvapli.models;

import android.os.Parcel;
import android.os.Parcelable;
public class Experience implements Parcelable {

    private String eid;
    private String companyName;
    private String industry;
    private String designation;
    private String location;
    private String fromDate;
    private String toDate;

    public Experience() {
    }

    protected Experience(Parcel in) {
        eid = in.readString();
        companyName = in.readString();
        industry = in.readString();
        designation = in.readString();
        location = in.readString();
        fromDate = in.readString();
        toDate = in.readString();
    }

    public static final Creator<Experience> CREATOR = new Creator<Experience>() {
        @Override
        public Experience createFromParcel(Parcel in) {
            return new Experience(in);
        }

        @Override
        public Experience[] newArray(int size) {
            return new Experience[size];
        }
    };

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

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
        dest.writeString(eid);
        dest.writeString(companyName);
        dest.writeString(industry);
        dest.writeString(designation);
        dest.writeString(location);
        dest.writeString(fromDate);
        dest.writeString(toDate);
    }
}
