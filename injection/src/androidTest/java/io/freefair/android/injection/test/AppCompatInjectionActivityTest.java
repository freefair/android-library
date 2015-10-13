package io.freefair.android.injection.test;

import android.support.annotation.Keep;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;

import io.freefair.android.injection.annotation.InjectView;
import io.freefair.android.injection.annotation.XmlLayout;
import io.freefair.android.injection.ui.InjectionAppCompatActivity;

/**
 * Created by larsgrefer on 13.10.15.
 */
public class AppCompatInjectionActivityTest extends ActivityInstrumentationTestCase2<AppCompatInjectionActivityTest.AppCompatInjectionTestActivity> {

	@Keep
	AppCompatInjectionTestActivity activity;

	public AppCompatInjectionActivityTest() {
		super(AppCompatInjectionTestActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
	}

	public void testLayoutInjection(){
		View button = activity.findViewById(R.id.test_button);
		assertNotNull(button);
		assertEquals("FOO", ((Button)button).getText());
	}

	public void testViewInjection(){
		assertEquals("FOO", activity.getButton().getText());
	}

	@Keep
	@XmlLayout(R.layout.activity_test)
	public static class AppCompatInjectionTestActivity extends InjectionAppCompatActivity {

		@InjectView(R.id.test_button)
		private Button button;

		public Button getButton() {
			return button;
		}
	}
}
