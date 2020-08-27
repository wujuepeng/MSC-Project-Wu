package com.project.monitorinfo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.hyperic.sigar.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Monitor {

    public static Map monitorMap=null;



    public static void monitorMachineInfo() throws SigarException, UnknownHostException {
        Map machineInfoMap=new HashMap();
        Map<String, String> map = System.getenv();
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();

        InetAddress addr;
        addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress();

        CpuPerc cpuList[] = null;
        cpuList = sigar.getCpuPercList();
        CpuInfo infos[] = sigar.getCpuInfoList();
        String ifNames[] = sigar.getNetInterfaceList();

        machineInfoMap.put("'DISTANCE'","'"+"114.900195,25.818099"+"'");
        machineInfoMap.put("'COMPUTERNAME'","'"+map.get("COMPUTERNAME")+"'");
        machineInfoMap.put("'IP'","'"+ip+"'");
        machineInfoMap.put("'LOCALNAME'","'"+addr.getHostName()+"'");
        machineInfoMap.put("'TOTALMEMORY'","'"+mem.getTotal() / 1024L + "K"+"'");
        machineInfoMap.put("'USEDMEMORY'","'"+mem.getUsed() / 1024L + "K"+"'");
        double memoryUsed=mem.getUsed() / mem.getTotal();
        machineInfoMap.put("'USEDMEMORYRATE'","'"+memoryUsed+"'");
        machineInfoMap.put("'FREEMEMORY'","'"+mem.getFree() / 1024L + "K"+"'");


        double cpuMHz=0;
        double cpuUserUsed=0;
        double cpuSystemUsed=0;
        double cpuCurrentWait=0;
        double cpuError=0;
        double cpuFree=0;
        double cpuTotalUsed=0;

        for (int i = 0; i < infos.length; i++) {
            CpuInfo info = infos[i];
            cpuMHz=cpuMHz+info.getMhz();
            cpuUserUsed=cpuUserUsed+cpuList[i].getUser();
            cpuSystemUsed=cpuSystemUsed+cpuList[i].getSys();
            cpuCurrentWait=cpuCurrentWait+cpuList[i].getWait();
            cpuError=cpuError+cpuList[i].getNice();
            cpuFree=cpuFree+cpuList[i].getIdle();
            cpuTotalUsed=cpuTotalUsed+cpuList[i].getCombined();

        }
        machineInfoMap.put("'CPUTOTALMHZ'","'"+cpuMHz+"'");
        machineInfoMap.put("'CPUTOTALUSED'","'"+cpuTotalUsed/infos.length+"'");
        machineInfoMap.put("'CUPFREE'","'"+cpuFree/infos.length+"'");
        machineInfoMap.put("'CPUERROR'","'"+cpuError/infos.length+"'");
        machineInfoMap.put("'CPUWAIT'","'"+cpuCurrentWait/infos.length+"'");
        machineInfoMap.put("'CPUSYSTEMUSED'","'"+cpuSystemUsed/infos.length+"'");
        machineInfoMap.put("'CPUUSERUSED'","'"+cpuUserUsed/infos.length+"'");

        for (int i = 0; i < ifNames.length; i++) {
            String name = ifNames[i];
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
            if(ip.equalsIgnoreCase(ifconfig.getAddress())){
                NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
                machineInfoMap.put("'ACCEPTTOTALPACKETS'","'"+ifstat.getRxPackets()+"'");
                machineInfoMap.put("'SENDTOTALPACKETS'","'"+ifstat.getTxPackets()+"'");
                machineInfoMap.put("'ACCEPTTOTALBYTES'","'"+ifstat.getRxBytes()+"'");
                machineInfoMap.put("'SENDTOTALBYTES'","'"+ifstat.getTxBytes()+"'");
                machineInfoMap.put("'ACCEPTERRORPACKETS'","'"+ifstat.getRxErrors()+"'");
                machineInfoMap.put("'SENDERRORPACKETS'","'"+ifstat.getTxErrors()+"'");
                machineInfoMap.put("'ACCEPTDROPPACKETS'","'"+ifstat.getRxDropped()+"'");
                machineInfoMap.put("'SENDDROPPACKETS'","'"+ifstat.getTxDropped()+"'");

            }

        }
        monitorMap=machineInfoMap;
        List<Map> list=getEMQ();
        machineInfoMap.put("list",list);

    }


public static List<Map> getEMQ() {

    List<Map> clientList=null;
    String jsonStr = "";
    String stringUrl = "http://localhost:8081/api/v4/clients";
    URL url = null;
    try {
        url = new URL(stringUrl);
    } catch (MalformedURLException e) {
        e.printStackTrace();
    }
    URLConnection uc = null;
    try {
        uc = url.openConnection();
    } catch (IOException e) {
        e.printStackTrace();
    }

    uc.setRequestProperty("X-Requested-With", "Curl");

    String userpass = "admin" + ":" + "public";
    String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
    uc.setRequestProperty("Authorization", basicAuth);

    try {
        InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());
        char[] cha = new char[1024000];
        int len = inputStreamReader.read(cha);
        System.out.println(new String(cha, 0, len));
        jsonStr = new String(cha, 0, len);
        inputStreamReader.close();

    } catch (IOException e) {
        e.printStackTrace();
    }

    
    JSONObject object = (JSONObject) JSONObject.parse(jsonStr);
    JSONArray jsonArray= (JSONArray) object.get("data");

    if(jsonArray.size()>0){
        clientList=new ArrayList<Map>();
        for(int i=0; i <jsonArray.size();i++){
            Map nodeMap=new HashMap();
            JSONObject obj= (JSONObject) jsonArray.get(i);
            nodeMap.put("'clientid'","'"+obj.get("clientid")+"'");
            nodeMap.put("'ip_address'","'"+obj.get("ip_address")+"'");
            nodeMap.put("'send_pkt'","'"+obj.get("send_pkt")+"'");
            nodeMap.put("'recv_pkt'","'"+obj.get("recv_pkt")+"'");
            clientList.add(nodeMap);
        }
    }
    return clientList;

}



}
