package com.twopandas.remind.ui.slideshow;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.twopandas.myishupanda.R;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        /*final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */

        final ImageView imageView2= root.findViewById(R.id.forbkgd);
        final AnimationDrawable animationDrawable1= (AnimationDrawable) imageView2.getDrawable();
        Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        imageView2.startAnimation(fadeIn);

        animationDrawable1.start();
        Toast.makeText(getActivity(), " Tap to Pause! " , Toast.LENGTH_LONG).show(); // display the current state for switch's

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(animationDrawable1.isRunning()){
                    animationDrawable1.stop();
                    Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
                    imageView2.startAnimation(fadeOut);
                    Toast.makeText(getActivity(), " Tap again to Play! " , Toast.LENGTH_LONG).show(); // display the current state for switch's
                }
                else {
                    Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
                    imageView2.startAnimation(fadeIn);
                    animationDrawable1.start();
                    Toast.makeText(getActivity(), " Tap again to Pause! " , Toast.LENGTH_LONG).show(); // display the current state for switch's
                }
            }
        });


        return root;
    }
}