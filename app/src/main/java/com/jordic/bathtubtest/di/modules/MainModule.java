package com.jordic.bathtubtest.di.modules;

import com.jordic.bathtubtest.main.MainActivity;
import com.jordic.bathtubtest.main.MainPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by J on 31/12/2016.
 */

@Module
public class MainModule {

    MainActivity activity;

    public MainModule(MainActivity activity)
    {
        this.activity = activity;
    }

    @Provides
    public MainPresenter provideMainPresenter()
    {
        return new MainPresenter(activity);
    }
}
