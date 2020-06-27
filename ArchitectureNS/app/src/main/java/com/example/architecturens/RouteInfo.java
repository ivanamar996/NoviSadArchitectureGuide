package com.example.architecturens;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public final class RouteInfo implements Parcelable {

    private Integer id;
    private String title;
    private List<PlaceInfo> places;
    private Double duration;
    private String description;
    private Double kilometres;
    private byte[] image;

    private RouteInfo(Parcel source) {
        id = source.readInt();
        title = source.readString();
        places = new ArrayList<>();
        source.readTypedList(places, PlaceInfo.CREATOR);
        duration=source.readDouble();
        description=source.readString();
        kilometres=source.readDouble();
        image = new byte[0];
        source.readByteArray(image);
    }

    public RouteInfo(Integer id, String title, List<PlaceInfo> places, Double duration, String description, Double kilometres, byte[] image) {
        this.id = id;
        this.title = title;
        this.places = places;
        this.duration = duration;
        this.description = description;
        this.kilometres = kilometres;
        this.image = image;
    }

    public RouteInfo(){}


    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<PlaceInfo> getPlaces() {return places;}

    public double getDuration() {return  duration;}

    public String getDescription() {return  description;}

    public double getKilometres() {return kilometres;}

    public byte[] getImage() {return image;}

    public PlaceInfo getPlace(String placeId) {
        for(PlaceInfo placeInfo: places) {
            if(placeId.equals(placeInfo.getId()))
                return placeInfo;
        }
        return null;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
   /* @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteInfo that = (RouteInfo) o;

        return id.equals(that.id);

    }*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeTypedList(places);
        dest.writeDouble(duration);
        dest.writeString(description);
        dest.writeDouble(kilometres);
        dest.writeByteArray(image);
    }

    public static final Creator<RouteInfo> CREATOR =
            new Creator<RouteInfo>() {

                @Override
                public RouteInfo createFromParcel(Parcel source) {
                    return new RouteInfo(source);
                }

                @Override
                public RouteInfo[] newArray(int size) {
                    return new RouteInfo[size];
                }
            };


    public void setPlaces(List<PlaceInfo> places) {
        this.places = places;
    }
}
