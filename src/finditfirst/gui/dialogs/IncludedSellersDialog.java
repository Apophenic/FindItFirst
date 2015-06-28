package finditfirst.gui.dialogs;

import finditfirst.main.Settings;

/**
 * @see {@link AbstractSellersDialog}
 */
public class IncludedSellersDialog extends AbstractSellersDialog
{
	public IncludedSellersDialog()
	{
		super(Settings.getInstance().getIncludedSellerList());

		lblDialogType.setText("Included Sellers List");
		lblSellerInfo.setText("Results will appear only if listed by a seller on this list");
	}
	
}
