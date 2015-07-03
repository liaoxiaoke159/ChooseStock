package UI;

import java.io.IOException;
import java.util.List;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;

import DataClasss.Selection;
import DataClasss.Selections;

public class SaveSelection {

	protected Shell shell;
	private Table table;
	private Text text_seletionname;
	private Selections selections;
	private String file_path = "C:\\Users\\Administrator\\workspace\\Agile\\data\\����.xls";

	public SaveSelection(List<Selection> selectionlist){
		selections = new Selections(selectionlist);
	}
	
	/**
	 * Launch the application.
	 * @param args
	 */
//	public static void main(String[] args) {
//		try {
//			SaveSelection window = new SaveSelection();
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage("C:\\Users\\Administrator\\workspace\\Agile\\data\\logo.png"));
		shell.setSize(323, 228);
		shell.setText("���浱ǰ����");
		
		
		
		table = new Table(shell,  SWT.FULL_SELECTION);
		table.setBounds(10, 62, 231, 107);
		table.setHeaderVisible(false);
		table.setLinesVisible(false);
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(89);
		tblclmnNewColumn.setText("New Column");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(76);
		tblclmnNewColumn_2.setText("New Column");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(65);
		tblclmnNewColumn_1.setText("New Column");
		
		Label lbl_notice = new Label(shell, SWT.NONE);
		
		lbl_notice.setBounds(168, 24, 73, 17);
		
		text_seletionname = new Text(shell, SWT.BORDER);
		text_seletionname.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
					if(text_seletionname.getText().isEmpty()){
					lbl_notice.setText("*���뷽����");
					lbl_notice.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
				}
				else{
					selections.setSelectionname(text_seletionname.getText());
					lbl_notice.setText("��������");
					lbl_notice.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
					}
			}
			
		});
		text_seletionname.setBounds(64, 21, 98, 23);
		
		
		
		Button btn_save = new Button(shell, SWT.NONE);
		btn_save.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				
				int PAG = 1;//PAG<��ʾ������������6����������ʷ������ʾ�û�ɾ������
				try {
					  PAG = selections.saveToexcel(file_path);
				} catch (BiffException | WriteException | IOException e1) {
					
					e1.printStackTrace();
				}
				
				if(PAG<0){
					MessageBox messagebox = new MessageBox(shell, SWT.YES | SWT.NO);
					messagebox.setText("��ʼ��");
						messagebox.setMessage("       �û�����ķ�������6��,�Ƿ�ǰȥɾ�����෽����");
						int val = messagebox.open();
					if (val == SWT.YES){
					HistorySelection HS = new HistorySelection();
					HS.open();}
				}
				else{
					shell.dispose();
				}
				
				
				
			}
		});
		btn_save.setBounds(247, 19, 54, 27);
		btn_save.setText("����");
		
		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setBounds(10, 24, 48, 17);
		lblNewLabel_3.setText("��������");
		
		Button btn_ = new Button(shell, SWT.NONE);
		btn_.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btn_.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shell.dispose();
			}
		});
		btn_.setBounds(247, 52, 54, 27);
		btn_.setText("ȡ��");
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 47, 231, 9);
		

		selections.selectiontable_nitialize(table);
	}
}
