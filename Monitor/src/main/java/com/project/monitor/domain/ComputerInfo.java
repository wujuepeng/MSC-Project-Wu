package com.project.monitor.domain;

import java.io.Serializable;

public class ComputerInfo implements Serializable {

    private String coordinate;

    private String Type;

    private String distance="0";

    private String computerName;

    private String IP;

    private String localName;

    private String totalMemory;

    private String usedMemory;

    private String usedMemoryRate="1";

    private String freeMemory;

    private String CPUTotalMHZ;

    private String CPUTotalUsed="1";

    private String CPUFree;

    private String CPUError;

    private String CPUWait;

    private String CPUSystemUsed;

    private String CPUUserUsed;

    private String acceptTotalPackets="999999";

    private String sendTotalPackets="999999";

    private String acceptTotalBytes;

    private String sendTotalBytes;

    private String acceptErrorPackets;

    private String sendErrorPackets;

    private String acceptDropPackets;

    private String sendDropPackets;

    private String pingTime="1000";

    private long recordTime;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(String totalMemory) {
        this.totalMemory = totalMemory;
    }

    public String getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(String usedMemory) {
        this.usedMemory = usedMemory;
    }

    public String getUsedMemoryRate() {
        return usedMemoryRate;
    }

    public void setUsedMemoryRate(String usedMemoryRate) {
        this.usedMemoryRate = usedMemoryRate;
    }

    public String getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(String freeMemory) {
        this.freeMemory = freeMemory;
    }

    public String getCPUTotalMHZ() {
        return CPUTotalMHZ;
    }

    public void setCPUTotalMHZ(String CPUTotalMHZ) {
        this.CPUTotalMHZ = CPUTotalMHZ;
    }

    public String getCPUTotalUsed() {
        return CPUTotalUsed;
    }

    public void setCPUTotalUsed(String CPUTotalUsed) {
        this.CPUTotalUsed = CPUTotalUsed;
    }

    public String getCPUFree() {
        return CPUFree;
    }

    public void setCPUFree(String CPUFree) {
        this.CPUFree = CPUFree;
    }

    public String getCPUError() {
        return CPUError;
    }

    public void setCPUError(String CPUError) {
        this.CPUError = CPUError;
    }

    public String getCPUWait() {
        return CPUWait;
    }

    public void setCPUWait(String CPUWait) {
        this.CPUWait = CPUWait;
    }

    public String getCPUSystemUsed() {
        return CPUSystemUsed;
    }

    public void setCPUSystemUsed(String CPUSystemUsed) {
        this.CPUSystemUsed = CPUSystemUsed;
    }

    public String getCPUUserUsed() {
        return CPUUserUsed;
    }

    public void setCPUUserUsed(String CPUUserUsed) {
        this.CPUUserUsed = CPUUserUsed;
    }

    public String getAcceptTotalPackets() {
        return acceptTotalPackets;
    }

    public void setAcceptTotalPackets(String acceptTotalPackets) {
        this.acceptTotalPackets = acceptTotalPackets;
    }

    public String getSendTotalPackets() {
        return sendTotalPackets;
    }

    public void setSendTotalPackets(String sendTotalPackets) {
        this.sendTotalPackets = sendTotalPackets;
    }

    public String getAcceptTotalBytes() {
        return acceptTotalBytes;
    }

    public void setAcceptTotalBytes(String acceptTotalBytes) {
        this.acceptTotalBytes = acceptTotalBytes;
    }

    public String getSendTotalBytes() {
        return sendTotalBytes;
    }

    public void setSendTotalBytes(String sendTotalBytes) {
        this.sendTotalBytes = sendTotalBytes;
    }

    public String getAcceptErrorPackets() {
        return acceptErrorPackets;
    }

    public void setAcceptErrorPackets(String acceptErrorPackets) {
        this.acceptErrorPackets = acceptErrorPackets;
    }

    public String getSendErrorPackets() {
        return sendErrorPackets;
    }

    public void setSendErrorPackets(String sendErrorPackets) {
        this.sendErrorPackets = sendErrorPackets;
    }

    public String getAcceptDropPackets() {
        return acceptDropPackets;
    }

    public void setAcceptDropPackets(String acceptDropPackets) {
        this.acceptDropPackets = acceptDropPackets;
    }

    public String getSendDropPackets() {
        return sendDropPackets;
    }

    public void setSendDropPackets(String sendDropPackets) {
        this.sendDropPackets = sendDropPackets;
    }

    public long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(long recordTime) {
        this.recordTime = recordTime;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPingTime() {
        return pingTime;
    }

    public void setPingTime(String pingTime) {
        this.pingTime = pingTime;
    }
}
