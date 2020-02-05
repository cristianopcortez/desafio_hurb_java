package com.ccortez.desafiohurbapplication;

import android.app.Activity;
import android.app.Application;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import com.ccortez.desafiohurbapplication.di.AppInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MVVMApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        Picasso.setSingletonInstance(new Picasso.Builder(this).build());
        AppInjector.init(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

}
