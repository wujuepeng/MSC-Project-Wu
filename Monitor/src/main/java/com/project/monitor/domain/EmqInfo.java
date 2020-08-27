package com.project.monitor.domain;

import java.io.Serializable;

public class EmqInfo implements Serializable {

    private String clientId;

    private String clientIp;

    private String receivePackets;

    private String sendPackets;

    private String brokerIp;

    private String brokerLocation;

    private long recordTime;

    public String getBrokerLocation() {
        return brokerLocation;
    }

    public void setBrokerLocation(String brokerLocation) {
        this.brokerLocation = brokerLocation;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getReceivePackets() {
        return receivePackets;
    }

    public void setReceivePackets(String receivePackets) {
        this.receivePackets = receivePackets;
    }

    public String getSendPackets() {
        return sendPackets;
    }

    public void setSendPackets(String sendPackets) {
        this.sendPackets = sendPackets;
    }

    public long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(long recordTime) {
        this.recordTime = recordTime;
    }

    public String getBrokerIp() {
        return brokerIp;
    }

    public void setBrokerIp(String brokerIp) {
        this.brokerIp = brokerIp;
    }

    @Override
    public String toString() {
        return "EmqInfo{" +
                "clientId='" + clientId + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", receivePackets='" + receivePackets + '\'' +
                ", sendPackets='" + sendPackets + '\'' +
                ", brokerIp='" + brokerIp + '\'' +
                ", recordTime=" + recordTime +
                '}';
    }
}
