package edu.uml.movie_app;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MovieDto implements Parcelable {
    Integer id;
    String title;
    Integer year;
    Integer length;
    String genre;

    public MovieDto() {}

    protected MovieDto(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        if (in.readByte() == 0) {
            year = null;
        } else {
            year = in.readInt();
        }
        if (in.readByte() == 0) {
            length = null;
        } else {
            length = in.readInt();
        }
        genre = in.readString();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(title);
        if (year == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(year);
        }
        if (length == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(length);
        }
        dest.writeString(genre);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieDto> CREATOR = new Creator<>() {
        @Override
        public MovieDto createFromParcel(Parcel in) {
            return new MovieDto(in);
        }

        @Override
        public MovieDto[] newArray(int size) {
            return new MovieDto[size];
        }
    };
}
