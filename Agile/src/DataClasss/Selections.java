package DataClasss;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import UI.Homepage;

public class Selections {
	private String selectionname;
	private List<Selection> selectionlist = new ArrayList<Selection>();

	
	public  Selections(List<Selection> selectionlist){
		this.selectionlist = selectionlist;
	}
	public  Selections(){
	}
	public void setSelectionlist(List<Selection> selectionlist) {
		this.selectionlist = selectionlist;
	}
	public List<Selection> getSelectionlist() {
		return selectionlist;
	}

	public String getSelectionname() {
		return selectionname;
	}

	public void setSelectionname(String selectionname) {
		this.selectionname = selectionname;
	}

	
	
	
	//�ѵ�ǰ����д����ǰ������table������
	public void selectiontable_nitialize(Table table) {
		
		//��ͷ
		String[]  tablestr0 = new String[]{"����","��Сֵ","���ֵ"};
		TableItem tableitem0 = new TableItem(table,SWT.NONE);
		tableitem0.setText(tablestr0);
		
		for(int listloop = 0 ; listloop < selectionlist.size();listloop++){
			
			
		String[] selectionstr = Homepage.getSelectionstr();
		String[] tablestr = new String[]{selectionstr[selectionlist.get(listloop).selectionindex],
				Double.toString(selectionlist.get(listloop).selectionmin),Double.toString(selectionlist.get(listloop).selectionmax)};
			
		TableItem tableitem = new TableItem(table,SWT.NONE);
		tableitem.setText(tablestr);
		}
		
	}
	
	//�ѵ�ǰ�������浽excel����
	//����һ�������� 1��ʾ����¹� -1��ʾexcle���Ѿ��޷��������淽��
	public int saveToexcel(String file_path) throws BiffException, FileNotFoundException, IOException, WriteException {
		// TODO Auto-generated method stub
		Workbook workBook = Workbook.getWorkbook(new FileInputStream(file_path));
		Sheet sheet1 = workBook.getSheet(0);
		if(sheet1.getRows()>5){
			return -1;
		}
		WritableWorkbook writeBook = Workbook.createWorkbook(new File(file_path),workBook);
		WritableSheet sheet = writeBook.getSheet(0);
		
		int rows = sheet.getRows();
		
		Label label = new Label(0,rows,this.selectionname);
		sheet.addCell(label);
		
		for(int listloop=0; listloop<this.selectionlist.size();listloop++){
			Label label1 = new Label(this.selectionlist.get(listloop).selectionindex*2+1,rows,
					Double.toString(this.selectionlist.get(listloop).selectionmin));
			sheet.addCell(label1);
			Label label2 = new Label(this.selectionlist.get(listloop).selectionindex*2+2,rows,
					Double.toString(this.selectionlist.get(listloop).selectionmax));
			sheet.addCell(label2);
			
			
		}
		
	    writeBook.write();
		writeBook.close();
		
		return 1;
		
	}
	
	
	
}
