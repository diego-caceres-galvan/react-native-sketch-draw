package com.sketchView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by keshav on 06/04/17.
 */

public class SketchViewContainer extends LinearLayout {

    public SketchView sketchView;
    private int mOriginalWidth;
    private int mOriginalHeight;

    public SketchViewContainer(Context context) {
        super(context);
        sketchView = new SketchView(context);
        addView(sketchView);

        mOriginalHeight = 720;
        mOriginalWidth = 720;
    }

    public SketchFile saveToLocalCache() throws IOException {

        Bitmap viewBitmap = Bitmap.createBitmap(sketchView.getWidth(), sketchView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(viewBitmap);
        draw(canvas);

        Bitmap viewBitmapOriginalSize = Bitmap.createScaledBitmap(viewBitmap, mOriginalWidth, mOriginalHeight, false);

        File cacheFile = File.createTempFile("kudos_", UUID.randomUUID().toString()+".jpeg");
        FileOutputStream imageOutput = new FileOutputStream(cacheFile);
        viewBitmapOriginalSize.compress(Bitmap.CompressFormat.JPEG, 100, imageOutput);

        SketchFile sketchFile = new SketchFile();
        sketchFile.localFilePath = cacheFile.getAbsolutePath();;
        sketchFile.width = mOriginalWidth; //viewBitmap.getWidth();
        sketchFile.height = mOriginalHeight; //viewBitmap.getHeight();
        return sketchFile;

    }

    public boolean openSketchFile(String localFilePath) {

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(localFilePath, bitmapOptions);
        if(bitmap != null) {
            sketchView.setViewImage(bitmap, localFilePath);
            mOriginalHeight = bitmap.getHeight();
            mOriginalWidth = bitmap.getWidth();
            return true;
        }
        return false;
    }

}
