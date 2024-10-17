package com.example.projekatrma.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekatrma.R;
import com.example.projekatrma.view.activities.MainActivity;
import com.example.projekatrma.view.recycler.adapter.DayAdapter;
import com.example.projekatrma.view.recycler.diffCallback.DayDiffItemCallback;
import com.example.projekatrma.view.viewPager.PagerAdapter;
import com.example.projekatrma.viewModels.CalendarViewModel;


public class CalendarFragment extends Fragment {
    RecyclerView recyclerView;
    DayAdapter dayAdapter;
    GridLayoutManager gridLayoutManager;
    CalendarViewModel viewModel;
    TextView monthHeaderTextView;

    public CalendarFragment() {
        super(R.layout.fragment_calendar);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel=new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);
        initView(view);
        initRecycler(view);
        initListeners();
        initAdapter();
        initObservers();
    }
    private void initObservers() {//kada se promeni priority tom danu.

        viewModel.getDays().observe(getViewLifecycleOwner(),days -> dayAdapter.submitList(days));
    }
    private void initView(View view) {
        gridLayoutManager = new GridLayoutManager(getContext(), 7); // 7 columns
        monthHeaderTextView=view.findViewById(R.id.monthHeaderTextView);
    }
    private void initAdapter(){
        dayAdapter=new DayAdapter(new DayDiffItemCallback(),day -> {
            //Toast.makeText(getContext(),day.toString(),Toast.LENGTH_SHORT).show();
            viewModel.setSelectedDay(day);
            MainActivity.viewPager.setCurrentItem(PagerAdapter.FRAGMENT_2,false);
            MainActivity.bottomNavigationView.setSelectedItemId(R.id.navigation_2);
        });
        recyclerView.setAdapter(dayAdapter);

    }
    private void initRecycler(View view) {
        recyclerView = view.findViewById(R.id.calendarRecyclerView);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(dayAdapter);

    }
    private void initListeners() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();


                // Assuming you have a list of Day objects in your viewModel, get the month of the first visible day
                String month = viewModel.getDayList().get(firstVisibleItemPosition).getMonth();

                // Update the text of the month header TextView
                monthHeaderTextView.setText(month);
            }
        });
    }
}
