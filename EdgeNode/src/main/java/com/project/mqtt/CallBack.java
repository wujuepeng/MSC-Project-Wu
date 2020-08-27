package com.project.mqtt;



import com.google.gson.Gson;
import com.project.monitorinfo.Monitor;
import com.project.monitorinfo.ObtainRouteTable;
import com.project.reroute.StreamProcess;
import org.apache.http.client.ClientProtocolException;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.hyperic.sigar.SigarException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;


public class CallBack implements MqttCallback {


    public void connectionLost(Throwable cause) {

    }

    public static String randomFunction(String table){

        if(table==null||"".equals(table)){
            return null;
        }
        String route[]=table.split(";");
        List<Map> mapList=new ArrayList<Map>();
        Gson gson = new Gson();
        for(int i=0;i<route.length;i++){
            route[i]="{"+route[i]+"}";
            Map<String, String> map = new HashMap<String, String>();
            map = gson.fromJson(route[i], map.getClass());
            mapList.add(map);
        }

        List IP=new ArrayList();
        for (Map map:mapList) {
            Double weight= Double.parseDouble((String) map.get("weight"));
            for(int i=0;i<weight;i++){
                IP.add(map.get("IP"));
            }
        }
        Random random=new Random();
        return (String) IP.get(random.nextInt(IP.size()));
    }



    public void messageArrived(String topic, MqttMessage message) throws Exception {

        String msg=new String(message.getPayload());
        if("reroute".equals(topic)){
            System.out.println("msg=>reroute=>process");
            StreamProcess.messageProcess(topic,msg);

        }

        if(Monitor.monitorMap.get("CPUTOTALUSED")!=null&&(Double)Monitor.monitorMap.get("CPUTOTALUSED")>0.5){
            if("".equals(ObtainRouteTable.table)||ObtainRouteTable.table==null){
                StreamProcess.messageProcess(topic,msg);
            }else {

                String IP=CallBack.randomFunction(ObtainRouteTable.table);
                MqttPublisher mqttPublisher=new MqttPublisher();
                mqttPublisher.setIp(IP,"335");
                mqttPublisher.publishMessage("reroute",msg,1);
            }

        }else {
            StreamProcess.messageProcess(topic,msg);

        }

    }

    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}

