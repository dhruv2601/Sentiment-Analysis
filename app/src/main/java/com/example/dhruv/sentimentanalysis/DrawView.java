package com.example.dhruv.sentimentanalysis;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by dhruv on 31/10/16.
 */

public class DrawView extends View {

    Paint paint = new Paint();
    public DrawView(Context context) {
        super(context);
//        int colo = ColorStateList.valueOf(Color.parseColor("#d50000"));
        paint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(10,10,100,100,paint);

    }
}
