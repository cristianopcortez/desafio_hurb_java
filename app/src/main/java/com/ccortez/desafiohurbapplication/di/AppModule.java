package com.ccortez.desafiohurbapplication.di;

import androidx.lifecycle.ViewModelProvider;

import javax.inject.Singleton;

import com.ccortez.desafiohurbapplication.service.repository.BackEndService;
import com.ccortez.desafiohurbapplication.viewmodel.HotelListViewModelFactory;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(subcomponents = ViewModelSubComponent.class)
class AppModule {

    @Singleton @Provides
    BackEndService provideBackEndService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return new Retrofit.Builder()
                .baseUrl(BackEndService.HTTP_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(BackEndService.class);
    }

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(
            ViewModelSubComponent.Builder viewModelSubComponent) {

        return new HotelListViewModelFactory(viewModelSubComponent.build());
    }

}
