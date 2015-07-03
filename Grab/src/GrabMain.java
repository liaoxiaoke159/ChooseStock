
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class GrabMain {

	public static void main(String[] args) throws BiffException, FileNotFoundException, IOException, RowsExceededException, WriteException {
		// TODO Auto-generated method stub

		
		
		//抓取同花顺数据并保存
		//注意：如果有400错误 需要更换cookie
		
		String cookie = 
				"s=yul12oxdgd; bid=4651608d7962f8e8bb2226bdd0ad82ae_iauqq3vj; xq_a_token=fdabf485a3ff707805388f2ae73f05b23f19696f; xq_r_token=7830f5bb373434a08337b6a2914c6ef5ea02b83b; route=; __utmt=1; __utma=1.796900497.1434168049.1434543069.1434896841.8; __utmb=1.1.10.1434896841; __utmc=1; __utmz=1.1434204697.3.3.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; Hm_lvt_1db88642e346389874251b5a1eded6e3=1434369150,1434464564,1434543070,1434896841; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1434896841"
					;
			URL url1 = null;
			try {
				url1 = new URL(
						"http://xueqiu.com/stock/screener/screen.json?category=SH&size=2300&exchange=&areacode=&indcode=&orderby=symbol&order=desc&current=ALL&pct=ALL&page=1&pettm=0.00_31668.88&pb=0.00_13685.71&pelyr=0.00_65735.97&_=1434464607341"
						);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("正在抓取雪球网数据...");
			Grab_xueqiu jag = new Grab_xueqiu();
			JSONArray ja= jag.Jsonget(cookie,url1);
			System.out.println("抓取完毕!共抓取"+ja.length()+"条股票信息！"+"正在保存到本地...");
		
			String path ="C:\\Users\\Administrator\\workspace\\Agile\\data\\xueqiudata.xls";
		
		    	  
			jag.saveExcel(ja,path);
			System.out.println("保存完毕！\n");
			
			
			
			//抓取同花顺数据并保存
			
			String url2 = "http://www.iwencai.com/stockpick/search?typed=0&preParams=&ts=1&f=1&qs=1&selfsectsn=&querytype=&searchfilter=&tid=stockpick&w=pe";
			
			Grab_tonghuashun j = new Grab_tonghuashun();
			String path2 ="C:\\Users\\Administrator\\workspace\\Agile\\data\\tonghuashundata.xls";
			System.out.println("正在抓取同花顺数据...");
			JSONArray ja1 = j.getJson(url2);
			System.out.println("抓取完毕!共抓取"+ja1.length()+"条股票信息！"+"正在保存到本地...");
			j.saveExcel(ja1,path2 );
			System.out.println("保存完毕!\n");
			
			
		
	}

}
