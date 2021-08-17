package com.example.marqueeservice;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author leon
 */
public class LedStatus implements Parcelable {
    int lednum;

    public int getLednum() {
        return lednum;
    }

    public void setLednum(int lednum) {
        this.lednum = lednum;
    }

    public boolean isLedstatus() {
        return ledstatus;
    }

    public void setLedstatus(boolean ledstatus) {
        this.ledstatus = ledstatus;
    }

    boolean ledstatus;

    public LedStatus(int lednum, boolean ledstatus) {
        this.lednum = lednum;
        this.ledstatus = ledstatus;
    }


    protected LedStatus(Parcel in) {
        lednum = in.readInt();
        ledstatus = in.readByte() != 0;
    }

    public static final Creator<LedStatus> CREATOR = new Creator<LedStatus>() {
        @Override
        public LedStatus createFromParcel(Parcel in) {
            return new LedStatus(in);
        }

        @Override
        public LedStatus[] newArray(int size) {
            return new LedStatus[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(lednum);
        parcel.writeByte((byte) (ledstatus ? 1 : 0));
    }
}
