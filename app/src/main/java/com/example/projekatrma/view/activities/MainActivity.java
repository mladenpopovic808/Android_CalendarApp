package com.example.projekatrma.view.activities;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.projekatrma.R;
import com.example.projekatrma.utils.PasswordCheck;
import com.example.projekatrma.view.viewPager.PagerAdapter;
import com.example.projekatrma.viewModels.CalendarViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    public static ViewPager viewPager; //javina view,opisivace view-ove za tabove
    public static BottomNavigationView bottomNavigationView; //javina view,prostor za tabove
    public CalendarViewModel calendarViewModel;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            Timber.plant(new Timber.DebugTree());
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            init();
        }
        public void init(){
            calendarViewModel=new ViewModelProvider(this).get(CalendarViewModel.class);
            PasswordCheck.writeInitialUsers(getApplicationContext());
            initViewPager();
            initNavigation();
        }

        private void initViewPager() {
            viewPager = findViewById(R.id.viewPager);
            viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
            bottomNavigationView = findViewById(R.id.bottomNavigation);
        }
        public void initNavigation(){
            bottomNavigationView.setOnItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    // setCurrentItem metoda viewPager samo obavesti koji je Item trenutno aktivan i onda metoda getItem u adapteru setuje odredjeni fragment za tu poziciju
                    case R.id.navigation_1: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_1, false); break;
                    case R.id.navigation_2: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_2, false); break;
                    case R.id.navigation_3: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_3, false); break;
                }
                return true;
            });
//            calendarViewModel.getSelectedDay().observe(this, day -> {
//                if(day != null){
//                    viewPager.setCurrentItem(PagerAdapter.FRAGMENT_2, false);
//                    bottomNavigationView.setSelectedItemId(R.id.navigation_2);
//                }
//            });
        }

}
