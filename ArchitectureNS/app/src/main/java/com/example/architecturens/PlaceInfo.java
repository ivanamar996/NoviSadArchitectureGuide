package com.example.architecturens;

import android.os.Parcel;
import android.os.Parcelable;

public final class PlaceInfo implements Parcelable {
    private final String placeId;
    private final String placeTitle;
    private final String placeDescription;

    public PlaceInfo(String placeId, String placeTitle, String placeDescription) {
        this.placeId = placeId;
        this.placeTitle = placeTitle;
        this.placeDescription = placeDescription;
    }

    private PlaceInfo(Parcel source) {
        placeId = source.readString();
        placeTitle = source.readString();
        placeDescription = source.readString();
    }


    public String getPlaceId() { return placeId;}

    public String getPlaceTitle() { return placeTitle;}

    public String getPlaceDescription() { return placeDescription; }

    @Override
    public int hashCode() {
        return placeId.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeId);
        dest.writeString(placeTitle);
        dest.writeString(placeDescription);
    }

    public static final Creator<PlaceInfo> CREATOR =
            new Creator<PlaceInfo>() {

                @Override
                public PlaceInfo createFromParcel(Parcel source) {
                    return new PlaceInfo(source);
                }

                @Override
                public PlaceInfo[] newArray(int size) {
                    return new PlaceInfo[size];
                }
            };
}
