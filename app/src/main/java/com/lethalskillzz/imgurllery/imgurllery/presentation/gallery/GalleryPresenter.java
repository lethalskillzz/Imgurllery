package com.lethalskillzz.imgurllery.imgurllery.presentation.gallery;

import com.lethalskillzz.imgurllery.imgurllery.data.model.Image;
import com.lethalskillzz.imgurllery.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibrahimabdulkadir on 18/04/2017.
 */

public class GalleryPresenter extends BasePresenter<GalleryMvpContract.View> implements GalleryMvpContract.Presenter {

    private static final String TAG = "GalleryPresenter";
    private List<Image> images;

    public GalleryPresenter() {
        images = new ArrayList<>();
    }


    @Override
    public void getGallery(String mOrderType) {


    }

}
