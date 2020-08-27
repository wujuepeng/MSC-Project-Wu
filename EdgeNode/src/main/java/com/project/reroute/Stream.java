package com.project.reroute;


import com.project.monitorinfo.Monitor;
import com.project.monitorinfo.ObtainRouteTable;
import com.project.mqtt.MqttSubscriber;



public class Stream {


    public static void main(String[] args){

        try {
            Monitor.monitorMachineInfo();
            ObtainRouteTable.convert(Monitor.monitorMap);
        } catch (Exception e) {
            e.printStackTrace();
        }


        ObtainRouteTable obtainRouteTable=new ObtainRouteTable();
        Thread threadObtainRouteTable = new Thread(obtainRouteTable,"123");
        threadObtainRouteTable.start();

        MqttSubscriber mqttSubscriber=new MqttSubscriber();
        mqttSubscriber.setIPId("tcp://127.0.0.1:1883","999");
        mqttSubscriber.recieve("edge");
       mqttSubscriber.recieve("reroute");

    }


}
