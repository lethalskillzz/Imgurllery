package com.lethalskillzz.mobigur.mobigur.manager;

/**
 * Created by ibrahimabdulkadir on 12/04/2017.
 */

public class AppConfig {

    // Imgur API url
    public static String BASE_API_URL = "https://api.imgur.com/3/";

    //public static final String ROUTE = "gallery/{section}/{sort}/{window}/{page}";


    public static final String GALLERY_ROUTE = "gallery";
    // Section

    // Get hot section
    public static final String SECTION_HOT = "/hot";

    // Get top section
    public static final String SECTION_TOP = "/top";

    // Get user section
    public static final String SECTION_USER = "/user";



    // Sort

    // Get viral sort
    public static final String SORT_VIRAL = "/viral";

    // Get top sort
    public static final String SORT_TOP = "/top";

    // Get time sort
    public static final String SORT_TIME = "/time";

    // Get rising sort
    public static final String SORT_RISING = "/rising";



    // Window

    // Get day window
    public static final String WINDOW_DAY = "/day";

    // Get week window
    public static final String WINDOW_WEEK = "/week";

    // Get month window
    public static final String WINDOW_MONTH = "/month";

    // Get year window
    public static final String WINDOW_YEAR = "/year";

    // Get all window
    public static final String WINDOW_ALL = "/all";


    // Get viral true
    public static final String SHOW_VIRAL_TRUE = "?showViral=true";

    // Get viral false
    public static final String SHOW_VIRAL_FALSE = "?showViral=false";


    // Imgur image url
    public static final String BASE_IMG_URL = "https://i.imgur.com/";


    // Imgur API Key
    public static final String API_KEY = "<Your API Key>";
    // User Agent
    public static final String USER_AGENT = "lethalskillzz.com";


    public static final String LARGE_IMG_SUFFIX = "l.jpg";
    public static final String MEDIUM_IMG_SUFFIX = "m.jpg";
    public static final String SMALL_IMG_SUFFIX = "t.jpg";




    public static final String ORDER_ROUTE_KEY = "orderRouteKey";
    public static final String IMAGE_TEMP_KEY = "imageTempKey";
    public static final String SHOW_VIRAL_KEY = "showViralKey";
    public static final String CURRENT_NAV_KEY = "currentNavKey";
    public static final String CURRENT_LAYOUT_KEY = "currentLayout";


    public static final String LAYOUT_GRID = "layoutGrid";
    public static final String LAYOUT_STAGGERED_GRID = "layoutStaggeredGrid";
    public static final String LAYOUT_LIST = "layoutList";

    public static final String CLICK_IMAGE = "clickImage";
}
