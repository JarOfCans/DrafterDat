package cardHelper.toolBox;

import static org.junit.Assert.*;

import org.junit.Test;

import cardHelper.Setting;
import cardHelper.Settings;

public class CardHelperTester {

	@Test
	public void settingsCheckValidTester() {
		assertTrue(Settings.checkValid("Settings=4"));
		assertTrue(Settings.checkValid("Hoi="));
		assertFalse(Settings.checkValid("Hoi"));
		assertFalse(Settings.checkValid("=Hoi"));
		assertFalse(Settings.checkValid("H=o=i"));
		assertTrue(Settings.checkValid("H="));
	}
	@Test
	public void settingsTestName()
	{
		System.out.println(Settings.getNameHelper("Hoi=yep"));
		assertTrue(Settings.getNameHelper("Hoi=yep").equals("Hoi"));
	}
	@Test
	public void settingsTestValue()
	{
		System.out.println(Settings.getValueHelper("Hoi=yep"));
		assertTrue(Settings.getValueHelper("Hoi=yep").equals("yep"));
		System.out.println(Settings.getValueHelper("Hoi="));
		assertTrue(Settings.getValueHelper("Hoi=").equals(""));
	}
}
