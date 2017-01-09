package com.jordic.bathtubtest.main;

import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.jordic.bathtubtest.R;
import com.jordic.bathtubtest.classes.BathTub;
import com.jordic.bathtubtest.retrofit.BathService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by Jordi on 31/12/2016.
 */

public class MainPresenter implements MainContract.MainPresenter {

    //ATRIBUTES

    //Webservice base URL, replace if needed.
    //If we have "http://localhost/bath.json", the TEMPERATURE_URL is "http://localhost"
    static final String TEMPERATURE_URL = "http://192.168.1.66";
    static final int BATH_FILL_MILLISECONDS = 1000;

    MainContract.MainView mainView;
    BathTub bathTub;

    Handler handler = new Handler();


    Runnable hotRunnable = new Runnable() {
        @Override
        public void run() {

            if (bathTub.addHotLiters()) {
                refreshBathtub();
                handler.postDelayed(hotRunnable, BATH_FILL_MILLISECONDS);
            } else {
                stopHotTap();
                mainView.showMessage(R.string.error_bath_full);
            }


        }
    };

    Runnable coldRunnable = new Runnable() {
        @Override
        public void run() {

            if (bathTub.addColdLiters()) {
                refreshBathtub();
                handler.postDelayed(coldRunnable, BATH_FILL_MILLISECONDS);
            } else {
                stopColdTap();
                mainView.showMessage(R.string.error_bath_full);
            }


        }
    };


    /**
     * Constructor
     * @param mainView In the Model View Presenter pattern, we pass the View to the Presenter. In this case, the MainActivity implements the View
     */
    public MainPresenter(MainContract.MainView mainView) {
        this.mainView = mainView;
        bathTub = new BathTub();

    }

    @Override
    public void refreshBathtub() {
        mainView.updateLiters(bathTub.getCurrentLiters());
        mainView.updateTemperature(bathTub.getCurrentTemperature());
    }

    /**
     * It request the Tap temperatures from the Webservice
     */
    @Override
    public void requestTemperatures() {
        mainView.showLoading(R.string.message_downloading_temperatures);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TEMPERATURE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        BathService service = retrofit.create(BathService.class);
        Call<ResponseBody> bathCall = service.loadTemperatures();

        bathCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    String bathTemperatureResponse = response.body().string();

                    Log.i("RESPONSE", bathTemperatureResponse);

                    JSONObject json = new JSONObject(bathTemperatureResponse);

                    int hotTemp = json.getInt("hot_water");
                    int coldTemp = json.getInt("cold_water");

                    bathTub.setTemperatures(coldTemp, hotTemp);

                } catch (IOException e) {
                    onFailure(call, e);
                } catch (JSONException e) {
                    onFailure(call, e);
                }
                mainView.hideLoading();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();

                mainView.showErrorMessage(R.string.error_network, t.getMessage());
                mainView.hideLoading();
            }
        });

    }


    /**
     * It turns ON or OFF the Hot Tap depending on the hot tap state
     */
    @Override
    public void switchHotTap() {

        if (bathTub.canTurnOnHotTap()) {
            bathTub.switchHotTap();

            if (bathTub.isHotTapON()) {
                fillHotWater();
            } else {
                stopHotTap();
            }
        }

    }

    /**
     * It turns ON or OFF the Cold Tap depending on the hot tap state
     */
    @Override
    public void switchColdTap() {

        if (bathTub.canTurnOnColdTap()) {
            bathTub.switchColdTap();

            if (bathTub.isColdTapON()) {
                fillColdWater();
            } else {
                stopColdTap();
            }
        }
    }

    @Override
    public void fillColdWater() {
        bathTub.setColdTapState(true);
        mainView.updateColdTapButton(true);

        handler.postDelayed(coldRunnable, BATH_FILL_MILLISECONDS);

    }

    @Override
    public void fillHotWater() {
        bathTub.setHotTapState(true);
        mainView.updateHotTapButton(true);

        handler.postDelayed(hotRunnable, BATH_FILL_MILLISECONDS);

    }

    @Override
    public void stopColdTap() {
        bathTub.setColdTapState(false);
        mainView.updateColdTapButton(false);
        handler.removeCallbacks(coldRunnable);
    }

    @Override
    public void stopHotTap() {
        bathTub.setHotTapState(false);
        mainView.updateHotTapButton(false);
        handler.removeCallbacks(hotRunnable);
    }
}
