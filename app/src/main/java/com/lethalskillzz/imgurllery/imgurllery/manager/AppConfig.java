package com.lethalskillzz.imgurllery.imgurllery.manager;

/**
 * Created by ibrahimabdulkadir on 12/04/2017.
 */

public class AppConfig {

    // Imgur API url
    public static String BASE_API_URL = "https://api.imgur.com/3/";

    public static final String ROUTE = "gallery/{section}/{sort}/{window}/{page}";


    // Section

    // Get hot section
    public static final String SECTION_HOT = "hot";

    // Get top section
    public static final String SECTION_TOP = "top";

    // Get user section
    public static final String SECTION_USER = "user";



    // Sort

    // Get viral sort
    public static final String SORT_VIRAL = "viral";

    // Get top sort
    public static final String SORT_TOP = "top";

    // Get time sort
    public static final String SORT_TIME = "time";

    // Get rising sort
    public static final String SORT_RISING = "rising";



    // Window

    // Get day window
    public static final String WINDOW_DAY = "day";

    // Get week window
    public static final String WINDOW_WEEK = "week";

    // Get month window
    public static final String WINDOW_MONTH = "month";

    // Get year window
    public static final String WINDOW_YEAR = "year";

    // Get all window
    public static final String WINDOW_ALL = "all";



    // Imgur image url
    public static final String BASE_IMG_URL = "https://i.imgur.com/";


    // Imgur API Key
    public static final String API_KEY = "<YOUR API KEY>";


    public static final String ORDER_TYPE_KEY = "orderTypeKey";
    public static final String IMAGE_TEMP_KEY = "imageTempKey";
    public static final String CURRENT_NAV_KEY = "currentNavKey";

    public static final String CLICK_GRID = "clickGrid";
}



//        section   hot | top | user - defaults to hot
//        sort		viral | top | time | rising (only available with user section) - defaults to viral
//        window	Change the date range of the request if the section is "top", day | week | month | year | all, defaults to day
//        showViral	true | false - Show or hide viral images from the 'user' section. Defaults to true
//
//
//        //gallery/{section}/{sort}/{window}/{page}?showViral=bool
//
//        hot.viral  //gallery/hot/viral/
//        hot.top    //gallery/hot/top/
//        hot.time   //gallery/hot/time
//
//        top.viral.day   //gallery/top/viral/day/
//        top.viral.week  //gallery/top/viral/week/
//        top.viral.month //gallery/top/viral/month/
//        top.viral.year  //gallery/top/viral/year/
//        top.viral.all   //gallery/top/viral/all/
//
//        top.top.day     //gallery/top/top/day/
//        top.top.week    //gallery/top/top/week/
//        top.top.month   //gallery/top/top/month/
//        top.top.year    //gallery/top/top/year/
//        top.top.all     //gallery/top/top/all/
//
//        top.time.day    //gallery/top/time/day/
//        top.time.week   //gallery/top/time/week/
//        top.time.month  //gallery/top/time/month/
//        top.time.year   //gallery/top/time/year/
//        top.time.all    //gallery/top/time/all/
//
//
//        user.viral.showViral(true)  //gallery/user/viral?showViral=true
//        user.viral.showViral(false) //gallery/user/viral?showViral=false
//
//        user.top.showViral(true)    //gallery/user/top?showViral=true
//        user.top.showViral(false)   //gallery/user/top?showViral=false
//
//        user.time.showViral(true)   //gallery/user/time?showViral=true
//        user.time.showViral(false)  //gallery/user/time?showViral=false
//
//        user.rising.showViral(true)    //gallery/user/rising?showViral=true
//        user.rising.showViral(false)   //gallery/user/rising?showViral=false


