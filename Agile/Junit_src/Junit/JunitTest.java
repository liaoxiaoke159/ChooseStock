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
	 * 测试把当前条件保存到excel表里
	 */
	@Test
	public void saveToexceltest() {
		
		//实例化一个selections
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
		 selections.setSelectionname("测试方案名");
		 
		 //调用方法
		 try {
			selections.saveToexcel("C:\\Users\\Administrator\\workspace\\Agile\\data\\测试写方案.xls");
		} catch (BiffException | WriteException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 Workbook rwb = null;
			try {
				rwb = Workbook.getWorkbook(new FileInputStream("C:\\Users\\Administrator\\workspace\\Agile\\data\\测试写方案.xls"));
			} catch (BiffException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Sheet sheet = rwb.getSheet(0);
			int rows = sheet.getRows();
			
			Cell cell = sheet.getCell(0, rows-1);
			String nameexpected = cell.getContents();//期望值
			String nametruevalue = selections.getSelectionname();//真实值
			assertEquals(nameexpected, nametruevalue);//断言方法
			
			for(int listloop=0;listloop<selections.getSelectionlist().size();listloop++){
				Cell cell1 = sheet.getCell( selections.getSelectionlist().get(listloop).selectionindex*2+1,rows-1);
				String expected1 = cell1.getContents();//期望值
				String truevalue1 = Double.toString(selections.getSelectionlist().get(listloop).selectionmin);//真实值
				assertEquals(expected1, truevalue1);//断言方法
				
				Cell cell2 = sheet.getCell( selections.getSelectionlist().get(listloop).selectionindex*2+2,rows-1);
				String expected2 = cell2.getContents();//期望值
				String truevalue2 = Double.toString(selections.getSelectionlist().get(listloop).selectionmax);//真实值
				assertEquals(expected2, truevalue2);//断言方法
			}
		 
	}
	
	/**
	 * 测试把方案excel读到selectionslist中
	 */
	@Test
	public void setSelectionlisttest() {
		
		 List<Selections> selectionslist = new ArrayList<Selections>();
		 String file_path = "C:\\Users\\Administrator\\workspace\\Agile\\data\\测试读方案.xls";

		 //调用方法
		 HistorySelection.setSelectionlist( selectionslist, file_path);
		 
		
		 
		 for(int listloop = 0;listloop<selectionslist.size();listloop++){
			 String expected = "测试方案名";//期望值
			 String truevalue = selectionslist.get(listloop).getSelectionname();//真实值
			 assertEquals(expected, truevalue);//断言方法
			 
		 }
		 
		 String expected1 = Double.toString(0);//期望值
		 String truevalue1 = Double.toString(selectionslist.get(0).getSelectionlist().get(0).selectionmin);//真实值
		 assertEquals(expected1, truevalue1);//断言方法
		 
		 String expected2 = Double.toString(1);//期望值
		 String truevalue2 = Double.toString(selectionslist.get(0).getSelectionlist().get(0).selectionmax);//真实值
		 assertEquals(expected2, truevalue2);//断言方法
		 
		 String expected3 = Double.toString(1);//期望值
		 String truevalue3 = Double.toString(selectionslist.get(1).getSelectionlist().get(0).selectionmin);//真实值
		 assertEquals(expected3, truevalue3);//断言方法
		 
		 String expected4 = Double.toString(2);//期望值
		 String truevalue4 = Double.toString(selectionslist.get(1).getSelectionlist().get(0).selectionmax);//真实值
		 assertEquals(expected4, truevalue4);//断言方法
		 
		 String expected5 = Double.toString(2);//期望值
		 String truevalue5 = Double.toString(selectionslist.get(1).getSelectionlist().get(1).selectionmin);//真实值
		 assertEquals(expected5, truevalue5);//断言方法
		 
		 String expected6 = Double.toString(3);//期望值
		 String truevalue6 = Double.toString(selectionslist.get(1).getSelectionlist().get(1).selectionmax);//真实值
		 assertEquals(expected6, truevalue6);//断言方法
		 
		 
	}
	
	/**
	 * 测试删除excel中的某一行
	 */
	@Test
	public void decExceltest() {
		String file_path = "C:\\Users\\Administrator\\workspace\\Agile\\data\\测试写方案.xls";
		
		
		
		Workbook rwb = null;
		try {
			rwb = Workbook.getWorkbook(new FileInputStream(file_path));
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Sheet sheet = rwb.getSheet(0);
		int rows = sheet.getRows();
		
		//调用方法
		HistorySelection.decExcel(0,file_path);
		
		Workbook rwb1 = null;
		try {
			rwb1 = Workbook.getWorkbook(new FileInputStream(file_path));
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Sheet sheet1 = rwb1.getSheet(0);
		 String expected = Integer.toString(rows-1);//期望值
		 String truevalue = Integer.toString(sheet1.getRows());//真实值
		 assertEquals(expected, truevalue);//断言方法
	}
}
