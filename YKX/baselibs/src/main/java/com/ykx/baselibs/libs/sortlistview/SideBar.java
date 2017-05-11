package com.ykx.baselibs.libs.sortlistview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
//import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.ykx.baselibs.R;

public class SideBar extends View {
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#" };
	private int choose = -1;
	private Paint paint = new Paint();

	private TextView mTextDialog;
	private float textSize;
	private int normalTextColor;
	private int selectedTextColor;
	private int selectedBgResId;

	public void setTextView(TextView mTextDialog) {
		this.mTextDialog = mTextDialog;
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initValues(14, getResources().getColor(R.color.theme_main_background_color), Color.RED, R.drawable.sidebar_background);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initValues(14, getResources().getColor(R.color.theme_main_background_color), Color.RED, R.drawable.sidebar_background);
	}

	public SideBar(Context context) {
		super(context);
		initValues(14, getResources().getColor(R.color.theme_main_background_color), Color.RED, R.drawable.sidebar_background);
	}

	public void initValues(float textSize, int normalTextColor, int selectedTextColor, int selectedBgResId) {
		setTextSize(textSize);
		this.normalTextColor = normalTextColor;
		this.selectedTextColor = selectedTextColor;
		this.selectedBgResId = selectedBgResId;
	}

	public void setTextSize(float textSize) {
		this.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize,
				getResources().getDisplayMetrics());
	}

	public float getTextSize() {
		return textSize;
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / b.length;
		for (int i = 0; i < b.length; i++) {
			paint.setColor(normalTextColor);
//			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(textSize);
			if (i == choose) {
				paint.setColor(selectedTextColor);
				paint.setFakeBoldText(true);
			}
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * b.length);

		switch (action) {
		case MotionEvent.ACTION_UP:
			setBackgroundColor(Color.TRANSPARENT);
			choose = -1;//
			invalidate();
			if (mTextDialog != null) {
				mTextDialog.setVisibility(View.INVISIBLE);
			}
			break;

		default:
			setBackgroundResource(selectedBgResId);
			if (oldChoose != c) {
				if (c >= 0 && c < b.length) {
					if (listener != null) {
						listener.onTouchingLetterChanged(b[c]);
					}
					if (mTextDialog != null) {
						mTextDialog.setText(b[c]);
						mTextDialog.setVisibility(View.VISIBLE);
					}

					choose = c;
					invalidate();
				}
			}

			break;
		}
		return true;
	}


	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}


	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

}