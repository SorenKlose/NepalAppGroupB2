package com.example.nepalappgroupb2.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nepalappgroupb2.R;

public class PopupImage extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    Button saveAsProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_image);

        imageView = (ImageView) findViewById(R.id.imageView);
        saveAsProfilePic = (Button) findViewById(R.id.saveAsProfilePic);
        saveAsProfilePic.setOnClickListener(this);

        Intent intent = getIntent();

        int position = intent.getExtras().getInt("id");
        ImageAdapter imageAdapter = new ImageAdapter(this);
        imageView.setImageBitmap(imageAdapter.images.get(position));

    }

    @Override
    public void onClick(View view) {


    }
}
