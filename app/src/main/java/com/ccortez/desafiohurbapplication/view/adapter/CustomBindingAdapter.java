package com.ccortez.desafiohurbapplication.view.adapter;

import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.text.DecimalFormat;

public class CustomBindingAdapter {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter({"currency", "amount"})
    public static void setCurrencyAndAmount(TextView textView, String currency, double amount) {
        SpannableString spannableString = new SpannableString(currency + " " +
                new DecimalFormat("#.##").format(amount));

//        spannableString.setSpan(new RelativeSizeSpan(0.6f), 0, 1, 0);

        textView.setText(spannableString);
    }
}