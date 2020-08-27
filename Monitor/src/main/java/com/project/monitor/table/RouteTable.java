package com.project.monitor.table;


import com.project.monitor.domain.ComputerInfo;
import com.project.monitor.networkstatus.CaculateDistance;
import com.project.monitor.networkstatus.Ping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteTable {




    public String generateAPRouteTable(String str[], List<ComputerInfo> computerInfoList){

        if(computerInfoList.size()==0){
            return "";
        }

        String table="";
        List<Map> scoreList=new ArrayList<>();

        ComputerInfo[] packetsReceiveList=new ComputerInfo[computerInfoList.size()];
        ComputerInfo[] packetsSendList=new ComputerInfo[computerInfoList.size()];
        ComputerInfo[] distanceList=new ComputerInfo[computerInfoList.size()];
        ComputerInfo[] PingList=new ComputerInfo[computerInfoList.size()];



        for (int i=0;i<computerInfoList.size();i++){
            double totalScore=0;
            double distance= CaculateDistance.getDistance(str[1],computerInfoList.get(i).getCoordinate());
            computerInfoList.get(i).setDistance(distance+"");
            try {
                String pingTime= Ping.ping(computerInfoList.get(i).getIP());
                computerInfoList.get(i).setPingTime(pingTime);
                System.out.println("ping time="+pingTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Map scoreMap=new HashMap();
            scoreMap.put("IP",computerInfoList.get(i).getIP());
            double CPU=Double.parseDouble(computerInfoList.get(i).getCPUTotalUsed());
            double memory=Double.parseDouble(computerInfoList.get(i).getUsedMemoryRate());
            if(CPU>0.5){
                totalScore=totalScore+(1-CPU)*computerInfoList.size();
            }else {
                totalScore=totalScore+(1-CPU)*computerInfoList.size()*2;
            }

            if(memory>0.5){
                totalScore=totalScore+(1-CPU)*computerInfoList.size();
            }else {
                totalScore=totalScore+(1-CPU)*computerInfoList.size()*2;
            }

            scoreMap.put("weight",totalScore);
            packetsReceiveList[i]=computerInfoList.get(i);
            packetsSendList[i]=computerInfoList.get(i);
            distanceList[i]=computerInfoList.get(i);
            PingList[i]=computerInfoList.get(i);
            scoreList.add(scoreMap);
        }
        for (int i=0;i<computerInfoList.size();i++){


            for(int j=0;j<computerInfoList.size()-i-1;j++){

                if(Double.parseDouble(packetsReceiveList[j+1].getAcceptTotalPackets())>Double.parseDouble(packetsReceiveList[j].getAcceptTotalPackets())){
                    ComputerInfo temp=packetsReceiveList[j+1];
                    packetsReceiveList[j+1]=packetsReceiveList[j];
                    packetsReceiveList[j]=temp;

                }

                if(Double.parseDouble(packetsSendList[j+1].getSendTotalPackets())>Double.parseDouble(packetsSendList[j].getSendTotalPackets())){
                    ComputerInfo temp=packetsSendList[j+1];
                    packetsSendList[j+1]=packetsSendList[j];
                    packetsSendList[j]=temp;
                }

                if(Double.parseDouble(distanceList[j+1].getDistance())>Double.parseDouble(distanceList[j].getDistance())){
                    ComputerInfo temp=distanceList[j+1];
                    distanceList[j+1]=distanceList[j];
                    distanceList[j]=temp;
                }

                if(Double.parseDouble(PingList[j+1].getPingTime())>Double.parseDouble(PingList[j].getPingTime())){
                    ComputerInfo temp=PingList[j+1];
                    PingList[j+1]=PingList[j];
                    PingList[j]=temp;
                }
            }

        }
        for(int i=0;i<computerInfoList.size();i++){

            for(int k=0;k<computerInfoList.size();k++){

                if(packetsReceiveList[i].getIP().equals(scoreList.get(k).get("IP"))){
                    double score= (double) scoreList.get(k).get("weight");
                    score=score+i;
                    scoreList.get(k).put("weight",score);
                }

                if(packetsSendList[i].getIP().equals(scoreList.get(k).get("IP"))){
                    double score= (double) scoreList.get(k).get("weight");
                    score=score+i;
                    scoreList.get(k).put("weight",score);
                }

                if(distanceList[i].getIP().equals(scoreList.get(k).get("IP"))){
                    double score= (double) scoreList.get(k).get("weight");
                    score=score+i;
                    scoreList.get(k).put("weight",score);
                }

                if(PingList[i].getIP().equals(scoreList.get(k).get("IP"))){
                    double score= (double) scoreList.get(k).get("weight");
                    score=score+i;
                    scoreList.get(k).put("weight",score);
                }

            }

        }

        StringBuffer stringBuffer=new StringBuffer();
        for (int i=0;i<scoreList.size();i++){
            String IP= (String) scoreList.get(i).get("IP");
            IP="'IP'='"+IP+"',";
            stringBuffer.append(IP);
            Double weight=(Double) scoreList.get(i).get("weight");
            weight= Double.valueOf(Math.round(weight));
            String weightStr="'weight'="+"'"+weight+"'";
            stringBuffer.append(weightStr);
            if(i<scoreList.size()-1){
                stringBuffer.append(";");
            }

        }
        table=stringBuffer.toString();
        return table;
    }


    public String generateNodeRouteTable(String currentIP, List<ComputerInfo> computerInfoList){
        System.out.println("node table="+computerInfoList);

        String table="";
        List<Map> scoreList=new ArrayList<>();

        ComputerInfo[] packetsReceiveList=new ComputerInfo[computerInfoList.size()];
        ComputerInfo[] packetsSendList=new ComputerInfo[computerInfoList.size()];
        ComputerInfo[] distanceList=new ComputerInfo[computerInfoList.size()];
        ComputerInfo[] PingList=new ComputerInfo[computerInfoList.size()];

        int flag=0;
        ComputerInfo currentComputerInfo=new ComputerInfo();

        for(int i=0;i<computerInfoList.size();i++){
            if(currentIP.equals(computerInfoList.get(i).getIP())){
                currentComputerInfo=computerInfoList.get(i);
                flag=i;
                break;
            }
        }

        for (int i=0;i<computerInfoList.size();i++){
            double totalScore=0;
            double distance= CaculateDistance.getDistance(currentComputerInfo.getCoordinate(),computerInfoList.get(i).getCoordinate());
            computerInfoList.get(i).setDistance(distance+"");
            try {
                String pingTime= Ping.ping(computerInfoList.get(i).getIP());
                computerInfoList.get(i).setPingTime(pingTime);
                System.out.println("ping time="+pingTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Map scoreMap=new HashMap();
            scoreMap.put("IP",computerInfoList.get(i).getIP());
            double CPU=Double.parseDouble(computerInfoList.get(i).getCPUTotalUsed());
            double memory=Double.parseDouble(computerInfoList.get(i).getUsedMemoryRate());
            if(CPU>0.5){
                totalScore=totalScore+(1-CPU)*computerInfoList.size();
            }else {
                totalScore=totalScore+(1-CPU)*computerInfoList.size()*2;
            }

            if(memory>0.5){
                totalScore=totalScore+(1-CPU)*computerInfoList.size();
            }else {
                totalScore=totalScore+(1-CPU)*computerInfoList.size()*2;
            }

            scoreMap.put("weight",totalScore);
            packetsReceiveList[i]=computerInfoList.get(i);
            packetsSendList[i]=computerInfoList.get(i);
            distanceList[i]=computerInfoList.get(i);
            PingList[i]=computerInfoList.get(i);
            scoreList.add(scoreMap);
        }
        for (int i=0;i<computerInfoList.size();i++){

            for(int j=0;j<computerInfoList.size()-i-1;j++){

                if(Double.parseDouble(packetsReceiveList[j+1].getAcceptTotalPackets())>Double.parseDouble(packetsReceiveList[j].getAcceptTotalPackets())){
                    ComputerInfo temp=packetsReceiveList[j+1];
                    packetsReceiveList[j+1]=packetsReceiveList[j];
                    packetsReceiveList[j]=temp;

                }

                if(Double.parseDouble(packetsSendList[j+1].getSendTotalPackets())>Double.parseDouble(packetsSendList[j].getSendTotalPackets())){
                   ComputerInfo temp=packetsSendList[j+1];
                   packetsSendList[j+1]=packetsSendList[j];
                   packetsSendList[j]=temp;
                }

                if(Double.parseDouble(distanceList[j+1].getDistance())>Double.parseDouble(distanceList[j].getDistance())){
                    ComputerInfo temp=distanceList[j+1];
                    distanceList[j+1]=distanceList[j];
                    distanceList[j]=temp;
                }

                if(Double.parseDouble(PingList[j+1].getPingTime())>Double.parseDouble(PingList[j].getPingTime())){
                    ComputerInfo temp=PingList[j+1];
                    PingList[j+1]=PingList[j];
                    PingList[j]=temp;
                }
            }

        }
        for(int i=0;i<computerInfoList.size();i++){

            for(int k=0;k<computerInfoList.size();k++){

                if(packetsReceiveList[i].getIP().equals(scoreList.get(k).get("IP"))){
                    double score= (double) scoreList.get(k).get("weight");
                    score=score+i;
                    scoreList.get(k).put("weight",score);
                }

                if(packetsSendList[i].getIP().equals(scoreList.get(k).get("IP"))){
                    double score= (double) scoreList.get(k).get("weight");
                    score=score+i;
                    scoreList.get(k).put("weight",score);
                }

                if(distanceList[i].getIP().equals(scoreList.get(k).get("IP"))){
                    double score= (double) scoreList.get(k).get("weight");
                    score=score+i;
                    scoreList.get(k).put("weight",score);
                }

                if(PingList[i].getIP().equals(scoreList.get(k).get("IP"))){
                    double score= (double) scoreList.get(k).get("weight");
                    score=score+i;
                    scoreList.get(k).put("weight",score);
                }

            }

        }

        StringBuffer stringBuffer=new StringBuffer();
        for (int i=0;i<scoreList.size();i++){
            if((Double) scoreList.get(i).get("weight")>(Double)scoreList.get(flag).get("weight")){
                String IP= (String) scoreList.get(i).get("IP");
                IP="'IP'='"+IP+"',";
                stringBuffer.append(IP);
                Double weight=(Double) scoreList.get(i).get("weight");
                weight= Double.valueOf(Math.round(weight));
                String weightStr="'weight'="+"'"+weight+"'";
                stringBuffer.append(weightStr);
                if(i<scoreList.size()-1){
                    stringBuffer.append(";");
                }
            }

        }
        table=stringBuffer.toString();
        return table;
    }


//    public static void main(String[] args){
//
//        String str[]={"999","114.96843,25.824473"};
//        List<ComputerInfo> computerInfoList=new ArrayList<>();
//        ComputerInfo computerInfo1=new ComputerInfo();
//        computerInfo1.setPingTime("11");
//        computerInfo1.setAcceptTotalPackets("112223");
//        computerInfo1.setSendTotalPackets("9987");
//        computerInfo1.setCoordinate("114.96553,25.824223");
//        computerInfo1.setCPUTotalUsed("0.667");
//        computerInfo1.setUsedMemoryRate("0.445");
//        computerInfo1.setIP("www.baidu.com");
//        computerInfoList.add(computerInfo1);
//
//        ComputerInfo computerInfo2=new ComputerInfo();
//        computerInfo2.setPingTime("23");
//        computerInfo2.setAcceptTotalPackets("114323");
//        computerInfo2.setSendTotalPackets("8887");
//        computerInfo2.setCoordinate("114.98853,25.874223");
//        computerInfo2.setCPUTotalUsed("0.957");
//        computerInfo2.setUsedMemoryRate("0.4");
//        computerInfo2.setIP("127.0.0.1");
//        computerInfoList.add(computerInfo2);
//
//        ComputerInfo computerInfo3=new ComputerInfo();
//        computerInfo3.setPingTime("33");
//        computerInfo3.setAcceptTotalPackets("111123");
//        computerInfo3.setSendTotalPackets("9187");
//        computerInfo3.setCoordinate("114.87553,25.624223");
//        computerInfo3.setCPUTotalUsed("0.87");
//        computerInfo3.setUsedMemoryRate("0.645");
//        computerInfo3.setIP("10.1.0.33");
//        computerInfoList.add(computerInfo3);
//
//        ComputerInfo computerInfo4=new ComputerInfo();
//        computerInfo4.setPingTime("44");
//        computerInfo4.setAcceptTotalPackets("11223");
//        computerInfo4.setSendTotalPackets("9087");
//        computerInfo4.setCoordinate("114.56553,25.524223");
//        computerInfo4.setCPUTotalUsed("0.1");
//        computerInfo4.setUsedMemoryRate("0.1");
//        computerInfo4.setIP("10.1.0.44");
//        computerInfoList.add(computerInfo4);
//
//        RouteTable routeTable=new RouteTable();
//        String table =routeTable.generateNodeRouteTable("127.0.0.1",computerInfoList);
//        System.out.println(table);
//
//
//    }

}
