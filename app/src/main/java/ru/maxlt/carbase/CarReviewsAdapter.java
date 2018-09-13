package ru.maxlt.carbase;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class CarReviewsAdapter extends RecyclerView.Adapter<CarReviewsAdapter.ViewHolder> {
    private List<UserReview> userReviewList;

    public List<UserReview> getUserReviewList() {
        return userReviewList;
    }

    public void setUserReviewList(List<UserReview> userReviewList) {
        this.userReviewList = userReviewList;
    }

    public CarReviewsAdapter(List<UserReview> userReviewList) {
        this.userReviewList = userReviewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_cardview_material,parent,false);
        return new CarReviewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserReview userReview = userReviewList.get(position);
        holder.userComment.setText(userReview.getComment());
        holder.userRatingBar.setRating(userReview.getStars());
    }

    @Override
    public int getItemCount() {
        if (userReviewList != null)
            return userReviewList.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_thumbnail_civ_material)
        CircleImageView userThumbnail;
        @BindView(R.id.user_rating_bar_material)
        MaterialRatingBar userRatingBar;
        @BindView(R.id.user_review_tv_material)
        TextView userComment;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
