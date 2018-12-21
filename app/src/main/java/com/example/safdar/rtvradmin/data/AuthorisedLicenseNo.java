package com.example.safdar.rtvradmin.data;

public class AuthorisedLicenseNo {
    private int mId;
    private String mOwner;
    private String mLicenseNo;

    public AuthorisedLicenseNo() {

    }

    public AuthorisedLicenseNo(String owner, String license_no) {
        mOwner = owner;
        mLicenseNo = license_no;
    }

    public String getOwnerName() {
        return mOwner;
    }

    public String getLicenseNo() {
        return mLicenseNo;
    }

    public void setOwner(String owner) {
        this.mOwner = owner;
    }

    public void setLicenseNo(String licenseNo) {
        this.mLicenseNo = licenseNo;
    }
}
