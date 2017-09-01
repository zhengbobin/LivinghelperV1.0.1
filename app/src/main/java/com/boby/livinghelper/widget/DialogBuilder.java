package com.boby.livinghelper.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boby.livinghelper.R;


/**
 * 默认对话框
 *
 * @author zbobin.com
 * @date 2014-11-20
 */
public class DialogBuilder implements OnClickListener {
	public static final int LEFT_BUTTON = 1;
	public static final int RIGHT_BUTTON = 2;

	private Dialog dialog;
	private Context context;
	private Button buttonLeft;
	private TextView titleView;
	private ImageView imageView;
	private TextView textViewMessage;
	// 竖线
	private View verticalLine;
	private DialogInterface.OnClickListener listener;

	public DialogBuilder(Context context) {
		this.context = context;
		dialog = new Dialog(context, R.style.TipDialog);
		dialog.setContentView(R.layout.default_dialog);
		verticalLine = getView(R.id.view_vertical_line);
		titleView = getView(R.id.textView_title);
		imageView = getView(R.id.imageView_icon);
		textViewMessage = getView(R.id.textView_message);
	}

	/**
	 * 设置标题
	 */
	public DialogBuilder setTitle(String titleText) {
		titleView.setText(parseParam(titleText));
		titleView.setVisibility(View.VISIBLE);
		imageView.setVisibility(View.GONE);
		return this;
	}

	public DialogBuilder setTitle(int titleText) {
		titleView.setText(parseParam(titleText));
		titleView.setVisibility(View.VISIBLE);
		imageView.setVisibility(View.GONE);
		return this;
	}

	/**
	 * 设置标题是否显示
	 */
	public DialogBuilder setTitleVisable(boolean visable) {
		titleView.setVisibility(visable ? View.VISIBLE : View.GONE);
		imageView.setVisibility(View.GONE);
		return this;
	}

	/**
	 * 设置标题图标
	 */
	public DialogBuilder setTitleIcon(int resId) {
		titleView.setVisibility(View.GONE);
		imageView.setBackgroundResource(resId);
		imageView.setVisibility(View.VISIBLE);
		return this;
	}

	/**
	 * 设置提示信息
	 */
	public DialogBuilder setMessage(String messageText) {
		TextView messageView = getView(R.id.textView_message);
		messageView.setText(parseParam(messageText));
		return this;
	}

	public DialogBuilder setMessage(int messageText) {
		TextView messageView = getView(R.id.textView_message);
		messageView.setText(parseParam(messageText));
		return this;
	}

	/**
	 * 设置是否可取消
	 */
	public DialogBuilder setCancelable(boolean flag) {
		dialog.setCancelable(flag);
		return this;
	}

	/**
	 * 自定义布局
	 */
	public DialogBuilder setView(View view) {
		LinearLayout messageLayout = getView(R.id.message_layout);
		TextView messageView = getView(R.id.textView_message);
		messageLayout.removeView(messageView);
		messageLayout.addView(view);
		return this;
	}

	public DialogBuilder setView(int layoutId) {
		LinearLayout messageLayout = getView(R.id.message_layout);
		TextView messageView = getView(R.id.textView_message);
		messageLayout.removeView(messageView);

		LayoutInflater.from(context).inflate(layoutId, messageLayout);
		return this;
	}

	/**
	 * 双按钮模式
	 */
	public DialogBuilder setButtons(int leftBtnText, int rightBtnText,
									final DialogInterface.OnClickListener listener) {
		this.listener = listener;
		this.buttonLeft = initButton(R.id.button_left, leftBtnText);
		initButton(R.id.button_right, rightBtnText).setVisibility(View.VISIBLE);
		verticalLine.setVisibility(View.VISIBLE);
		return this;
	}

	public DialogBuilder setButtons(String leftBtnText, String rightBtnText,
									final DialogInterface.OnClickListener listener) {
		this.listener = listener;
		this.buttonLeft = initButton(R.id.button_left, leftBtnText);
		initButton(R.id.button_right, rightBtnText).setVisibility(View.VISIBLE);
		verticalLine.setVisibility(View.VISIBLE);
		return this;
	}

	/**
	 * 单按钮模式
	 */
	public DialogBuilder setOneButton(String btnText, final DialogInterface.OnClickListener listener) {
		this.buttonLeft = initButton(R.id.button_left, btnText);
		this.buttonLeft.setBackgroundResource(R.drawable.dialog_button_middle_selector);
		this.listener = listener;
		verticalLine.setVisibility(View.GONE);
		getView(R.id.button_right).setVisibility(View.GONE);
		return this;
	}

	public DialogBuilder setMessageGravity(int gravity) {
		textViewMessage.setGravity(gravity);
		return this;
	}

	public DialogBuilder setOneButton(int btnText, final DialogInterface.OnClickListener listener) {
		this.buttonLeft = initButton(R.id.button_left, btnText);
		this.buttonLeft.setBackgroundResource(R.drawable.dialog_button_middle_selector);
		this.listener = listener;
		verticalLine.setVisibility(View.GONE);
		getView(R.id.button_right).setVisibility(View.GONE);
		return this;
	}

	private Button initButton(int buttonId, Object buttonText) {
		Button button = getView(buttonId);
		button.setText(parseParam(buttonText));
		button.setOnClickListener(this);
		return button;
	}

	/**
	 * 创建对话框
	 */
	public Dialog create() {
		if (buttonLeft == null) {
			LinearLayout root = getView(R.id.root);
			LinearLayout buttonLayout = getView(R.id.relativeLayout_button);
			root.removeView(buttonLayout);
		}
		return dialog;
	}

	/**
	 * 解析参数
	 */
	private String parseParam(Object param) {
		if (param instanceof Integer) {
			return context.getString((Integer) param);
		} else if (param instanceof String) {
			return param.toString();
		}
		return null;
	}

	@Override
	public void onClick(View view) {
		dialog.dismiss();

		if (listener == null)
			return;

		switch (view.getId()) {
			case R.id.button_left:
				listener.onClick(dialog, LEFT_BUTTON);
				break;
			case R.id.button_right:
				listener.onClick(dialog, RIGHT_BUTTON);
				break;
		}
	}

	@SuppressWarnings("unchecked")
	public <V extends View> V getView(int resId) {
		return (V) dialog.findViewById(resId);
	}
}
