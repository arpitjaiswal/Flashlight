package com.arpit.flashlight;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ToggleButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private FrameLayout rootLayout;
    private boolean isFlashOn = false;
    private Camera cam;
    private Parameters params;

    /**
     * Starting point of the app lifecycle, get references to resources and
     * set primary functionality here
     *
     * @param savedInstanceState used to save intermittent data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get references to root view and the toggle switch
        rootLayout = (FrameLayout) findViewById(R.id.root);
        ToggleButton flashSwitch = (ToggleButton) findViewById(R.id.button_flash_switch);

        // check and get reference to Camera
        getCamera();

        // set an event listener to toggle switch
        flashSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) { // if toggled on, turn on the flash
                    turnOnFlash();
                } else { // else turn off the flash
                    turnOffFlash();
                }
            }
        });
    }

    // check weather the device has camera-flash feature or not
    private void getCamera() {
        if (getApplicationContext().getPackageManager()
                                   .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            cam = Camera.open();
            params = cam.getParameters();
        }
    }

    /**
     * Set the flash mode parameter of camera to TORCH
     * Start the flash by calling startPreview() method of camera
     * Change the background color, and set isFlashOn flag
     */
    private void turnOnFlash() {
        if (!isFlashOn) {
            if (cam == null || params == null) {
                return;
            }
        }

        params.setFlashMode(Parameters.FLASH_MODE_TORCH);
        cam.setParameters(params);
        cam.startPreview();

        setBackgroundColorOn();
        isFlashOn = true;
    }

    /**
     * Set the flash mode parameter of camera to OFF
     * Stop the flash by calling stopPreview() method of camera
     * Change the background color, and unset isFlashOn flag
     */
    private void turnOffFlash() {
        if (!isFlashOn) {
            if (cam == null || params == null) {
                return;
            }
        }

        params.setFlashMode(Parameters.FLASH_MODE_OFF);
        cam.setParameters(params);
        cam.stopPreview();

        setBackgroundColorOff();
        isFlashOn = false;
    }

    private void setBackgroundColorOn() {
        rootLayout.setBackgroundResource(R.drawable.background_flash_on);
    }

    private void setBackgroundColorOff() {
        rootLayout.setBackgroundResource(R.drawable.background_flash_off);
    }

    /**
     * Lifecycle method, called when app comes into view, or selected from recent screens
     * Reconnect to camera resource if disconnected
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (cam == null) {
            try {
                getCamera();
                cam.reconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Lifecycle method, called when app goes to background
     * Release the camera resource
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (cam != null) {
            cam.release();
            cam = null;
        }
    }
}
