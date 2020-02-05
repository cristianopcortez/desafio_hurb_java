package com.ccortez.desafiohurbapplication.di;

import com.ccortez.desafiohurbapplication.viewmodel.HotelListViewModel;
import com.ccortez.desafiohurbapplication.viewmodel.HotelViewModel;

import dagger.Subcomponent;

/**
 * A sub component to create ViewModels. It is called by the
 * {@link com.ccortez.desafiohurbapplication.viewmodel.HotelListViewModelFactory}.
 */
@Subcomponent
public interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        ViewModelSubComponent build();
    }

    HotelListViewModel hotelListViewModel();
    HotelViewModel hotelViewModel();
}
