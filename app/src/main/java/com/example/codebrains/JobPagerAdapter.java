package com.example.codebrains;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class JobPagerAdapter extends FragmentStateAdapter {
    public JobPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return JobFragment.newInstance("all");
            case 1:
                return JobFragment.newInstance("open");
            case 2:
                return JobFragment.newInstance("in-progress");
            case 3:
                return JobFragment.newInstance("completed");
            default:
                return JobFragment.newInstance("all");
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
