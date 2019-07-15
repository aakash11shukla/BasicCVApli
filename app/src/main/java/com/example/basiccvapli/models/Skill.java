package com.example.basiccvapli.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Skill implements Parcelable {

    public static final Creator<Skill> CREATOR = new Creator<Skill>() {
        @Override
        public Skill createFromParcel(Parcel in) {
            return new Skill(in);
        }

        @Override
        public Skill[] newArray(int size) {
            return new Skill[size];
        }
    };
    private String sid;
    private String skillName;
    private String skillLevel;

    public Skill() {

    }

    protected Skill(Parcel in) {
        skillName = in.readString();
        skillLevel = in.readString();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(skillName);
        dest.writeString(skillLevel);
    }
}
