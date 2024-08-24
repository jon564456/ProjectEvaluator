package com.itsoeh.jbrigido.projectevaluator.ui.helpers;

import android.view.View;

import androidx.core.content.ContextCompat;

import com.itsoeh.jbrigido.projectevaluator.R;

public class ColorUtils {

    public static void changeColor(View element, int op) {
        int color = 0;
        switch (op) {
            case 1:
                color = ContextCompat.getColor(element.getContext(), R.color.card_color_1);
                element.setBackgroundColor(color);
                break;
            case 2:
                color = ContextCompat.getColor(element.getContext(), R.color.card_color_2);
                element.setBackgroundColor(color);
                break;
            case 3:
                color = ContextCompat.getColor(element.getContext(), R.color.card_color_3);
                element.setBackgroundColor(color);
                break;
            case 4:
                color = ContextCompat.getColor(element.getContext(), R.color.card_color_4);
                element.setBackgroundColor(color);
                break;
            case 5:
                color = ContextCompat.getColor(element.getContext(), R.color.card_color_5);
                element.setBackgroundColor(color);
                break;
            case 6:
                color = ContextCompat.getColor(element.getContext(), R.color.card_color_6);
                element.setBackgroundColor(color);
                break;
            case 7:
                color = ContextCompat.getColor(element.getContext(), R.color.card_color_7);
                element.setBackgroundColor(color);
                break;
            case 8:
                color = ContextCompat.getColor(element.getContext(), R.color.card_color_8);
                element.setBackgroundColor(color);
                break;
        }
    }

    public static void changeColor(int op, View... elements) {
        {
            for (View element : elements) {
                changeColor(element, op);
            }
        }
    }
}

