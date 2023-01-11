package com.akkupatel.torchLight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.akkupatel.torchLight.databinding.ActivityMainBinding;

import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        MediaPlayer mediaPlayer = MediaPlayer.create(this , R.raw.beep);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.button.getText().toString().equals("Turn On"))
                {
                    binding.button.setText(R.string.turnOff);
                    binding.torchLight.setImageResource(R.drawable.on);
                    mediaPlayer.start();
                    changeLightState(true);
                }
                else
                {
                    binding.button.setText(R.string.turnOn);
                    binding.torchLight.setImageResource(R.drawable.off);
                    mediaPlayer.start();
                    changeLightState(false);
                }
            }
        });
    }

    private void changeLightState(boolean state) {
        // Whatever sdk version greater than or equal to 23 will run here
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            // Using camera service coz there are lot of services. Here we are accessing hardware
            CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
            String camID = null;
            try {
                camID = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(camID,state);

            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(binding.button.getText().toString().equals("Turn Off"))
            binding.button.setText(R.string.turnOff);
        else
            binding.button.setText(R.string.turnOn);
    }
}