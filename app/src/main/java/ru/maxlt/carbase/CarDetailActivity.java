package ru.maxlt.carbase;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.support.v7.widget.Toolbar;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import ru.maxlt.carbase.data.CarDetail;
import ru.maxlt.carbase.data.CarOverview;
import ru.maxlt.carbase.data.UserReview;
import ru.maxlt.carbase.ui.CarReviewsAdapter;

public class CarDetailActivity extends AppCompatActivity {

    CarDetail mCarDetail;
    CarOverview mCarOverview;
    UserReview mUserReview;
    Query mQueryOverview, mQueryDetail, mQueryReview;
    String user_id;


    @BindView(R.id.reviews_recycler_view)
    RecyclerView mReviewsRV;
    @BindView(R.id.engineering_mechanical_hidden_layout)
    ConstraintLayout mHiddenConstraintLayout;
    @BindView(R.id.expand_collapse_btn)
    ToggleButton mExpandCollapseBtn;
    @BindView(R.id.expand_collapse_btn_2)
    ToggleButton mExpandCollapseBtn2;
    @BindView(R.id.design_features_hidden_layout)
    ConstraintLayout mHiddenConstraintLayout2;
    @BindView(R.id.expand_collapse_btn_3)
    ToggleButton mExpandCollapseBtn3;
    @BindView(R.id.reviews_hidden_layout)
    ConstraintLayout mHiddenConstraintLayout3;
    @BindView(R.id.power_tv)
    TextView mPowerTV;
    @BindView(R.id.drive_type_tv)
    TextView mDriveTypeTV;
    @BindView(R.id.mpg_tv)
    TextView mMpgTV;
    @BindView(R.id.capacity_tv)
    TextView mCapacityTV;
    @BindView(R.id.transmission_tv)
    TextView mTransmissionTV;
    @BindView(R.id.interior_color_tv)
    TextView mInteriorColorTV;
    @BindView(R.id.exterior_color_tv)
    TextView mExteriorColorTV;
    @BindView(R.id.features_tv)
    TextView mFeaturesTV;
    @BindView(R.id.msrp_tv)
    TextView mMsrpTV;
    @BindView(R.id.beaten_msrp_tv)
    TextView mBeatenMsrpTV;
    @BindView(R.id.toolbar_detail_title)
    Toolbar mToolbarTitle;
    @BindView(R.id.car_background_iv)
    ImageView mCarBackgroundIV;
    @BindView(R.id.user_rating_bar)
    MaterialRatingBar mRatingBar;
    @BindView(R.id.user_thumbnail_civ)
    CircleImageView mUserThumbnail;
    @BindView(R.id.user_review_tv)
    TextView mUserComment;
    @BindView(R.id.favorite_floating_button)
    FloatingActionButton mFavoriteBtn;
    @BindView(R.id.cancel_favorite_floating_button)
    FloatingActionButton mCancelFavoriteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        ButterKnife.bind(this);
        Intent mIntent = getIntent();
        String car_id = mIntent.getStringExtra("car_id");
        user_id = mIntent.getStringExtra("user_id");
        YouTubePlayerFragment mTrailerVideoFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.trailer_video_fragment);

        mQueryOverview = FirebaseDatabase.getInstance().getReference("car_overview").orderByChild("car_id").equalTo(car_id);
        mQueryOverview.keepSynced(true);
        mQueryDetail = FirebaseDatabase.getInstance().getReference("car_details").orderByChild("car_id").equalTo(car_id);
        mQueryDetail.keepSynced(true);
        mQueryReview = FirebaseDatabase.getInstance().getReference("reviews").orderByChild("car_id").equalTo(car_id);
        mQueryReview.keepSynced(true);
        mTrailerVideoFragment.initialize(String.valueOf(R.string.my_youtube_api), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {

                if (!b) {
                    youTubePlayer.cueVideo(mCarDetail.getTest_drive_link());
                }
                youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String s) {

                    }

                    @Override
                    public void onAdStarted() {
                        int orientation = getResources().getConfiguration().orientation;
                        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            youTubePlayer.setFullscreen(true);
                        }
                    }

                    @Override
                    public void onVideoStarted() {
                        int orientation = getResources().getConfiguration().orientation;
                        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            youTubePlayer.setFullscreen(true);
                        }
                    }

                    @Override
                    public void onVideoEnded() {

                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {

                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                if (youTubeInitializationResult.isUserRecoverableError()) {
                    youTubeInitializationResult.getErrorDialog(CarDetailActivity.this, 1).show();
                } else {
                    String errorMessage = String.format("There was an error initializing the YouTubePlayer (%1$s)", youTubeInitializationResult.toString());
                    Toast.makeText(CarDetailActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void populateOtherReviews(List<UserReview> mUserReviewList) {
        CarReviewsAdapter mCarReviewsAdapter = new CarReviewsAdapter(mUserReviewList);
        mReviewsRV.setAdapter(mCarReviewsAdapter);
    }

    private void populateFirstReview() {
        Picasso.get().load(mUserReview.getThumbnail()).into(mUserThumbnail);
        mRatingBar.setRating(mUserReview.getStars());
        Log.v("mLog", " " + mUserReview.getStars());
        mUserComment.setText(mUserReview.getComment());
    }

    private void populateOverview() {
        Picasso.get().load(mCarOverview.getCar_image_overview()).into(mCarBackgroundIV);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbarTitle.setTitle(mCarOverview.getCar_name());
        }
    }

    private void populateDetail() {
        mPowerTV.setText(mCarDetail.getEngine());
        mDriveTypeTV.setText(mCarDetail.getDrive_type());
        mMpgTV.setText(mCarDetail.getMpg());
        mCapacityTV.setText(mCarDetail.getCapacity());
        mTransmissionTV.setText(mCarDetail.getTransmission());
        mInteriorColorTV.setText(mCarDetail.getInterior_color());
        mExteriorColorTV.setText(mCarDetail.getExterior_color());
        mFeaturesTV.setText(mCarDetail.getConvenience());
        mMsrpTV.setText(mCarDetail.getPrice_msrp());
        mBeatenMsrpTV.setText(mCarDetail.getBeaten_price_msrp());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mExpandCollapseBtn.setPressed(false);
        mQueryOverview.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        mCarOverview = snapshot.getValue(CarOverview.class);
                        populateOverview();
                        //CHECKING IF USER FAVORITE-D THE CAR
                        if (snapshot.hasChild("users/" + user_id)) {
                            mFavoriteBtn.setVisibility(View.GONE);
                            mCancelFavoriteBtn.setVisibility(View.VISIBLE);
                        } else {
                            mFavoriteBtn.setVisibility(View.VISIBLE);
                            mCancelFavoriteBtn.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("CarDetailActivity", "failed to read " + databaseError.getCode());
            }
        });
        mQueryDetail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        mCarDetail = snapshot.getValue(CarDetail.class);
                        populateDetail();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("CarDetailActivity", "failed to read " + databaseError.getCode());
            }
        });
        mQueryReview.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<UserReview> mUserReviewList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DataSnapshot mSnapshot = snapshot.child("users");
                        for (DataSnapshot userSnapshot : mSnapshot.getChildren()) {
                            mUserReview = userSnapshot.getValue(UserReview.class);
                            if (mUserReview.getUser_order() == 0) {
                                populateFirstReview();
                            } else {
                                mUserReviewList.add(mUserReview);
                            }
                        }
                        populateOtherReviews(mUserReviewList);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    public void onExpandCollapseButtonMechanicalClicked(View view) {

        if (mExpandCollapseBtn.isChecked()) {
            mHiddenConstraintLayout.setVisibility(View.VISIBLE);
        } else
            mHiddenConstraintLayout.setVisibility(View.GONE);
    }

    public void onExpandCollapseButtonDesignClicked(View view) {
        if (mExpandCollapseBtn2.isChecked()) {
            mHiddenConstraintLayout2.setVisibility(View.VISIBLE);
        } else
            mHiddenConstraintLayout2.setVisibility(View.GONE);
    }

    public void onExpandCollapseButtonReview(View view) {
        if (mExpandCollapseBtn3.isChecked()) {
            mHiddenConstraintLayout3.setVisibility(View.VISIBLE);
        } else
            mHiddenConstraintLayout3.setVisibility(View.GONE);
    }

    public void onFavoriteFABClicked(View view) {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("car_overview/" + mCarOverview.getCar_id() + "/users/" + user_id);
        mRef.setValue(Boolean.TRUE);

    }

    public void onCancelFavoriteFABClicked(View view) {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("car_overview/" + mCarOverview.getCar_id() + "/users/" + user_id);
        mRef.removeValue();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
