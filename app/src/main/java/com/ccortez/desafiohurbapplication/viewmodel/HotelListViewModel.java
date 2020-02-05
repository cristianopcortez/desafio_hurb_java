package com.ccortez.desafiohurbapplication.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import com.ccortez.desafiohurbapplication.service.model.HotelSearch;
import com.ccortez.desafiohurbapplication.service.repository.HotelSearchRepository;

public class HotelListViewModel extends AndroidViewModel {
    private LiveData<HotelSearch> hotelListObservable;

    public String city;
    HotelSearchRepository hotelRepository;

    @Inject
    public HotelListViewModel(@NonNull HotelSearchRepository hotelRepository, @NonNull Application application) {
        super(application);
        this.hotelRepository = hotelRepository;
    }

    /**
     * Expose the LiveData Hotels query so the UI can observe it.
     */
    public LiveData<HotelSearch> getHotelListObservable() {
        // If any transformation is needed, this can be simply done by Transformations class ...
        hotelListObservable = hotelRepository.getHotelList(city);
        return hotelListObservable;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
