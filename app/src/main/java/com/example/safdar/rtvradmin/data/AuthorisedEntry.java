package com.example.safdar.rtvradmin.data;

public class AuthorisedEntry {


    private int mId;
    private String mName;
    private long mTime;
    private String mLicense;

    public AuthorisedEntry(String name, String license, long time) {
        mName = name;
        mLicense = license;
        mTime = time;
    }

    public String getName() {
        return mName;
    }

    public long getTime() {
        return mTime;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getLicenseNo() {
        return mLicense;
    }

    public int getId() {
        return mId;
    }
}
