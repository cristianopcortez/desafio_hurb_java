package com.ccortez.desafiohurbapplication.view.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import com.ccortez.desafiohurbapplication.R;
import com.ccortez.desafiohurbapplication.databinding.FragmentHotelListBinding;
import com.ccortez.desafiohurbapplication.di.Injectable;
import com.ccortez.desafiohurbapplication.service.model.HotelSearch;
import com.ccortez.desafiohurbapplication.service.model.Result;
import com.ccortez.desafiohurbapplication.service.model.Suggestion;
import com.ccortez.desafiohurbapplication.view.adapter.HotelAdapter;
import com.ccortez.desafiohurbapplication.view.callback.HotelClickCallback;
import com.ccortez.desafiohurbapplication.viewmodel.HotelListViewModel;
import com.ccortez.desafiohurbapplication.viewmodel.HotelViewModel;

public class HotelListFragment extends Fragment implements Injectable {
    public static final String TAG = "HotelListFragment";
    private HotelAdapter hotelAdapter;
    private FragmentHotelListBinding binding;
    public Context mContext = null;
    String search = "";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hotel_list, container, false);

        hotelAdapter = new HotelAdapter(hotelClickCallback);
        binding.hotelList.setAdapter(hotelAdapter);
        binding.setIsLoading(false);
        mContext = Objects.requireNonNull(getActivity()).getApplicationContext();

        return (View) binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final HotelListViewModel viewModel = ViewModelProviders.of(this,
                viewModelFactory).get(HotelListViewModel.class);
        final HotelViewModel suggestionViewModel = ViewModelProviders.of(this,
                viewModelFactory).get(HotelViewModel.class);

        binding.city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (binding.city.getText() != null
                        && binding.city.getText().toString().length() > 2) {
                    binding.setIsLoading(true);
                    suggestionViewModel.setCity(binding.city.getText().toString());
                    suggestionViewModel.getSuggestionList();
                    observeViewModel(suggestionViewModel);
                }
            }
        });

        AdapterView.OnItemClickListener onItemClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                        Toast.makeText(getContext(),
//                                "Clicked item "
//                                        + adapterView.getItemAtPosition(i)
//                                , Toast.LENGTH_SHORT).show();
                        hideKeyboard(getActivity());

                        search = adapterView.getItemAtPosition(i).toString();
                        if (search.length() > 2) {
                            Log.d(TAG, "City: "+search);
                            viewModel.setCity(search);
                            viewModel.getHotelListObservable();
                            observeViewModel(viewModel);
                        }

//                    for (int j = 0; j < suggestionList.size(); j++) {
//                        Suggestion suggestion =  suggestionList.get(j);
//
//                        if (suggestion.getText().equals(adapterView.getItemAtPosition(i))) {
//                            Log.d("Selected suggestion: ", suggestion.getText());
//                        }
//
//                    }
                    }
                };
        binding.city.setOnItemClickListener(onItemClickListener);

//        observeViewModel(viewModel);
//        observeViewModel(suggestionViewModel);
    }

    private void observeViewModel(HotelListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getHotelListObservable().observe(this, new Observer<HotelSearch>() {
            @Override
            public void onChanged(@Nullable HotelSearch hotels) {
                if (hotels != null) {
                    binding.setIsLoading(false);
                    hotelAdapter.setHotelList(hotels.results);
                }
            }
        });
    }

    private void observeViewModel(HotelViewModel suggestionViewModel) {
        // Update the list when the data changes
        suggestionViewModel.getSuggestionList().observe(this, new Observer<List<Suggestion>>() {
            @Override
            public void onChanged(@Nullable List<Suggestion> hotels) {
                if (hotels != null) {
                    binding.setIsLoading(false);

                    List<String> str = new ArrayList<String>();
                    for(Suggestion s : hotels){
                        str.add(s.getText());
                    }

                    ArrayAdapter<String> adapteo = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_dropdown_item_1line, str.toArray(new String[0]));
                    binding.city.setAdapter(adapteo);
                    adapteo.notifyDataSetChanged();

                }
            }
        });
    }

    private final HotelClickCallback hotelClickCallback = new HotelClickCallback() {
        @Override
        public void onClick(Result hotel) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(hotel);
            }
        }

    };

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume City: "+search);

        final HotelListViewModel viewModel = ViewModelProviders.of(this,
                viewModelFactory).get(HotelListViewModel.class);

        viewModel.setCity(search);
        viewModel.getHotelListObservable();
        observeViewModel(viewModel);
        hotelAdapter.notifyDataSetChanged();

        super.onResume();
    }
}
