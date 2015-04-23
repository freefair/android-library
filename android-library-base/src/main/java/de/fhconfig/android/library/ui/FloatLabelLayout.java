package de.fhconfig.android.library.ui;
/*
 * Copyright (C) 2014 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import de.fhconfig.android.library.Logger;
import de.fhconfig.android.library.R;
import de.fhconfig.android.library.ui.animation.TextColorAnimation;
import de.fhconfig.android.library.ui.animation.TextSizeAnimation;

/**
 * Layout which an {@link android.widget.EditText} to show a floating label when the hint is hidden
 * due to the user inputting text.
 * <p/>
 * <p>This is an modification based on <a href="https://gist.github.com/chrisbanes/11247418#comment-1220415">this suggestion</a>
 * which is based on <a href="https://gist.github.com/chrisbanes/11247418">this implementation</a> by Chris Banes.</p>
 *
 * @see <a href="https://dribbble.com/shots/1254439--GIF-Mobile-Form-Interaction">Matt D. Smith on Dribble</a>
 * @see <a href="http://bradfrostweb.com/blog/post/float-label-pattern/">Brad Frost's blog post</a>
 * @see <a href="https://gist.github.com/chrisbanes/11247418">Chris Banes gist</a>
 * @see <a href="http://www.google.com/design/spec/components/text-fields.html#text-fields-floating-labels">Material Design specification</a>
 */
public class FloatLabelLayout extends FrameLayout {

	private static final float DEFAULT_PADDING_LEFT_RIGHT_DP = 4f;

	private Logger log = Logger.forObject(this);
	private Convert convert = new Convert(this.getResources());

	private float labelTextSize = 12f;
	private float labelHeight;

	private long animationDuration = 200;
	private int labelColor;
	private int labelActiveColor;

	private EditText editText;
	private ColorStateList hintTextColors;
	private TextView label;

	public FloatLabelLayout(Context context) {
		this(context, null);
	}

	public FloatLabelLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FloatLabelLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatLabelLayout);

		final int sidePadding = a.getDimensionPixelSize(R.styleable.FloatLabelLayout_floatLabelSidePadding,
															   convert.dipToPx(DEFAULT_PADDING_LEFT_RIGHT_DP));
		label = new TextView(context);
		label.setPadding(sidePadding, 0, sidePadding, 0);
		label.setVisibility(INVISIBLE);

		label.setTextAppearance(context, a.getResourceId(R.styleable.FloatLabelLayout_floatLabelTextAppearance,
																R.style.TextAppearance_FloatLabel));
		labelTextSize = label.getTextSize();

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(new int[]{R.attr.colorControlActivated});
		labelActiveColor = typedArray.getColor(0, 0);
		labelColor = label.getTextColors().getDefaultColor();

		FrameLayout.LayoutParams labelLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		labelLayoutParams.setMargins(0, convert.dipToPx(16), 0, 0);
		labelLayoutParams.gravity = Gravity.TOP;

		addView(label, labelLayoutParams);

		this.setMinimumHeight(convert.dipToPx(72f));

		a.recycle();
	}

	@Override
	public final void addView(View child, int index, ViewGroup.LayoutParams params) {
		if (child instanceof EditText) {
			// If we already have an EditText, throw an exception
			if (editText != null) {
				throw new IllegalArgumentException("We already have an EditText, can only have one");
			}

			// Update the layout params so that the EditText is at the bottom, with enough top
			// margin to show the label
			final LayoutParams lp = new LayoutParams(params);
			lp.gravity = Gravity.TOP;
			lp.topMargin = (int) (convert.dipToPx(16) + label.getTextSize());
			params = lp;

			setEditText((EditText) child);

		}

		// Carry on adding the View...
		super.addView(child, index, params);
	}

	private void setEditText(EditText editText) {
		this.editText = editText;

		updateHintTextColors();

		label.setText(this.editText.getHint());

		if (TextUtils.isEmpty(this.editText.getText())) {
			hideLabel(0);
		} else {
			showLabel(0);
		}

		// Add a TextWatcher so that we know when the text input has changed
		this.editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				if (TextUtils.isEmpty(s)) {
					// The text is empty, so hide the label if it is visible
					if (label.getVisibility() == View.VISIBLE) {
						hideLabel(animationDuration);
					}
				} else {
					// The text is not empty, so show the label if it is not visible
					if (label.getVisibility() != View.VISIBLE) {
						showLabel(animationDuration);
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});

		// Add focus listener to the EditText so that we can notify the label that it is activated.
		// Allows the use of a ColorStateList for the text color on the label
		this.editText.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean focused) {
				setLabelActivated(focused);
				updateLabelTextColor();
			}
		});
		updateLabelTextColor();
	}

	private void updateHintTextColors() {
		if (Color.alpha(this.editText.getCurrentHintTextColor()) > 0) {
			hintTextColors = this.editText.getHintTextColors();
		}
	}

	@TargetApi(11)
	private void setLabelActivated(boolean focused) {
		if (Build.VERSION.SDK_INT >= 11) {
			label.setActivated(focused);
		}
	}

	/**
	 * Show the label using an animation
	 */
	private void showLabel(long animationDuration) {

		AnimationSet animationSet = new AnimationSet(true);

		if (labelHeight == 0) {
			labelHeight = label.getHeight();
		}

		TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_SELF, 0,
																			  Animation.ABSOLUTE, labelTextSize + getEditText().getPaddingTop(), Animation.ABSOLUTE, 0);
		translateAnimation.setDuration(animationDuration);
		animationSet.addAnimation(translateAnimation);

		TextColorAnimation tcAnimation = new TextColorAnimation(label, editText.getCurrentHintTextColor(), editText.isFocused() ? labelActiveColor : labelColor);
		tcAnimation.setDuration(animationDuration);
		animationSet.addAnimation(tcAnimation);

		TextSizeAnimation textSizeAnimation = new TextSizeAnimation(label, editText.getTextSize(), labelTextSize);
		textSizeAnimation.setDuration(animationDuration);
		textSizeAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		animationSet.addAnimation(textSizeAnimation);
		animationSet.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				label.setText(editText.getHint());
				label.setVisibility(VISIBLE);
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				updateLabelTextColor();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		label.clearAnimation();
		label.startAnimation(animationSet);
	}

	private void updateLabelTextColor() {
		label.setTextColor(editText.isFocused() ? labelActiveColor : labelColor);
	}

	/**
	 * Hide the label using an animation
	 */
	private void hideLabel(long animationDuration) {
		AnimationSet animationSet = new AnimationSet(true);

		TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
																			  Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, labelTextSize + getEditText().getPaddingTop());
		translateAnimation.setDuration(animationDuration);
		animationSet.addAnimation(translateAnimation);

		TextSizeAnimation textSizeAnimation = new TextSizeAnimation(label, labelTextSize, editText.getTextSize());
		textSizeAnimation.setDuration(animationDuration);
		textSizeAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		animationSet.addAnimation(textSizeAnimation);

		TextColorAnimation textColorAnimation = new TextColorAnimation(label, label.getCurrentTextColor(), editText.getCurrentHintTextColor());
		textColorAnimation.setDuration(animationDuration);
		animationSet.addAnimation(textColorAnimation);

		animationSet.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				label.setText(editText.getHint());
				updateHintTextColors();
				editText.setHintTextColor(Color.TRANSPARENT);
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				label.setVisibility(INVISIBLE);
				editText.setHintTextColor(hintTextColors);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		label.clearAnimation();
		label.startAnimation(animationSet);
	}

	/**
	 * @return the {@link android.widget.EditText} text input
	 */
	public EditText getEditText() {
		return editText;
	}

	/**
	 * @return the {@link android.widget.TextView} label
	 */
	public TextView getLabel() {
		return label;
	}

	public long getAnimationDuration() {
		return animationDuration;
	}

	public void setAnimationDuration(long animationDuration) {
		this.animationDuration = animationDuration;
	}

	public int getLabelColor() {
		return labelColor;
	}

	public void setLabelColor(int labelColor) {
		this.labelColor = labelColor;
	}

	public int getLabelActiveColor() {
		return labelActiveColor;
	}

	public void setLabelActiveColor(int labelActiveColor) {
		this.labelActiveColor = labelActiveColor;
	}
}