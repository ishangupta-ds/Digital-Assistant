package com.twopandas.remind.ui.tools;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.twopandas.myishupanda.R;
import android.widget.Toast;
import static android.content.Context.MODE_PRIVATE;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;

    Intent mServiceIntent;
    private AlarmService mSensorService;

    Context ctx;

    public Context getCtx() {
        return ctx;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);

        final TextView textView = root.findViewById(R.id.text_tools);
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);


            }
        });


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("save", MODE_PRIVATE);

        // initiate view's
        final SwitchCompat pandaSwitch;
        pandaSwitch = root.findViewById(R.id.simpleSwitch);
        pandaSwitch.setChecked(sharedPreferences.getBoolean("value",false));
        pandaSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String statusSwitch;

                if (pandaSwitch.isChecked()) {

                    ctx = getActivity();
                    mSensorService = new AlarmService(getCtx());
                    mServiceIntent = new Intent(getCtx(), mSensorService.getClass());

                    statusSwitch = pandaSwitch.getTextOn().toString();
                    SharedPreferences.Editor editor= getActivity().getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    pandaSwitch.setChecked(true);
                    editor.commit();

                    if (!isMyServiceRunning(mSensorService.getClass())) {
                        mServiceIntent.putExtra("start","1");
                        getContext().startService(mServiceIntent);
                    }


                }
                else {
                    statusSwitch = pandaSwitch.getTextOff().toString();
                    SharedPreferences.Editor editor= getActivity().getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    pandaSwitch.setChecked(false);
                    editor.commit();

                    if ((mSensorService != null) && isMyServiceRunning(mSensorService.getClass())) {
                        mServiceIntent.putExtra("start","0");
                        getContext().stopService(mServiceIntent);
                    }
                    else
                        if(mSensorService == null)
                        {
                            ctx = getActivity();
                            mSensorService = new AlarmService(getCtx());
                            mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
                            mServiceIntent.putExtra("start","0");
                            getContext().startService(mServiceIntent);
                            getContext().stopService(mServiceIntent);

                        }

                    Log.i("MAINACT", "onDestroy!");

                }
                Toast.makeText(getActivity(), " " + statusSwitch, Toast.LENGTH_LONG).show(); // display the current state for switch's

            }
        });

        return root;
    }

}
