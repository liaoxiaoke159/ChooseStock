package UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import DataClasss.Selection;
import DataClasss.Selections;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;

public class HistorySelection {

	protected Shell shell;
	private Table table;
	private Composite[] composites ;
	private Button[] buttons;
	private List<Selections> selectionslist = new ArrayList<Selections>();
	private String file_path = "C:\\Users\\Administrator\\workspace\\Agile\\data\\方案.xls";
	private int selectedindex=-1;//标记当前选中的方案

	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			HistorySelection window = new HistorySelection();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * @return 
	 */
	public  List<Selection> open() {
		Display display = Display.getDefault();
		createContents();
		
		//把方案excel读到selectionslist中
		setSelectionlist(selectionslist,file_path);
		
		//重新排列方案
		setComposite();
		
		
		
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		if(selectedindex>=0)
		  return selectionslist.get(selectedindex).getSelectionlist();
		
		else
		  return null;
			

}

	private void setComposite() {
		// TODO Auto-generated method stub
		
		for(int listloop = 0; listloop<selectionslist.size();listloop++){
			composites[listloop].setVisible(true);
			composites[listloop].setBounds(0, 10+23*listloop, 186, 17);
			
			buttons[listloop].setText(selectionslist.get(listloop).getSelectionname());
//			System.out.println("方案"+listloop+selectionslist.get(listloop).getSelectionname()
//					);
//			for(int j = 0; j<selectionslist.get(listloop).getSelectionlist().size();j++){
//				System.out.println("index："+selectionslist.get(listloop).getSelectionlist().get(j).selectionindex
//						+" min:"+selectionslist.get(listloop).getSelectionlist().get(j).selectionmin
//						+" max:"+selectionslist.get(listloop).getSelectionlist().get(j).selectionmax);
//			}
			
			
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage("C:\\Users\\Administrator\\workspace\\Agile\\data\\logo.png"));
		shell.setSize(484, 300);
		shell.setText("SWT Application");
		
		
		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setBounds(10, 10, 61, 17);
		lblNewLabel_3.setText("历史方案：");
		
		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				
				shell.dispose();
				
			}
		});
		btnNewButton_1.setBounds(258, 208, 60, 27);
		btnNewButton_1.setText("确认选择");
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shell.dispose();
			}
		});
		btnNewButton.setBounds(342, 208, 61, 27);
		btnNewButton.setText("取消");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(226, 114, 12, 17);
		lblNewLabel.setText("》");
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(244, 33, 218, 149);
		table.setHeaderVisible(false);
		table.setLinesVisible(false);
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(74);
		tblclmnNewColumn.setText("New Column");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(71);
		tblclmnNewColumn_1.setText("New Column");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(68);
		tblclmnNewColumn_2.setText("New Column");
		
		Composite composite = new Composite(shell, SWT.BORDER);
		composite.setBounds(20, 33, 200, 200);
		
		//方案1
		Composite composite_1 = new Composite(composite, SWT.NONE);
		
		Label label_1 = new Label(composite_1, SWT.NONE);
		labelEvent(label_1,0);
		label_1.setBounds(153, 0, 24, 17);
		label_1.setText("删除");
		
		Button button_1 = new Button(composite_1, SWT.CHECK);
		btnEvent(button_1,0);
		
		
		button_1.setBounds(10, 0, 98, 17);
		button_1.setText("方案名");
		
		//方案2
		Composite composite_2 = new Composite(composite, SWT.NONE);
		
		Label label_2 = new Label(composite_2, SWT.NONE);
		labelEvent(label_2,1 );
		label_2.setText("\u5220\u9664");
		label_2.setBounds(153, 0, 24, 17);
		
		Button button_2 = new Button(composite_2, SWT.CHECK);
		btnEvent(button_2,1);
		button_2.setBounds(10, 0, 98, 17);
		button_2.setText("\u65B9\u6848\u540D");
		
		
		//方案3
		Composite composite_3 = new Composite(composite, SWT.NONE);
		
		Label label_3 = new Label(composite_3, SWT.NONE);
		labelEvent(label_3,2 );
		label_3.setText("\u5220\u9664");
		label_3.setBounds(153, 0, 24, 17);
		
		Button button_3 = new Button(composite_3, SWT.CHECK);
		btnEvent(button_3,2);
		button_3.setText("\u65B9\u6848\u540D");
		button_3.setBounds(10, 0, 98, 17);
		
		
		//方案4
		Composite composite_4 = new Composite(composite, SWT.NONE);
		
		Label label_4 = new Label(composite_4, SWT.NONE);
		labelEvent(label_4,3 );
		label_4.setText("\u5220\u9664");
		label_4.setBounds(153, 0, 24, 17);
		
		Button button_4 = new Button(composite_4, SWT.CHECK);
		btnEvent(button_4,3);
		button_4.setText("\u65B9\u6848\u540D");
		button_4.setBounds(10, 0, 98, 17);
		
		//方案5
		Composite composite_5 = new Composite(composite, SWT.NONE);
		
		Label label_5 = new Label(composite_5, SWT.NONE);
		labelEvent(label_5,4 );
		label_5.setText("\u5220\u9664");
		label_5.setBounds(153, 0, 24, 17);
		
		Button button_5 = new Button(composite_5, SWT.CHECK);
		btnEvent(button_5,4);
		button_5.setText("\u65B9\u6848\u540D");
		button_5.setBounds(10, 0, 98, 17);
		
		//方案6
		Composite composite_6 = new Composite(composite, SWT.NONE);
		
		Label label_6 = new Label(composite_6, SWT.NONE);
		labelEvent(label_6,5 );
		label_6.setText("\u5220\u9664");
		label_6.setBounds(153, 0, 24, 17);
		
		Button button_6 = new Button(composite_6, SWT.CHECK);
		btnEvent(button_6,5);
		button_6.setText("sg");
		button_6.setBounds(10, 0, 98, 17);
		
		
		
		
		composites = new Composite[]{composite_1,composite_2,composite_3,composite_4,composite_5,composite_6};
		buttons = new Button[]{button_1,button_2,button_3,button_4,button_5,button_6};
		
		
	}

	

	

	//babel事件
	private void labelEvent(Label label,int index) {
		// TODO Auto-generated method stub
		label.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				label.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			}
			@Override
			public void mouseExit(MouseEvent e) {
				label.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			}
		});
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				MessageBox messagebox = new MessageBox(shell, SWT.YES | SWT.NO);
				messagebox.setText("注意");
				messagebox.setMessage("          确认是否删除该方案");
				int val = messagebox.open();
				if (val == SWT.YES){
					lbldecEvent(index,file_path);
				}
			}
		});
		
	}
	
	protected void lbldecEvent(int index,String file_path) {
		// TODO Auto-generated method stub
		if(buttons[index].getSelection()){
			selectedindex = -1;//用一个负值标记当前没有方案被选中
			table.removeAll();
		}
		
		for(int listloop=0; listloop<selectionslist.size();listloop++){
			composites[listloop].setVisible(false);
			
		}
		
		//删除excel中的第index行方案
		decExcel(index,file_path);
		selectionslist.remove(index);
		
		//重新排列方案
		setComposite();
		
	}
	
	//btn事件
	protected void btnEvent(Button button, int index) {
		// TODO Auto-generated method stub
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(button.getSelection()){
					
					//如果有其他方案被选中
					for(int buttonloop = 0; buttonloop<selectionslist.size();buttonloop++){
						if(buttonloop==index)
							continue;
						if(buttons[buttonloop].getSelection()){
							buttons[buttonloop].setSelection(false);
							table.removeAll();
						}
					}
					selectionslist.get(index).selectiontable_nitialize(table);
					selectedindex = index;
					
				}
				else{
					selectedindex = -1;//用一个负值标记当前没有方案被选中
					table.removeAll();
				}
				
				
			}
		});
		
	}


	
	//删除excel中的第index行方案
	public static void decExcel(int index, String file_path) {
		// TODO Auto-generated method stub
		
		Workbook workBook = null;
		try {
			workBook = Workbook.getWorkbook(new FileInputStream(file_path));
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WritableWorkbook writeBook = null;
		try {
			writeBook = Workbook.createWorkbook(new File(file_path),workBook);
		} catch (IOException e) {
			e.printStackTrace();
		}
		WritableSheet sheet = writeBook.getSheet(0);
		
		sheet.removeRow(index);
		
	    try {
			writeBook.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			writeBook.close();
		} catch (WriteException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}

	//把方案excel读到selectionslist中
	public static void setSelectionlist(  List<Selections> selectionslist, String file_path) {
		// TODO Auto-generated method stub
		Workbook rwb = null;
		try {
			rwb = Workbook.getWorkbook(new FileInputStream(file_path));
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Sheet sheet = rwb.getSheet(0);
		int rows = sheet.getRows();
		int columns = sheet.getColumns();
		for(int rowsloop=0;rowsloop<rows;rowsloop++){
			Cell cell = sheet.getCell(0, rowsloop);
			Selections selections = new Selections();
			selections.setSelectionname(cell.getContents());
			List<Selection> selectionlist = new ArrayList<Selection>();
			
			for(int columnsloop = 1;columnsloop < columns;columnsloop+=2){
				Cell cell1 = sheet.getCell(columnsloop, rowsloop);
				//判断单元格为空
				if(cell1.getType() == CellType.EMPTY){
					continue;
				}
				else{
					Selection selection = new Selection();
					selection.selectionindex = (columnsloop-1)/2;
					selection.selectionmin = Double.parseDouble(cell1.getContents());
					Cell cell2 = sheet.getCell(columnsloop+1, rowsloop);
					selection.selectionmax = Double.parseDouble(cell2.getContents());
					selectionlist.add(selection);
					
				}
			}
			selections.setSelectionlist(selectionlist);
			selectionslist.add(selections);
			
		}
		
		
	}
}
