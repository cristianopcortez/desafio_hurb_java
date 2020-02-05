package com.ccortez.desafiohurbapplication.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ccortez.desafiohurbapplication.service.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.ccortez.desafiohurbapplication.R;
import com.ccortez.desafiohurbapplication.databinding.HotelListItemClBinding;
import com.ccortez.desafiohurbapplication.view.callback.HotelClickCallback;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private static final String TAG = HotelAdapter.class.getSimpleName();

    List<? extends Result> hotelList;

    @Nullable
    private final HotelClickCallback hotelClickCallback;

    public HotelAdapter(@Nullable HotelClickCallback hotelClickCallback) {
        this.hotelClickCallback = hotelClickCallback;
    }

    public void setHotelList(final List<? extends Result> hotelList) {
        if (this.hotelList == null) {
            this.hotelList = hotelList;
            notifyItemRangeInserted(0, hotelList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return HotelAdapter.this.hotelList.size();
                }

                @Override
                public int getNewListSize() {
                    return hotelList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return HotelAdapter.this.hotelList.get(oldItemPosition).sku ==
                            hotelList.get(newItemPosition).sku;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Result result = hotelList.get(newItemPosition);
                    Result old = hotelList.get(oldItemPosition);
                    return result.sku == old.sku;
                }
            });
            this.hotelList = hotelList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public HotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HotelListItemClBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.hotel_list_item_cl,
                        parent, false);

        binding.setCallback(hotelClickCallback);

        return new HotelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(HotelViewHolder holder, int position) {
        holder.binding.setHotel(hotelList.get(position));

        // Log.d(TAG, "img: "+hotelList.get(position).imagem);
        Log.d(TAG, "hotel: "+ hotelList.get(position));

        Picasso.get()
                .load(hotelList.get(position).image)
                .resize(200, 150)
                .onlyScaleDown()
                .centerCrop()
                .noFade()
                .placeholder(R.drawable.ic_hotel)
                .error(R.drawable.ic_alert)
                .into(holder.hotelImage);

        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return hotelList == null ? 0 : hotelList.size();
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {

        final HotelListItemClBinding binding;
        final ImageView hotelImage;

        public HotelViewHolder(HotelListItemClBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            hotelImage = binding.hotelImage;
        }
    }
}
