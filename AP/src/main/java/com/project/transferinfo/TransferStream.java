package com.project.transferinfo;

import com.google.gson.Gson;
import com.project.monitorinfo.ObtainRouteTable;
import com.project.mqtt.MqttPublisher;

import java.io.IOException;
import java.util.*;

public class TransferStream implements Runnable{

    private MqttPublisher mqttPublisher = new MqttPublisher();
    public static int sendCount=0;
    private String name;

    public TransferStream(String name){
        this.name=name;
    }

    public static String randomFunction(String table){
        if("".equals(table)||table==null){
            return null;
        }
        String route[]=table.split(";");

        List<Map> mapList=new ArrayList<Map>();
        Gson gson = new Gson();
        for(int i=0;i<route.length;i++){

            route[i]="{"+route[i]+"}";
            System.out.println(route[i]);
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


    public void transfer(String IP,String message){
        mqttPublisher.setIp(IP);
        mqttPublisher.publishMessage("edge", "Message:"+message, 1);

    }

    public static void main(String[] args){

        ObtainRouteTable obtainRouteTable=new ObtainRouteTable();
        try {
            ObtainRouteTable.convert("127.0.0.1;114.96843,25.824473");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread threadObtainRoute = new Thread(obtainRouteTable,"obtainRoute");
        threadObtainRoute.start();

            TransferStream thread=new TransferStream("thread");
            new Thread(thread).start();

    }

    public void run() {
        while (true){
            String IP=randomFunction(ObtainRouteTable.table);
            if(IP==null){
                IP="127.0.0.1";
            }
            IP="tcp://"+IP;
            transfer(IP,"message"+sendCount);
            System.out.println(this.name+"===>"+sendCount);
            sendCount++;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
