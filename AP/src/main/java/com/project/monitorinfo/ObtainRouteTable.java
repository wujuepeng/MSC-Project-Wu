package com.project.monitorinfo;


import com.project.transferinfo.TransferStream;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ObtainRouteTable implements Runnable{

    public static String table="";

    InetAddress addr;

    {
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    String ip = addr.getHostAddress();

    public static void convert(String location) throws ClientProtocolException, IOException {
        String url = "http://127.0.0.1:8888/getAPRouteTable";
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        String param1 = "APLocation=" + location + "&coordsys=baidu&output=JSON";
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
                String msg=ip+";"+"114.96843,25.824473;"+TransferStream.sendCount;
                ObtainRouteTable.convert(msg);
                Thread.sleep(10000);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}