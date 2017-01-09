package com.jordic.bathtubtest.main;

import android.app.ProgressDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jordic.bathtubtest.R;
import com.jordic.bathtubtest.classes.BathTub;
import com.jordic.bathtubtest.di.components.DaggerMainComponent;
import com.jordic.bathtubtest.di.modules.MainModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {



    @BindView(R.id.coldTapButton)
    ImageButton coldTapButton;

    @BindView(R.id.hotTapButton)
    ImageButton hotTapButton;

    @BindView(R.id.temperatureTextView)
    TextView temperatureTextView;

    @BindView(R.id.litersTextView)
    TextView litersTextView;

    @Inject
    MainPresenter mainPresenter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //Init Dagger
        buildComponent();

        //InitializeViews
        updateLiters(0);
        updateTemperature(0);

        //InitializeDialog
        progressDialog = new ProgressDialog(this);

        mainPresenter.requestTemperatures();
    }

    private void buildComponent() {
        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mainPresenter.stopHotTap();
        mainPresenter.stopColdTap();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //EVENTS

    /**
     * It is called when the Hot Tap is pressed
     * @param v The Hot Tap Button
     */
    public void onHotTapSwitch(View v)
    {
        mainPresenter.switchHotTap();
    }

    /**
     * It is called when the Cold Tap is pressed
     * @param v The Cold Tap Button
     */
    public void onColdTapSwitch(View v)
    {
        mainPresenter.switchColdTap();
    }

    public void onRefreshMenuClick(MenuItem item){
            mainPresenter.refreshBathtub();
    }

    //VIEW

    @Override
    public void showLoading(int message) {
        progressDialog.setMessage(getString(message));
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if(progressDialog!=null && progressDialog.isShowing()) progressDialog.dismiss();
    }

    @Override
    public void showErrorMessage(int message, String errorMessage) {
        Toast.makeText(this,String.format(getString(message),errorMessage),Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(int message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateLiters(float liters) {
        litersTextView.setText(liters+"/"+ BathTub.CAPACITY);
    }

    @Override
    public void updateTemperature(int temperature) {
        temperatureTextView.setText(temperature+"º");
        setTemperatureColor(temperature);
    }

    public void setTemperatureColor(int temperature) {

        if(temperature==0) temperatureTextView.setTextColor(ContextCompat.getColor(this, R.color.textViewDefault));

        else if(temperature<20)
            temperatureTextView.setTextColor(ContextCompat.getColor(this, R.color.temperature10));

        else if(temperature>=20 && temperature<35)
            temperatureTextView.setTextColor(ContextCompat.getColor(this, R.color.temperature30));

        else if(temperature>=35 && temperature<45)
            temperatureTextView.setTextColor(ContextCompat.getColor(this, R.color.temperature40));

        else  temperatureTextView.setTextColor(ContextCompat.getColor(this, R.color.temperature50));
    }

    @Override
    public void updateHotTapButton(boolean on) {
        if(on)
        {
            hotTapButton.setRotation(45);
        }
        else
        {
            hotTapButton.setRotation(0);
        }
    }

    @Override
    public void updateColdTapButton(boolean on) {
        if(on)
        {
            coldTapButton.setRotation(45);
        }
        else
        {
            coldTapButton.setRotation(0);
        }
    }


}
