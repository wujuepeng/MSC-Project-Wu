package com.project.monitor.networkstatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ping {


    public static String ping(String ipAddress) throws Exception {
        String line = null;
        String pingTime="0";
        try {
            Process pro = Runtime.getRuntime().exec("ping " + ipAddress);
            BufferedReader buf = new BufferedReader(new InputStreamReader(
                    pro.getInputStream()));
            int count =0;
            while ((line = buf.readLine()) != null)
            {
                count++;
                System.out.println(line);
                if(count>1){
                    pingTime=line;
                    String ping[]=null;
                    ping=pingTime.split(" ");
                    if(ping.length<7){
                        return "0";
                    }
                    ping=ping[6].split("=");
                    pingTime=ping[1];
                    return pingTime;
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return pingTime;
    }




}
