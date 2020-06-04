package com.twopandas.remind.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("Happy Anniversary love,wont say the same filmy dialogues..2 years passed..you are the world to me..i cant live without you...Today i wanna say just one thing-I thank god every day for sending you in my life.            I love you miss Maharana #isha #doc ishu #cuteness overload #ishuiii #panda queen #buddhu #ishu ji #cutie pie #sunshine               ♥");
    }

    public LiveData<String> getText() {
        return mText;
    }
}