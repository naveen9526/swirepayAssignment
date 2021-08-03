package com.ssbsoft.loadinganimation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ssbsoft.loadinganimation.R;
import com.ssbsoft.loadinganimation.api.ApiClient;
import com.ssbsoft.loadinganimation.api.service.UserService;
import com.ssbsoft.loadinganimation.model.Page;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ImageView starImageView;
    Button clickHereButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        onPress();

    }

    void initView() {
        starImageView = findViewById(R.id.loadingImage);
        clickHereButton = findViewById(R.id.apiButton);
    }

    void onPress() {
        clickHereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetData data = new GetData();
                data.execute("");
            }
        });
    }

    private class GetData extends AsyncTask<String,Void,Page>{
        Page page;
        @Override
        protected Page doInBackground(String... strings) {
            UserService userService = ApiClient.getClient().create(UserService.class);
            Call<Page> call = userService.getUser();
            call.enqueue(new Callback<Page>() {
                @Override
                public void onResponse(Call<Page> call, Response<Page> response) {
                    page = response.body();

                    //End Animation
                    starImageView.setAnimation(null);

                    starImageView.setVisibility(View.INVISIBLE);
                    clickHereButton.setEnabled(false);

                    Toast.makeText(getApplicationContext(),"Success!!!",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Page> call, Throwable t) {
                    page = new Page();
                }
            });
            return page;
        }

        @Override
        protected void onPreExecute() {
            rotateImage();
            starImageView.setVisibility(View.VISIBLE);
        }
    }

    void rotateImage() {
        AlphaAnimation animation = new AlphaAnimation(0, 360);
        animation.setInterpolator(new OvershootInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(250);


        //Starting Animation
        starImageView.startAnimation(animation);
    }
}