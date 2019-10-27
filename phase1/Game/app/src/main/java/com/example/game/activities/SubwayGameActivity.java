package com.example.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.game.R;

import java.util.ArrayList;

public class SubwayGameActivity extends AppCompatActivity {
    private ArrayList<ImageView> obstacles = new ArrayList<>();
    private ImageView runner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway_game);
    }

    // move runner right when right button is clicked
    private void moveRight(View view) {

    }

    // move runner left when left button is clicked
    private void moveLeft() {

    }

    // move all obstacles down every second
    private void moveDown() {
        for (int i=0; i<obstacles.size(); i++ ) {
            ImageView obstacle = obstacles.get(i);
            // move down
        }
    }
}
