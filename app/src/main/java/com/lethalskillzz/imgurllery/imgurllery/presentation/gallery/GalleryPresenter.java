package com.lethalskillzz.imgurllery.imgurllery.presentation.gallery;

import android.util.Log;

import com.lethalskillzz.imgurllery.imgurllery.data.model.Image;
import com.lethalskillzz.imgurllery.imgurllery.data.model.Page;
import com.lethalskillzz.imgurllery.imgurllery.data.rest.ApiClient;
import com.lethalskillzz.imgurllery.imgurllery.data.rest.ApiInterface;
import com.lethalskillzz.imgurllery.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.GALLERY_ROUTE;

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
    public void getGallery(String mOrderRoute) {

        checkViewAttached();
        getView().showLoading();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Observable<Page> call;

        call = apiService.getPage(GALLERY_ROUTE+mOrderRoute);

        addSubscription(call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Page>() {
                    @Override
                    public void onCompleted() {

                        getView().hideLoading();

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                        getView().hideLoading();

                        // cast to retrofit.HttpException to get the response code
                        if (e instanceof HttpException) {

                            HttpException response = (HttpException) e;
                            Log.e(TAG, "Response Code: " + response.code());
                            Log.e(TAG, "Response Message: " + response.message());

                            getView().showError(response.code()+" "+response.message());

                        }else {
                            getView().showError(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(Page page) {
                        images = page.getData();
                        getView().hideLoading();
                        getView().showGallery(images);

                    }

                })
        );

    }

}
