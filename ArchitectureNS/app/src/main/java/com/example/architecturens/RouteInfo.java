package com.example.architecturens;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public final class RouteInfo implements Parcelable {
    private final String id;
    private final String title;
    private final List<PlaceInfo> places;
    private final int duration;
    private  final String description;
    private final double kilometres;
    private final String pictureFileName;


    public RouteInfo(String id, String title, List<PlaceInfo> places, int duration, String description, double kilometres, String pictureFileName) {
        this.id = id;
        this.title = title;
        this.places = places;
        this.duration = duration;
        this.description = description;
        this.kilometres = kilometres;
        this.pictureFileName = pictureFileName;
    }

    private RouteInfo(Parcel source) {
        id = source.readString();
        title = source.readString();
        places = new ArrayList<>();
        source.readTypedList(places, PlaceInfo.CREATOR);
        duration=source.readInt();
        description=source.readString();
        kilometres=source.readDouble();
        pictureFileName=source.readString();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<PlaceInfo> getPlaces() {return places;}

    public int getDuration() {return  duration;}

    public String getDescription() {return  description;}

    public double getKilometres() {return kilometres;}

    public String getPictureFileName() {return pictureFileName;}

    public PlaceInfo getPlace(String placeId) {
        for(PlaceInfo placeInfo: places) {
            if(placeId.equals(placeInfo.getPlaceId()))
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
        dest.writeString(id);
        dest.writeString(title);
        dest.writeTypedList(places);
        dest.writeInt(duration);
        dest.writeString(description);
        dest.writeDouble(kilometres);
        dest.writeString(pictureFileName);
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




}
