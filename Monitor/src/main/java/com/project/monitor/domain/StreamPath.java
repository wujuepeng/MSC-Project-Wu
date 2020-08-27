package com.project.monitor.domain;

import java.util.List;

public class StreamPath {

    private String streamId;

    private String departureMachineIp;

    private String departureLocation;

    private String arriveMachineIp;

    private String arriveLocation;

    private List<String> middleNodesIp;

    public String getDepartureLocation() {
        return departureLocation;
    }

    public void setDepartureLocation(String departureLocation) {
        this.departureLocation = departureLocation;
    }

    public String getArriveLocation() {
        return arriveLocation;
    }

    public void setArriveLocation(String arriveLocation) {
        this.arriveLocation = arriveLocation;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getDepartureMachineIp() {
        return departureMachineIp;
    }

    public void setDepartureMachineIp(String departureMachineIp) {
        this.departureMachineIp = departureMachineIp;
    }

    public String getArriveMachineIp() {
        return arriveMachineIp;
    }

    public void setArriveMachineIp(String arriveMachineIp) {
        this.arriveMachineIp = arriveMachineIp;
    }

    public List<String> getMiddleNodesIp() {
        return middleNodesIp;
    }

    public void setMiddleNodesIp(List<String> middleNodesIp) {
        this.middleNodesIp = middleNodesIp;
    }
}
