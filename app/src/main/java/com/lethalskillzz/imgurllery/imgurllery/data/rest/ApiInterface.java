package com.lethalskillzz.imgurllery.imgurllery.data.rest;


import com.lethalskillzz.imgurllery.imgurllery.data.model.Page;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;
import rx.Observable;

import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.API_KEY;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.USER_AGENT;

/**
 * Created by ibrahimabdulkadir on 9/14/2016.
 */
public interface ApiInterface {

//    @GET(ROUTE)
//    Observable<Page> getGallery(@Path("section") String section, @Path("sort") String sort,
//                             @Path("window") String window, @Path("page") String page, @Query("showViral") boolean showViral);


    @Headers({
            "Authorization: " + API_KEY,
            "User-Agent: " + USER_AGENT
    })
    @GET
    Observable<Page> getPage(@Url String route);
}


