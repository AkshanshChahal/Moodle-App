package com.example.akshanshchahal.moodle_app_aaa;

/**
 * Created by amareshiitd on 21-02-2016.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {

            case 0:
                //Fragment for Ios Tab
                return new courses_grades();
            case 1:
                //Fragment for Windows Tab
                return new courses_resources();
            case 2:
                return new courses_threads();
            case 3:
                return new courses_assignments();
        }
        return null;

    }
    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position) {

            case 0:
                //Fragment for Ios Tab
                return "ASSIGNMENTS";
            case 1:
                //Fragment for Windows Tab
                return "RESOURCES";
            case 2:
                return "THREADS";
            case 3:
                return "GRADES";
        }
        return null;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 4; //No of Tabs
    }

}