/*
 * Copyright (C) 2021.  Aravind Chowdary (@kamaravichow)
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

package com.aravi.dot.activities.custom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.aravi.dot.databinding.ActivityCustomisationBinding;
import com.aravi.dot.manager.AnalyticsManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;
import java.util.Objects;


public class CustomisationActivity extends AppCompatActivity {
    private ActivityCustomisationBinding mBinding;
    private AnalyticsManager analyticsManager;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCustomisationBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        analyticsManager = AnalyticsManager.getInstance(this);
        mAuth = FirebaseAuth.getInstance();
        setSupportActionBar(mBinding.toolbar);

        mBinding.saveButton.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(CustomisationActivity.this)
                    .setTitle("Requires Upgrade")
                    .setMessage("Customisation Center and more other features will be available only in the PRO version of the app.")
                    .setPositiveButton("Get Premium", (dialog, which) -> {

                        Bundle bundle = new Bundle();
                        bundle.putString("user_id", Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                        bundle.putString("language", Locale.getDefault().getDisplayLanguage());
                        bundle.putString("location", Locale.getDefault().getCountry());
                        analyticsManager.getAnalytics().logEvent("likely_purchaser", bundle);

                        String url = "https://play.google.com/store/apps/details?id=com.aravi.dotpro";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    })
                    .setNeutralButton("Never Mind", null)
                    .show();
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}