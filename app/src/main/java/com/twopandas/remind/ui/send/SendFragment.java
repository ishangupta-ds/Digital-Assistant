package com.twopandas.remind.ui.send;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.twopandas.myishupanda.R;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;

    void openWhatsappContact(String number) {
        Uri uri = Uri.parse("smsto:" + number);
        Intent iwhat = new Intent(Intent.ACTION_SENDTO, uri);
        iwhat.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(iwhat, ""));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        sendViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        openWhatsappContact("918240592504");

        return root;
    }
}