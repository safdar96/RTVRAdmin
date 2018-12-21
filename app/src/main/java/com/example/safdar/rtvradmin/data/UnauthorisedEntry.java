package com.example.safdar.rtvradmin.data;

public class UnauthorisedEntry {

    private long mTime;
    private String mLicense;

    public UnauthorisedEntry(String license, long time) {
        mLicense = license;
        mTime = time;
    }

    public long getTime() {
        return mTime;
    }

    public String getLicenseNo() {
        return mLicense;
    }
}
