package com.recipes.android.fragment;

import com.recipes.R;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * More info here: http://developer.android.com/guide/topics/graphics/prop-animation.html
 * <p/>
 * Jacek Modrakowski
 * modrakowski.pl
 * 13/09/2015.
 */
public class AnimationsFragment extends Fragment {

	@InjectView(R.id.animationsBall) View ballView;
	@InjectView(R.id.animationsSecondBall) View secondBallView;
	@InjectView(R.id.animationsCenterTransition) View centerView;

	@Nullable @Override
	public View onCreateView(
			@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View result = inflater.inflate(R.layout.fragment_animations, container, false);
		ButterKnife.inject(this, result);
		return result;
	}

	@OnClick(R.id.animationsBall)
	public void onFirstBallClick(final View view) {
		Toast.makeText(getActivity(), "First ball clicked!", Toast.LENGTH_SHORT).show();
	}

	@OnClick(R.id.animationsSecondBall)
	public void onSecondBallClick(final View view) {
		Toast.makeText(getActivity(), "Second ball clicked!", Toast.LENGTH_SHORT).show();
	}

	@OnClick(R.id.animationsArgbAnimator)
	public void argbEvaluator(final View view) {
		ValueAnimator anim = ValueAnimator.ofArgb(Color.RED, Color.BLUE);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override public void onAnimationUpdate(ValueAnimator animation) {
				final Drawable ballDrawable = ballView.getBackground();
				// Drawable.mutate()
				final Drawable wrappedDrawable = DrawableCompat.wrap(ballDrawable).mutate();
				// final Drawable wrappedDrawable = DrawableCompat.wrap(ballDrawable);
				DrawableCompat.setTint(wrappedDrawable, (Integer) animation.getAnimatedValue());
			}
		});
		anim.setDuration(1500);
		anim.setInterpolator(new BounceInterpolator());
		anim.start();
	}

	@OnClick(R.id.animationsAnimatorSet)
	public void animatorSet(final View view) {
		final ViewGroup parent = (ViewGroup) ballView.getParent();
		final int parentHalfWidth = parent.getMeasuredWidth() / 2;
		final int parentHalfHeight = parent.getMeasuredHeight() / 2;

		final ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(ballView,
				View.TRANSLATION_Y.getName(), 0f, -parentHalfHeight, 0f);
		final ObjectAnimator translationXAnim = ObjectAnimator.ofFloat(ballView,
				View.TRANSLATION_X.getName(), 0f, parentHalfWidth, 0f);
		final ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(ballView,
				View.ROTATION.getName(), 0f, 90f, 0f);

		final AnimatorSet as = new AnimatorSet();
		as.playSequentially(translationXAnim, translationYAnim);
		as.playSequentially(rotationAnim);
		as.setInterpolator(new AnticipateOvershootInterpolator(2.8f, 0.9f));
		as.setDuration(2300);
		as.start();
	}

	@OnClick(R.id.animationsLayoutParamsAnimator)
	public void valueLayoutParamsAnimator(final View view) {
		ballView.clearAnimation();
		final ValueAnimator va = ValueAnimator.ofInt(0, 300, 0);
		va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override public void onAnimationUpdate(@NonNull ValueAnimator animation) {
				final int currentValue = (Integer) animation.getAnimatedValue();
				final FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams)
						secondBallView.getLayoutParams();
				lp.bottomMargin = currentValue;
				secondBallView.setLayoutParams(lp);
			}
		});
		va.setInterpolator(new BounceInterpolator());
		va.setDuration(1300);
		va.start();
	}

	@OnClick(R.id.animationsValueAnimator)
	public void valueAnimator(final View view) {
		ballView.clearAnimation();

		final ValueAnimator va = ValueAnimator.ofInt(0, -300, 0);
		va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override public void onAnimationUpdate(@NonNull ValueAnimator animation) {
				final int currentValue =
						(Integer) animation.getAnimatedValue();
				ballView.setTranslationY(currentValue);
			}
		});
		va.setInterpolator(new BounceInterpolator());
		va.setDuration(1300);
		va.start();
	}

	@OnClick(R.id.animationsObjectAnimator)
	public void objectAnimator(final View view) {
		ballView.clearAnimation();
		final ObjectAnimator va = ObjectAnimator.ofFloat(ballView,
				View.TRANSLATION_Y.getName(), 0f, -300f, 0f);
		va.setInterpolator(
				new AnticipateOvershootInterpolator(1.6f,1.2f));
		va.setDuration(1300);
		va.start();
	}

	@OnClick(R.id.animationsViewChainAnimator)
	public void viewChainAnimator(final View view) {
		ballView.clearAnimation();

		ballView.animate().translationY(-300).
				setInterpolator(new FastOutLinearInInterpolator()).
				setListener(new Animator.AnimatorListener() {
					@Override public void onAnimationStart(Animator animation) {

					}

					@Override public void onAnimationEnd(Animator animation) {
						ballView.animate()
								.translationY(0)
								.setDuration(1300).start();
					}

					@Override public void onAnimationCancel(Animator animation) {

					}

					@Override public void onAnimationRepeat(Animator animation) {

					}
				}).setDuration(1300).start();
	}

	@OnClick(R.id.animationsXmlDefinedAnimator)
	public void xmlDefinedAnimator(final View view) {
		AnimatorSet anim = (AnimatorSet) AnimatorInflater.
				loadAnimator(getActivity(), R.animator.ball_animation);
		anim.setTarget(ballView);
		anim.start();
	}

	@OnClick(R.id.animationsTransitionDrawable)
	public void transitionDrawable(final View view) {
		TransitionDrawable td = (TransitionDrawable) centerView.getBackground();
		td.startTransition(450);
	}
}
