package com.lethalskillzz.mobigur.mobigur.presentation.detail;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.lethalskillzz.imgurllery.R;
import com.lethalskillzz.mobigur.mobigur.data.model.Image;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lethalskillzz.mobigur.mobigur.manager.AppConfig.BASE_IMG_URL;
import static com.lethalskillzz.mobigur.mobigur.manager.AppConfig.CLICK_IMAGE;
import static com.lethalskillzz.mobigur.mobigur.manager.AppConfig.LARGE_IMG_SUFFIX;
import static com.lethalskillzz.mobigur.mobigur.util.TextUtil.formatDouble;

public class DetailsActivity extends AppCompatActivity implements DetailsMvpContract.View{

    private static final String TAG = "DetailsActivity";
    DetailsMvpContract.Presenter presenter;


    @BindView(R.id.details_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.details_coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.details_title)
    TextView mTitle;
    @BindView(R.id.details_up_vote)
    TextView upVote;
    @BindView(R.id.details_down_vote)
    TextView downVote;
    @BindView(R.id.details_score)
    TextView mScore;
    @BindView(R.id.details_desc)
    TextView mDesc;
    @BindView(R.id.details_image)
    ImageView mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new DetailsPresenter();
        presenter.attachView(this);

        Image image = getIntent().getParcelableExtra(CLICK_IMAGE);

        mTitle.setText(image.getTitle());
        upVote.setText(formatDouble(image.getUps()));
        downVote.setText(formatDouble(image.getDowns()));
        mScore.setText(formatDouble(image.getScore())+" Points");
        mDesc.setText(image.getDescription());


        if(!image.getIsAlbum())
            Picasso.with(this).load(BASE_IMG_URL+image.getId()+LARGE_IMG_SUFFIX)
                    .placeholder(R.mipmap.placeholder).into(mImage);
        else
            Picasso.with(this).load(BASE_IMG_URL+image.getCover()+LARGE_IMG_SUFFIX)
                    .placeholder(R.mipmap.placeholder).into(mImage);
    }
}
