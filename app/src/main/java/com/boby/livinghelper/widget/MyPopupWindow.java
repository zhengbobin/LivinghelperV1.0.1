package com.boby.livinghelper.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.boby.livinghelper.R;

/**
 * 弹窗选择控件
 *
 * @author zbobin.com
 */
public class MyPopupWindow extends PopupWindow {

    private String[] homeAppliances = {"Item1", "Item2", "Item3", "Item4", "Item5"};
    private NumberPicker numberPicker;
    private int position;

    private TextView textViewShowTime;
    TextView textViewChooseServiceTime;
    private int selectPosition;
    private OnSelectListener mOnSelectListener;

    public MyPopupWindow(Context mContext, View parent) {
        super(parent);
        textViewChooseServiceTime = (TextView) parent;

        View view = View.inflate(mContext, R.layout.item_my_popup_window, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
        RelativeLayout relativeLayoutDateTimePicker = (RelativeLayout) view.findViewById(R.id.relativeLayout_date_time_picker);
        Button buttonCancel = (Button) view.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        textViewShowTime = (TextView) view.findViewById(R.id.textView_show_time);
        Button buttonOk = (Button) view.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textViewShowTime.getText().equals(""))
                    textViewChooseServiceTime.setText(textViewShowTime.getText());
                if (mOnSelectListener != null) {
                    mOnSelectListener.selectListener(selectPosition);
                }
                dismiss();
            }
        });
        //选择类型
        numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker.setMaxValue(1);
        numberPicker.setMinValue(0);
        updateNumberPickerControl();
        numberPicker.setOnValueChangedListener(mOnHomeAppliancesChangedListener);

        relativeLayoutDateTimePicker.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in));

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();


    }

    public MyPopupWindow setHomeAppliances(String[] homeAppliances, int position) {
        this.homeAppliances = homeAppliances;
        this.position = position;
        this.selectPosition = position;
        textViewShowTime.setText(this.homeAppliances[position]);
        return this;

    }

    public MyPopupWindow updateNumberPickerControl() {
        numberPicker.setDisplayedValues(null);
        numberPicker.setMaxValue(homeAppliances.length - 1);
        numberPicker.setMinValue(0);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDisplayedValues(homeAppliances);
        numberPicker.setValue(position);
        numberPicker.invalidate();
        return this;
    }

    private NumberPicker.OnValueChangeListener mOnHomeAppliancesChangedListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            textViewShowTime.setText(homeAppliances[newVal]);
            selectPosition = newVal;
        }
    };


    public void setmOnSelectListener(OnSelectListener mOnSelectListener) {
        this.mOnSelectListener = mOnSelectListener;
    }

    /**
     * 获得选中的位置
     */
    public interface OnSelectListener {
        void selectListener(int index);
    }

}
