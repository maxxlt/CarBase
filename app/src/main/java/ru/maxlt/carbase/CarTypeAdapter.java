package ru.maxlt.carbase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarTypeAdapter extends RecyclerView.Adapter<CarTypeAdapter.ViewHolder> {


    private List<Integer> mIconList;
    private List<String> mNameList;
    private Context context;
    private String uID;

    public void setuID(String uID) {
        this.uID = uID;
    }

    public CarTypeAdapter(Context context) {
        this.context = context;
    }

    public void setmNameList(List<String> mNameList) {
        this.mNameList = mNameList;
    }

    public void setmIconList(List<Integer> mIconList) {
        this.mIconList = mIconList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_type_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Integer resInt = mIconList.get(position);
        String nameString = mNameList.get(position);
        holder.mCarTypeIcon.setImageResource(resInt);
        holder.mCarTypeName.setText(nameString);
        //add Click Listener here
        holder.mCarTypeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(context,CarListActivity.class);
                mIntent.putExtra("car_type_position",holder.getAdapterPosition());
                mIntent.putExtra("user_id",uID);
                //add animation here

                context.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mIconList != null)
            return mIconList.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.car_type_cardview)
        CardView mCarTypeCardView;
        @BindView(R.id.car_type_name)
        TextView mCarTypeName;
        @BindView(R.id.car_type_icon)
        ImageView mCarTypeIcon;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
