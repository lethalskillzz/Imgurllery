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
    public static final String RESULT_KEY = "resultTempKey";

    public static final String CLICK_GRID = "clickGrid";
}


