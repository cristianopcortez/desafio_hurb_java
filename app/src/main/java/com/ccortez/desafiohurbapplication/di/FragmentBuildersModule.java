package com.ccortez.desafiohurbapplication.di;

import com.ccortez.desafiohurbapplication.view.ui.HotelListFragment;
import com.ccortez.desafiohurbapplication.view.ui.HotelDetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract HotelListFragment contributeHotelListFragment();

    @ContributesAndroidInjector
    abstract HotelDetailFragment contributeHotelDetailFragment();

}
