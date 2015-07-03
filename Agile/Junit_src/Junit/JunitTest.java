package Junit;


import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import org.junit.Test;

import UI.HistorySelection;
import DataClasss.Selection;
import DataClasss.Selections;


public class JunitTest {

	/**
	 * ���԰ѵ�ǰ�������浽excel����
	 */
	@Test
	public void saveToexceltest() {
		
		//ʵ����һ��selections
		 List<Selection> selectionlist = new ArrayList<Selection>();
		 Selection selection1 = new Selection();
		 selection1.selectionindex = 5;
		 selection1.setSelection(5, 20);
		 selectionlist.add(selection1);
		 
		 Selection selection2 = new Selection();
		 selection2.selectionindex = 3;
		 selection2.setSelection(3, 10);
		 selectionlist.add(selection2);
		 
		 Selections selections = new Selections(selectionlist);
		 selections.setSelectionname("���Է�����");
		 
		 //���÷���
		 try {
			selections.saveToexcel("C:\\Users\\Administrator\\workspace\\Agile\\data\\����д����.xls");
		} catch (BiffException | WriteException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 Workbook rwb = null;
			try {
				rwb = Workbook.getWorkbook(new FileInputStream("C:\\Users\\Administrator\\workspace\\Agile\\data\\����д����.xls"));
			} catch (BiffException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Sheet sheet = rwb.getSheet(0);
			int rows = sheet.getRows();
			
			Cell cell = sheet.getCell(0, rows-1);
			String nameexpected = cell.getContents();//����ֵ
			String nametruevalue = selections.getSelectionname();//��ʵֵ
			assertEquals(nameexpected, nametruevalue);//���Է���
			
			for(int listloop=0;listloop<selections.getSelectionlist().size();listloop++){
				Cell cell1 = sheet.getCell( selections.getSelectionlist().get(listloop).selectionindex*2+1,rows-1);
				String expected1 = cell1.getContents();//����ֵ
				String truevalue1 = Double.toString(selections.getSelectionlist().get(listloop).selectionmin);//��ʵֵ
				assertEquals(expected1, truevalue1);//���Է���
				
				Cell cell2 = sheet.getCell( selections.getSelectionlist().get(listloop).selectionindex*2+2,rows-1);
				String expected2 = cell2.getContents();//����ֵ
				String truevalue2 = Double.toString(selections.getSelectionlist().get(listloop).selectionmax);//��ʵֵ
				assertEquals(expected2, truevalue2);//���Է���
			}
		 
	}
	
	/**
	 * ���԰ѷ���excel����selectionslist��
	 */
	@Test
	public void setSelectionlisttest() {
		
		 List<Selections> selectionslist = new ArrayList<Selections>();
		 String file_path = "C:\\Users\\Administrator\\workspace\\Agile\\data\\���Զ�����.xls";

		 //���÷���
		 HistorySelection.setSelectionlist( selectionslist, file_path);
		 
		
		 
		 for(int listloop = 0;listloop<selectionslist.size();listloop++){
			 String expected = "���Է�����";//����ֵ
			 String truevalue = selectionslist.get(listloop).getSelectionname();//��ʵֵ
			 assertEquals(expected, truevalue);//���Է���
			 
		 }
		 
		 String expected1 = Double.toString(0);//����ֵ
		 String truevalue1 = Double.toString(selectionslist.get(0).getSelectionlist().get(0).selectionmin);//��ʵֵ
		 assertEquals(expected1, truevalue1);//���Է���
		 
		 String expected2 = Double.toString(1);//����ֵ
		 String truevalue2 = Double.toString(selectionslist.get(0).getSelectionlist().get(0).selectionmax);//��ʵֵ
		 assertEquals(expected2, truevalue2);//���Է���
		 
		 String expected3 = Double.toString(1);//����ֵ
		 String truevalue3 = Double.toString(selectionslist.get(1).getSelectionlist().get(0).selectionmin);//��ʵֵ
		 assertEquals(expected3, truevalue3);//���Է���
		 
		 String expected4 = Double.toString(2);//����ֵ
		 String truevalue4 = Double.toString(selectionslist.get(1).getSelectionlist().get(0).selectionmax);//��ʵֵ
		 assertEquals(expected4, truevalue4);//���Է���
		 
		 String expected5 = Double.toString(2);//����ֵ
		 String truevalue5 = Double.toString(selectionslist.get(1).getSelectionlist().get(1).selectionmin);//��ʵֵ
		 assertEquals(expected5, truevalue5);//���Է���
		 
		 String expected6 = Double.toString(3);//����ֵ
		 String truevalue6 = Double.toString(selectionslist.get(1).getSelectionlist().get(1).selectionmax);//��ʵֵ
		 assertEquals(expected6, truevalue6);//���Է���
		 
		 
	}
	
	/**
	 * ����ɾ��excel�е�ĳһ��
	 */
	@Test
	public void decExceltest() {
		String file_path = "C:\\Users\\Administrator\\workspace\\Agile\\data\\����д����.xls";
		
		
		
		Workbook rwb = null;
		try {
			rwb = Workbook.getWorkbook(new FileInputStream(file_path));
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Sheet sheet = rwb.getSheet(0);
		int rows = sheet.getRows();
		
		//���÷���
		HistorySelection.decExcel(0,file_path);
		
		Workbook rwb1 = null;
		try {
			rwb1 = Workbook.getWorkbook(new FileInputStream(file_path));
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Sheet sheet1 = rwb1.getSheet(0);
		 String expected = Integer.toString(rows-1);//����ֵ
		 String truevalue = Integer.toString(sheet1.getRows());//��ʵֵ
		 assertEquals(expected, truevalue);//���Է���
	}
}
