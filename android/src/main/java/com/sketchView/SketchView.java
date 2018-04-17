package com.sketchView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.sketchView.tools.EraseSketchTool;
import com.sketchView.tools.PenSketchTool;
import com.sketchView.tools.SketchTool;


/**
 * Created by keshav on 05/04/17.
 */

public class SketchView extends View {

    SketchTool currentTool;
    SketchTool penTool;
    SketchTool eraseTool;

    Bitmap incrementalImage;
    String originalImagePath;

    public SketchView(Context context) {
        super(context);

        penTool = new PenSketchTool(this);
        eraseTool = new EraseSketchTool(this);
        setToolType(SketchTool.TYPE_PEN);

        setBackgroundColor(Color.TRANSPARENT);
    }

    public void setToolType(int toolType) {
        switch (toolType) {
            case SketchTool.TYPE_PEN:
                currentTool = penTool;
                break;
            case SketchTool.TYPE_ERASE:
                currentTool = eraseTool;
                break;
            default:
                currentTool = penTool;
        }
    }

    public void setToolColor(int toolColor) {
        ((PenSketchTool) penTool).setToolColor(toolColor);
    }

    public void setToolThickness(float thickness) {
        ((PenSketchTool) penTool).setToolThickness(thickness);
    }

    public void setViewImage(Bitmap bitmap) {
        incrementalImage = bitmap;
        invalidate();
    }

    public void setViewImage(Bitmap bitmap, String localFilePath) {
        incrementalImage = bitmap;
        originalImagePath = localFilePath;
        invalidate();
    }

    Bitmap drawBitmap() {
        Bitmap viewBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(viewBitmap);
        draw(canvas);
        return  viewBitmap;
    }

    public void clear() {

        if(originalImagePath != null && originalImagePath.length() > 0) {
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            //bitmapOptions.outWidth = this.getWidth();
            bitmapOptions.inScaled = false;
            Bitmap bitmap = BitmapFactory.decodeFile(originalImagePath, bitmapOptions);
            if(bitmap != null) {
                incrementalImage = bitmap;
            }
        }
        else {
            incrementalImage = null;
        }

        currentTool.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(incrementalImage != null) {
            //canvas.drawBitmap(incrementalImage, getLeft(), getTop(), null);
            Rect dstRect = new Rect();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(incrementalImage, null, dstRect, null);
        }
        if(currentTool != null)
            currentTool.render(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = currentTool.onTouch(this, event);
        if(event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
            setViewImage(drawBitmap());
            currentTool.clear();
        }
        return value;
    }

}
