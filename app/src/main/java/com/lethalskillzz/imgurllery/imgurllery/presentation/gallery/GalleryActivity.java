package com.lethalskillzz.imgurllery.imgurllery.presentation.gallery;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.lethalskillzz.imgurllery.R;
import com.lethalskillzz.imgurllery.imgurllery.data.model.Image;
import com.lethalskillzz.imgurllery.imgurllery.util.ConnectionDetector;
import com.lethalskillzz.imgurllery.imgurllery.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.CURRENT_LAYOUT_KEY;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.CURRENT_NAV_KEY;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.IMAGE_TEMP_KEY;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.LAYOUT_GRID;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.LAYOUT_LIST;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.LAYOUT_STAGGERED_GRID;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.ORDER_ROUTE_KEY;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.SECTION_HOT;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.SECTION_TOP;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.SECTION_USER;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.SHOW_VIRAL_FALSE;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.SHOW_VIRAL_KEY;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.SHOW_VIRAL_TRUE;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.SORT_RISING;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.SORT_TIME;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.SORT_TOP;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.SORT_VIRAL;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.WINDOW_ALL;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.WINDOW_DAY;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.WINDOW_MONTH;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.WINDOW_WEEK;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.WINDOW_YEAR;

public class GalleryActivity extends AppCompatActivity implements GalleryMvpContract.View {

    private static final String TAG = "GalleryActivity";
    GalleryMvpContract.Presenter presenter;
    private ConnectionDetector cd;
    private GalleryAdapter galleryAdapter;

    private ActionBar mActionBar;
    private String mOrderRoute;
    private List<Image> mImages;
    private int navItemId;
    private boolean showViral;
    private String recyclerLayout;


    @BindView(R.id.gallery_drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.gallery_navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.gallery_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.gallery_coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.gallery_recycler_view)
    RecyclerView rView;
    @BindView(R.id.gallery_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.refresh_text)
    TextView refreshText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();

        // creating connection detector class instance
        cd = new ConnectionDetector(this);

        mImages = new ArrayList<>();
        mOrderRoute = SECTION_HOT+SORT_VIRAL;
        navItemId = R.id.nav_hot;
        showViral = true;
        recyclerLayout = LAYOUT_LIST;

        refreshActionBar();

        presenter = new GalleryPresenter();
        presenter.attachView(this);

        setupViews();
        setupDrawer();

        galleryAdapter = new GalleryAdapter(this);
        rView.setAdapter(galleryAdapter);

        refreshText.setVisibility(View.GONE);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(CURRENT_NAV_KEY)) {
                navItemId = savedInstanceState.getInt(CURRENT_NAV_KEY);
            }
            if (savedInstanceState.containsKey(ORDER_ROUTE_KEY)) {
                mOrderRoute = savedInstanceState.getString(ORDER_ROUTE_KEY, SECTION_HOT+SORT_VIRAL);
            }
            if (savedInstanceState.containsKey(IMAGE_TEMP_KEY)) {
                mImages = savedInstanceState.getParcelableArrayList(IMAGE_TEMP_KEY);
                galleryAdapter.setResults(mImages);
            }
            if (savedInstanceState.containsKey(SHOW_VIRAL_KEY)) {
                showViral = savedInstanceState.getBoolean(SHOW_VIRAL_KEY);
            }
            if (savedInstanceState.containsKey(CURRENT_LAYOUT_KEY)) {
                recyclerLayout = savedInstanceState.getString(CURRENT_LAYOUT_KEY);
            }

            refreshActionBar();
            setRecyclerLayout();

        } else {
            if (cd.isConnectingToInternet()) {

                presenter.getGallery(mOrderRoute);

            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                showError(getString(R.string.error_no_internet));
            }
        }

    }




    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showGallery(List<Image> images) {
        mImages = images;
        galleryAdapter.setResults(mImages);
    }

    @Override
    public void showError(String msg) {

        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        if (mImages != null) {
            outState.putString(ORDER_ROUTE_KEY, mOrderRoute);
            outState.putInt(CURRENT_NAV_KEY, navItemId);
            outState.putParcelableArrayList(IMAGE_TEMP_KEY, (ArrayList<? extends Parcelable>) mImages);
            outState.putBoolean(SHOW_VIRAL_KEY, showViral);
            outState.putString(CURRENT_LAYOUT_KEY, recyclerLayout);
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_gallery_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                //toggleOrderType(item);
                showPopupMenu(item.getItemId());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private String currentTitle(String title) {

        switch(title) {

            case SECTION_HOT+SORT_VIRAL:
                title = getString(R.string.subtitle_hot_viral);
                break;
            case SECTION_HOT+SORT_TOP:
                title = getString(R.string.subtitle_hot_top);
                break;
            case SECTION_HOT+SORT_TIME:
                title = getString(R.string.subtitle_hot_time);
                break;

            case SECTION_TOP+SORT_VIRAL+WINDOW_DAY:
                title = getString(R.string.subtitle_top_viral_day);
                break;
            case SECTION_TOP+SORT_VIRAL+WINDOW_WEEK:
                title = getString(R.string.subtitle_top_viral_week);
                break;
            case SECTION_TOP+SORT_VIRAL+WINDOW_MONTH:
                title = getString(R.string.subtitle_top_viral_month);
                break;
            case SECTION_TOP+SORT_VIRAL+WINDOW_YEAR:
                title = getString(R.string.subtitle_top_viral_year);
                break;
            case SECTION_TOP+SORT_VIRAL+WINDOW_ALL:
                title = getString(R.string.subtitle_top_viral_all);
                break;

            case SECTION_TOP+SORT_TOP+WINDOW_DAY:
                title = getString(R.string.subtitle_top_top_day);
                break;
            case SECTION_TOP+SORT_TOP+WINDOW_WEEK:
                title = getString(R.string.subtitle_top_top_week);
                break;
            case SECTION_TOP+SORT_TOP+WINDOW_MONTH:
                title = getString(R.string.subtitle_top_top_month);
                break;
            case SECTION_TOP+SORT_TOP+WINDOW_YEAR:
                title = getString(R.string.subtitle_top_top_year);
                break;
            case SECTION_TOP+SORT_TOP+WINDOW_ALL:
                title = getString(R.string.subtitle_top_top_all);
                break;

            case SECTION_TOP+SORT_TIME+WINDOW_DAY:
                title = getString(R.string.subtitle_top_time_day);
                break;
            case SECTION_TOP+SORT_TIME+WINDOW_WEEK:
                title = getString(R.string.subtitle_top_time_week);
                break;
            case SECTION_TOP+SORT_TIME+WINDOW_MONTH:
                title = getString(R.string.subtitle_top_time_month);
                break;
            case SECTION_TOP+SORT_TIME+WINDOW_YEAR:
                title = getString(R.string.subtitle_top_time_year);
                break;
            case SECTION_TOP+SORT_TIME+WINDOW_ALL:
                title = getString(R.string.subtitle_top_time_all);
                break;

            case SECTION_USER+SORT_VIRAL+SHOW_VIRAL_TRUE:
                title = getString(R.string.subtitle_user_viral);
                break;
            case SECTION_USER+SORT_VIRAL+SHOW_VIRAL_FALSE:
                title = getString(R.string.subtitle_user_viral);
                break;

            case SECTION_USER+SORT_TOP+SHOW_VIRAL_TRUE:
                title = getString(R.string.subtitle_user_top);
                break;
            case SECTION_USER+SORT_TOP+SHOW_VIRAL_FALSE:
                title = getString(R.string.subtitle_user_top);
                break;

            case SECTION_USER+SORT_TIME+SHOW_VIRAL_TRUE:
                title = getString(R.string.subtitle_user_time);
                break;
            case SECTION_USER+SORT_TIME+SHOW_VIRAL_FALSE:
                title = getString(R.string.subtitle_user_time);
                break;

            case SECTION_USER+SORT_RISING+SHOW_VIRAL_TRUE:
                title = getString(R.string.subtitle_user_rising);
                break;
            case SECTION_USER+SORT_RISING+SHOW_VIRAL_FALSE:
                title = getString(R.string.subtitle_user_rising);
                break;
        }

        return title;
    }

    private void refreshActionBar() {
        if (mActionBar != null) {

            mActionBar.setSubtitle(currentTitle(mOrderRoute));
            mActionBar.invalidateOptionsMenu();
        }
    }

    private void toggleOrderRoute() {

        if (cd.isConnectingToInternet()) {

            presenter.getGallery(mOrderRoute);

            refreshActionBar();

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            showError(getString(R.string.error_no_internet));
        }
    }

    private  void setupViews() {

        rView.setHasFixedSize(true);
        setRecyclerLayout();
        rView.addItemDecoration(new SpacesItemDecoration(10));

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (cd.isConnectingToInternet()) {

                    presenter.getGallery(mOrderRoute);

                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    showError(getString(R.string.error_no_internet));
                }
            }
        });
    }

    private void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_about:
                        //showAbout();
                        break;

                    case R.id.nav_list: {
                        recyclerLayout = LAYOUT_LIST;
                        setRecyclerLayout();
                    }
                    break;

                    case R.id.nav_grid: {
                        recyclerLayout = LAYOUT_GRID;
                        setRecyclerLayout();
                    }
                    break;

                    case R.id.nav_staggered_grid:{
                        recyclerLayout = LAYOUT_STAGGERED_GRID;
                        setRecyclerLayout();
                    }
                    break;

                    case R.id.nav_top: {
                        navItemId = R.id.nav_top;
                        mOrderRoute = SECTION_TOP+SORT_VIRAL+WINDOW_ALL;
                        refreshActionBar();
                        toggleOrderRoute();
                    }
                    break;

                    case R.id.nav_user: {
                        navItemId = R.id.nav_user;

                        mOrderRoute = SECTION_USER+SORT_VIRAL;
                        if(showViral)
                            mOrderRoute = mOrderRoute+SHOW_VIRAL_TRUE;
                        else
                            mOrderRoute = mOrderRoute+SHOW_VIRAL_FALSE;

                        refreshActionBar();
                        toggleOrderRoute();
                    }
                    break;

                    default:
                    case R.id.nav_hot: {
                        navItemId = R.id.nav_hot;
                        mOrderRoute = SECTION_HOT+SORT_VIRAL;
                        refreshActionBar();
                        toggleOrderRoute();
                    }
                    break;

                }

                mDrawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void showPopupMenu (int viewId) {

        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(GalleryActivity.this, findViewById(R.id.menu_filter));

        if(viewId == R.id.menu_filter) {

            if(navItemId == R.id.nav_user) {

                popup.getMenuInflater().inflate(R.menu.filter_show_viral_popup_menu, popup.getMenu());

                MenuItem item = popup.getMenu().findItem(R.id.filter_show_viral);
                item.setChecked(showViral);

            } else {
                popup.getMenuInflater().inflate(R.menu.filter_sort_other_popup_menu, popup.getMenu());
            }

        } else if(viewId == R.id.filter_sort_other_viral) {

            mOrderRoute = SECTION_TOP+SORT_VIRAL;
            popup.getMenuInflater().inflate(R.menu.filter_window_popup_menu, popup.getMenu());

        } else if(viewId == R.id.filter_sort_other_top) {

            mOrderRoute = SECTION_TOP+SORT_TOP;
            popup.getMenuInflater().inflate(R.menu.filter_window_popup_menu, popup.getMenu());

        }else if(viewId == R.id.filter_sort_other_time) {

            mOrderRoute = SECTION_TOP+SORT_TIME;
            popup.getMenuInflater().inflate(R.menu.filter_window_popup_menu, popup.getMenu());

        } else if(viewId == R.id.filter_show_viral_sort) {

            popup.getMenuInflater().inflate(R.menu.filter_sort_user_popup_menu, popup.getMenu());

        } else {

        }

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.filter_sort_other_viral: {
                        if(navItemId == R.id.nav_hot) {
                            mOrderRoute = SECTION_HOT+SORT_VIRAL;
                            refreshActionBar();
                            toggleOrderRoute();
                        } else {
                            showPopupMenu(R.id.filter_sort_other_viral);
                        }
                    }
                    break;

                    case R.id.filter_sort_other_top: {
                        if (navItemId == R.id.nav_hot) {
                            mOrderRoute = SECTION_HOT+SORT_TOP;
                            refreshActionBar();
                            toggleOrderRoute();
                        } else {
                            showPopupMenu(R.id.filter_sort_other_top);
                        }
                    }
                    break;

                    case R.id.filter_sort_other_time: {
                        if (navItemId == R.id.nav_hot) {
                            mOrderRoute = SECTION_HOT+SORT_TIME;
                            refreshActionBar();
                            toggleOrderRoute();
                        } else {
                            showPopupMenu(R.id.filter_sort_other_time);
                        }
                    }
                    break;

                    case R.id.filter_window_day: {
                        mOrderRoute = mOrderRoute + WINDOW_DAY;
                        refreshActionBar();
                        toggleOrderRoute();
                    }
                    break;

                    case R.id.filter_window_week: {
                        mOrderRoute = mOrderRoute + WINDOW_WEEK;
                        refreshActionBar();
                        toggleOrderRoute();
                    }
                    break;

                    case R.id.filter_window_month: {
                        mOrderRoute = mOrderRoute + WINDOW_MONTH;
                        refreshActionBar();
                        toggleOrderRoute();
                    }
                    break;

                    case R.id.filter_window_year: {
                        mOrderRoute = mOrderRoute + WINDOW_YEAR;
                        refreshActionBar();
                        toggleOrderRoute();
                    }
                    break;

                    case R.id.filter_window_all: {
                        mOrderRoute = mOrderRoute + WINDOW_ALL;
                        refreshActionBar();
                        toggleOrderRoute();
                    }
                    break;

                    case R.id.filter_sort_user_viral: {
                        mOrderRoute = SECTION_USER + SORT_VIRAL;
                        if(showViral)
                            mOrderRoute = mOrderRoute+SHOW_VIRAL_TRUE;
                        else
                            mOrderRoute = mOrderRoute+SHOW_VIRAL_FALSE;

                        refreshActionBar();
                        toggleOrderRoute();
                    }
                    break;

                    case R.id.filter_sort_user_top: {
                        mOrderRoute = SECTION_USER + SORT_TOP;
                        if(showViral)
                            mOrderRoute = mOrderRoute+SHOW_VIRAL_TRUE;
                        else
                            mOrderRoute = mOrderRoute+SHOW_VIRAL_FALSE;

                        refreshActionBar();
                        toggleOrderRoute();
                    }
                    break;

                    case R.id.filter_sort_user_time: {
                        mOrderRoute = SECTION_USER + SORT_TIME;
                        if(showViral)
                            mOrderRoute = mOrderRoute+SHOW_VIRAL_TRUE;
                        else
                            mOrderRoute = mOrderRoute+SHOW_VIRAL_FALSE;

                        refreshActionBar();
                        toggleOrderRoute();
                    }
                    break;

                    case R.id.filter_sort_user_rising: {
                        mOrderRoute = SECTION_USER + SORT_RISING;
                        if(showViral)
                            mOrderRoute = mOrderRoute+SHOW_VIRAL_TRUE;
                        else
                            mOrderRoute = mOrderRoute+SHOW_VIRAL_FALSE;

                        refreshActionBar();
                        toggleOrderRoute();
                    }
                    break;

                    case R.id.filter_show_viral: {

                        showViral = !showViral;

                        if(showViral) {
                            mOrderRoute = mOrderRoute.replace(mOrderRoute.substring(mOrderRoute.length() - 16), "");
                            mOrderRoute = mOrderRoute + SHOW_VIRAL_TRUE;
                        } else {
                            mOrderRoute = mOrderRoute.replace(mOrderRoute.substring(mOrderRoute.length() - 15), "");
                            mOrderRoute = mOrderRoute + SHOW_VIRAL_FALSE;
                        }
                        toggleOrderRoute();

                    }
                    break;

                    case R.id.filter_show_viral_sort:
                        showPopupMenu(R.id.filter_show_viral_sort);
                        break;

                    default:
                        break;
                }
                return true;
            }
        });

        popup.show();//showing popup menu
    }

    private void setRecyclerLayout() {

        switch (recyclerLayout) {

            case LAYOUT_LIST:
                rView.setLayoutManager(new LinearLayoutManager(this));
                break;

            case LAYOUT_GRID:
                rView.setLayoutManager(new GridLayoutManager(this, 2));
                break;

            case LAYOUT_STAGGERED_GRID:
                rView.setLayoutManager(new StaggeredGridLayoutManager(2,
                        StaggeredGridLayoutManager.VERTICAL));
                break;

        }

    }

}
