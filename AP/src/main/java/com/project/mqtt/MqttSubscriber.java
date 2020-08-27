package com.project.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;



public class MqttSubscriber {


    private int QoS = 1;
    private String Host = null;
    private String clientId = null;
    private static MemoryPersistence memoryPersistence = null;
    private static MqttConnectOptions mqttConnectOptions = null;
    private static MqttClient mqttClient  = null;





    public MqttSubscriber(String IP_Address,String client_Id){
        Host = IP_Address;
        clientId = client_Id;
    }


    public void init() {
        mqttConnectOptions = new MqttConnectOptions();
        memoryPersistence = new MemoryPersistence();
        if(null != memoryPersistence && null != clientId && null != Host) {
            try {
                mqttClient = new MqttClient(Host, clientId, memoryPersistence);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("memoryPersistence & clientId & Host is null");
        }

        if(null != mqttConnectOptions) {
            mqttConnectOptions.setCleanSession(true);
            mqttConnectOptions.setConnectionTimeout(30);
            mqttConnectOptions.setKeepAliveInterval(45);
            if(null != mqttClient && !mqttClient.isConnected()) {
                mqttClient.setCallback(new CallBack());
                try {
                    mqttClient.connect();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("mqttClient is null");
            }
        }else {
            System.out.println("mqttConnectOptions is null");
        }
    }


    public void recieve(String topic) {
        int[] Qos = {QoS};
        String[] topics = {topic};
        if(null != mqttClient && mqttClient.isConnected()) {
            if(null!=topics && null!=Qos && topics.length>0 && Qos.length>0) {
                try {
                    mqttClient.subscribe(topics, Qos);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("topics & Qos is null");
            }
        }else {
            init();
            recieve(topic);
        }
    }


}
