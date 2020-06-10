package com.example.architecturens;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public final class PlaceInfo implements Parcelable {
    @SerializedName("placeId")
    private final String placeId;
    @SerializedName("placeTitle")
    private final String placeTitle;
    @SerializedName("placeDescription")
    private final String placeDescription;
    @SerializedName("pictureFileName")
    private final String pictureFileName;

    public PlaceInfo(String placeId, String placeTitle, String placeDescription, String pictureFileName) {
        this.placeId = placeId;
        this.placeTitle = placeTitle;
        this.placeDescription = placeDescription;
        this.pictureFileName = pictureFileName;
    }

    private PlaceInfo(Parcel source) {
        placeId = source.readString();
        placeTitle = source.readString();
        placeDescription = source.readString();
        pictureFileName = source.readString();
    }


    public String getPlaceId() { return placeId;}

    public String getPlaceTitle() { return placeTitle;}

    public String getPlaceDescription() { return placeDescription; }

    public String getPictureFileName() { return pictureFileName; }

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
        dest.writeString(pictureFileName);
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
