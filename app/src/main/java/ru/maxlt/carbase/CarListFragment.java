package ru.maxlt.carbase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarListFragment extends Fragment {
    @BindView(R.id.fragment_car_list_recycler)
    RecyclerView mFragmentListRecycler;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_car_list, container, false);
        ButterKnife.bind(this, rootView);


        return rootView;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.car_list_cardview)
        CardView mCarListCardView;
        @BindView(R.id.car_list_iv)
        ImageView mCarListIV;
        @BindView(R.id.car_name_tv)
        TextView mCarNameTV;
        @BindView(R.id.car_type_tv)
        TextView mCarTypeTV;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Query mQuery = FirebaseDatabase.getInstance()
                .getReference("car_overview").orderByChild("car_body_type");

        FirebaseRecyclerOptions<CarOverview> mOptions =
                new FirebaseRecyclerOptions.Builder<CarOverview>()
                        .setQuery(mQuery, CarOverview.class)
                        .build();
        FirebaseRecyclerAdapter mFirebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<CarOverview, ViewHolder>(mOptions) {
                    @Override
                    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull CarOverview model) {
                        Picasso.get().load(model.getCar_image_overview()).into(holder.mCarListIV);
                        holder.mCarNameTV.setText(model.getCar_name());
                        holder.mCarTypeTV.setText(model.getCar_type());
                        Log.v("CarListFragment","car type is " + model.getCar_type());
                    }

                    @NonNull
                    @Override
                    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_car_list_material,parent,false);
                        return new ViewHolder(view);
                    }
                };
        mFragmentListRecycler.setAdapter(mFirebaseRecyclerAdapter);
        mFirebaseRecyclerAdapter.startListening();
        mFragmentListRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
