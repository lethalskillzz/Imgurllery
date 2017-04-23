package com.lethalskillzz.mobigur.mobigur.presentation.gallery;

import android.content.Context;
import android.util.Log;

import com.lethalskillzz.imgurllery.R;
import com.lethalskillzz.mobigur.mobigur.data.database.dao.ImageDataSource;
import com.lethalskillzz.mobigur.mobigur.data.model.Image;
import com.lethalskillzz.mobigur.mobigur.data.model.Page;
import com.lethalskillzz.mobigur.mobigur.data.rest.ApiClient;
import com.lethalskillzz.mobigur.mobigur.data.rest.ApiInterface;
import com.lethalskillzz.mobigur.mvp.BasePresenter;
import com.lethalskillzz.mobigur.mobigur.manager.AppConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ibrahimabdulkadir on 18/04/2017.
 */

public class GalleryPresenter extends BasePresenter<GalleryMvpContract.View> implements GalleryMvpContract.Presenter {

    private static final String TAG = "GalleryPresenter";

    private ImageDataSource imageDataSource;
    private List<Image> images;
    private Context context;

    public GalleryPresenter(Context context) {
        this.context = context;
        imageDataSource = new ImageDataSource(context);
        images = new ArrayList<>();

    }

    @Override
    public void getGallery(String mOrderRoute) {

        checkViewAttached();
        getView().showLoading();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Observable<Page> call;

        call = apiService.getPage(context.getString(R.string.api_key),
                AppConfig.GALLERY_ROUTE+mOrderRoute);

        addSubscription(call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Page>() {
                    @Override
                    public void onCompleted() {

                        for(Image i : images) {
                            imageDataSource.open();
                            imageDataSource.deleteImage(i);
                            imageDataSource.createImage(i);
                            imageDataSource.close();
                        }

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



    @Override
    public void getOfflineGallery() {

        imageDataSource.open();
        if(!imageDataSource.isImageEmpty()) {
            images = imageDataSource.readAllImages();
            getView().showGallery(images);
        }
        imageDataSource.close();


    }

}
