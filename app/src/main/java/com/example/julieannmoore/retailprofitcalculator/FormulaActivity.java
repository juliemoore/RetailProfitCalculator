package com.example.julieannmoore.retailprofitcalculator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;

public class FormulaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula);

        Intent intent = getIntent();
        int index = intent.getIntExtra("com.example.julieannmoore.ITEM_INDEX",  -1);

        if (index > -1) {
            int pic = getImg(index);
            ImageView img = findViewById(R.id.imageView);
            scaleImg(img, pic);
        }
    }

    private int getImg(int index) {
        switch (index) {
            case 0: return R.drawable.formulasquare;
            case 1: return R.drawable.formulasquare;
            case 2: return R.drawable.mark_up_dollars;
            case 3: return R.drawable.mark_up_percentage;
            case 4: return R.drawable.gm_dollars;
            case 5: return R.drawable.gm_percentage;
            case 6: return R.drawable.inventory_turn;
            case 7: return R.drawable.weeks_supply;
            case 8: return R.drawable.gmroi;
            case 9: return R.drawable.sales_per_feet;
            case 10: return R.drawable.gm_per_linear_ft;
            default: return -1;
        }
    }

    private void scaleImg(ImageView img, int pic) {

        Display screen = getWindowManager().getDefaultDisplay();
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), pic, options);

        int imgWidth = options.outWidth;
        int screenWidth = screen.getWidth();

        if (imgWidth > screenWidth) {
            int ratio = Math.round( (float)imgWidth / (float)screenWidth );
            options.inSampleSize = ratio;
        }

        options.inJustDecodeBounds = false;
        Bitmap scaledImg = BitmapFactory.decodeResource(getResources(), pic, options);
        img.setImageBitmap(scaledImg);

    }
}
