package com.example.recyclerviewapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.BreakIterator;

public class FloatingActivity_add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setTitle("꽃말정보");
        Log.d("zn_check", "21");

        Intent intent = getIntent(); //전달할 데이터를 받을 Intent
        int img = intent.getIntExtra("img", 0);
        String name = intent.getStringExtra("name");
        String story = intent.getStringExtra("story");

        ImageView imageView = findViewById(R.id.flower_img);
        imageView.setImageResource(img);
        TextView text_name = findViewById(R.id.flower_text);
        text_name.setText(name);
        TextView text_story = findViewById(R.id.story_text);
        text_story.setText(story);
    }
}
