package com.example.projekatrma.view.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projekatrma.R;

import timber.log.Timber;

public class OuterDailyActivity extends Fragment {

    public OuterDailyActivity() {
        super(R.layout.fragment_outer_daily_activity);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        getChildFragmentManager().beginTransaction().replace(R.id.outerFragmentFcv, new DailyPlanFragment()).commit();

    }

    private void init() {
        initInnerFragment();
    }

    private void initInnerFragment() {
        // U fragment-u koristimo getChildFragmentManager() za dobijanje fragment managera

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.outerFragmentFcv, new DailyPlanFragment());
        transaction.commit();
    }
}