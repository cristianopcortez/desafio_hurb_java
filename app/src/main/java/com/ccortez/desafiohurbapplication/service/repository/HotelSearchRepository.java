package com.ccortez.desafiohurbapplication.service.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ccortez.desafiohurbapplication.service.model.HotelSearch;
import com.ccortez.desafiohurbapplication.service.model.Result;
import com.ccortez.desafiohurbapplication.service.model.Suggestion;
import com.ccortez.desafiohurbapplication.wrapper.SuggestionDataWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class HotelSearchRepository {
    private BackEndService backEndService;

    public Context mContext = null;

    private static final String TAG = HotelSearchRepository.class.getSimpleName();

    @Inject
    public HotelSearchRepository(BackEndService backEndService) {
        this.backEndService = backEndService;
    }

    public LiveData<HotelSearch> getHotelList(String query) {
        final MutableLiveData<HotelSearch> data = new MutableLiveData<>();

        backEndService.getHotelList(query).enqueue(new Callback<HotelSearch>() {
            @Override
            public void onResponse(Call<HotelSearch> call, Response<HotelSearch> response) {

                Log.d(TAG, "hotelList: " + response.body());

                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HotelSearch> call, Throwable t) {

                Log.e(TAG, "error hotelList: ", t);

                // TODO better error handling in part #2 ...
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<List<Suggestion>> getCityList(String city) {
        final MutableLiveData<List<Suggestion>> data = new MutableLiveData<>();

        backEndService.getCityData(city).enqueue(new Callback<SuggestionDataWrapper>() {
            @Override
            public void onResponse(Call<SuggestionDataWrapper> call, Response<SuggestionDataWrapper> response) {

                Log.d("Async Data RemoteData",
                        "Got REMOTE DATA "+response.body().getSuggestions().size());

                List<String> str = new ArrayList<String>();
                for(Suggestion s : response.body().getSuggestions()){
                    str.add(s.getText());
                }

                data.setValue(response.body().getSuggestions());
            }

            @Override
            public void onFailure(Call<SuggestionDataWrapper> call, Throwable t) {

                Log.e(TAG, "error hotelList: ", t);

                // TODO better error handling in part #2 ...
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<Result> getHotelDetails(Result hotel) {

        final MutableLiveData<Result> data = new MutableLiveData<>();
        data.setValue(hotel);

        return data;
    }

    private void simulateDelay() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
