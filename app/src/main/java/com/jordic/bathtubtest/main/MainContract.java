package com.jordic.bathtubtest.main;


/**
 * Created by Jordi on 31/12/2016.
 */

public interface MainContract
{
    interface MainView
    {
        void showLoading(int message);

        void hideLoading();

        void showErrorMessage(int message, String errorMessage);

        void showMessage(int message);

        void updateLiters(float liters);

        void updateTemperature(int temperature);

        void updateHotTapButton(boolean on);

        void updateColdTapButton(boolean on);
    }

    interface MainPresenter
    {
        void refreshBathtub();

        void requestTemperatures();

        void switchHotTap();

        void switchColdTap();

        void fillColdWater();

        void fillHotWater();

        void stopColdTap();

        void stopHotTap();
    }

}
