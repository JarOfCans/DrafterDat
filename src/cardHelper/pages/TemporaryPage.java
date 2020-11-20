package cardHelper.pages;

import cardHelper.MainClass;
import cardHelper.MainClass.Pages;

public abstract class TemporaryPage extends Page {

	protected Page previousPage;
	public TemporaryPage(MainClass main, Page previous) {
		super(main, Pages.tempPage);
		previousPage = previous;
	}
	public void revert()
	{
		/*main.cScreen = previousPage;
		if (previousPage.equals(main.packopen)) {
			main.packopen.setPack(main.packopen.apack);
		}
		if (previousPage.equals(main.collection))
		{
			main.collection.reSort();
		}*/
		main.setPage(previousPage.getPageEnum());
		if (previousPage.equals(Pages.openPack))
		{
			((Packopen)previousPage).setPack(((Packopen)previousPage).apack);
		}
		else if (previousPage.equals(Pages.collection))
		{
			((Collection)previousPage).reSort();
		}
	}

}
