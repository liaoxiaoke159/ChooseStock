package DataClasss;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import UI.Homepage;

public class Selection {
	public int selectionindex;//��������
	public double selectionmin;//��Сֵ
	public double selectionmax;//���ֵ

	public void setSelection(double min, double max){
		this.selectionmin = min;
		this.selectionmax = max;
	}
	
		
}
