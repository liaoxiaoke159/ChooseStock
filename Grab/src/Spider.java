

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Spider {

	
    /**
     * ≈¿≥Ê¿‡
     * @param path
     * @return
     */
    public static String spider(String path) {
        HttpGet httpget = new HttpGet(path);  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        CloseableHttpResponse response = null;
        String content = null;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();  
	        if (entity != null) {  
	            content = EntityUtils.toString(entity);
	        }  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            try {  
            	if(response != null) {
            		response.close();  
            		response = null;
            	}
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
		}
            
        return content;
    }
}
