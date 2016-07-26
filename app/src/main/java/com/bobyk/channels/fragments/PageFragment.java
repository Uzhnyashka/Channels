package com.bobyk.channels.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bobyk.channels.R;

import java.util.Random;

/**
 * Created by bobyk on 26/07/16.
 */
public class PageFragment extends Fragment {

    int backColor;

    public static PageFragment newInstance() {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, null);

        ProgressBar pb = (ProgressBar) view.findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        view.setBackgroundColor(backColor);

        return view;
    }
}
