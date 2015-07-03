package DataClasss;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import UI.Homepage;

public class Selection {
	public int selectionindex;//条件索引
	public double selectionmin;//最小值
	public double selectionmax;//最大值

	public void setSelection(double min, double max){
		this.selectionmin = min;
		this.selectionmax = max;
	}
	
		
}
