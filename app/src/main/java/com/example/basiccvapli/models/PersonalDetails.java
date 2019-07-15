package com.example.basiccvapli.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonalDetails implements Parcelable {

    private String name;
    private String dateOfBirth;
    private String gender;
    private String email;
    private String aadharNumber;
    private String maritialStatus;
    private String hometown;
    private String permanentAddress;
    private String pincode;
    private String profileImage;

    public PersonalDetails() {
    }

    protected PersonalDetails(Parcel in) {
        name = in.readString();
        dateOfBirth = in.readString();
        gender = in.readString();
        email = in.readString();
        aadharNumber = in.readString();
        maritialStatus = in.readString();
        hometown = in.readString();
        permanentAddress = in.readString();
        pincode = in.readString();
        profileImage = in.readString();
    }

    public static final Creator<PersonalDetails> CREATOR = new Creator<PersonalDetails>() {
        @Override
        public PersonalDetails createFromParcel(Parcel in) {
            return new PersonalDetails(in);
        }

        @Override
        public PersonalDetails[] newArray(int size) {
            return new PersonalDetails[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getMaritialStatus() {
        return maritialStatus;
    }

    public void setMaritialStatus(String maritialStatus) {
        this.maritialStatus = maritialStatus;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(dateOfBirth);
        dest.writeString(gender);
        dest.writeString(email);
        dest.writeString(aadharNumber);
        dest.writeString(maritialStatus);
        dest.writeString(hometown);
        dest.writeString(permanentAddress);
        dest.writeString(pincode);
        dest.writeString(profileImage);
    }
}
