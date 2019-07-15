package com.example.basiccvapli.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Education implements Parcelable {

    public String getEdid() {
        return edid;
    }

    public void setEdid(String edid) {
        this.edid = edid;
    }

    private String edid;
    private String degreeType;
    private String instituteName;
    private String pertype;
    private String percentage;
    private String fieldOfStudy;
    private String fromdate;
    private String todate;

    public Education() {
    }

    protected Education(Parcel in) {
        edid = in.readString();
        degreeType = in.readString();
        instituteName = in.readString();
        pertype = in.readString();
        percentage = in.readString();
        fieldOfStudy = in.readString();
        fromdate = in.readString();
        todate = in.readString();
    }

    public static final Creator<Education> CREATOR = new Creator<Education>() {
        @Override
        public Education createFromParcel(Parcel in) {
            return new Education(in);
        }

        @Override
        public Education[] newArray(int size) {
            return new Education[size];
        }
    };

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getPertype() {
        return pertype;
    }

    public void setPertype(String pertype) {
        this.pertype = pertype;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(edid);
        dest.writeString(degreeType);
        dest.writeString(instituteName);
        dest.writeString(pertype);
        dest.writeString(percentage);
        dest.writeString(fieldOfStudy);
        dest.writeString(fromdate);
        dest.writeString(todate);
    }
}
