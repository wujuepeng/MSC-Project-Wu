package com.project.monitor.domain;

import java.io.Serializable;

public class Machine implements Serializable {

    private String machineType;

    private String machineIp;

    private String location;

    private String CPU="";

    private String Ping="";

    private boolean islived=true;

    private long updateTime;

    public String getCPU() {
        return CPU;
    }

    public void setCPU(String CPU) {
        this.CPU = CPU;
    }

    public String getPing() {
        return Ping;
    }

    public void setPing(String ping) {
        Ping = ping;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getMachineIp() {
        return machineIp;
    }

    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }

    public boolean isIslived() {
        return islived;
    }

    public void setIslived(boolean islived) {
        this.islived = islived;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
