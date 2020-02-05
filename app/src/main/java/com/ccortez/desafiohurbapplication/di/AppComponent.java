package com.ccortez.desafiohurbapplication.di;

import android.app.Application;

import javax.inject.Singleton;

import com.ccortez.desafiohurbapplication.MVVMApplication;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        MainActivityModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance Builder application(Application application);
        AppComponent build();
    }
    void inject(MVVMApplication mvvmApplication);
}
