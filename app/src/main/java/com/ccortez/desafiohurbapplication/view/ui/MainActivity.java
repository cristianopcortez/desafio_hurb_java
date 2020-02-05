package com.ccortez.desafiohurbapplication.view.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.ccortez.desafiohurbapplication.R;
import com.ccortez.desafiohurbapplication.service.model.Result;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add hotel list fragment if this is first creation
        if (savedInstanceState == null) {

            HotelListFragment fragment = new HotelListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, HotelListFragment.TAG).commit();

        }
    }

    public void show(Result hotel) {
        HotelDetailFragment hotelDetailFragment = HotelDetailFragment.forHotel(hotel);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("hotel")
                .replace(R.id.fragment_container,
                        hotelDetailFragment, HotelDetailFragment.TAG).commit();
    }

    public void showHome() {
        HotelListFragment hotelListFragment = new HotelListFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("hotellist")
                .replace(R.id.fragment_container,
                        hotelListFragment, HotelListFragment.TAG).commit();
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
