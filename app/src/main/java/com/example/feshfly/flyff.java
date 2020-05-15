package com.example.feshfly;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class flyff extends View {
    private Bitmap fish[] = new Bitmap[2];
    private int fishx = 10;
    private int fishy;
    private int fishsped;
    private int canvahight, canvaswhight;
    private boolean toch = false;
    private int yallox, yalloy, yallospeed = 16;
    private Paint youloupaint = new Paint();
    private int greenx, greeny, greenspeed = 20;
    private Paint grrenPaint = new Paint();

    private int readx, ready, readspeed = 25;
    private Paint readPaint = new Paint();

    private int score, lifecontener;

    private Bitmap bac;
    private Paint paint = new Paint();
    private Bitmap life[] = new Bitmap[2];

    public flyff(Context context) {
        super(context);
        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.a);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.a);

        bac = BitmapFactory.decodeResource(getResources(), R.drawable.back);
        paint.setColor(Color.WHITE);
        paint.setTextSize(70);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setAntiAlias(true);
        youloupaint.setColor(Color.YELLOW);
        youloupaint.setAntiAlias(false);
        grrenPaint.setColor(Color.GREEN);
        grrenPaint.setAntiAlias(false);

        readPaint.setColor(Color.RED);
        readPaint.setAntiAlias(false);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.like);
        fishy = 550;
        score = 0;
        lifecontener = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvahight = canvas.getHeight();
        canvaswhight = canvas.getWidth();
        canvas.drawBitmap(bac, 0, 0, null);
        int minfis = fish[0].getHeight();
        int maxfis = canvahight - fish[0].getHeight() * 3;
        fishy = fishy + fishsped;

        if (fishy < minfis) {
            fishy = minfis;
        }
        if (fishy > maxfis) {
            fishy = maxfis;
        }
        fishsped = fishsped + 2;

        if (toch) {
            canvas.drawBitmap(fish[1], fishx, fishy, null);
            toch = false;

        } else {
            canvas.drawBitmap(fish[0], fishx, fishy, null);

        }
        yallox = yallox - yallospeed;
        if (hitBallCehker(yallox, yalloy)) {
            score = score + 10;
            yallox = -100;

        }
        if (yallox < 0) {
            yallox = canvaswhight + 21;
            yalloy = (int) Math.floor(Math.random() * (maxfis - minfis)) + minfis;

        }
        greenx = greenx - greenspeed;
        if (hitBallCehker(greenx, greeny)) {
            score = score + 20;
            greenx = -100;

        }
        if (greenx < 0) {
            greenx = canvaswhight + 21;
            greeny = (int) Math.floor(Math.random() * (maxfis - minfis)) + minfis;

        }

        readx = readx - readspeed;
        if (hitBallCehker(readx, ready)) {
            readx = -100;
            lifecontener--;

            if (lifecontener == 0) {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                Intent game = new Intent(getContext(), GameOver.class);
                game.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                game.putExtra("scour", score + "");
                getContext().startActivity(game);

            }

        }
        if (readx < 0) {
            readx = canvaswhight + 21;
            ready = (int) Math.floor(Math.random() * (maxfis - minfis)) + minfis;

        }
        canvas.drawCircle(readx, ready, 30, readPaint);
        canvas.drawCircle(greenx, greeny, 25, grrenPaint);
        canvas.drawCircle(yallox, yalloy, 25, youloupaint);
        canvas.drawText("Score : " + score, 20, 60, paint);

        for (int i = 0; i < 3; i++) {
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;
            if (i < lifecontener) {
                canvas.drawBitmap(life[0], x, y, null);

            } else {
                canvas.drawBitmap(life[1], x, y, null);

            }
        }


    }

    public boolean hitBallCehker(int x, int y) {
        if (fishx < x && x < (fishx + fish[0].getWidth()) && fishy < y && y < (fishy + fish[0].getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //   return super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            toch = true;
            fishsped = -22;

        }
        return true;

    }
}
