/*
 * Copyright (C) 2020.  Aravind Chowdary (@kamaravichow)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.aravi.dotpro.activities.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityManager;

import androidx.appcompat.app.AppCompatActivity;

import com.aravi.dotpro.BuildConfig;
import com.aravi.dotpro.R;
import com.aravi.dotpro.Utils;
import com.aravi.dotpro.activities.log.LogsActivity;
import com.aravi.dotpro.databinding.ActivityMainBinding;
import com.aravi.dotpro.manager.PreferenceManager;
import com.aravi.dotpro.service.DotService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 1802;
    private boolean TRIGGERED_START = false;

    private PreferenceManager sharedPreferenceManager;
    private Intent serviceIntent;
    private ActivityMainBinding mBinding;

    @Override
    protected void onStart() {
        super.onStart();
        if (!sharedPreferenceManager.isServiceEnabled()) {
            mBinding.mainSwitch.setChecked(checkAccessibility());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        sharedPreferenceManager = PreferenceManager.getInstance(getApplication());
        init();
        checkAutoStartRequirement();
    }

    private void init() {
        mBinding.vibrationSwitch.setChecked(sharedPreferenceManager.isVibrationEnabled());

        mBinding.vibrationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> sharedPreferenceManager.setVibrationEnabled(isChecked));
        mBinding.mainSwitch.setOnCheckedChangeListener((buttonView, b) -> {
            if (b) {
                checkForAccessibilityAndStart();
                TRIGGERED_START = true;
            } else {
                stopService();
                TRIGGERED_START = false;
            }
        });
        mBinding.align.setOnCheckedChangeListener((group, i) -> {
            // fixed: Resource IDs will be non-final in Android Gradle Plugin version 5.0, avoid using them in switch case statements
            // fix source : http://tools.android.com/tips/non-constant-fields
            if (i == R.id.topLeft) {
                sharedPreferenceManager.setPosition(0);
            } else if (i == R.id.topRight) {
                sharedPreferenceManager.setPosition(1);
            }
        });

        mBinding.twitterButton.setOnClickListener(v -> openWeb("https://www.twitter.com/kamaravichow"));
        mBinding.githubButton.setOnClickListener(v -> openWeb("https://www.github.com/kamaravichow"));
        mBinding.logsOption.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LogsActivity.class);
            startActivity(intent);
        });
        mBinding.shareOption.setOnClickListener(v -> {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Protect your camera and microphone privacy with SafeDot. Download from Google Play : https://play.google.com/store/apps/details?id=com.aravi.dotpro");
            shareIntent.setType("text/plain");
            startActivity(shareIntent);
        });
        mBinding.versionText.setText("Version - " + BuildConfig.VERSION_NAME);
    }

    private void checkForAccessibilityAndStart() {
        if (!accessibilityPermission(getApplicationContext(), DotService.class)) {
            mBinding.mainSwitch.setChecked(false);
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Requires Accessibility Permission")
                    .setMessage("You're required to enable accessibility permission to Safe Dot Pro to enable the safe dots")
                    .setIcon(R.drawable.ic_baseline_accessibility_24)
                    .setPositiveButton("Open Accessibility", (dialog, which) -> startActivity(new Intent("android.settings.ACCESSIBILITY_SETTINGS")))
                    .setNegativeButton("Cancel", null)
                    .setCancelable(true)
                    .show();
        } else {
            mBinding.mainSwitch.setChecked(true);
            sharedPreferenceManager.setServiceEnabled(true);
            serviceIntent = new Intent(MainActivity.this, DotService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }
        }
    }

    private void stopService() {
        if (isAccessibilityServiceRunning()) {
            sharedPreferenceManager.setServiceEnabled(false);
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        }
        showSnack(getString(R.string.close_app_note));
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (TRIGGERED_START) {
//            TRIGGERED_START = false;
//            checkForAccessibilityAndStart();
//        }
//        if (!sharedPreferenceManager.isServiceEnabled()) {
//            mBinding.mainSwitch.setChecked(checkAccessibility());
//        }
//    }

    /**
     * @param context
     * @param cls
     * @return
     */
    public static boolean accessibilityPermission(Context context, Class<?> cls) {
        ComponentName componentName = new ComponentName(context, cls);
        String string = Settings.Secure.getString(context.getContentResolver(), "enabled_accessibility_services");
        if (string == null) {
            return false;
        }
        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(':');
        simpleStringSplitter.setString(string);
        while (simpleStringSplitter.hasNext()) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(simpleStringSplitter.next());
            if (unflattenFromString != null && unflattenFromString.equals(componentName)) {
                return true;
            }
        }
        return false;
    }


    private void sendFeedbackEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", getString(R.string.feedback_email_address), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_subject));
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Device Information : \n----- Don't clear these ----\n " + Build.DEVICE + " ,\n " + Build.BOARD + " ,\n " + Build.BRAND + " , " + Build.MANUFACTURER + " ,\n " + Build.MODEL + "\n ------ ");
        startActivity(Intent.createChooser(emailIntent, "Send feedback..."));
    }


    /**
     * @return
     */
    private boolean checkAccessibility() {
        AccessibilityManager manager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        return manager.isEnabled();
    }

    /**
     * @return
     */
    private boolean isAccessibilityServiceRunning() {
        String prefString = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        return prefString != null && prefString.contains(this.getPackageName() + "/" + DotService.class.getName());
    }


    /**
     * @param message
     */
    private void showSnack(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    /**
     * @param url
     */
    private void openWeb(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    /**
     * Chinese ROM's kill the app services frequently so AutoStart Permission is required
     */
    private void checkAutoStartRequirement() {
        String manufacturer = android.os.Build.MANUFACTURER;
        if (sharedPreferenceManager.isFirstLaunch()) {
            if ("xiaomi".equalsIgnoreCase(manufacturer)
                    || ("oppo".equalsIgnoreCase(manufacturer))
                    || ("vivo".equalsIgnoreCase(manufacturer))
                    || ("Honor".equalsIgnoreCase(manufacturer))) {
                Utils.showAutoStartDialog(MainActivity.this);
                sharedPreferenceManager.setFirstLaunch();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}