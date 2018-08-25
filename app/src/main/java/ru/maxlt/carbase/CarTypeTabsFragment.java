package ru.maxlt.carbase;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarTypeTabsFragment extends Fragment {

    AdapterTabClicked mAdapterTabClickedListener;
    LinearLayoutManager mLayoutManager;
    CarTypeTabsFragmentAdapter mCarTypeAdapter;

    @BindView(R.id.fragment_car_type_tabs_recycler)
    RecyclerView mCarTypeTabsRecycler;

    public interface AdapterTabClicked{
        void adapterTabClicked(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mAdapterTabClickedListener = (AdapterTabClicked) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement adapterTabClicked");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_car_type_tabs,container,false);
        ButterKnife.bind(this,rootView);
        mCarTypeAdapter = new CarTypeTabsFragmentAdapter();
        mCarTypeAdapter.setmNameList(getNameList());
        mCarTypeAdapter.setmIconList(getIconList());
        mCarTypeAdapter.notifyDataSetChanged();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mCarTypeAdapter.setOnTabCLickListener(new CarTypeTabsFragmentAdapter.OnTabClickListener() {
            @Override
            public void onTabClicked(int position) {
                mAdapterTabClickedListener.adapterTabClicked(position);
            }
        });

        mCarTypeTabsRecycler.setAdapter(mCarTypeAdapter);
        mCarTypeTabsRecycler.setLayoutManager(mLayoutManager);
        return rootView;
    }

    private List<Integer> getIconList() {
        Resources res = getResources();
        List<Integer> mIconList = new ArrayList<>();
        mIconList.add(R.drawable.car_icon_0);
        mIconList.add(R.drawable.car_icon_1);
        mIconList.add(R.drawable.car_icon_2);
        mIconList.add(R.drawable.car_icon_3);
        mIconList.add(R.drawable.car_icon_4);
        mIconList.add(R.drawable.car_icon_5);
        mIconList.add(R.drawable.car_icon_6);
        mIconList.add(R.drawable.car_icon_7);
        mIconList.add(R.drawable.car_icon_8);
        mIconList.add(R.drawable.car_icon_9);
        return mIconList;
    }

    private List<String> getNameList() {
        Resources res = getResources();
        String[] mStringArr = res.getStringArray(R.array.carTypeList);
        List<String> mNameList = new ArrayList<>(Arrays.asList(mStringArr));
        return mNameList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
