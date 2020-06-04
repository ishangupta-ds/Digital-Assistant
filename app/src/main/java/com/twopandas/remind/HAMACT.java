package com.twopandas.remind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.twopandas.myishupanda.R;

public class HAMACT extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hamact);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(HAMACT.this, MainActivity.class));
    }

}
