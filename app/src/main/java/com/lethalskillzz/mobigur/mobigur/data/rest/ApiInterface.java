package com.lethalskillzz.mobigur.mobigur.data.rest;


import com.lethalskillzz.mobigur.mobigur.data.model.Page;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ibrahimabdulkadir on 9/14/2016.
 */
public interface ApiInterface {

//    @GET(ROUTE)
//    Observable<Page> getGallery(@Path("section") String section, @Path("sort") String sort,
//                             @Path("window") String window, @Path("page") String page, @Query("showViral") boolean showViral);

    @GET
    Observable<Page> getPage(@Header("Authorization") String header, @Url String route);
}


