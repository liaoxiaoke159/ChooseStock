
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.json.JSONArray;


public class Grab_tonghuashun {
	

	/**
	 *  利用爬虫抓取股票数据
	 * @param path
	 * @return JSONArray
	 */
	public JSONArray getJson(String path) {
		
		String stringJsonContent = null;
		String stringHTMLContent = Spider.spider(path); 
		
		int totalIndex = stringHTMLContent.indexOf("\"total\":");
		String stringTotal = stringHTMLContent.substring(totalIndex + 8, totalIndex + 8 + 4);
		
		
		int tokenIndex = stringHTMLContent.indexOf("token");
		int endTokenIndex = stringHTMLContent.indexOf("\",\"staticList\"", tokenIndex);
		
		String stringToken = stringHTMLContent.substring(tokenIndex + 8, endTokenIndex);
		String newPath = path.substring(0, path.indexOf("search")) + "cache?token="
				+ stringToken + "&perpage=" + stringTotal;
		
		
		stringJsonContent = Spider.spider(newPath); 
		
		String stringJsonArray = stringJsonContent.substring(stringJsonContent.indexOf("[["), stringJsonContent.indexOf("]]") + 2);
				
		JSONArray jsonArray = new JSONArray(stringJsonArray);	
		
		return jsonArray;				
	}
	/**
	 * 保存股票数据到本地
	 * @param ja
	 * @param path
	 * @throws IOException
	 * @throws BiffException
	 * @throws WriteException
	 */
	public void saveExcel(JSONArray ja,String path) throws IOException, BiffException, WriteException {
		// TODO Auto-generated method stub
		
		
		
		Workbook wb = Workbook.getWorkbook(new FileInputStream(path));
		WritableWorkbook wwb = Workbook.createWorkbook(new File(path),wb);
		WritableSheet sheet;
		if(wb.getSheetNames()[0].equals("股票数据")){
			sheet = wwb.getSheet(0);
		}else{
		    sheet = wwb.createSheet("股票数据", 0);
		}
			
		for(int i=1;i<=ja.length();i++){
			
			JSONArray temp = ja.getJSONArray(i-1);
			
			for(int j=0;j<9;j++){
				Label label0 = new Label(j,i,temp.getString(j));
				sheet.addCell(label0);
			}
			
				
		}
		Label label0 = new Label(0,0,"股票代码");//股票代码
		sheet.addCell(label0);
		Label label1 = new Label(1,0,"股票简称");//股票简称
		sheet.addCell(label1);
		Label label2 = new Label(2,0,"涨跌幅");//涨跌幅
		sheet.addCell(label2);
		Label label3 = new Label(3,0,"现价");//现价
		sheet.addCell(label3);
		Label label4 = new Label(4,0,"市盈率");//市盈率
		sheet.addCell(label4);
		Label label5 = new Label(5,0,"预测市盈率");//预测市盈率
		sheet.addCell(label5);
		Label label6 = new Label(6,0,"市净率");//市净率
		sheet.addCell(label6);
		Label label7 = new Label(7,0,"总股本");//总股本
		sheet.addCell(label7);
		Label label8 = new Label(8,0,"所属行业");//所属行业
		sheet.addCell(label8);
		
		wwb.write();
		wwb.close();
		
	}
	   
}
