/*******************************************************************************
 * Copyright 2013 NEGU Soft
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.negusoft.holoaccent.widget;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;

import com.negusoft.holoaccent.R;
import com.negusoft.holoaccent.util.NativeResources;

/**
 * This class is based on the <a href="http://github.com/ankri/SwitchCompatLibrary">SwitchCompat Library</a>, just the styling attributes have been modified to fit the HoloAccent library. 
 * For more information refer to: http://github.com/ankri/SwitchCompatLibrary.
 * <br/><br/>
 * A Switch is a two-state toggle switch widget that can select between two options. The user may drag the "thumb" back and forth to choose the selected option, or simply tap to toggle as if it were a
 * checkbox. The {@link #setText(CharSequence) text} property controls the text displayed in the label for the switch, whereas the {@link #setTextOff(CharSequence) off} and
 * {@link #setTextOn(CharSequence) on} text controls the text on the thumb. Similarly, the {@link #setTextAppearance(android.content.Context, int) textAppearance} and the related setTypeface() methods
 * control the typeface and style of label text, whereas the {@link #setSwitchTextAppearance(android.content.Context, int) switchTextAppearance} and the related seSwitchTypeface() methods control that
 * of the thumb. <br>
 * 
 * <p>
 * See the <a href="http://developer.android.com/guide/topics/ui/controls/togglebutton.html">Toggle Buttons</a> guide.
 * </p>
 * 
 * @attr ref android.R.styleable#Switch_textOn
 * @attr ref android.R.styleable#Switch_textOff
 * @attr ref android.R.styleable#Switch_switchMinWidth
 * @attr ref android.R.styleable#Switch_switchPadding
 * @attr ref android.R.styleable#Switch_switchTextAppearance
 * @attr ref android.R.styleable#Switch_thumb
 * @attr ref android.R.styleable#Switch_thumbTextPadding
 * @attr ref android.R.styleable#Switch_track
 */
public class AccentSwitch extends CompoundButton {

	private static final String RESOURCE_NAME_ON = "capital_on";
	private static final String RESOURCE_NAME_OFF = "capital_off";
	private static final String DEFAULT_TEXT_ON = "ON";
	private static final String DEFAULT_TEXT_OFF = "OFF";

	private static final String RESOURCE_NAME_THUMB = "switch_inner_holo_dark";
	private static final String RESOURCE_NAME_TRACK = "switch_track_holo_dark";
	
	private static final int TOUCH_MODE_IDLE = 0;
	private static final int TOUCH_MODE_DOWN = 1;
	private static final int TOUCH_MODE_DRAGGING = 2;

	// Enum for the "typeface" XML parameter.
	private static final int SANS = 1;
	private static final int SERIF = 2;
	private static final int MONOSPACE = 3;

	private Drawable mThumbDrawable;
	private Drawable mTrackDrawable;
	private int mThumbTextPadding;
	private int mSwitchMinWidth;
	private int mSwitchPadding;
	private CharSequence mTextOn;
	private CharSequence mTextOff;

	private int mTouchMode;
	private int mTouchSlop;
	private float mTouchX;
	private float mTouchY;
	@SuppressLint("Recycle")
	private VelocityTracker mVelocityTracker = VelocityTracker.obtain();
	private int mMinFlingVelocity;

	private float mThumbPosition;
	private int mSwitchWidth;
	private int mSwitchHeight;
	private int mThumbWidth; // Does not include padding

	private int mSwitchLeft;
	private int mSwitchTop;
	private int mSwitchRight;
	private int mSwitchBottom;

	private TextPaint mTextPaint;
	private ColorStateList mTextColors;
	private Layout mOnLayout;
	private Layout mOffLayout;

	private AllCapsTransformationMethod mSwitchTransformationMethod;

	private final Rect mTempRect = new Rect();

	private static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked };

	/**
	 * Construct a new Switch with default styling.
	 * 
	 * @param context
	 *            The Context that will determine this widget's theming.
	 */
	public AccentSwitch(Context context) {
		this(context, null);
	}

	/**
	 * Construct a new Switch with default styling, overriding specific style
	 * attributes as requested.
	 * 
	 * @param context
	 *            The Context that will determine this widget's theming.
	 * @param attrs
	 *            Specification of attributes that should deviate from default
	 *            styling.
	 */
	public AccentSwitch(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.accentSwitchStyle);
	}

	/**
	 * Construct a new Switch with a default style determined by the given theme
	 * attribute, overriding specific style attributes as requested.
	 * 
	 * @param context
	 *            The Context that will determine this widget's theming.
	 * @param attrs
	 *            Specification of attributes that should deviate from the
	 *            default styling.
	 * @param defStyle
	 *            An attribute ID within the active theme containing a reference
	 *            to the default style for this widget. e.g.
	 *            android.R.attr.switchStyle.
	 */
	public AccentSwitch(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		Resources res = getResources();
		mTextPaint.density = res.getDisplayMetrics().density;

		// mTextPaint.setCompatibilityScaling(res.getCompatibilityInfo().applicationScale);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.AccentSwitch, defStyle, 0);

		mThumbDrawable = a.getDrawable(R.styleable.AccentSwitch_android_thumb);
		if (mThumbDrawable == null)
			mThumbDrawable = getDefaultThumbDrawable();
		
		mTrackDrawable = a.getDrawable(R.styleable.AccentSwitch_android_track);
		if (mTrackDrawable == null)
			mTrackDrawable = getDefaultTrackDrawable();
		
		mTextOn = a.getText(R.styleable.AccentSwitch_android_textOn);
		if (mTextOn == null)
			mTextOn = getDefaultOnString();
		
		mTextOff = a.getText(R.styleable.AccentSwitch_android_textOff);
		if (mTextOff == null)
			mTextOff = getDefaultOffString();
		
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		
		mThumbTextPadding = a.getDimensionPixelSize(
				R.styleable.AccentSwitch_android_thumbTextPadding, 
				(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, metrics));
		mSwitchMinWidth = a.getDimensionPixelSize(
				R.styleable.AccentSwitch_android_switchMinWidth, 
				(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96, metrics));
		mSwitchPadding = a.getDimensionPixelSize(
				R.styleable.AccentSwitch_android_switchPadding, 
				(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, metrics));

		int appearance = a.getResourceId(
				R.styleable.AccentSwitch_android_switchTextAppearance, R.style.TextAppearance_HoloAccent_Switch);
		if (appearance != 0) {
			setSwitchTextAppearance(context, appearance);
		}
		a.recycle();

		ViewConfiguration config = ViewConfiguration.get(context);
		mTouchSlop = config.getScaledTouchSlop();
		mMinFlingVelocity = config.getScaledMinimumFlingVelocity();

		// Refresh display with current params
		refreshDrawableState();
		setChecked(isChecked());

		// to work this switch has to have an OnClickListener
		this.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// do nothing
			}
		});
	}
	
	private Drawable getDefaultThumbDrawable() {
        return NativeResources.getDrawable(RESOURCE_NAME_THUMB);
	}
	
	private Drawable getDefaultTrackDrawable() {
        return NativeResources.getDrawable(RESOURCE_NAME_TRACK);
	}
	
	private String getDefaultOnString() {
        String result = NativeResources.getString(RESOURCE_NAME_ON);
        return result == null ? DEFAULT_TEXT_ON : result;
	}
	
	private String getDefaultOffString() {
        String result = NativeResources.getString(RESOURCE_NAME_OFF);
        return result == null ? DEFAULT_TEXT_OFF : result;
	}

	/**
	 * Sets the switch text color, size, style, hint color, and highlight color
	 * from the specified TextAppearance resource.
	 * 
	 * @attr ref android.R.styleable#Switch_switchTextAppearance
	 */
	public void setSwitchTextAppearance(Context context, int resid) {
		TypedArray appearance = context.obtainStyledAttributes(resid,
				R.styleable.TextAppearanceAccentSwitch);

		ColorStateList colors;
		int ts;

		colors = appearance
				.getColorStateList(R.styleable.TextAppearanceAccentSwitch_android_textColor);
		if (colors != null) {
			mTextColors = colors;
		} else {
			// If no color set in TextAppearance, default to the view's
			// textColor
			mTextColors = getTextColors();
		}

		ts = appearance.getDimensionPixelSize(
				R.styleable.TextAppearanceAccentSwitch_android_textSize, 0);
		if (ts != 0) {
			if (ts != mTextPaint.getTextSize()) {
				mTextPaint.setTextSize(ts);
				requestLayout();
			}
		}

		int typefaceIndex, styleIndex;

		typefaceIndex = appearance.getInt(
				R.styleable.TextAppearanceAccentSwitch_android_typeface, -1);
		styleIndex = appearance.getInt(
				R.styleable.TextAppearanceAccentSwitch_android_textStyle, -1);

		setSwitchTypefaceByIndex(typefaceIndex, styleIndex);

		boolean allCaps = appearance.getBoolean(
				R.styleable.TextAppearanceAccentSwitch_android_textAllCaps, false);
		if (allCaps) {
			mSwitchTransformationMethod = new AllCapsTransformationMethod(
					getContext());
			mSwitchTransformationMethod.setLengthChangesAllowed(true);
		} else {
			mSwitchTransformationMethod = null;
		}

		appearance.recycle();
	}

	private void setSwitchTypefaceByIndex(int typefaceIndex, int styleIndex) {
		Typeface tf = null;
		switch (typefaceIndex) {
		case SANS:
			tf = Typeface.SANS_SERIF;
			break;

		case SERIF:
			tf = Typeface.SERIF;
			break;

		case MONOSPACE:
			tf = Typeface.MONOSPACE;
			break;
		}

		setSwitchTypeface(tf, styleIndex);
	}

	public void setSwitchTypeface(Typeface tf, int style) {
		if (style > 0) {
			if (tf == null) {
				tf = Typeface.defaultFromStyle(style);
			} else {
				tf = Typeface.create(tf, style);
			}

			setSwitchTypeface(tf);
			// now compute what (if any) algorithmic styling is needed
			int typefaceStyle = tf != null ? tf.getStyle() : 0;
			int need = style & ~typefaceStyle;
			mTextPaint.setFakeBoldText((need & Typeface.BOLD) != 0);
			mTextPaint.setTextSkewX((need & Typeface.ITALIC) != 0 ? -0.25f : 0);
		} else {
			mTextPaint.setFakeBoldText(false);
			mTextPaint.setTextSkewX(0);
			setSwitchTypeface(tf);
		}
	}

	/**
	 * Sets the typeface in which the text should be displayed on the switch.
	 * Note that not all Typeface families actually have bold and italic
	 * variants, so you may need to use
	 * {@link #setSwitchTypeface(Typeface, int)} to get the appearance that you
	 * actually want.
	 * 
	 * @attr ref android.R.styleable#TextView_typeface
	 * @attr ref android.R.styleable#TextView_textStyle
	 */
	public void setSwitchTypeface(Typeface tf) {
		if (mTextPaint.getTypeface() != tf) {
			mTextPaint.setTypeface(tf);

			requestLayout();
			invalidate();
		}
	}

	/**
	 * Set the amount of horizontal padding between the switch and the
	 * associated text.
	 * 
	 * @param pixels
	 *            Amount of padding in pixels
	 * 
	 * @attr ref android.R.styleable#Switch_switchPadding
	 */
	public void setSwitchPadding(int pixels) {
		mSwitchPadding = pixels;
		requestLayout();
	}

	/**
	 * Get the amount of horizontal padding between the switch and the
	 * associated text.
	 * 
	 * @return Amount of padding in pixels
	 * 
	 * @attr ref android.R.styleable#Switch_switchPadding
	 */
	public int getSwitchPadding() {
		return mSwitchPadding;
	}

	/**
	 * Set the minimum width of the switch in pixels. The switch's width will be
	 * the maximum of this value and its measured width as determined by the
	 * switch drawables and text used.
	 * 
	 * @param pixels
	 *            Minimum width of the switch in pixels
	 * 
	 * @attr ref android.R.styleable#Switch_switchMinWidth
	 */
	public void setSwitchMinWidth(int pixels) {
		mSwitchMinWidth = pixels;
		requestLayout();
	}

	/**
	 * Get the minimum width of the switch in pixels. The switch's width will be
	 * the maximum of this value and its measured width as determined by the
	 * switch drawables and text used.
	 * 
	 * @return Minimum width of the switch in pixels
	 * 
	 * @attr ref android.R.styleable#Switch_switchMinWidth
	 */
	public int getSwitchMinWidth() {
		return mSwitchMinWidth;
	}

	/**
	 * Set the horizontal padding around the text drawn on the switch itself.
	 * 
	 * @param pixels
	 *            Horizontal padding for switch thumb text in pixels
	 * 
	 * @attr ref android.R.styleable#Switch_thumbTextPadding
	 */
	public void setThumbTextPadding(int pixels) {
		mThumbTextPadding = pixels;
		requestLayout();
	}

	/**
	 * Get the horizontal padding around the text drawn on the switch itself.
	 * 
	 * @return Horizontal padding for switch thumb text in pixels
	 * 
	 * @attr ref android.R.styleable#Switch_thumbTextPadding
	 */
	public int getThumbTextPadding() {
		return mThumbTextPadding;
	}

	/**
	 * Set the drawable used for the track that the switch slides within.
	 * 
	 * @param track
	 *            Track drawable
	 * 
	 * @attr ref android.R.styleable#Switch_track
	 */
	public void setTrackDrawable(Drawable track) {
		mTrackDrawable = track;
		requestLayout();
	}

	/**
	 * Set the drawable used for the track that the switch slides within.
	 * 
	 * @param resId
	 *            Resource ID of a track drawable
	 * 
	 * @attr ref android.R.styleable#Switch_track
	 */
	public void setTrackResource(int resId) {
		setTrackDrawable(getContext().getResources().getDrawable(resId));
	}

	/**
	 * Get the drawable used for the track that the switch slides within.
	 * 
	 * @return Track drawable
	 * 
	 * @attr ref android.R.styleable#Switch_track
	 */
	public Drawable getTrackDrawable() {
		return mTrackDrawable;
	}

	/**
	 * Set the drawable used for the switch "thumb" - the piece that the user
	 * can physically touch and drag along the track.
	 * 
	 * @param thumb
	 *            Thumb drawable
	 * 
	 * @attr ref android.R.styleable#Switch_thumb
	 */
	public void setThumbDrawable(Drawable thumb) {
		mThumbDrawable = thumb;
		requestLayout();
	}

	/**
	 * Set the drawable used for the switch "thumb" - the piece that the user
	 * can physically touch and drag along the track.
	 * 
	 * @param resId
	 *            Resource ID of a thumb drawable
	 * 
	 * @attr ref android.R.styleable#Switch_thumb
	 */
	public void setThumbResource(int resId) {
		setThumbDrawable(getContext().getResources().getDrawable(resId));
	}

	/**
	 * Get the drawable used for the switch "thumb" - the piece that the user
	 * can physically touch and drag along the track.
	 * 
	 * @return Thumb drawable
	 * 
	 * @attr ref android.R.styleable#Switch_thumb
	 */
	public Drawable getThumbDrawable() {
		return mThumbDrawable;
	}

	/**
	 * Returns the text displayed when the button is in the checked state.
	 * 
	 * @attr ref android.R.styleable#Switch_textOn
	 */
	public CharSequence getTextOn() {
		return mTextOn;
	}

	/**
	 * Sets the text displayed when the button is in the checked state.
	 * 
	 * @attr ref android.R.styleable#Switch_textOn
	 */
	public void setTextOn(CharSequence textOn) {
		mTextOn = textOn;
		requestLayout();
	}

	/**
	 * Returns the text displayed when the button is not in the checked state.
	 * 
	 * @attr ref android.R.styleable#Switch_textOff
	 */
	public CharSequence getTextOff() {
		return mTextOff;
	}

	/**
	 * Sets the text displayed when the button is not in the checked state.
	 * 
	 * @attr ref android.R.styleable#Switch_textOff
	 */
	public void setTextOff(CharSequence textOff) {
		mTextOff = textOff;
		requestLayout();
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (mOnLayout == null) {
			mOnLayout = makeLayout(mTextOn);
		}
		if (mOffLayout == null) {
			mOffLayout = makeLayout(mTextOff);
		}

		mTrackDrawable.getPadding(mTempRect);
		final int maxTextWidth = Math.max(mOnLayout.getWidth(),
				mOffLayout.getWidth());
		final int switchWidth = Math.max(mSwitchMinWidth, maxTextWidth * 2
				+ mThumbTextPadding * 4 + mTempRect.left + mTempRect.right);
		final int switchHeight = mTrackDrawable.getIntrinsicHeight();

		mThumbWidth = maxTextWidth + mThumbTextPadding * 2;

		mSwitchWidth = switchWidth;
		mSwitchHeight = switchHeight;

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int measuredHeight = getMeasuredHeight();
		if (measuredHeight < switchHeight) {
			setMeasuredDimension(getMeasuredWidth(), switchHeight);
		}
	}

	@Override
	public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
		super.onPopulateAccessibilityEvent(event);
		Layout layout = isChecked() ? mOnLayout : mOffLayout;
		if (layout != null && !TextUtils.isEmpty(layout.getText())) {
			event.getText().add(layout.getText());
		}
	}

	private Layout makeLayout(CharSequence text) {
		final CharSequence transformed = (mSwitchTransformationMethod != null) ? mSwitchTransformationMethod
				.getTransformation(text, this) : text;
		return new StaticLayout(transformed, mTextPaint, (int) Math.ceil(Layout
				.getDesiredWidth(transformed, mTextPaint)),
				Layout.Alignment.ALIGN_NORMAL, 1.f, 0, true);
	}

	/**
	 * @return true if (x, y) is within the target area of the switch thumb
	 */
	private boolean hitThumb(float x, float y) {
		mThumbDrawable.getPadding(mTempRect);
		final int thumbTop = mSwitchTop - mTouchSlop;
		final int thumbLeft = mSwitchLeft + (int) (mThumbPosition + 0.5f)
				- mTouchSlop;
		final int thumbRight = thumbLeft + mThumbWidth + mTempRect.left
				+ mTempRect.right + mTouchSlop;
		final int thumbBottom = mSwitchBottom + mTouchSlop;
		return x > thumbLeft && x < thumbRight && y > thumbTop
				&& y < thumbBottom;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		mVelocityTracker.addMovement(ev);
		final int action = ev.getActionMasked();
		switch (action) {
		case MotionEvent.ACTION_DOWN: {
			final float x = ev.getX();
			final float y = ev.getY();
			if (isEnabled() && hitThumb(x, y)) {
				mTouchMode = TOUCH_MODE_DOWN;
				mTouchX = x;
				mTouchY = y;
			}
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			switch (mTouchMode) {
			case TOUCH_MODE_IDLE:
				// Didn't target the thumb, treat normally.
				break;

			case TOUCH_MODE_DOWN: {
				final float x = ev.getX();
				final float y = ev.getY();
				if (Math.abs(x - mTouchX) > mTouchSlop
						|| Math.abs(y - mTouchY) > mTouchSlop) {
					mTouchMode = TOUCH_MODE_DRAGGING;
					getParent().requestDisallowInterceptTouchEvent(true);
					mTouchX = x;
					mTouchY = y;
					return true;
				}
				break;
			}

			case TOUCH_MODE_DRAGGING: {
				final float x = ev.getX();
				final float dx = x - mTouchX;
				float newPos = Math.max(0,
						Math.min(mThumbPosition + dx, getThumbScrollRange()));
				if (newPos != mThumbPosition) {
					mThumbPosition = newPos;
					mTouchX = x;
					invalidate();
				}
				return true;
			}
			}
			break;
		}

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL: {
			if (mTouchMode == TOUCH_MODE_DRAGGING) {
				stopDrag(ev);
				return true;
			}
			mTouchMode = TOUCH_MODE_IDLE;
			mVelocityTracker.clear();
			break;
		}
		}

		return super.onTouchEvent(ev);
	}

	private void cancelSuperTouch(MotionEvent ev) {
		MotionEvent cancel = MotionEvent.obtain(ev);
		cancel.setAction(MotionEvent.ACTION_CANCEL);
		super.onTouchEvent(cancel);
		cancel.recycle();
	}

	/**
	 * Called from onTouchEvent to end a drag operation.
	 * 
	 * @param ev
	 *            Event that triggered the end of drag mode - ACTION_UP or
	 *            ACTION_CANCEL
	 */
	private void stopDrag(MotionEvent ev) {
		mTouchMode = TOUCH_MODE_IDLE;
		// Up and not canceled, also checks the switch has not been disabled
		// during the drag
		boolean commitChange = ev.getAction() == MotionEvent.ACTION_UP
				&& isEnabled();

		cancelSuperTouch(ev);

		if (commitChange) {
			boolean newState;
			mVelocityTracker.computeCurrentVelocity(1000);
			float xvel = mVelocityTracker.getXVelocity();
			if (Math.abs(xvel) > mMinFlingVelocity) {
				// newState = isLayoutRtl() ? (xvel < 0) : (xvel > 0);
				newState = xvel > 0;
			} else {
				newState = getTargetCheckedState();
			}
			animateThumbToCheckedState(newState);
		} else {
			animateThumbToCheckedState(isChecked());
		}
	}

	private void animateThumbToCheckedState(boolean newCheckedState) {
		// float targetPos = newCheckedState ? 0 : getThumbScrollRange();
		// mThumbPosition = targetPos;
		setChecked(newCheckedState);
	}

	private boolean getTargetCheckedState() {
		return mThumbPosition >= getThumbScrollRange() / 2;
	}

	private void setThumbPosition(boolean checked) {
		mThumbPosition = checked ? getThumbScrollRange() : 0;
	}

	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
		setThumbPosition(isChecked());
		invalidate();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		setThumbPosition(isChecked());

		int switchRight;
		int switchLeft;

		switchRight = getWidth() - getPaddingRight();
		switchLeft = switchRight - mSwitchWidth;

		int switchTop = 0;
		int switchBottom = 0;
		switch (getGravity() & Gravity.VERTICAL_GRAVITY_MASK) {
		default:
		case Gravity.TOP:
			switchTop = getPaddingTop();
			switchBottom = switchTop + mSwitchHeight;
			break;

		case Gravity.CENTER_VERTICAL:
			switchTop = (getPaddingTop() + getHeight() - getPaddingBottom())
					/ 2 - mSwitchHeight / 2;
			switchBottom = switchTop + mSwitchHeight;
			break;

		case Gravity.BOTTOM:
			switchBottom = getHeight() - getPaddingBottom();
			switchTop = switchBottom - mSwitchHeight;
			break;
		}

		mSwitchLeft = switchLeft;
		mSwitchTop = switchTop;
		mSwitchBottom = switchBottom;
		mSwitchRight = switchRight;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// Draw the switch
		int switchLeft = mSwitchLeft;
		int switchTop = mSwitchTop;
		int switchRight = mSwitchRight;
		int switchBottom = mSwitchBottom;

		mTrackDrawable.setBounds(switchLeft, switchTop, switchRight,
				switchBottom);
		mTrackDrawable.draw(canvas);

		canvas.save();

		mTrackDrawable.getPadding(mTempRect);
		int switchInnerLeft = switchLeft + mTempRect.left;
		int switchInnerTop = switchTop + mTempRect.top;
		int switchInnerRight = switchRight - mTempRect.right;
		int switchInnerBottom = switchBottom - mTempRect.bottom;
		canvas.clipRect(switchInnerLeft, switchTop, switchInnerRight,
				switchBottom);

		mThumbDrawable.getPadding(mTempRect);
		final int thumbPos = (int) (mThumbPosition + 0.5f);
		int thumbLeft = switchInnerLeft - mTempRect.left + thumbPos;
		int thumbRight = switchInnerLeft + thumbPos + mThumbWidth
				+ mTempRect.right;

		mThumbDrawable
				.setBounds(thumbLeft, switchTop, thumbRight, switchBottom);
		mThumbDrawable.draw(canvas);

		// mTextColors should not be null, but just in case
		if (mTextColors != null) {
			mTextPaint.setColor(mTextColors.getColorForState(
					getDrawableState(), mTextColors.getDefaultColor()));
		}
		mTextPaint.drawableState = getDrawableState();

		Layout switchText = getTargetCheckedState() ? mOnLayout : mOffLayout;
		if (switchText != null) {
			canvas.translate(
					(thumbLeft + thumbRight) / 2 - switchText.getWidth() / 2,
					(switchInnerTop + switchInnerBottom) / 2
							- switchText.getHeight() / 2);
			switchText.draw(canvas);
		}

		canvas.restore();
	}

	@Override
	public int getCompoundPaddingRight() {
		int padding = super.getCompoundPaddingRight() + mSwitchWidth;
		if (!TextUtils.isEmpty(getText())) {
			padding += mSwitchPadding;
		}
		return padding;
	}

	private int getThumbScrollRange() {
		if (mTrackDrawable == null) {
			return 0;
		}
		mTrackDrawable.getPadding(mTempRect);
		return mSwitchWidth - mThumbWidth - mTempRect.left - mTempRect.right;
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
		if (isChecked()) {
			mergeDrawableStates(drawableState, CHECKED_STATE_SET);
		}
		return drawableState;
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();

		int[] myDrawableState = getDrawableState();

		// Set the state of the Drawable
		// Drawable may be null when checked state is set from XML, from super
		// constructor
		if (mThumbDrawable != null)
			mThumbDrawable.setState(myDrawableState);
		if (mTrackDrawable != null)
			mTrackDrawable.setState(myDrawableState);

		invalidate();
	}

	@Override
	protected boolean verifyDrawable(Drawable who) {
		return super.verifyDrawable(who) || who == mThumbDrawable
				|| who == mTrackDrawable;
	}

	@Override
	public void jumpDrawablesToCurrentState() {
		super.jumpDrawablesToCurrentState();
		mThumbDrawable.jumpToCurrentState();
		mTrackDrawable.jumpToCurrentState();
	}

	@Override
	public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
		super.onInitializeAccessibilityEvent(event);
		event.setClassName(AccentSwitch.class.getName());
	}

	@Override
	public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
		super.onInitializeAccessibilityNodeInfo(info);
		info.setClassName(AccentSwitch.class.getName());
		CharSequence switchText = isChecked() ? mTextOn : mTextOff;
		if (!TextUtils.isEmpty(switchText)) {
			CharSequence oldText = info.getText();
			if (TextUtils.isEmpty(oldText)) {
				info.setText(switchText);
			} else {
				StringBuilder newText = new StringBuilder();
				newText.append(oldText).append(' ').append(switchText);
				info.setText(newText);
			}
		}
	}

	public class AllCapsTransformationMethod {
		private boolean mEnabled;
		private Locale mLocale;

		public AllCapsTransformationMethod(Context context) {
			mLocale = context.getResources().getConfiguration().locale;
		}

		public CharSequence getTransformation(CharSequence source, View view) {
			if (mEnabled) {
				return source != null ? source.toString().toUpperCase(mLocale)
						: null;
			}
			return source;
		}

		public void onFocusChanged(View view, CharSequence sourceText,
				boolean focused, int direction, Rect previouslyFocusedRect) {
		}

		public void setLengthChangesAllowed(boolean allowLengthChanges) {
			mEnabled = allowLengthChanges;
		}

	}
}
