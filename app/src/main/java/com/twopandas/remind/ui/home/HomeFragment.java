package com.twopandas.remind.ui.home;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.twopandas.myishupanda.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        ImageView imageView1= root.findViewById(R.id.forbkgdha);
        AnimationDrawable animationDrawable1= (AnimationDrawable) imageView1.getDrawable();
        Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        imageView1.startAnimation(fadeIn);
        animationDrawable1.start();





        return root;
    }
}