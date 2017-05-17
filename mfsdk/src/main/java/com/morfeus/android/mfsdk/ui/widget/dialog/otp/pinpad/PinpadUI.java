package com.morfeus.android.mfsdk.ui.widget.dialog.otp.pinpad;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.morfeus.android.R;

import java.util.Vector;

public class PinpadUI extends LinearLayout implements View.OnTouchListener {

    private static int screenWidth;
    private Context context;
    private int pinCount = 6;
    private LinearLayout pinLayout = null;
    private EditText editText = null;
    private int textSize = 20;
    private int componentWidth = 100;
    private int leftPadding = 20;
    private Vector<TextView> textViewArray = new Vector<TextView>();
    private LinearLayout linearLayout = null;
    private int currentCount = 0;
    private int pinHeight = 50;
    private int pinPadColor = Color.YELLOW;
    private int pinPadFocusColor = Color.CYAN;
    private Drawable nonselectedImage, selectedImage;
    private boolean isCustompin = true;
    private Drawable focusImage;
    private int pinWidth;

    public PinpadUI(Context ctContext, int pinCount) {
        super(ctContext);
        this.context = ctContext;
        this.pinCount = pinCount;
        textSize = (int) convertDpToPixel(20f, context);
        getScreenResolution(ctContext);
        getStyles();
        init();
    }

    private void getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
    }

    private void getStyles() {
        if (isCustompin) {
            nonselectedImage = getResources().getDrawable(R.drawable.line);//(R.drawable.underlinebg);//(R.drawable.ppnormal);
            if (nonselectedImage != null) {
                pinHeight = nonselectedImage.getIntrinsicHeight(); // pinHeight;//
                pinWidth = nonselectedImage.getIntrinsicWidth();// pinHeight;//nonselectedImage.getIntrinsicWidth();//
            }
            selectedImage = getResources().getDrawable(R.drawable.asterix);//(R.drawable.asteriskbg);//(R.mipmap.asterix);//(R.drawable.ppselected);
            focusImage = getResources().getDrawable(R.drawable.line);//(R.drawable.underlinebg);//(R.mipmap.line);//(R.drawable.background_border/*ppfocus*/);
        }
    }

    private void init() {
        pinLayout = new LinearLayout(context);
        pinLayout.setOrientation(HORIZONTAL);
        pinLayout.setLayoutParams(new LayoutParams(convertDpToPixel(270, context), LayoutParams.WRAP_CONTENT));
        pinLayout.setGravity(Gravity.CENTER);

        linearLayout = new LinearLayout(context);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));

        editText = new EditText(getContext());
        componentWidth = (componentWidth * screenWidth) / 100;
        editText.setWidth(componentWidth);
        editText.setCursorVisible(false);
        if (pinHeight <= 0) {
            pinHeight = 2 * textSize;
        }
        if (pinWidth <= 0 && nonselectedImage == null) {
            pinWidth = (componentWidth - (leftPadding * (pinCount - 1))) / pinCount;
        }

        pinWidth = convertDpToPixel(35f, context);
        pinHeight = convertDpToPixel(35f, context);

        editText.setHeight(textSize);
        editText.setTextColor(Color.TRANSPARENT);
        editText.setBackgroundColor(Color.TRANSPARENT);
        InputFilter[] filter = new InputFilter[1];
        filter[0] = new InputFilter.LengthFilter(pinCount);
        editText.setFilters(filter);

        if (pinCount > 0) {
            for (int i = 0; i < pinCount; i++) {
                TextView tv = new TextView(context);
                if (nonselectedImage != null) {
                    tv.setBackgroundDrawable(nonselectedImage);
                    //    tv.setWidth(pinWidth);
                } else
                    tv.setBackgroundColor(pinPadColor);
                tv.setHeight(pinHeight);


                tv.setText("");
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.BLACK);
//                tv.setTextSize(Utils.convertDpToPixel(13f, context));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
                tv.setId(i);

                LayoutParams leftParams = new LayoutParams(pinWidth, pinHeight);

                LayoutParams rightParams = new LayoutParams(pinWidth, pinHeight);
                rightParams.setMargins(leftPadding, 0, 0, 0);

                LayoutParams midParams = new LayoutParams(pinWidth, pinHeight);
                midParams.setMargins(leftPadding, 0, 0, 0);
                if (i == 0)
                    pinLayout.addView(tv, leftParams);
                else if (i == pinCount - 1)
                    pinLayout.addView(tv, rightParams);
                else
                    pinLayout.addView(tv, midParams);
                textViewArray.add(tv);
            }
        }


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                currentCount = editText.getText().length();
            }

            @Override
            public void afterTextChanged(Editable s) {
                int count = editText.getText().length();

                if (count > currentCount) {
                    if (count > 0 && textViewArray.size() > 0)

                        (textViewArray.get(count - 1))
                                .setGravity(Gravity.CENTER_HORIZONTAL
                                        | Gravity.CENTER_VERTICAL);
                    if (count == 1)
                        (textViewArray.get(count - 1)).setText(s);
                    else if (count > 1) {
                        String str = editText.getText().toString().substring(count - 1);
                        (textViewArray.get(count - 1)).setText(str);
                    }

                } else if (count < currentCount) {
                    if (focusImage != null) {
                        (textViewArray.get(currentCount - 1))
                                .setBackgroundDrawable(focusImage);
                        //      (textViewArray.get(currentCount - 1)).setWidth(pinWidth);
                    } else {
                        (textViewArray.get(currentCount - 1))
                                .setBackgroundColor(pinPadColor);
                    }
                    (textViewArray.get(currentCount - 1))
                            .setHeight(pinHeight);


                    if (currentCount > 0)
                        (textViewArray.get(currentCount - 1)).setText("");
                }
                if (count == pinCount) {
//                    if (pinpadCallback != null) {
//                        pinpadCallback.onAllPinEntered(editText.getText().toString());
//                    }
                }
            }
        });


        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    for (int i = 0; i < pinCount; i++) {
                        if (textViewArray.size() > 0
                                && i >= editText.getText()
                                .length()) {
                            if (focusImage != null) {
                                (textViewArray.get(i))
                                        .setBackgroundDrawable(focusImage);
                                //     (textViewArray.get(i)).setWidth(pinWidth);

                            } else {
                                (textViewArray.get(i))
                                        .setBackgroundColor(pinPadFocusColor);
                            }

                            (textViewArray.get(i))
                                    .setHeight(pinHeight);

                        }
                    }
                } else {
                    if (((EditText) v).getText().toString()
                            .length() < pinCount) {
                        ((EditText) v).setText("");

                        for (int i = 0; i < textViewArray.size(); i++)
                            textViewArray.get(i).setText("");
                    }
                    for (int i = 0; i < pinCount; i++) {
                        if (textViewArray.size() > 0
                                && i >= editText.getText()
                                .length()) {
                                   /* (textViewArray.get(i))
                                                .setBackgroundColor(Color.TRANSPARENT);*/
                            if (nonselectedImage != null) {
                                (textViewArray.get(i))
                                        .setBackgroundDrawable(nonselectedImage);
                                //              (textViewArray.get(i)).setWidth(pinWidth);
                            } else {
                                (textViewArray.get(currentCount - 1))
                                        .setBackgroundColor(pinPadColor);
                            }
                            (textViewArray.get(i))
                                    .setHeight(pinHeight);

                        }
                    }
                }
            }
        });

        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        pinLayout.addView(editText);
        linearLayout.addView(pinLayout);
        setOnTouchListener(this);


        addView(linearLayout);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        this.requestFocus();
        return true;
    }

    public boolean isAllDigitEntered() {
        if (editText.length() == pinCount) {
            return true;
        }
        return false;
    }

    public String getPinString() {
        return editText.getText().toString();
    }

    public void setPinString(String pin) {
        if (pin != null && pin.length() > 0) {
            for (int i = 0; i < pin.length(); i++) {
                if (i == 0) {
                    editText.setText(pin.charAt(i) + "");
                } else {
                    editText.setText(editText.getText().toString().trim() + pin.charAt(i));
                }

            }

        }
    }

    public void clearText() {
        for (int i = pinCount - 1; i >= 0; i--) {
            textViewArray.get(i).setText("");
        }
        InputFilter[] filter = new InputFilter[1];
        filter[0] = new InputFilter.LengthFilter(pinCount);
        editText.setFilters(filter);
        editText.setText("");
        editText.setSelection(0);
    }

    protected int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }
}
