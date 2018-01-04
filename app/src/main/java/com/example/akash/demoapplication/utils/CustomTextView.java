package com.example.akash.demoapplication.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.akash.demoapplication.R;

/**
 * Created by daljeet on 4/1/18.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        useCustomFont(context, attrs);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        useCustomFont(context, attrs);
    }

    private void useCustomFont(Context context, AttributeSet attrs) {
        //get array of all attributes of CustomTextView
        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        //get font name from the font attribute of the attribute array
        String fontName = attrArray.getString(R.styleable.CustomTextView_textFont);
        Typeface customFont = selectTypeFace(context, fontName);
        setTypeface(customFont);
        attrArray.recycle();
    }

    private Typeface selectTypeFace(Context context, String fontName) {
        if (fontName.contentEquals(context.getString(R.string.lato_thin))) {
            return FontCache.getTypeface("Lato-Thin.ttf", context);
        } else if (fontName.contentEquals(context.getString(R.string.lato_regular))) {
            return FontCache.getTypeface("Lato-Regular.ttf", context);
        }else if (fontName.contentEquals(context.getString(R.string.lato_bold))) {
            return FontCache.getTypeface("Lato-Bold.ttf", context);
        }else if (fontName.contentEquals(context.getString(R.string.roboto_thin))) {
            return FontCache.getTypeface("Roboto-Thin.ttf", context);
        }else if (fontName.contentEquals(context.getString(R.string.roboto_regular))) {
            return FontCache.getTypeface("Roboto-Regular.ttf", context);
        }else if (fontName.contentEquals(context.getString(R.string.roboto_bold))) {
            return FontCache.getTypeface("Roboto-Black.ttf", context);
        }
        else {
            return null;
        }
    }

}
