package UI;


import java.io.FileInputStream;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import DataClasss.Selection;
import DataClasss.StockMessage;

import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.widgets.TableColumn;
public class Homepage {

	protected Shell shell;
	private Table table;
	private Group group_selection;
	private  Button[] buttons;
	private Composite[] composites ;
	private Spinner[] spinnermin;
	private Spinner[] spinnermax;
	private Label[] labelnotices;
	private static String[] selectionstr = new String[]{"现价","涨跌幅","市盈率","动态市盈率","市净率"};
	
	private  List<StockMessage> stocklist_xueqiu = new ArrayList<StockMessage>();
	private  List<StockMessage>stocklist_tonghuashun = new ArrayList<StockMessage>();
	private  List<Selection> selectionlist= new ArrayList<Selection>();
	private List<StockMessage> stocklist = new ArrayList<StockMessage>();
	
	Label lbl_stocknumnotice;
	private Button btn_tonghuashun;
	private Button btn_xueqiu;
	protected String path_xueqiu="C:\\Users\\Administrator\\workspace\\Agile\\data\\xueqiudata.xls";
	protected String path_tonghuashun="C:\\Users\\Administrator\\workspace\\Agile\\data\\tonghuashundata.xls";
	
	public static String[] getSelectionstr() {
		return selectionstr;
	}
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Homepage window = new Homepage();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		
		shell.open();
		shell.layout();
		
		lbl_stocknumnotice.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lbl_stocknumnotice.setText("正在获取股票数据.....");
		
		
		SourceEvent( path_tonghuashun, 1);
		SourceEvent(path_xueqiu, 2);
		stocklist = stocklist_tonghuashun;
		
		lbl_stocknumnotice.setText("初始化完成！");
		lbl_stocknumnotice.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		
		
		
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void SourceEvent(String path,int index) {
		// TODO Auto-generated method stub
		
		Workbook wb = null;
		try {
			wb = Workbook.getWorkbook(new FileInputStream(path));
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet sheet = wb.getSheet(0);
		
		for(int i=1; i<sheet.getRows();i++){
			
			String code= sheet.getCell(0, i).getContents();
			String name=sheet.getCell(1, i).getContents();
			String updownrate=sheet.getCell(2, i).getContents();
			String presentprice=sheet.getCell(3, i).getContents();
			String pe=sheet.getCell(4, i).getContents();
			String pridectpe=sheet.getCell(5, i).getContents();
			String pb=sheet.getCell(6, i).getContents();
			String sumnum=sheet.getCell(7, i).getContents();
			String industry=sheet.getCell(8, i).getContents();
			
			StockMessage sm = new StockMessage(code, name, updownrate, presentprice, pe, pridectpe, pb, sumnum, industry);
			if(index==1){
				stocklist_tonghuashun.add(sm);
			}else if(index==2){
				stocklist_xueqiu.add(sm);
			}
			
		}
		
	}
	

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage("C:\\Users\\Administrator\\workspace\\Agile\\data\\logo.png"));
		shell.setSize(956, 527);
		shell.setText("\u9009\u80A1\u795E\u5668");
		
		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setBounds(255, 24, 128, 80);
		lblNewLabel_3.setImage(SWTResourceManager.getImage("C:\\Users\\Administrator\\workspace\\Agile\\data\\\u718A\u732B.png"));
		
		
		//菜单栏
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmNewItem = new MenuItem(menu, SWT.CASCADE);
		mntmNewItem.setText("方案");
		
		Menu menu_1 = new Menu(mntmNewItem);
		mntmNewItem.setMenu(menu_1);
		
		MenuItem mntmNewItem_2 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectionscope();//初始化每个条件范围
				SaveSelection window = new SaveSelection(selectionlist);
				window.open();
			}
		});
		mntmNewItem_2.setText("保存当前方案");
		
		MenuItem mntmNewItem_1 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HistorySelection window = new HistorySelection();
				List<Selection> selectionlisttemp = window.open();
				if(selectionlisttemp!=null){
					resetEvent();//重置方案
					selectionlist = selectionlisttemp;
					resetComposite();//重新排列方案
				}
			}
		});
		mntmNewItem_1.setText("选择历史方案");
		
		MenuItem mntmNewItem_3 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		mntmNewItem_3.setText("退出");
		
		
		//搜索按钮
		Button button_research = new Button(shell, SWT.NONE);
		button_research.setBounds(389, 284, 54, 27);
		button_research.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				 selectionscope();
				 startSearch();
				 
			}
		});
		
		
		button_research.setText("开始搜索");
		Button button_reset = new Button(shell, SWT.NONE);
		button_reset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				resetEvent();
				
			}
		});
		
		//重置按钮
		button_reset.setBounds(389, 186, 54, 27);
		button_reset.setText("重置");
		
		Label lblNewLabel_5 = new Label(shell, SWT.NONE);
		lblNewLabel_5.setText("\u300B");
		lblNewLabel_5.setBounds(447, 289, 12, 17);
		
		
		Group group = new Group(shell, SWT.NONE);
		group.setText("条件选择");
		group.setBounds(10, 10, 236, 94);
		
		Button btn_presentprice = new Button(group, SWT.CHECK);
		btn_presentprice.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btn_event(btn_presentprice.getSelection(),composites[0],0);
			}
		});
	
		
		
		
		btn_presentprice.setBounds(10, 21, 98, 17);
		btn_presentprice.setText("现价");
		
		Button btn_updownrate = new Button(group, SWT.CHECK);
		btn_updownrate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btn_event(btn_updownrate.getSelection(),composites[1],1);
			}
		});
		btn_updownrate.setText("涨跌幅");
		btn_updownrate.setBounds(128, 21, 98, 17);
		
		Button btn_pe = new Button(group, SWT.CHECK);
		btn_pe.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btn_event(btn_pe.getSelection(),composites[2],2);
			}
		});
		btn_pe.setBounds(10, 44, 98, 17);
		btn_pe.setText("(静态)市盈率");
		
		Button btn_predictpe = new Button(group, SWT.CHECK);
		btn_predictpe.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btn_event(btn_predictpe.getSelection(),composites[3],3);
			}
		});
		btn_predictpe.setBounds(128, 44, 98, 17);
		btn_predictpe.setText("动态市盈率");
		
		Button btn_pb = new Button(group, SWT.CHECK);
		btn_pb.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btn_event(btn_pb.getSelection(),composites[4],4);
			}
		});
		btn_pb.setBounds(10, 67, 98, 17);
		btn_pb.setText("市净率");
		
		
		Group group_2 = new Group(shell, SWT.NONE);
		group_2.setText("搜索结果");
		group_2.setBounds(465, 32, 462, 402);
		
		table = new Table(group_2, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 20, 443, 372);
		table.setHeaderVisible(true);
		table.setLinesVisible(false);
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		addStringSorter(table,tblclmnNewColumn);
		tblclmnNewColumn.setWidth(81);
		tblclmnNewColumn.setText("股票代码");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		addStringSorter(table,tblclmnNewColumn_1);
		tblclmnNewColumn_1.setWidth(76);
		tblclmnNewColumn_1.setText("股票简称");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		addNumberSorter(table,tblclmnNewColumn_2);
		tblclmnNewColumn_2.setWidth(62);
		tblclmnNewColumn_2.setText("涨跌幅");
		
		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		addNumberSorter(table,tblclmnNewColumn_3);
		tblclmnNewColumn_3.setWidth(64);
		tblclmnNewColumn_3.setText("现价");
		
		TableColumn tblclmnNewColumn_4 = new TableColumn(table, SWT.NONE);
		addNumberSorter(table,tblclmnNewColumn_4);
		tblclmnNewColumn_4.setWidth(77);
		tblclmnNewColumn_4.setText("市盈率");
		
		TableColumn tblclmnNewColumn_8 = new TableColumn(table, SWT.NONE);
		addNumberSorter(table,tblclmnNewColumn_8);
		tblclmnNewColumn_8.setWidth(80);
		tblclmnNewColumn_8.setText("预测市盈率");
		
		TableColumn tblclmnNewColumn_7 = new TableColumn(table, SWT.NONE);
		addNumberSorter(table,tblclmnNewColumn_7);
		tblclmnNewColumn_7.setWidth(67);
		tblclmnNewColumn_7.setText("市净率");
		
		TableColumn tblclmnNewColumn_6 = new TableColumn(table, SWT.NONE);
		addStringSorter(table,tblclmnNewColumn_6);
		tblclmnNewColumn_6.setWidth(53);
		tblclmnNewColumn_6.setText("总股本");
		
		TableColumn tblclmnNewColumn_5 = new TableColumn(table, SWT.NONE);
		addStringSorter(table,tblclmnNewColumn_5);
		tblclmnNewColumn_5.setWidth(150);
		tblclmnNewColumn_5.setText("所属行业");
		
		btn_xueqiu = new Button(shell, SWT.RADIO);
		btn_xueqiu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btn_xueqiu.getSelection()){
					
					if(btn_tonghuashun.getSelection()) 
							btn_tonghuashun.setSelection(false);
					
					stocklist = stocklist_xueqiu;
				}
				
			}
		});
		btn_xueqiu.setBounds(607, 440, 57, 17);
		btn_xueqiu.setText("雪球网");
		
		
		btn_tonghuashun = new Button(shell, SWT.RADIO);
		btn_tonghuashun.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			
				if(btn_tonghuashun.getSelection()){
					
					if(btn_xueqiu.getSelection()) 
							btn_xueqiu.setSelection(false);
					stocklist = stocklist_tonghuashun;
				}
			}
			
		});
		btn_tonghuashun.setBounds(532, 440, 69, 17);
		btn_tonghuashun.setText("同花顺网");
		btn_tonghuashun.setSelection(true);
		
		Label lbl_source = new Label(shell, SWT.NONE);
		lbl_source.setBounds(475, 439, 48, 17);
		lbl_source.setText("数据源：");
		
		/**
		 * 条件筛选************************
		 */
		group_selection = new Group(shell, SWT.NONE);
		group_selection.setText("分组框");
		group_selection.setBounds(11, 123, 372, 311);
		
		Composite composite_selection = new Composite(group_selection, SWT.NONE);
		composite_selection.setBounds(10, 21, 352, 17);
		
		Label label = new Label(composite_selection, SWT.NONE);
		label.setBounds(10, 0, 24, 17);
		label.setText("条件");
		
		Label lblNewLabel = new Label(composite_selection, SWT.NONE);
		lblNewLabel.setBounds(91, 0, 36, 17);
		lblNewLabel.setText("最小值");
		
		Label lblNewLabel_1 = new Label(composite_selection, SWT.NONE);
		lblNewLabel_1.setBounds(170, 0, 36, 17);
		lblNewLabel_1.setText("最大值");
		
		Label lblNewLabel_2 = new Label(composite_selection, SWT.NONE);
		lblNewLabel_2.setBounds(298, 0, 24, 17);
		lblNewLabel_2.setText("操作");
		
		
		//现价
		
		
		
		Composite composite_presentprice = new Composite(group_selection, SWT.NONE);
		Spinner spinnermin_presentprice = new Spinner(composite_presentprice, SWT.BORDER);
		spinnermin_presentprice.setMinimum(0);
		spinnermin_presentprice.setMaximum(50000);
		spinnermin_presentprice.setSelection(0);
		spinnermin_presentprice.setIncrement(100);
		spinnermin_presentprice.setDigits(2);
		spinnermin_presentprice.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				spinnerevnt(0);
			}
		});
		
		spinnermin_presentprice.setBounds(82, 7, 60, 23);
		
		Spinner spinnermax_presentprice = new Spinner(composite_presentprice, SWT.BORDER);
		spinnermax_presentprice.setMinimum(0);
		spinnermax_presentprice.setMaximum(50000);
		spinnermax_presentprice.setSelection(0);
		spinnermax_presentprice.setIncrement(100);
		spinnermax_presentprice.setDigits(2);
		spinnermax_presentprice.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				spinnerevnt(0);
			}
		});
		spinnermax_presentprice.setBounds(162, 7, 60, 23);
		
		Label lblNewLabel_4 = new Label(composite_presentprice, SWT.NONE);
		lblNewLabel_4.setBounds(10, 10, 24, 17);
		lblNewLabel_4.setText("现价");
		
		Label lbldec_presentprice = new Label(composite_presentprice, SWT.NONE);
		lbldec_presentprice.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				lbldec_presentprice.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			}
			@Override
			public void mouseExit(MouseEvent e) {
				lbldec_presentprice.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			}
		});
		
		
		lbldec_presentprice.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				btn_presentprice.setSelection(false);
				deleteselection(composite_presentprice,0);
			}
		});
		
		lbldec_presentprice.setBounds(301, 10, 24, 17);
		lbldec_presentprice.setText("删除");
		
		Label lblnotice_presentprice = new Label(composite_presentprice, SWT.NONE);
		lblnotice_presentprice.setBounds(228, 10, 66, 17);
		
		
		
		
	
		
		//涨跌幅
		Composite composite_updownrate = new Composite(group_selection, SWT.NONE);
		
		Spinner spinnermin_updownrate = new Spinner(composite_updownrate, SWT.BORDER);
		spinnermin_updownrate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				spinnerevnt(1);
			}
		});
		spinnermin_updownrate.setMaximum(1000);
		spinnermin_updownrate.setMinimum(-1000);
		spinnermin_updownrate.setSelection(-1000);
		spinnermin_updownrate.setIncrement(100);
		spinnermin_updownrate.setDigits(2);
		spinnermin_updownrate.setBounds(83, 4, 61, 23);
		
		Spinner spinnermax_updownrate = new Spinner(composite_updownrate, SWT.BORDER);
		spinnermax_updownrate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				spinnerevnt(1);
			}
		});
		spinnermax_updownrate.setMaximum(1000);
		spinnermax_updownrate.setMinimum(-1000);
		spinnermax_updownrate.setSelection(1000);
		spinnermax_updownrate.setIncrement(100);
		spinnermax_updownrate.setDigits(2);
		spinnermax_updownrate.setBounds(161, 4, 61, 23);
		
		Label label_1 = new Label(composite_updownrate, SWT.NONE);
		label_1.setText("涨跌幅(%)");
		label_1.setBounds(10, 10, 61, 17);
		
		Label labeldec_updownrate = new Label(composite_updownrate, SWT.NONE);
		labeldec_updownrate.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				labeldec_updownrate.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			}
			@Override
			public void mouseExit(MouseEvent e) {
				labeldec_updownrate.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			}
		});
		labeldec_updownrate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				btn_updownrate.setSelection(false);
				deleteselection(composite_updownrate,1);
			}
		});
		labeldec_updownrate.setText("删除");
		labeldec_updownrate.setBounds(302, 7, 24, 17);
		
		Label label_updownratenotice = new Label(composite_updownrate, SWT.NONE);
		label_updownratenotice.setBounds(228, 7, 66, 17);
		
		
		
		//市盈率
		Composite composite_pe = new Composite(group_selection, SWT.NONE);
		
		Spinner spinnermin_pe = new Spinner(composite_pe, SWT.BORDER);
		spinnermin_pe.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				spinnerevnt(2);
			}
		});
		spinnermin_pe.setMaximum(50000000);
		spinnermin_pe.setMinimum(0);
		spinnermin_pe.setSelection(0);
		spinnermin_pe.setIncrement(100);
		spinnermin_pe.setDigits(2);
		spinnermin_pe.setBounds(66, 7, 80, 23);
		
		Spinner spinnermax_pe = new Spinner(composite_pe, SWT.BORDER);
		spinnermax_pe.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				spinnerevnt(2);
			}
		});
		spinnermax_pe.setMaximum(50000);
		spinnermax_pe.setMinimum(-200000);
		spinnermax_pe.setSelection(0);
		spinnermax_pe.setIncrement(100);
		spinnermax_pe.setDigits(2);
		spinnermax_pe.setBounds(147, 7, 75, 23);
		
		Label label_3 = new Label(composite_pe, SWT.NONE);
		label_3.setText("市盈率");
		label_3.setBounds(10, 10, 36, 17);
		
		Label labeldec_pe = new Label(composite_pe, SWT.NONE);
		labeldec_pe.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				labeldec_pe.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			}
			@Override
			public void mouseExit(MouseEvent e) {
				labeldec_pe.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			}
		});
		labeldec_pe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				btn_pe.setSelection(false);
				deleteselection(composite_pe,2);
			}
		});
		labeldec_pe.setText("删除");
		labeldec_pe.setBounds(300, 10, 24, 17);
		
		Label label_penotice = new Label(composite_pe, SWT.NONE);
		label_penotice.setBounds(228, 10, 66, 17);
		
		Composite composite_predictpe = new Composite(group_selection, SWT.NONE);
		
		Spinner spinnermin_predictpe = new Spinner(composite_predictpe, SWT.BORDER);
		spinnermin_predictpe.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				spinnerevnt(3);
			}
		});
		spinnermin_predictpe.setMaximum(1000000);
		spinnermin_predictpe.setMinimum(-200000);
		spinnermin_predictpe.setSelection(0);
		spinnermin_predictpe.setIncrement(100);
		spinnermin_predictpe.setDigits(2);
		spinnermin_predictpe.setBounds(76, 7, 70, 23);
		
		Spinner spinnermax_predictpe = new Spinner(composite_predictpe, SWT.BORDER);
		spinnermax_predictpe.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				spinnerevnt(3);
			}
		});
		spinnermax_predictpe.setMaximum(1000000);
		spinnermax_predictpe.setMinimum(-100000);
		spinnermax_predictpe.setSelection(50000);
		spinnermax_predictpe.setIncrement(100);
		spinnermax_predictpe.setDigits(2);
		spinnermax_predictpe.setBounds(152, 7, 70, 23);
		
		Label label_2 = new Label(composite_predictpe, SWT.NONE);
		label_2.setText("动态市盈率");
		label_2.setBounds(10, 10, 60, 17);
		
		Label labeldec_predictpe = new Label(composite_predictpe, SWT.NONE);
		labeldec_predictpe.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				labeldec_predictpe.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			}
			@Override
			public void mouseExit(MouseEvent e) {
				labeldec_predictpe.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			}
		});
		labeldec_predictpe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				btn_predictpe.setSelection(false);
				deleteselection(composite_predictpe,3);
			}
		});
		labeldec_predictpe.setText("删除");
		labeldec_predictpe.setBounds(299, 10, 24, 17);
		
		Label label_predictpenotice = new Label(composite_predictpe, SWT.NONE);
		label_predictpenotice.setBounds(228, 10, 66, 17);
		
		Composite composite_pb = new Composite(group_selection, SWT.NONE);
		
		Spinner spinnermin_pb = new Spinner(composite_pb, SWT.BORDER);
		spinnermin_pb.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				spinnerevnt(4);
			}
		});
		spinnermin_pb.setMaximum(2000000);
		spinnermin_pb.setMinimum(-50000);
		spinnermin_pb.setSelection(0);
		spinnermin_pb.setIncrement(100);
		spinnermin_pb.setDigits(2);
		spinnermin_pb.setBounds(76, 7, 70, 23);
		
		Spinner spinnermax_pb = new Spinner(composite_pb, SWT.BORDER);
		spinnermax_pb.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				spinnerevnt(4);
			}
		});
		spinnermax_pb.setMaximum(2000000);
		spinnermax_pb.setMinimum(-50000);
		spinnermax_pb.setSelection(20000);
		spinnermax_pb.setIncrement(100);
		spinnermax_pb.setDigits(2);
		spinnermax_pb.setBounds(152, 7, 70, 23);
		
		Label label_4 = new Label(composite_pb, SWT.NONE);
		label_4.setText("市净率");
		label_4.setBounds(10, 10, 36, 17);
		
		Label labeldec_pb = new Label(composite_pb, SWT.NONE);
		labeldec_pb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				btn_pb.setSelection(false);
				deleteselection(composite_pb,4);
			}
		});
		labeldec_pb.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				labeldec_pb.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			}
			@Override
			public void mouseExit(MouseEvent e) {
				labeldec_pb.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			}
		});
		labeldec_pb.setText("\u5220\u9664");
		labeldec_pb.setBounds(299, 10, 24, 17);
		
		Label label_pbnotice = new Label(composite_pb, SWT.NONE);
		label_pbnotice.setBounds(228, 10, 66, 17);
		
		
		 lbl_stocknumnotice = new Label(shell, SWT.BORDER);
		lbl_stocknumnotice.setBounds(771, 440, 156, 17);
		
		
		
		
		
		
		buttons = new Button[]{btn_presentprice,btn_updownrate,btn_pe,btn_predictpe,btn_pb};
		composites = new Composite[] { composite_presentprice,composite_updownrate,composite_pe,composite_predictpe,composite_pb };
		spinnermin = new Spinner[] { spinnermin_presentprice,spinnermin_updownrate,spinnermin_pe,spinnermin_predictpe,spinnermin_pb };
		spinnermax = new Spinner[] { spinnermax_presentprice ,spinnermax_updownrate,spinnermax_pe ,spinnermax_predictpe,spinnermax_pb};
		labelnotices = new Label[]{lblnotice_presentprice,label_updownratenotice,label_penotice,label_predictpenotice,label_pbnotice};
		
	}
	

	private void addNumberSorter(final Table table,
			final TableColumn column) {
		// TODO Auto-generated method stub
		column.addSelectionListener(new SelectionAdapter() {
			boolean isAscend = true; // 按照升序排序
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				lbl_stocknumnotice.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
				lbl_stocknumnotice.setText("正在对"+column.getText()+"进行排序.....");
				
				int columnIndex = getColumnIndex(table, column);
				TableItem[] items = table.getItems();
				// 使用冒泡法进行排序
				for (int i = 1; i < items.length; i++) {
					String strvalue2 = items[i].getText(columnIndex);
					if (strvalue2.equalsIgnoreCase("--")) {
						// 当遇到表格中的空项目时，就停止往下检索排序项目
						continue;
					}
					for (int j = 0; j < i; j++) {
						String strvalue1 = items[j].getText(columnIndex);
						// 将字符串类型数据转化为float类型
						if (strvalue1.equalsIgnoreCase("--")) {
							// 当遇到表格中的空项目时，就停止往下检索排序项目
							continue;
						}
						float numbervalue1 = Float.valueOf(strvalue1);
						float numbervalue2 = Float.valueOf(strvalue2);
						boolean isLessThan = false;
						if (numbervalue2 < numbervalue1) {
							isLessThan = true;
						}
						if ((isAscend && isLessThan)
								|| (!isAscend && !isLessThan)) {
							String[] values = getTableItemText(table, items[i]);
							Object obj = items[i].getData();
							items[i].dispose();
							TableItem item = new TableItem(table, SWT.NONE, j);
							item.setText(values);
							item.setData(obj);
							items = table.getItems();
							break;
						}
					}
				}
				table.setSortColumn(column);
				table.setSortDirection((isAscend ? SWT.UP : SWT.DOWN));
				isAscend = !isAscend;
				
				
				
				
				
				lbl_stocknumnotice.setText(column.getText()+"排序完成!");
				lbl_stocknumnotice.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
				
			}
			
			
		});
	}
	
	public  void addStringSorter(final Table table,
			final TableColumn column) {
		column.addSelectionListener(new SelectionAdapter() {
			
			
			boolean isAscend = true; // 按照升序排序
			Collator comparator = Collator.getInstance(Locale.getDefault());

			public void widgetSelected(SelectionEvent e) {
				
				lbl_stocknumnotice.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
				lbl_stocknumnotice.setText("正在对"+column.getText()+"进行排序.....");
				
				int columnIndex = getColumnIndex(table, column);
				TableItem[] items = table.getItems();
				// 使用冒泡法进行排序
				for (int i = 1; i < items.length; i++) {
					String str2value = items[i].getText(columnIndex);
					if (str2value.equalsIgnoreCase("--")) {
						// 当遇到表格中的空项目时，就停止往下检索排序项目
						break;
					}
					for (int j = 0; j < i; j++) {
						String str1value = items[j].getText(columnIndex);
						boolean isLessThan = comparator.compare(str2value,
								str1value) < 0;
						if ((isAscend && isLessThan)
								|| (!isAscend && !isLessThan)) {
							String[] values = getTableItemText(table, items[i]);
							Object obj = items[i].getData();
							items[i].dispose();
							TableItem item = new TableItem(table, SWT.NONE, j);
							item.setText(values);
							item.setData(obj);
							items = table.getItems();
							break;
						}
					}
				}
				table.setSortColumn(column);
				table.setSortDirection((isAscend ? SWT.UP : SWT.DOWN));
				isAscend = !isAscend;
				
				lbl_stocknumnotice.setText(column.getText()+"排序完成！");
				lbl_stocknumnotice.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
			}
		});
	}
	 public static int getColumnIndex(Table table, TableColumn column) {  
	        TableColumn[] columns = table.getColumns();  
	        for (int i = 0; i < columns.length; i++) {  
	            if (columns[i].equals(column))  
	                return i;  
	        }  
	        return -1;  
	    }  
	  
	    public static String[] getTableItemText(Table table, TableItem item) {  
	        int count = table.getColumnCount();  
	        String[] strs = new String[count];  
	        for (int i = 0; i < count; i++) {  
	            strs[i] = item.getText(i);  
	        }  
	        return strs;  
	    }  
	    
	//按条件筛选并显示在table上
	protected void startSearch() {
		// TODO Auto-generated method stub
		
		//如果范围不正确则提示用户范围不正确
		for(int listloop = 0;listloop <selectionlist.size();listloop++){
			if(!spinnerevnt(selectionlist.get(listloop).selectionindex))
				return;
		}
		
		
		table.removeAll();//擦除原先table表里面的内容
		
		for(int stockloop = 0 ;stockloop<stocklist.size(); ++stockloop){
			
			int TAG = 1; //标记是否符合所有条件  1为符合 - 1为不符合
			for(int SLloop = 0; SLloop<selectionlist.size();++SLloop){
				
				//现价
				if (selectionlist.get(SLloop).selectionindex == 0) {
				TAG = judgeSelection(stocklist.get(stockloop).presentprice,SLloop);
				}
				
				//涨幅
				if (selectionlist.get(SLloop).selectionindex == 1) {
					TAG = judgeSelection(stocklist.get(stockloop).updownrate,SLloop);
					}
				
				//市盈率
				if (selectionlist.get(SLloop).selectionindex == 2) {
				TAG = judgeSelection(stocklist.get(stockloop).pe,SLloop);
				}
				
				//动态市盈率
				if (selectionlist.get(SLloop).selectionindex == 3) {
					TAG = judgeSelection(stocklist.get(stockloop).pridectpe,SLloop);
					}
				
				//市净率
				if (selectionlist.get(SLloop).selectionindex == 4) {
					TAG = judgeSelection(stocklist.get(stockloop).pb,SLloop);
					}
				
				
				
			}
			
			if(TAG == -1){
				continue;
			}
			else{
				String[] tablestr = new String[]{stocklist.get(stockloop).code,stocklist.get(stockloop).name
						,stocklist.get(stockloop).updownrate,stocklist.get(stockloop).presentprice,stocklist.get(stockloop).pe,stocklist.get(stockloop).pridectpe
						,stocklist.get(stockloop).pb,stocklist.get(stockloop).sumnum,stocklist.get(stockloop).industry};
				TableItem tableitem = new TableItem(table,SWT.NONE);
				tableitem.setText(tablestr);
				
			}
		}
		
		lbl_stocknumnotice.setText( "总共搜索出"+table.getItemCount()+"只股票");
		lbl_stocknumnotice.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		
	}
	private int judgeSelection(String data, int SLloop) {
		// TODO Auto-generated method stub
		
			if (data.equals("--")) {
				return -1;
			} else if(Double.parseDouble(data) < selectionlist.get(SLloop).selectionmin
					||Double.parseDouble(data) > selectionlist.get(SLloop).selectionmax ) {

				return -1;
			}
		
		return 1;
	}
	
	
	
	//重新排列方案
	protected void resetComposite() {
		// TODO Auto-generated method stub
		
		//排列composite
		selectionlayout();
		
		//标记button并且给每个条件的范围初始化
		for(int listloop=0;listloop<selectionlist.size();listloop++){
			int index = selectionlist.get(listloop).selectionindex;
			buttons[index].setSelection(true);
			spinnermin[index].setSelection((int)selectionlist.get(listloop).selectionmin*100);
			spinnermax[index].setSelection((int)selectionlist.get(listloop).selectionmax*100);
			
		}
		
	}
	
	//重置方案
	protected void resetEvent() {
		// TODO Auto-generated method stub
		
		table.removeAll();
		
		for(int listloop = 0 ; listloop < selectionlist.size();listloop++){
			int index = selectionlist.get(listloop).selectionindex;
			buttons[index].setSelection(false);
			composites[index].setVisible(false);
			composites[index].setBounds(10, 309, 337, 36);
		}
		selectionlist.removeAll(selectionlist);
		
	}
	
	//判断条件范围是否正确
	//返回true表示范围正确 false表示范围错误 
	protected boolean spinnerevnt(int index) {
		// TODO Auto-generated method stub
		double min = spinnermin[index].getSelection();
		double max = spinnermax[index].getSelection();
		if(min>=max){
			labelnotices[index].setText("*范围不正确");
			labelnotices[index].setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			return false;
		}
		else{
			labelnotices[index].setText("");
			labelnotices[index].setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
			return true;
		}
	}
	
	//初始化每个条件范围
	protected void selectionscope() {
		// TODO Auto-generated method stub
		for(int listloop= 0 ;listloop<selectionlist.size();listloop++){
			double min = spinnermin[selectionlist.get(listloop).selectionindex].getSelection()/100;
			double max = spinnermax[selectionlist.get(listloop).selectionindex].getSelection()/100;
			selectionlist.get(listloop).setSelection(min, max);
		}
		
	}
	
	//按钮事件
	protected void btn_event(boolean btn_getselection, Composite composite, int selectioncomindex) {
		// TODO Auto-generated method stub
		if(btn_getselection){
			Selection selection = new Selection();
			selection.selectionindex = selectioncomindex;
			selectionlist.add(selection);
			selectionlayout();
			composite.setVisible(true);
		}
			
		else{
			
			deleteselection(composite,selectioncomindex);
			
			
		}
	}

	private void deleteselection(Composite composite, int selectioncomindex) {
		// TODO Auto-generated method stub
		for(int indexloop=0; indexloop < selectionlist.size();indexloop++){
			if(selectionlist.get(indexloop).selectionindex==selectioncomindex)
				selectionlist.remove(indexloop);
		}
		
		composite.setVisible(false);
		composite.setBounds(10, 309, 337, 36);
	   
		selectionlayout();
	}

	protected void selectionlayout() {
		// TODO Auto-generated method stub
		
		for(int compositeloop=0;compositeloop < selectionlist.size();compositeloop++){
			
			
			composites[selectionlist.get(compositeloop).selectionindex].setBounds(10, 46+compositeloop*42, 337, 36);
			composites[selectionlist.get(compositeloop).selectionindex].setVisible(true);
		}
		
	}
}
