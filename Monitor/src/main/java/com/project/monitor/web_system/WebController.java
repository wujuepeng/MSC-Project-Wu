package com.project.monitor.web_system;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.Map;

@RestController
public class WebController {


    @Resource
    private WebService webService;



    @RequestMapping("/getMachineInfo")
    public Map getMachineInfo() {

        return webService.getMachineInfo();
    }


    @RequestMapping("/getAPRouteTable")
    public String getAPRouteTable(String APLocation) {



        return webService.getAPRouteTable(APLocation);
    }


    @RequestMapping("/getNodeRouteTable")
    public String getNodeRouteTable(String machineInfo) {
        return webService.getNodeRouteTable(machineInfo);
    }



}
