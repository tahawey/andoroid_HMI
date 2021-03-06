package com.example.tahaweyplccontrol;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rohit on 20/03/16.
 */
public class RegisterValue implements Parcelable {
    private int regId;
    private int regValue;

    /**
     * @param regId    register Id
     * @param regValue register value
     */
    public RegisterValue(int regId, int regValue) {
        this.regId = regId;
        this.regValue = regValue;
    }

    protected RegisterValue(Parcel in) {
        regId = in.readInt();
        regValue = in.readInt();
    }

    public static final Creator<RegisterValue> CREATOR = new Creator<RegisterValue>() {
        @Override
        public com.example.tahaweyplccontrol.RegisterValue createFromParcel(Parcel in) {
            return new com.example.tahaweyplccontrol.RegisterValue(in);
        }

        @Override
        public com.example.tahaweyplccontrol.RegisterValue[] newArray(int size) {
            return new com.example.tahaweyplccontrol.RegisterValue[size];
        }
    };

    public float getRegValue() {
        return regValue;
    }

    public void setRegValue(int regValue) {
        this.regValue = regValue;
    }


    public int getRegId() {
        return regId;
    }

    public void setRegId(int regId) {
        this.regId = regId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(regId);
        dest.writeFloat(regValue);
    }

}
