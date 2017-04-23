package com.lethalskillzz.mobigur.mobigur.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ibrahimabdulkadir on 16/04/2017.
 */

public class Image implements Parcelable {


    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("datetime")
    @Expose
    private String datetime;

    @SerializedName("cover")
    @Expose
    private String cover;

    @SerializedName("ups")
    @Expose
    private Integer ups;

    @SerializedName("downs")
    @Expose
    private Integer downs;

    @SerializedName("score")
    @Expose
    private Integer score;

    @SerializedName("is_album")
    @Expose
    private Boolean isAlbum;


    public Image () {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }


    public Integer getUps() {
        return ups;
    }

    public void setUps(Integer ups) {
        this.ups = ups;
    }

    public Integer getDowns() {
        return downs;
    }

    public void setDowns(Integer downs) {
        this.downs = downs;
    }


    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getIsAlbum() {
        return isAlbum;
    }

    public void setIsAlbum(Boolean isAlbum) {
        this.isAlbum = isAlbum;
    }


    @Override
    public int describeContents() {
        return 0;
    }


    protected Image(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        datetime = in.readString();
        cover = in.readString();
        ups = in.readInt();
        downs = in.readInt();
        score = in.readInt();
        isAlbum = in.readInt() == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(cover);
        dest.writeString(cover);
        dest.writeInt(ups);
        dest.writeInt(downs);
        dest.writeInt(score);
        dest.writeByte((byte) (isAlbum ? 1 : 0));
    }

}
