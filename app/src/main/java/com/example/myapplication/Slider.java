package com.example.myapplication;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.jar.Attributes;

public class Slider extends View {

// On va fixer des dimensions minimales à nos objets
    final static float MIN_BAR_LENGTH = 160; //C'est des constantes
    final static float MIN_CURSOR_DIAMETER = 30;

// Dimensions par défaut du widget (en dp)
    final static float DEFAULT_BAR_WIDTH = 20;
    final static float DEFAULT_BAR_LENGTH = 160;
    final static float DEFAULT_CURSOR_DIAMETER =40;


    private float mBarLenght;
    private float mBarWidth;
    private float mCursorDiameter;

// Couleurs
    private Paint mCursorPaint = null; //Sur Paint mettre le curseur dessus et Alt+entrée et import
    private Paint mValueBarPaint = null;
    private Paint mBarPaint = null;

    private int mDisabledColor;
    private int mCursorColor;
    private int mBarColor;
    private int mValueBarColor;

    private boolean mEnabled = true;
    // Valeur du slider
    private float mValue =50;
    // Borne min
    private float mMin =0;
    // Borne max
    private float mMax =100;


    public Slider(Context context) {
        super(context);
        init(context,null);
    }

    public Slider(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        //Initialisation des dimensions par défaut en pixel
        mBarLenght = dpToPixel (DEFAULT_BAR_LENGTH);
        mCursorDiameter = dpToPixel (DEFAULT_CURSOR_DIAMETER);
        mBarWidth = dpToPixel (DEFAULT_BAR_WIDTH);
    }

    private float dpToPixel(float valueInDp){
        return TypedValue.applyDimension((TypedValue.COMPLEX_UNIT_DIP, valueInDp, getResources().getDisplayMetrics());
    }





}
