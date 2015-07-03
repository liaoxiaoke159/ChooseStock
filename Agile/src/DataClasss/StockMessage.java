package DataClasss;

import java.text.DecimalFormat;

import org.json.JSONArray;

public class StockMessage {
	public String code;//股票代码
	public String name;//股票名称
	public String updownrate;//涨跌幅
	public String  presentprice;//现价
	public String  pe;//市盈率
	public String pridectpe;//预测市盈率
	public String pb;//市净率
	public String sumnum;//总股本
	public String industry;//所属行业
	
	public StockMessage(String code,String name,String updownrate,String presentprice
			,String pe,String pridectpe,String pb,String sumnum,String industry){
		DecimalFormat df = new DecimalFormat(".00");
		this.code = code;
		this.name= name;
		this.updownrate = setdouble(updownrate);
		this.presentprice = setdouble(presentprice);
		this.pe = setdouble(pe);
		this.pridectpe = setdouble(pridectpe);
		this.pb = setdouble(pb);
		if(sumnum.contains("E")){
			String str = sumnum.substring(0, sumnum.indexOf("E"));
			this.sumnum = (df.format(Double.parseDouble(str))+"亿");
			}else if(sumnum.equals("--")){
				this.sumnum = "--";
			}
			else{
				this.sumnum = df.format(Double.parseDouble( sumnum));
			}
		this.industry = industry;
		
	}
	public StockMessage(JSONArray temp){
		
			this.code = temp.getString(0);
			this.name = temp.getString(1);
			this.updownrate= setdouble(temp.getString(2));
			this.presentprice = setdouble(temp.getString(3));
			
			this.pe = setdouble(temp.getString(4));
			this.pridectpe= setdouble(temp.getString(5));
			this.pb = setdouble(temp.getString(6));
			
			DecimalFormat df = new DecimalFormat(".00");
			
			if(temp.getString(7).contains("E")){
			String str = temp.getString(7).substring(0, temp.getString(7).indexOf("E"));
			this.sumnum = (df.format(Double.parseDouble(str))+"亿");
			}else if(temp.getString(7).equals("--")){
				this.sumnum = "--";
			}
			else{
				this.sumnum = df.format(Double.parseDouble( temp.getString(7)));
			}
			
			this.industry = temp.getString(8);
	}
	
	private String setdouble(String str){
		
		DecimalFormat df = new DecimalFormat(".00");
		if(str.equals("--")){
			return "--";
		}
		else{
		      return df.format(Double.parseDouble(str));
		}
		
		
	}


}
