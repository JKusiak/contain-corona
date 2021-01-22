package com.example.containcorona.fragments;

import android.app.Notification;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.containcorona.R;

import static com.example.containcorona.MainActivity.NOTIFICATIONS_CHANNEL_ID;


public class SettingsFragment extends Fragment {
    NotificationManagerCompat notificationManager;

    ConstraintLayout settingsLayout;
    TextView importantInformationText;
    TextView aboutUsText;
    View popupView;
    PopupWindow popupWindow;
    SwitchCompat notificationsSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notificationManager = NotificationManagerCompat.from(getActivity());

        if (view != null) {
            settingsLayout = (ConstraintLayout) getView();

            importantInformationText = (TextView) view.findViewById(R.id.importantInformationText);
            aboutUsText = (TextView) view.findViewById(R.id.aboutUsText);
            notificationsSwitch = (SwitchCompat) view.findViewById(R.id.notificationsSwitch);

            importantInformationText.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    showImportantInformation(v);
                    settingsLayout.setAlpha((float)0.05); // dim activity
                }
            });

            aboutUsText.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    showAboutUs(v);
                    settingsLayout.setAlpha((float)0.05); // dim activity
                }
            });

            notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        enableNotifications(buttonView);
                    }
                }
            });
        }
    }


    public void showImportantInformation(View v) {
        popupView = getLayoutInflater().inflate(R.layout.popup_important_info, null);
        popupWindow = new PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, true);

        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow.setTouchable(true);

        TextView textAboutStatistics;
        textAboutStatistics = popupView.findViewById(R.id.important_info_text);
        textAboutStatistics.setMovementMethod(new ScrollingMovementMethod());
        textAboutStatistics.setVerticalScrollBarEnabled(false);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                settingsLayout.setAlpha((float)1);
            }
        });
    }


    public void showAboutUs(View v) {
        popupView = getLayoutInflater().inflate(R.layout.popup_aboutus, null);
        popupWindow = new PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, true);

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


    public void enableNotifications(View v) {
        Notification testNotification = new NotificationCompat.Builder(getActivity(), NOTIFICATIONS_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_info)
                .setContentTitle("12345")
                .setContentText("Test text")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, testNotification);

    }
}