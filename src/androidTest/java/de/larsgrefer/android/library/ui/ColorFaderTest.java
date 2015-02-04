package de.larsgrefer.android.library.ui;

import android.graphics.Color;

import junit.framework.TestCase;

import de.larsgrefer.android.library.fader.ColorFader;

public class ColorFaderTest extends TestCase {

	public void testGetSet() throws Exception {
		ColorFader colorFader = new ColorFader(Color.WHITE, Color.BLACK);

		assertEquals(Color.WHITE, colorFader.getFrom());
		assertEquals(Color.BLACK, colorFader.getTo());

		assertEquals(Color.GRAY, colorFader.getColor(0.5f));

	}

	public void testGetFrom() throws Exception {

	}

	public void testGetTo() throws Exception {

	}
}