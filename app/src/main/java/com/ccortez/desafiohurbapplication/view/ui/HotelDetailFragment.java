package com.ccortez.desafiohurbapplication.view.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.ccortez.desafiohurbapplication.R;
import com.ccortez.desafiohurbapplication.databinding.FragmentHotelDetailsBinding;
import com.ccortez.desafiohurbapplication.di.Injectable;
import com.ccortez.desafiohurbapplication.service.model.ImageFromGallery;
import com.ccortez.desafiohurbapplication.service.model.Result;
import com.ccortez.desafiohurbapplication.viewmodel.HotelViewModel;
import com.google.gson.Gson;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.builder.GallerySettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import static ogbe.ozioma.com.glideimageloader.dsl.DSL.image;

public class HotelDetailFragment extends Fragment implements Injectable {
    public static final String TAG = "HotelDetailFragment";
    private static final String KEY_HOTEL_ID = "hotel_id";
    private static final String HOTEL = "";

    private Context mContext = null;

    private FragmentHotelDetailsBinding binding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hotel_details, container, false);

        mContext = Objects.requireNonNull(getActivity()).getApplicationContext();

        // Create and set the adapter for the RecyclerView.
        return (View) binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final HotelViewModel viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(HotelViewModel.class);

        Gson gson = new Gson();
        Result hotel = gson.fromJson(getArguments().getString(HOTEL), Result.class);
        viewModel.setHotel(hotel);
        binding.setHotelViewModel(viewModel);

        binding.setIsLoading(false);
        binding.setCurrency(hotel.price.currency);
        binding.setAmount(Double.valueOf(hotel.price.amount));

//        Picasso.get()
//                .load(hotel.image)
//                .placeholder(R.drawable.ic_hotel)
//                .error(R.drawable.ic_alert)
//                .into(binding.imageView);

        List<MediaInfo> mediaInfoList = new ArrayList<MediaInfo>();
        for (ImageFromGallery item : hotel.gallery) {
//            gb = gb.add(image(item.url));
            mediaInfoList.add(image(item.url));
        }

//        GalleryBuilder gb = ScrollGalleryView
        ScrollGalleryView
                .from(binding.scrollGalleryView)
                .settings(
                        GallerySettings
                                .from(getActivity().getSupportFragmentManager())
                                .thumbnailSize(100)
                                .enableZoom(true)
                                .build()
                )
                .onPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                })
                .add(mediaInfoList)
//                .add(image(hotel.image))
//                .add(image(hotel.image))
//                .add(image("http://povodu.ru/wp-content/uploads/2016/04/pochemu-korabl-derzitsa-na-vode.jpg"))
//                .add(video("http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4", R.mipmap.default_video))
//        binding.scrollGalleryView = gb.build();
                .build();

        observeViewModel(viewModel);
    }

    private void observeViewModel(final HotelViewModel viewModel) {
        // Observe hotel data
        viewModel.getObservableHotel().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(@Nullable Result hotel) {
                if (hotel != null) {
//                    binding.setIsLoading(false);
                    viewModel.setHotel(hotel);

//                    Picasso.get()
//                            .load(hotel.image)
//                            .placeholder(R.drawable.ic_hotel)
//                            .error(R.drawable.ic_alert)
//                            .into(binding.imageView);

                }
            }
        });
    }

    /**
     * Creates hotel detail fragment for specific hotel
     */
    public static HotelDetailFragment forHotel(Result hotel) {
        HotelDetailFragment fragment = new HotelDetailFragment();
        Bundle args = new Bundle();

        args.putString(KEY_HOTEL_ID, hotel.sku);

        Gson gson = new Gson();
        args.putString(HOTEL, gson.toJson(hotel));
        fragment.setArguments(args);

        return fragment;
    }
}
