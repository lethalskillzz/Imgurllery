package com.lethalskillzz.imgurllery.imgurllery.data.rest;


import com.lethalskillzz.imgurllery.imgurllery.data.model.Page;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.ROUTE;

/**
 * Created by ibrahimabdulkadir on 9/14/2016.
 */
public interface ApiInterface {

    @GET(ROUTE)
    Observable<Page> getGallery(@Path("section") String section, @Path("sort") String sort,
                             @Path("window") String window, @Path("page") String page, @Query("showViral") boolean showViral);

}
