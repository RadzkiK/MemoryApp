package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.example.myproject.Database.MemoriesDatabase;

public class MainActivity extends AppCompatActivity {

   // public static Context MyAppsContext;
    private MemoriesDatabase memoriesDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memoriesDatabase = MemoriesDatabase.getInstance(getApplicationContext());
        //MyAppsContext = getApplicationContext();
    }

}