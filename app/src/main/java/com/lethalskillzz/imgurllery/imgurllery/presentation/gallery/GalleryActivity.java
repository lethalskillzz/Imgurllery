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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
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

import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.CURRENT_NAV_KEY;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.IMAGE_TEMP_KEY;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.ORDER_TYPE_KEY;

public class GalleryActivity extends AppCompatActivity implements GalleryMvpContract.View {

    private static final String TAG = "GalleryActivity";
    GalleryMvpContract.Presenter presenter;
    private ConnectionDetector cd;
    private GalleryAdapter galleryAdapter;

    private ActionBar mActionBar;
    private String mOrderType;
    private List<Image> mImages;
    private int navItemId;


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
        mOrderType = "popularity";
        navItemId = R.id.nav_hot;

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
            if (savedInstanceState.containsKey(ORDER_TYPE_KEY)) {
                mOrderType = savedInstanceState.getString(ORDER_TYPE_KEY, "popularity");
            }
            if (savedInstanceState.containsKey(IMAGE_TEMP_KEY)) {
                mImages = savedInstanceState.getParcelableArrayList(IMAGE_TEMP_KEY);
                galleryAdapter.setResults(mImages);
            }

            refreshActionBar();

        } else {
            if (cd.isConnectingToInternet()) {

                presenter.getGallery(mOrderType);

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
            outState.putString(ORDER_TYPE_KEY, mOrderType);
            outState.putInt(CURRENT_NAV_KEY, navItemId);
            outState.putParcelableArrayList(IMAGE_TEMP_KEY, (ArrayList<? extends Parcelable>) mImages);
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

//        if (!mOrderType.equals("popularity")) {
//            menu.findItem(R.id.menu_toggle).setTitle(R.string.toggle_popular);
//        } else {
//            menu.findItem(R.id.menu_toggle).setTitle(R.string.toggle_top_rated);
//        }
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


    private void refreshActionBar() {
//        if (mActionBar != null) {
//            mActionBar.setSubtitle((mOrderType.equals("popularity")
//                    ? getString(R.string.subtitle_most_popular)
//                    : getString(R.string.subtitle_top_rated)));
//            mActionBar.invalidateOptionsMenu();
//        }
    }

    private void toggleOrderType(MenuItem item) {

//        if (cd.isConnectingToInternet()) {
//
//            if (item.getTitle().equals(getString(R.string.toggle_popular))) {
//                item.setTitle(getString(R.string.toggle_top_rated));
//                mOrderType = "popularity";
//            } else {
//                item.setTitle(getString(R.string.toggle_popular));
//                mOrderType = "top_rating";
//            }
//
//            presenter.getPage(mOrderType);
//
//            refreshActionBar();
//
//        } else {
//            mSwipeRefreshLayout.setRefreshing(false);
//            showError(getString(R.string.error_no_internet));
//        }
    }

    private  void setupViews() {

        rView.addItemDecoration(new SpacesItemDecoration(10));
        GridLayoutManager gLayout = new GridLayoutManager(this, 2);
        rView.setLayoutManager(gLayout);
        rView.setHasFixedSize(true);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (cd.isConnectingToInternet()) {

                    presenter.getGallery(mOrderType);

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
                    case R.id.nav_top:
                        navItemId = R.id.nav_top;
                        break;

                    case R.id.nav_user:
                        navItemId = R.id.nav_user;
                        break;

                    case R.id.nav_about:
                        //showAbout();
                        break;
                    case R.id.nav_list:
                        break;
                    case R.id.nav_grid:
                        break;

                    case R.id.nav_staggered_grid:
                        break;

                    default:
                    case R.id.nav_hot:
                        navItemId = R.id.nav_hot;
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

                popup.getMenuInflater().inflate(R.menu.filter_sort_user_popup_menu, popup.getMenu());

            } else {

                popup.getMenuInflater().inflate(R.menu.filter_sort_other_popup_menu, popup.getMenu());
            }

        } else if(viewId == R.id.filter_sort_other_viral || viewId == R.id.filter_sort_other_top ||
                viewId == R.id.filter_sort_other_time) {

            if(navItemId == R.id.nav_hot) {

            } else {
                popup.getMenuInflater().inflate(R.menu.filter_window_popup_menu, popup.getMenu());
            }

        } else if(viewId == R.id.filter_sort_user_viral || viewId == R.id.filter_sort_user_top ||
                viewId == R.id.filter_sort_user_time || viewId == R.id.filter_sort_user_rising) {

            popup.getMenuInflater().inflate(R.menu.filter_show_viral_popup_menu, popup.getMenu());

        } else {


        }

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.filter_sort_other_viral:
                        showPopupMenu(R.id.filter_sort_other_viral);
                        break;

                    case R.id.filter_sort_other_top:
                        showPopupMenu(R.id.filter_sort_other_top);
                        break;

                    case R.id.filter_sort_other_time:
                        showPopupMenu(R.id.filter_sort_other_time);
                        break;

                    case R.id.filter_sort_user_viral:
                        showPopupMenu(R.id.filter_sort_user_viral);
                        break;

                    case R.id.filter_sort_user_top:
                        showPopupMenu(R.id.filter_sort_user_top);
                        break;

                    case R.id.filter_sort_user_time:
                        showPopupMenu(R.id.filter_sort_user_time);
                        break;

                    case R.id.filter_sort_user_rising:
                        showPopupMenu(R.id.filter_sort_user_rising);
                        break;

                    case R.id.filter_show_viral:
                        showPopupMenu(R.id.filter_show_viral);
                        break;

                    default:
                        break;
                }
                return true;
            }
        });

        popup.show();//showing popup menu
    }

}
