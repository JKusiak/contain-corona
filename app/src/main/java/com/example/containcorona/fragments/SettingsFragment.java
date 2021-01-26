package com.example.containcorona.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.containcorona.R;


public class SettingsFragment extends Fragment {
    SharedPreferences appPreferences;
    SharedPreferences.Editor editor;

    ConstraintLayout settingsLayout;
    TextView importantInformationText;
    TextView aboutUsText;
    View popupView;
    PopupWindow popupWindow;
    SwitchCompat notificationsSwitch;
    boolean switchState;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        appPreferences = this.getActivity().getSharedPreferences("com.example.containcorona", Context.MODE_PRIVATE);

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (view != null) {
            settingsLayout = (ConstraintLayout) getView();

            importantInformationText = (TextView) view.findViewById(R.id.importantInformationText);
            aboutUsText = (TextView) view.findViewById(R.id.aboutUsText);
            notificationsSwitch = (SwitchCompat) view.findViewById(R.id.notificationsSwitch);
            switchState = appPreferences.getBoolean("switch_state", false);

            if (switchState) {
                notificationsSwitch.setChecked(true);
            }

            importantInformationText.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    showImportantInformation(v);
                    settingsLayout.setAlpha((float)0.05); // dim activity
                }
            });

            aboutUsText.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    showAboutUs(v);
                    settingsLayout.setAlpha((float)0.05); // dim activity
                }
            });

            notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    editor = appPreferences.edit();
                    editor.putBoolean("switch_state", isChecked);
                    editor.apply();
                }
            });
        }
    }


    public void showImportantInformation(View v) {
        popupView = getLayoutInflater().inflate(R.layout.popup_important_info, null);
        popupWindow = new PopupWindow(popupView, 900, 1500, true);

        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow.setTouchable(true);

        ScrollView importantInfoScroll;
        importantInfoScroll = popupView.findViewById(R.id.important_info_scroll);
        importantInfoScroll.setVerticalScrollBarEnabled(false);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                settingsLayout.setAlpha((float)1);
            }
        });
    }


    public void showAboutUs(View v) {
        popupView = getLayoutInflater().inflate(R.layout.popup_aboutus, null);
        popupWindow = new PopupWindow(popupView, 900, 1500, true);

        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow.setTouchable(true);
        TextView textAboutUs;
        textAboutUs = popupView.findViewById(R.id.aboutUsText);
        textAboutUs.setMovementMethod(new ScrollingMovementMethod());
        textAboutUs.setVerticalScrollBarEnabled(false);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                settingsLayout.setAlpha((float)1);
            }
        });
    }
}