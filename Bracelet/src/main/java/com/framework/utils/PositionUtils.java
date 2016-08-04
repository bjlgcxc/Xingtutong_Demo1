package com.framework.utils;

/**
 * ����ͼAPI����ϵͳ�Ƚ���ת��;
 * WGS84����ϵ������������ϵ��������ͨ�õ�����ϵ���豸һ�����GPSоƬ���߱���оƬ��ȡ�ľ�γ��ΪWGS84��������ϵ,
 * �ȸ��ͼ���õ���WGS84��������ϵ���й���Χ���⣩;
 * GCJ02����ϵ������������ϵ�������й����Ҳ����ƶ��ĵ�����Ϣϵͳ������ϵͳ����WGS84����ϵ�����ܺ������ϵ��
 * �ȸ��й���ͼ�������й���ͼ���õ���GCJ02��������ϵ; BD09����ϵ�����ٶ�����ϵ��GCJ02����ϵ�����ܺ������ϵ;
 * �ѹ�����ϵ��ͼ������ϵ�ȣ�����Ҳ����GCJ02�����ϼ��ܶ��ɵġ� chenhua
 */
public class PositionUtils
{
    public static double pi = 3.1415926535897932384626;
    public static double a = 6378245.0;
    public static double ee = 0.00669342162296594323;
    final static double DEFAULT_LATITUDE = 39.913423;
    final static double DEFAULT_LONGITUDE = 116.368904;

    /**
     * 84 to ��������ϵ (GCJ-02) World Geodetic System ==> Mars Geodetic System
     *
     * @param lat
     * @param lon
     * @return
     */
    public static Gps wgs84_To_Gcj02(double lat, double lon)
    {
        if(Math.abs(lat) < 0.0001 && Math.abs(lon) < 0.0001)
        {
            return new Gps(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
        }
        if (outOfChina(lat, lon))
        {
            return new Gps(lat, lon);
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new Gps(mgLat, mgLon);
    }

    /**
     * * ��������ϵ (GCJ-02) to 84 * * @param lon * @param lat * @return
     */
    public static Gps gcj02_To_Wgs84(double lat, double lon)
    {
        Gps gps = transform(lat, lon);
        double lontitude = lon * 2 - gps.getLongitude();
        double latitude = lat * 2 - gps.getLatitude();
        return new Gps(latitude, lontitude);
    }

    /**
     * ��������ϵ (GCJ-02) ��ٶ�����ϵ (BD-09) ��ת���㷨 �� GCJ-02 ����ת���� BD-09 ����
     *
     * @param gg_lat
     * @param gg_lon
     */
    public static Gps gcj02_To_Bd09(double gg_lat, double gg_lon)
    {
        double x = gg_lon, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        return new Gps(bd_lat, bd_lon);
    }

    /**
     * * ��������ϵ (GCJ-02) ��ٶ�����ϵ (BD-09) ��ת���㷨 * * �� BD-09 ����ת����GCJ-02 ���� * * @param
     * bd_lat * @param bd_lon * @return
     */
    public static Gps bd09_To_Gcj02(double bd_lat, double bd_lon)
    {
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new Gps(gg_lat, gg_lon);
    }

    public static boolean outOfChina(double lat, double lon)
    {
        if (lon < 72.004 || lon > 137.8347) return true;
        if (lat < 0.8293 || lat > 55.8271) return true;
        return false;
    }

    public static Gps transform(double lat, double lon)
    {
        if (outOfChina(lat, lon))
        {
            return new Gps(lat, lon);
        }
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        return new Gps(mgLat, mgLon);
    }

    public static double transformLat(double x, double y)
    {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    public static double transformLon(double x, double y)
    {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }

    private static final double EARTH_RADIUS = 6370996.81002;
    private static final double ONE_RADIAN = pi / 180.0;
    public static double getDistance(double latitude1, double longitude1, double latitude2,double longitude2)
    {
        double a = (latitude1 - latitude2) * ONE_RADIAN;
        double b = (longitude1 - longitude2) * ONE_RADIAN;
        double distance = EARTH_RADIUS * 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(latitude1 * ONE_RADIAN)*Math.cos(latitude2 * ONE_RADIAN)*Math.pow(Math.sin(b/2),2)));
        return distance;
    }

    public static Gps gcj02_to_Wgs84_exact(double gcjLat, double gcjLon)
    {
        double initDelta = 0.01;
        double threshold = 0.000000001;
        double dLat = initDelta, dLon = initDelta;
        double mLat = gcjLat - dLat, mLon = gcjLon - dLon;
        double pLat = gcjLat + dLat, pLon = gcjLon + dLon;
        double wgsLat, wgsLon, i = 0;
        while (true)
        {
            wgsLat = (mLat + pLat) / 2;
            wgsLon = (mLon + pLon) / 2;
            Gps tmp = wgs84_To_Gcj02(wgsLat, wgsLon);
            dLat = tmp.getLatitude() - gcjLat;
            dLon = tmp.getLongitude() - gcjLon;
            if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold)) break;
            if (dLat > 0) pLat = wgsLat;
            else mLat = wgsLat;
            if (dLon > 0) pLon = wgsLon;
            else mLon = wgsLon;

            if (++i > 10000) break;
        }
        return new Gps(wgsLat, wgsLon);
    }

}