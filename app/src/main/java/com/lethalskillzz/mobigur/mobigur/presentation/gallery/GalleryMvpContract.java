package com.lethalskillzz.mobigur.mobigur.presentation.gallery;

import com.lethalskillzz.mobigur.mobigur.data.model.Image;
import com.lethalskillzz.mobigur.mvp.Mvp;

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
