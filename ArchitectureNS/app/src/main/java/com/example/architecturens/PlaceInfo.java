package com.example.architecturens;

import android.os.Parcel;
import android.os.Parcelable;


public final class PlaceInfo implements Parcelable {

    private  Integer id;
    private  String title;
    private  String description;
    private  byte[] image;
    private  Double grade;
    private  Double latitude;
    private  Double longitude;
    private  RouteInfo routeInfo;


    public PlaceInfo(){}

    private PlaceInfo(Parcel source) {
        id = source.readInt();
        title = source.readString();
        description = source.readString();
        image = new byte[0];
        source.readByteArray(image);
        grade = source.readDouble();
        latitude = source.readDouble();
        longitude = source.readDouble();
        routeInfo = new RouteInfo();
    }

    public PlaceInfo(Integer id, String title, String description, byte[] image, Double grade, Double latitude, Double longitude, RouteInfo routeInfo) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.grade = grade;
        this.latitude = latitude;
        this.longitude = longitude;
        this.routeInfo = routeInfo;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeByteArray(image);
        dest.writeDouble(grade);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeParcelable(null, flags);
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

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }

    public double getGrade() {
        return grade;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public RouteInfo getRouteInfo() {
        return routeInfo;
    }


    public void setGrade(Double grade) {
        this.grade = grade;
    }
}
