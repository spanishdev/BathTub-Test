package com.jordic.bathtubtest.di.components;

import com.jordic.bathtubtest.di.modules.MainModule;
import com.jordic.bathtubtest.di.scopes.ActivityScope;
import com.jordic.bathtubtest.main.MainActivity;

import dagger.Component;

/**
 * Created by J on 31/12/2016.
 */
@ActivityScope
@Component(modules = {MainModule.class})
public interface MainComponent {

    void inject(MainActivity mainActivity);
}
