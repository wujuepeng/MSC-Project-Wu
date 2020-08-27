package com.project.monitor.networkstatus;



import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;


public class CaculateDistance {


        public static double getDistance(String coordinate1,String coordinate2){
            if(coordinate1==null||"".equals(coordinate1)){
                return 0;
            }
            if(coordinate2==null||"".equals(coordinate2)){
                return 0;
            }
            String str1[]=coordinate1.split(",");
            double latitude1=Double.parseDouble(str1[0]);
            double longitude1=Double.parseDouble(str1[1]);

            String str2[]=coordinate2.split(",");
            double latitude2=Double.parseDouble(str2[0]);
            double longitude2=Double.parseDouble(str2[1]);

            GlobalCoordinates source = new GlobalCoordinates(latitude1, longitude1);
            GlobalCoordinates target = new GlobalCoordinates(latitude2, longitude2);

            double meter1 = getDistanceMeter(source, target, Ellipsoid.Sphere);
            //double meter2 = getDistanceMeter(source, target, Ellipsoid.WGS84);
            return meter1;
        }

        public static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid){

            GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);

            return geoCurve.getEllipsoidalDistance();
        }





}



