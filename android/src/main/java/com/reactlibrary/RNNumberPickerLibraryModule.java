
package com.reactlibrary;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RNNumberPickerLibraryModule extends ReactContextBaseJavaModule {

    private static final String MIN_VALUE = "minValue";
    private static final String MAX_VALUE = "maxValue";
    private static final String SELECTED_VALUE = "selectedValue";
    private static final String DONE_TEXT = "doneText";
    private static final String DONE_TEXT_COLOR = "doneTextColor";
    private static final String CANCEL_TEXT = "cancelText";
    private static final String CANCEL_TEXT_COLOR = "cancelTextColor";
    private static final String ARRAY_VALUE = "arrayValue";

    private final ReactApplicationContext reactContext;
    private Dialog dialog;
    private String[] arrayString;
    private boolean isArray = false;

    public RNNumberPickerLibraryModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNNumberPickerLibrary";
    }

    @ReactMethod
    public void createDialog(ReadableMap options, final Callback onDoneClick, final Callback onCancelClick) {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_numberpicker);
            if(dialog.getWindow() != null) {
                if (activity.getResources().getString(R.string.type_device).equals("tablet")) {
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    dialog.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.TRANSPARENT));
                } else {
                    dialog.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.TRANSPARENT));
                }
            }
            dialog.show();

            final NumberPicker numberPicker = (NumberPicker) dialog.findViewById(R.id.np_quantity);
            numberPicker.setWrapSelectorWheel(false);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

            if (options.hasKey(ARRAY_VALUE)){
                isArray = true;
                ReadableArray array = options.getArray(ARRAY_VALUE);
                ArrayList<Object> arrayList = array.toArrayList();
                arrayString = new String[arrayList.size()];

                for (int i = 0; i < arrayList.size(); i++){
                    arrayString[i] = String.valueOf(((Double) arrayList.get(i)).intValue());
                }
                numberPicker.setMinValue(0);
                numberPicker.setMaxValue(arrayList.size()-1);
                numberPicker.setDisplayedValues(arrayString);
            }else {
                int minValue = 0;
                if(options.hasKey(MIN_VALUE)) {
                    minValue = options.getInt(MIN_VALUE);
                }
                numberPicker.setMinValue(minValue);

                int maxValue = 9999;
                if(options.hasKey(MAX_VALUE)) {
                    maxValue = options.getInt(MAX_VALUE);
                }
                numberPicker.setMaxValue(maxValue);
            }

            if(options.hasKey(SELECTED_VALUE)) {
                int selectedValue = options.getInt(SELECTED_VALUE);
                numberPicker.setValue(selectedValue);
            }

            TextView bt_apply = (TextView) dialog.findViewById(R.id.bt_apply);
            String applyText = "Done";
            if(options.hasKey(DONE_TEXT)) {
                applyText = options.getString(DONE_TEXT);
            }
            bt_apply.setText(applyText);
            String applyTextColor = "#0173F2";
            if(options.hasKey(DONE_TEXT_COLOR)) {
                applyTextColor = options.getString(DONE_TEXT_COLOR);
            }
            bt_apply.setTextColor(Color.parseColor(applyTextColor));
            bt_apply.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String qtyPicked = String.valueOf(numberPicker.getValue() + 1);
                    if (isArray == true){
                        qtyPicked = arrayString[numberPicker.getValue()];
                    }
                    if(onDoneClick != null) {
                        WritableArray array = new WritableNativeArray();
                        array.pushString(qtyPicked);
                        onDoneClick.invoke(null, array);
                    }
                    dialog.dismiss();
                }
            });

            TextView bt_cancel = (TextView) dialog.findViewById(R.id.bt_cancel);
            String cancelText = "Cancel";
            if(options.hasKey(CANCEL_TEXT)) {
                cancelText = options.getString(CANCEL_TEXT);
            }
            bt_cancel.setText(cancelText);
            String cancelTextColor = "#0173F2";
            if(options.hasKey(CANCEL_TEXT_COLOR)) {
                cancelTextColor = options.getString(CANCEL_TEXT_COLOR);
            }
            bt_cancel.setTextColor(Color.parseColor(cancelTextColor));
            bt_cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(onCancelClick != null) {
                        onCancelClick.invoke(null, new WritableNativeArray());
                    }
                    dialog.dismiss();
                }
            });
        }
    }

    @ReactMethod
    public void show() {
        if (dialog == null) {
            return;
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    @ReactMethod
    public void hide() {
        if (dialog == null) {
            return;
        }
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}