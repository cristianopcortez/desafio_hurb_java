package com.ccortez.desafiohurbapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ccortez.desafiohurbapplication.service.model.Result;
import com.ccortez.desafiohurbapplication.service.model.Suggestion;
import com.ccortez.desafiohurbapplication.service.repository.HotelSearchRepository;

import java.util.List;

import javax.inject.Inject;

public class HotelViewModel extends AndroidViewModel {
    private static final String TAG = HotelViewModel.class.getName();
    private static final MutableLiveData ABSENT = new MutableLiveData();
    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }

    private final LiveData<Result> hotelObservable;
    private LiveData<List<Suggestion>> suggestionList;

//    public ObservableField<Result> hotel = new ObservableField<>();
    public Result hotel;
    public String city;
    HotelSearchRepository hotelSearchRepository;

    @Inject
    public HotelViewModel(@NonNull HotelSearchRepository hotelSearchRepository, @NonNull Application application) {
        super(application);

//        final MutableLiveData<Result> data = new MutableLiveData<>();
//        data.setValue(hotel);
        this.hotelSearchRepository = hotelSearchRepository;

        hotelObservable = hotelSearchRepository.getHotelDetails(hotel);
//        suggestionList = hotelSearchRepository.getCityList(city);
    }

    public LiveData<Result> getObservableHotel() {
        return hotelObservable;
    }

    public LiveData<List<Suggestion>> getSuggestionList() {

        suggestionList = hotelSearchRepository.getCityList(city);

        return suggestionList;
    }

    public void setHotel(Result hotel) {
        this.hotel = hotel;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
