package com.lethalskillzz.mobigur.mobigur.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ibrahimabdulkadir on 16/04/2017.
 */

public class Page {

    @SerializedName("data")
    @Expose
    private List<Image> images = null;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<Image> getData() {
        return images;
    }

    public void setData(List<Image> images) {
        this.images = images;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
