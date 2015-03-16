package de.fhconfig.android.binding.viewAttributes.view;

public class AnimationTrigger {

	private TriggerListener mListener;
	private int mAnimationId;

	public AnimationTrigger() {
	}

	public void setTriggerListener(TriggerListener listener) {
		mListener = listener;
	}

	public void removeTriggerListener(TriggerListener listener) {
		mListener = null;
	}

	public void notifyAnimationFire() {
		if (mListener != null)
			mListener.fireAnimation(this);
	}

	public int getAnimationId() {
		return mAnimationId;
	}

	public void setAnimationId(int animationId) {
		mAnimationId = animationId;
	}

	public enum TriggerType {
		True,
		Equal,
		Change,
		WhenLargerThan,
		FireWhenLarger
	}

	public interface TriggerListener {
		public void fireAnimation(AnimationTrigger trigger);
	}
}