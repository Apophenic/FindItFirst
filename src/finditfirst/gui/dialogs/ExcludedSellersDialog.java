package finditfirst.gui.dialogs;

import finditfirst.main.Settings;

/**
 * @see {@link AbstractSellersDialog}
 */
public class ExcludedSellersDialog extends AbstractSellersDialog
{
	
	public ExcludedSellersDialog()
	{
		super(Settings.getInstance().getExcludedSellerList());
		
		lblDialogType.setText("Excluded Sellers List");
		lblSellerInfo.setText("Results will not appear if listed by a seller on this list");
	}
	
}
