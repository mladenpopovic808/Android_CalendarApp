package com.example.projekatrma.view.viewPager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.projekatrma.view.fragments.CalendarFragment;
import com.example.projekatrma.view.fragments.OuterDailyActivity;
import com.example.projekatrma.view.fragments.ProfileFragment;

public class PagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {

        //slicno kao kod recyclerView,ovaj adapter sluzi da prikazuje fragmente
        private final int NUM_ITEMS = 3;
        public static final int FRAGMENT_1 = 0;
        public static final int FRAGMENT_2 = 1;
        public static final int FRAGMENT_3 = 2;

        public PagerAdapter(@NonNull FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT); //ako je nesto vec bilo uradjeno na njemu
            //nece ga kreirati ponovo nego ce uraditi resume.i zato je ova klasa deprecated,zato sto
            //je to memorijski zahtevno.
        }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment;

        switch (position) {
            case FRAGMENT_1: fragment = new CalendarFragment(); break;
            case FRAGMENT_2: fragment = new OuterDailyActivity(); break;
            default: fragment = new ProfileFragment(); break;
        }
        return fragment;
    }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case FRAGMENT_1: return "Calendar";
                case FRAGMENT_2: return "Daily plan";
                default: return "Profile";
            }
        }
}
