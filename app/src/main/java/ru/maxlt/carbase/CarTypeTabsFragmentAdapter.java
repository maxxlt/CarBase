package ru.maxlt.carbase;

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

public class CarTypeTabsFragmentAdapter extends RecyclerView.Adapter<CarTypeTabsFragmentAdapter.ViewHolder> {
    private List<Integer> mIconList;
    private List<String> mNameList;

    public void setmIconList(List<Integer> mIconList) {
        this.mIconList = mIconList;
    }

    public void setmNameList(List<String> mNameList) {
        this.mNameList = mNameList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_car_type_tabs_material,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Integer resInt = mIconList.get(position);
        String nameString = mNameList.get(position);
        holder.mCarTypeIcon.setImageResource(resInt);
        holder.mCarTypeName.setText(nameString);
    }

    @Override
    public int getItemCount() {
        if (mIconList != null)
            return mIconList.size();
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.car_type_cardview_tab)
        CardView mCarTypeCardView;
        @BindView(R.id.car_type_name_tab)
        TextView mCarTypeName;
        @BindView(R.id.car_type_icon_tab)
        ImageView mCarTypeIcon;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
