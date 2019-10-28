package com.example.nepalappgroupb2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button comic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comic = findViewById(R.id.comicButton);
        comic.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View v){
        if (v == comic){
            Intent intent = new Intent(this, ComicActivity.class);
            startActivity(intent);
        }

    }
}
