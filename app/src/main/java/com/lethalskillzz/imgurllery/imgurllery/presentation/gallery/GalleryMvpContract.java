package com.lethalskillzz.imgurllery.imgurllery.presentation.gallery;

import com.lethalskillzz.imgurllery.imgurllery.data.model.Image;
import com.lethalskillzz.imgurllery.mvp.Mvp;

import java.util.List;

/**
 * Created by ibrahimabdulkadir on 18/04/2017.
 */

public interface GalleryMvpContract {

    public interface View extends Mvp.View {

        void showGallery(List<Image> images);

        void showError(String error);

        void showLoading();

        void hideLoading();
    }

    public interface Presenter extends Mvp.Presenter<GalleryMvpContract.View> {
        void getGallery(String mOrderType);
    }

}
