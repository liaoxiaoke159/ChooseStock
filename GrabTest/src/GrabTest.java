



import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;


public class GrabTest {

	/**
	 * 
	 * ����ץȡѩ��������
	 * �����ڱ���excel
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws BiffException 
	 * @throws WriteException 
	 * 
	 * ע�⣺�����400���� ��Ҫ����cookie
	 */
	@Test
	public void Grab_Xueqiutest() throws BiffException, FileNotFoundException, IOException, WriteException {
		String cookie = 
				"s=yul12oxdgd; bid=4651608d7962f8e8bb2226bdd0ad82ae_iauqq3vj; xq_a_token=fdabf485a3ff707805388f2ae73f05b23f19696f; xq_r_token=7830f5bb373434a08337b6a2914c6ef5ea02b83b; route=; __utmt=1; __utma=1.796900497.1434168049.1434543069.1434896841.8; __utmb=1.1.10.1434896841; __utmc=1; __utmz=1.1434204697.3.3.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; Hm_lvt_1db88642e346389874251b5a1eded6e3=1434369150,1434464564,1434543070,1434896841; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1434896841"
				;
		URL url = null;
		try {
			url = new URL(
					"http://xueqiu.com/stock/screener/screen.json?category=SH&size=2300&exchange=&areacode=&indcode=&orderby=symbol&order=desc&current=ALL&pct=ALL&page=1&pettm=0.00_31668.88&pb=0.00_13685.71&pelyr=0.00_65735.97&_=1434464607341"
					);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("����ץȡѩ��������...");
		Grab_xueqiu jag = new Grab_xueqiu();
		JSONArray ja= jag.Jsonget(cookie,url);
		System.out.println("ץȡ���!��ץȡ"+ja.length()+"����Ʊ��Ϣ��"+"���ڱ��浽����...");
	
		String path ="C:\\Users\\Administrator\\workspace\\Agile\\data\\xueqiudataTest.xls";
	
	    	  
		jag.saveExcel(ja,path);
		System.out.println("������ϣ����ڲ�����ȷ�Բ��ָ�����...");
	    
	    
	    Workbook wb = Workbook.getWorkbook(new FileInputStream(path));
	   
	    Sheet sheet = wb.getSheet(0);
	    for(int i=1;i<=ja.length();i++){
	    	JSONObject jb = (JSONObject) ja.get(i-1);
	    	
	    	String expected1 = jb.getString("symbol");//����ֵ
	    	String truevalue1 = sheet.getCell(0, i).getContents();//��ʵֵ
	    	assertEquals(expected1, truevalue1);//���Է���
	    	
	    	String expected2 = jb.getString("name");//����ֵ
	    	String truevalue2 = sheet.getCell(1, i).getContents();//��ʵֵ
	    	assertEquals(expected2, truevalue2);
	    	
	    	String expected3 = Double.toString((double) jb.get("pct"));//����ֵ
	    	String truevalue3 = sheet.getCell(2, i).getContents();//��ʵֵ
	    	assertEquals(expected3, truevalue3);
	    	
	    	String expected4 =Double.toString((double) jb.get("current"));//����ֵ
	    	String truevalue4 = sheet.getCell(3, i).getContents();//��ʵֵ
	    	assertEquals(expected4, truevalue4);
	    	
	    	String expected5 =Double.toString((double) jb.get("pelyr"));//����ֵ
	    	String truevalue5 = sheet.getCell(4, i).getContents();//��ʵֵ
	    	assertEquals(expected5, truevalue5);
	    	
	    	String expected6 =Double.toString((double) jb.get("pettm"));//����ֵ
	    	String truevalue6 = sheet.getCell(5, i).getContents();//��ʵֵ
	    	assertEquals(expected6, truevalue6);
	    	
	    	String expected7=Double.toString((double) jb.get("pb"));//����ֵ
	    	String truevalue7 = sheet.getCell(6, i).getContents();//��ʵֵ
	    	assertEquals(expected7, truevalue7);
	    	
	    	String expected8="--";//����ֵ
	    	String truevalue8 = sheet.getCell(7, i).getContents();//��ʵֵ
	    	assertEquals(expected8, truevalue8);
	    	
	    	String expected9="--";//����ֵ
	    	String truevalue9 = sheet.getCell(8, i).getContents();//��ʵֵ
	    	assertEquals(expected9, truevalue9);
	    	
	    }
	    
	    
	    WritableWorkbook wwb =Workbook.createWorkbook(new File(path), wb);
	    wwb.removeSheet(0);
	    
	    wwb.write();
	    wwb.close();
	    System.out.println("�ò�����ϣ�\n");
	    	
	}
	
	/**
	 * ����ץȡͬ��˳����
	 * �����ڱ���excel
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws BiffException 
	 * 
	 */
	@Test
	public void Grab_TonghuashunTest() throws BiffException, WriteException, IOException{
		//ץȡͬ��˳���ݲ�����
		
		String url2 = "http://www.iwencai.com/stockpick/search?typed=0&preParams=&ts=1&f=1&qs=1&selfsectsn=&querytype=&searchfilter=&tid=stockpick&w=pe";
		Grab_tonghuashun j = new Grab_tonghuashun();
		String path2 ="C:\\Users\\Administrator\\workspace\\Agile\\data\\tonghuashundataTest.xls";
		System.out.println("����ץȡͬ��˳����...");
		JSONArray ja = j.getJson(url2);
		System.out.println("ץȡ���!��ץȡ"+ja.length()+"����Ʊ��Ϣ��"+"���ڱ��浽����...");
		j.saveExcel(ja,path2 );
		System.out.println("�������! ���ڲ�����ȷ�Բ��ָ�����...");
		
		Workbook wb = Workbook.getWorkbook(new FileInputStream(path2));
	   
	    Sheet sheet = wb.getSheet(0);
	    
	    for(int i=1;i<=ja.length();i++){
	    	JSONArray temp = ja.getJSONArray(i-1);
	    	
	    	for(int loop =0; loop<9;loop++){
	    		String expected1 = temp.getString(loop);//����ֵ
	    	    String truevalue1 = sheet.getCell(loop, i).getContents();//��ʵֵ
	    	    assertEquals(expected1, truevalue1);//���Է���
	    	}
	    	
	    }
	    
	    WritableWorkbook wwb =Workbook.createWorkbook(new File(path2), wb);
	    wwb.removeSheet(0);
	    
	    wwb.write();
	    wwb.close();
	    System.out.println("�ò�����ϣ�\n");
	}
}
