package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

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
        mBarLenght = dpToPixel (DEFAULT_BAR_LENGTH); // Le rouge des fonctions se fini quand la fonction est déclaré en bas
        mCursorDiameter = dpToPixel (DEFAULT_CURSOR_DIAMETER);
        mBarWidth = dpToPixel (DEFAULT_BAR_WIDTH);

        // On instancie les variables Paint
        mCursorPaint = new Paint();
        mBarPaint = new Paint();
        mValueBarPaint = new Paint();

        // On empêche aliasing (repliement) On limite les hautes fréquences dans les trucs graphiques
        mCursorPaint.setAntiAlias(true);
        mBarPaint.setAntiAlias(true);
        mValueBarPaint.setAntiAlias(true);

        //Il va falloir définir nos pinceaux ==> Application du style (plein)
        mValueBarPaint.setStyle(Paint.Style.STROKE); //Le contour de notre objet
        mBarPaint.setStyle(Paint.Style.STROKE);
        mCursorPaint.setStyle(Paint.Style.FILL_AND_STROKE); //Contour et remplissage intérieur

        //Petite astuce pour avoir un trait arrondis en haut et en bas
        mBarPaint.setStrokeCap(Paint.Cap.ROUND);
        mValueBarPaint.setStrokeCap(Paint.Cap.ROUND);

        //Couleur par défaut si on va voir dans res ==> Values et qu'on ouvre le colors.xml on a les couleurs
        mDisabledColor = ContextCompat.getColor(context,R.color.colorDisabled);

        //fixe les largeurs
        mBarPaint.setStrokeWidth(mBarWidth);
        mValueBarPaint.setStrokeWidth(mBarWidth);

        //On initialise les dimensions minimales en pixel
        int minWidth = (int) dpToPixel(MIN_CURSOR_DIAMETER)+ getPaddingLeft() + getPaddingRight();
        int minHeight = (int) dpToPixel(MIN_BAR_LENGTH + MIN_CURSOR_DIAMETER)+ getPaddingBottom() + getPaddingTop();

        setMinimumHeight(minHeight);
        setMinimumWidth(minWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Pour pouvoir faire notre tracé à l'écran, on va s'aider de point. Drawline = fait une ligne et va falloir lui donner une couleur avec Paint
        Point p1, p2;
        p1 = toPos(mMin); //Il faut définir la méthode toPos
        p2 = toPos(mMax);

        canvas.drawLine(p1.x, p1.y, p2.x, p2.y, mBarPaint);

        //positionnement du curseur et de la barre d'amplitude
        Point curseurPosition = toPos(mValue);
        Point originPosition = toPos(Math.max(0,mMin));

        //On va faire le tracé de notre curseur rond
        canvas.drawCircle(curseurPosition.x,curseurPosition.y, mCursorDiameter/2, mCursorPaint); //Une fois fait va falloir l'instancier dans le Oncreate
    }

    private float dpToPixel(float valueInDp){
        return TypedValue.applyDimension((TypedValue.COMPLEX_UNIT_DIP), valueInDp, getResources().getDisplayMetrics());
    }

    private Point toPos(float value){
        int x, y;
        //à présent x1 et y1 représentent le centre du curseur
        //cette méthode adapte la dimension demandée aux spécifications,
        // Si la place est disponible, la suggestion est conservée sinon la contrainte est conservée

        y = (int) ((1- valueToRatio(value))* mBarLenght + mCursorDiameter /2);
        x = (int) (Math.max(mCursorDiameter, mBarWidth)/2);
        x += getPaddingLeft();
        y += getPaddingTop();

        return new Point(x,y);
    }

    //Transforme le min et max de notre curseur en une valeur entre 0 et 1
    private float valueToRatio(float value){
        return (value - mMin)/(mMax - mMin);
    }

    private float ratioToValue(float ratio) {
        return ratio * (mMax-mMin)+mMin;

    }
}
