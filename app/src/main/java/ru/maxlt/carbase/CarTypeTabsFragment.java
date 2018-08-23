package ru.maxlt.carbase;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarTypeTabsFragment extends Fragment {

    private List<Integer> mCarTabsList = new ArrayList<>();

    @BindView(R.id.fragment_car_type_tabs_recycler)
    RecyclerView mCarTypeTabsRecycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_car_type_tabs,container,false);
        ButterKnife.bind(this,rootView);
        CarTypeTabsFragmentAdapter mCarTypeAdapter = new CarTypeTabsFragmentAdapter();
        mCarTypeAdapter.setmNameList(getNameList());
        mCarTypeAdapter.setmIconList(getIconList());
        mCarTypeAdapter.notifyDataSetChanged();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
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

    private void getCarTabsDrawables() {
        mCarTabsList.add(R.drawable.car_type_tab_0);
        mCarTabsList.add(R.drawable.car_type_tab_1);
        mCarTabsList.add(R.drawable.car_type_tab_2);
        mCarTabsList.add(R.drawable.car_type_tab_3);
        mCarTabsList.add(R.drawable.car_type_tab_4);
        mCarTabsList.add(R.drawable.car_type_tab_5);
        mCarTabsList.add(R.drawable.car_type_tab_6);
        mCarTabsList.add(R.drawable.car_type_tab_7);
        mCarTabsList.add(R.drawable.car_type_tab_8);
        mCarTabsList.add(R.drawable.car_type_tab_9);
    }
}
