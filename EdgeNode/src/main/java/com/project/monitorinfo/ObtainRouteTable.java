package com.project.monitorinfo;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hyperic.sigar.SigarException;

import java.io.IOException;
import java.util.Map;

public class ObtainRouteTable implements Runnable {

    public static String table="";

    public static void convert(Map location) throws ClientProtocolException, IOException {
        String url = "http://127.0.0.1:8888/getNodeRouteTable";
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        String param1 = "machineInfo=" + location.toString() + "&coordsys=baidu&output=JSON";
        StringEntity stringEntity = new StringEntity(param1);
        stringEntity.setContentType("application/x-www-form-urlencoded");
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(stringEntity);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        HttpResponse response = httpclient.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        table=result;

    }

    public void run() {
        while (true){
            try {
                Monitor.monitorMachineInfo();
                ObtainRouteTable.convert(Monitor.monitorMap);
                Thread.sleep(10000);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (SigarException e) {
            e.printStackTrace();
            }
        }
    }
}
