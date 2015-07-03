

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.json.JSONArray;
import org.json.JSONObject;






public class Grab_xueqiu {

	public JSONArray Jsonget(String cookie, URL url) {

		JSONArray jsonArray = null;
		try {

			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			httpConn.setConnectTimeout(5 * 1000);
			httpConn.setDoOutput(true);
			httpConn.setRequestMethod("GET");
			httpConn.setRequestProperty("Cookie", cookie);
			httpConn.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36");
			InputStreamReader input = new InputStreamReader(
					httpConn.getInputStream(), "utf-8");
			BufferedReader bufReader = new BufferedReader(input);
			String line = "";
			StringBuilder contentBuf = new StringBuilder();

			while ((line = bufReader.readLine()) != null) {
				contentBuf.append(line);
			}

			String contenbufstr = contentBuf.toString();
			String JsonArraystr = contenbufstr.substring(
					contenbufstr.indexOf("["), contenbufstr.indexOf("]") + 1);
			jsonArray = new JSONArray(JsonArraystr);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return jsonArray;
	}

	public void saveExcel(JSONArray ja, String path) throws IOException,
			BiffException, WriteException {
		// TODO Auto-generated method stub

		Workbook wb = Workbook.getWorkbook(new FileInputStream(path));
		WritableWorkbook wwb = Workbook.createWorkbook(new File(path), wb);

		WritableSheet sheet;
		if (wb.getSheetNames()[0].equals("��Ʊ����")) {
			sheet = wwb.getSheet(0);
		} else {
			sheet = wwb.createSheet("��Ʊ����", 0);
		}

		for (int i = 1; i <= ja.length(); i++) {

			JSONObject jb = (JSONObject) ja.get(i - 1);

			Label label0 = new Label(0, i, jb.getString("symbol"));// ��Ʊ����
			sheet.addCell(label0);
			Label label1 = new Label(1, i, jb.getString("name"));// ��Ʊ���
			sheet.addCell(label1);
			Label label2 = new Label(2, i, Double.toString((double) jb
					.get("pct")));// �ǵ���
			sheet.addCell(label2);
			Label label3 = new Label(3, i, Double.toString((double) jb
					.get("current")));// �ּ�
			sheet.addCell(label3);
			Label label4 = new Label(4, i, Double.toString((double) jb
					.get("pelyr")));// ��ӯ��
			sheet.addCell(label4);
			Label label5 = new Label(5, i, Double.toString((double) jb
					.get("pettm")));// Ԥ����ӯ��
			sheet.addCell(label5);
			Label label6 = new Label(6, i, Double.toString((double) jb
					.get("pb")));// �о���
			sheet.addCell(label6);
			Label label7 = new Label(7, i, "--");// �ܹɱ�
			sheet.addCell(label7);
			Label label8 = new Label(8, i, "--");// ������ҵ
			sheet.addCell(label8);

		}
		Label label0 = new Label(0, 0, "��Ʊ����");// ��Ʊ����
		sheet.addCell(label0);
		Label label1 = new Label(1, 0, "��Ʊ���");// ��Ʊ���
		sheet.addCell(label1);
		Label label2 = new Label(2, 0, "�ǵ���");// �ǵ���
		sheet.addCell(label2);
		Label label3 = new Label(3, 0, "�ּ�");// �ּ�
		sheet.addCell(label3);
		Label label4 = new Label(4, 0, "��ӯ��");// ��ӯ��
		sheet.addCell(label4);
		Label label5 = new Label(5, 0, "Ԥ����ӯ��");// Ԥ����ӯ��
		sheet.addCell(label5);
		Label label6 = new Label(6, 0, "�о���");// �о���
		sheet.addCell(label6);
		Label label7 = new Label(7, 0, "�ܹɱ�");// �ܹɱ�
		sheet.addCell(label7);
		Label label8 = new Label(8, 0, "������ҵ");// ������ҵ
		sheet.addCell(label8);

		wwb.write();
		wwb.close();

	}
   
}
