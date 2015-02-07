package de.larsgrefer.android.library.fader;

import junit.framework.TestCase;

public class IntFaderTest extends TestCase {

	public void testSetBounds() throws Exception {
		IntFader intFader = new IntFader(0, 100);

		assertEquals(0, intFader.getFrom().intValue());
		assertEquals(100, intFader.getTo().intValue());

		intFader.setBounds(50, 150);

		assertEquals(50, intFader.getFrom().intValue());
		assertEquals(150, intFader.getTo().intValue());
	}

	public void testGetValue() throws Exception {

	}
}