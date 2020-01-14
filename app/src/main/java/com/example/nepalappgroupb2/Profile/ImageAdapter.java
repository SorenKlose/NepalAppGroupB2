package com.example.nepalappgroupb2.Profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import java.util.ArrayList;


public class ImageAdapter extends BaseAdapter {

    private Context context;

    public ArrayList<Bitmap> images = new ArrayList<>();

    /*{R.drawable.birthday, R.drawable.baby_temperature,
                                R.drawable._2_month_old, R.drawable._1_month_old,
                                R.drawable._3_month_old};
*/


    public ImageAdapter(Context c){
        context = c;
       /* images.add(R.drawable.birthday);
        images.add(R.drawable._2_month_old);
        images.add(R.drawable._1_month_old);
        images.add(R.drawable._3_month_old);
        images.add(R.drawable.baby_temperature);
*/
    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return images.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(images.get(i));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(240,240));
        return imageView;
    }
}
