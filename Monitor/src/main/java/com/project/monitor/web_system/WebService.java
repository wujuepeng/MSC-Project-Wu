package com.project.monitor.web_system;



import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.project.monitor.config.RedisUtil;
import com.project.monitor.domain.ComputerInfo;
import com.project.monitor.domain.EmqInfo;
import com.project.monitor.domain.Machine;
import com.project.monitor.domain.StreamPath;
import com.project.monitor.networkstatus.Ping;
import com.project.monitor.table.RouteTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class WebService {



    @Autowired
    private RedisUtil redisUtil;

    private RouteTable routeTable =new RouteTable();

    public Map getMachineInfo() {

        List<Machine> machineList = (ArrayList) redisUtil.get("machineList");
        List<StreamPath> streamPaths = new ArrayList<>();
        List<EmqInfo> emqInfoList = new ArrayList<>();
        List<EmqInfo> emqInfoList1 = new ArrayList<>();
        List<EmqInfo> webList = new ArrayList<>();

        Map map = new HashMap();
        for (Machine m : machineList) {
            if ("Node".equals(m.getMachineType())) {
                ComputerInfo computerInfo = (ComputerInfo) redisUtil.get(m.getMachineIp());
                emqInfoList = (List<EmqInfo>) redisUtil.get(computerInfo.getIP() + ":broker");
                if (emqInfoList != null && emqInfoList.size() != 0) {
                    for (EmqInfo e : emqInfoList) {
                        e.setBrokerLocation(computerInfo.getCoordinate());

                        webList.add(e);
                    }
                }
            }
        }

        for (EmqInfo emqInfo : webList) {
            for (Machine machine : machineList) {
                if (emqInfo.getClientIp().equals(machine.getMachineIp())) {
                    StreamPath streamPath = new StreamPath();
                    streamPath.setArriveMachineIp(emqInfo.getBrokerIp());
                    streamPath.setArriveLocation(emqInfo.getBrokerLocation());
                    streamPath.setDepartureMachineIp(emqInfo.getClientIp());
                    streamPath.setDepartureLocation(machine.getLocation());
                    streamPaths.add(streamPath);
                }

            }
        }

            map.put("client", webList);
            map.put("machine", machineList);
            map.put("stream", streamPaths);
            return map;
        }


    public String getAPRouteTable(String APLocation) {

        String APinfo[]=APLocation.split(";");

        ArrayList machineList= (ArrayList) redisUtil.get("machineList");
        int count=0;
        if(machineList!=null&&machineList.size()>0){
            for(int i=0;i<machineList.size();i++){
                Machine machine= (Machine) machineList.get(i);
                if(APinfo[0].equals(machine.getMachineIp())){
                    count++;
                    machine.setUpdateTime(System.currentTimeMillis());
                }
            }
        }

        if(count==0){
            machineList=new ArrayList();
            Machine machine=new Machine();
            machine.setUpdateTime(System.currentTimeMillis());
            machine.setLocation(APinfo[1]);
            machine.setMachineIp(APinfo[0]);
            machine.setIslived(true);
            machine.setMachineType("AP");
            machineList.add(machine);
        }
        redisUtil.set("machineList",machineList);
        String table=routeTable.generateAPRouteTable(APinfo,getComputerInfo());

        return table;
    }


    public String getNodeRouteTable(String machineMap) {
        if(machineMap=="" || machineMap==null){
            return "no message!!";
        }
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(machineMap, map.getClass());
        String machineIP=(String) map.get("IP");
        ArrayList machineList= (ArrayList) redisUtil.get("machineList");
        if(machineList==null){
            machineList=new ArrayList();
        }
        int count=0;
        for(int i=0;i<machineList.size();i++){
            Machine machine= (Machine) machineList.get(i);
            if(machineIP.equals(machine.getMachineIp())){
                count++;
                machine.setUpdateTime(System.currentTimeMillis());
            }
        }
        String ping="";
        if(count==0){
            Machine machine=new Machine();
            machine.setUpdateTime(System.currentTimeMillis());
            machine.setMachineIp(machineIP);
            machine.setIslived(true);
            machine.setLocation((String) map.get("DISTANCE"));
            machine.setCPU((String) map.get("CPUTOTALUSED"));
            machine.setMachineType("Node");

            try {
                ping= Ping.ping((String) map.get("IP"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            machine.setPing(ping);
            machineList.add(machine);
        }
        redisUtil.set("machineList",machineList);
        ComputerInfo computerInfo=new ComputerInfo();
        computerInfo.setRecordTime(System.currentTimeMillis());
        computerInfo.setCoordinate((String) map.get("DISTANCE"));
        computerInfo.setComputerName((String) map.get("COMPUTERNAME"));
        computerInfo.setIP((String) map.get("IP"));
        computerInfo.setLocalName((String) map.get("LOCALNAME"));
        computerInfo.setTotalMemory((String) map.get("TOTALMEMORY"));
        computerInfo.setUsedMemory((String) map.get("USEDMEMORY"));
        computerInfo.setUsedMemoryRate((String) map.get("USEDMEMORYRATE"));
        computerInfo.setFreeMemory((String) map.get("FREEMEMORY"));

        computerInfo.setCPUTotalMHZ((String) map.get("CPUTOTALMHZ"));
        computerInfo.setCPUTotalUsed((String) map.get("CPUTOTALUSED"));
        computerInfo.setCPUFree((String) map.get("CUPFREE"));
        computerInfo.setCPUError((String) map.get("CPUERROR"));
        computerInfo.setCPUWait((String) map.get("CPUWAIT"));
        computerInfo.setCPUSystemUsed((String) map.get("CPUSYSTEMUSED"));
        computerInfo.setCPUUserUsed((String) map.get("CPUUSERUSED"));

        computerInfo.setAcceptTotalPackets((String) map.get("ACCEPTTOTALPACKETS"));
        computerInfo.setSendTotalPackets((String) map.get("SENDTOTALPACKETS"));
        computerInfo.setAcceptTotalBytes((String) map.get("ACCEPTTOTALBYTES"));
        computerInfo.setSendTotalBytes((String) map.get("SENDTOTALBYTES"));
        computerInfo.setAcceptErrorPackets((String) map.get("ACCEPTERRORPACKETS"));
        computerInfo.setSendErrorPackets((String) map.get("SENDERRORPACKETS"));
        computerInfo.setAcceptDropPackets((String) map.get("ACCEPTDROPPACKETS"));
        computerInfo.setSendDropPackets((String) map.get("SENDDROPPACKETS"));
        computerInfo.setPingTime(ping);
        redisUtil.set(computerInfo.getIP(),computerInfo);

        List list= (ArrayList) map.get("list");
        if(list!=null&&list.size()>0){
            ArrayList emqList=new ArrayList();
            String brokerIP="";
            for(int i=0; i<list.size();i++){
                EmqInfo emqInfo=new EmqInfo();
                Map emqMap=(LinkedTreeMap) list.get(i);
                emqInfo.setClientId((String) emqMap.get("clientid"));
                emqInfo.setBrokerIp((String) map.get("IP"));
                brokerIP=(String) map.get("IP");
                if("127.0.0.1".equals((String) emqMap.get("ip_address"))){
                    emqInfo.setClientIp((String) map.get("IP"));
                }
                emqInfo.setClientIp((String) emqMap.get("ip_address"));
                emqInfo.setReceivePackets((String) emqMap.get("recv_pkt"));
                emqInfo.setSendPackets((String) emqMap.get("send_pkt"));
                emqInfo.setRecordTime(System.currentTimeMillis());
                emqList.add(emqInfo);
            }
            redisUtil.set(brokerIP+":broker",emqList);
        }
        List<ComputerInfo> list1=getComputerInfo();
        String table=routeTable.generateNodeRouteTable(machineIP,list1);

        return table;
    }

    public List<ComputerInfo> getComputerInfo(){
        ArrayList list=new ArrayList();
        ArrayList arrayList=(ArrayList) redisUtil.get("machineList");
        for (int i=0; i<arrayList.size();i++){
            Machine machine= (Machine) arrayList.get(i);
            if("Node".equals(machine.getMachineType())){
                ComputerInfo computerInfo=(ComputerInfo) redisUtil.get(machine.getMachineIp());
                list.add(computerInfo);
            }

        }
        return list;
    }


}
