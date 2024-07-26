package excelutils;

import excelutils.vo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteUtil {

    public static void init() throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS onlineresult(carCode text," +
                "shouKuanCode text," +
                "carUserName text," +
                "monthRentAll text,"+
                "cash text,"+
                "monthRentNow text,"+
                "numberStr text,"+
                "rentOfMonth text,"+
                "rentPayMethod text,"+
                "remark text,"+
                "otherCost text,"+
                "otherCostAmount text,"+
                "otherCostDate text,"+
                "otherCostPayMethod text,"+
                "remarkOne text,"+
                "otherCostVoucher text,"+
                "otherCostCode text"+
                ")");
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS onlinefinal(carCode text," +
                "shouKuanCode text," +
                "carUserName text," +
                "monthRentAll text,"+
                "cash text,"+
                "monthRentNow text,"+
                "numberStr text,"+
                "rentOfMonth text,"+
                "rentPayMethod text,"+
                "remark text,"+
                "otherCost text,"+
                "otherCostAmount text,"+
                "otherCostDate text,"+
                "otherCostPayMethod text,"+
                "remarkOne text,"+
                "otherCostVoucher text,"+
                "otherCostCode text"+
                ")");
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS onlinestream(carCode text,carUserName text,monthRentNow text,remark text)");
//        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS price(itemname STRING,itemprice INTEGER,regioncode STRING,itemnum INTEGER)");
//        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS item(itemname STRING)");
//        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS timetemp(nowstr STRING)");
        stmt.close();
        conn.close();
    }
    public void insertOnlineResult(List<OnlineResultVo> onlineResultVoList)throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        onlineResultVoList.forEach(i->{
            try {
                stmt.executeUpdate("INSERT INTO onlineresult VALUES('"+i.getCarCode()+"', '"+i.getShouKuanCode()+"', '"+i.getCarUserName()+"', '"+i.getMonthRentAll()+"', '"+i.getCash()+"', '"+i.getMonthRentNow()+"', '"+i.getNumberStr()+"', '"+i.getRentOfMonth()+"', '"+i.getRentPayMethod()+"', '"+i.getRemark()+"', '"+i.getOtherCost()+"', '"+i.getOtherCostAmount()+"', '"+i.getOtherCostDate()+"', '"+i.getOtherCostPayMethod()+"', '"+i.getRemarkOne()+"', '"+i.getOtherCostVoucher()+"', '"+i.getOtherCostCode()+"')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stmt.close();
        conn.close();
    }
    public void insertOnlineStream(List<OnlineStream> onlineResultVoList)throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        onlineResultVoList.forEach(i->{
            try {
                stmt.executeUpdate("INSERT INTO onlinestream VALUES('"+i.getCarCode()+"', '"+i.getCarUserName()+"', '"+i.getMonthRentNow()+"', '"+i.getRemark()+"')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stmt.close();
        conn.close();
    }
    public void insertOnlineFinal(List<OnlineResultVo> onlineResultVoList)throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        onlineResultVoList.forEach(i->{
            try {
                stmt.executeUpdate("INSERT INTO onlinefinal VALUES('"+i.getCarCode()+"', '"+i.getShouKuanCode()+"', '"+i.getCarUserName()+"', '"+i.getMonthRentAll()+"', '"+i.getCash()+"', '"+i.getMonthRentNow()+"', '"+i.getNumberStr()+"', '"+i.getRentOfMonth()+"', '"+i.getRentPayMethod()+"', '"+i.getRemark()+"', '"+i.getOtherCost()+"', '"+i.getOtherCostAmount()+"', '"+i.getOtherCostDate()+"', '"+i.getOtherCostPayMethod()+"', '"+i.getRemarkOne()+"', '"+i.getOtherCostVoucher()+"', '"+i.getOtherCostCode()+"')");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        stmt.close();
        conn.close();
    }
    public void removeOnlineResult() throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        boolean rs = stmt.execute("DELETE FROM onlineresult WHERE 1=1");
        stmt.close();
        conn.close();
    }
    public void removeOnlineFinal() throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        boolean rs = stmt.execute("DELETE FROM onlinefinal WHERE 1=1");
        stmt.close();
        conn.close();
    }
    public void removeOnlineStream() throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        boolean rs = stmt.execute("DELETE FROM onlineStream WHERE 1=1");
        stmt.close();
        conn.close();
    }
    public List<OnlineStream> queryOnlineStreamForUser(String carCode,String carUserName) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM onlinestream where carCode = '"+carCode+"' and carUserName='"+carUserName+"'");
        List<OnlineStream> itemList = new ArrayList<>();
        while(rs.next()){
            OnlineStream ysSjVo = new OnlineStream();
            ysSjVo.setCarCode(rs.getString("carCode"));
            ysSjVo.setCarUserName(rs.getString("carUserName"));
            ysSjVo.setMonthRentNow(rs.getString("monthRentNow"));
            ysSjVo.setRemark(rs.getString("remark"));
            itemList.add(ysSjVo);
        }
        stmt.close();
        conn.close();
        return itemList;
    }
    public List<OnlineStream> queryAllOnlineStream()throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM onlinestream");
        List<OnlineStream> itemList = new ArrayList<>();
        while(rs.next()){
            OnlineStream ysSjVo = new OnlineStream();
            ysSjVo.setCarCode(rs.getString("carCode"));
            ysSjVo.setCarUserName(rs.getString("carUserName"));
            ysSjVo.setMonthRentNow(rs.getString("monthRentNow"));
            ysSjVo.setRemark(rs.getString("remark"));
            itemList.add(ysSjVo);
        }
        stmt.close();
        conn.close();
        return itemList;
    }
    public List<OnlineFinalVo> queryAllOnlineFinal()throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM onlinefinal order by carCode,carUserName,rentOfMonth,NumberStr");
        List<OnlineFinalVo> itemList = new ArrayList<>();
        while(rs.next()){
            OnlineFinalVo ysSjVo = new OnlineFinalVo();
            ysSjVo.setCarCode(rs.getString("carCode"));
            ysSjVo.setCarUserName(rs.getString("carUserName"));
            ysSjVo.setMonthRentNow(rs.getString("monthRentNow"));
            ysSjVo.setNumberStr(rs.getString("NumberStr"));
            ysSjVo.setRentOfMonth(rs.getString("rentOfMonth"));
            itemList.add(ysSjVo);
        }
        stmt.close();
        conn.close();
        return itemList;
    }

    public List<String> queryAllUserName()throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT carCode,carUserName FROM onlineresult  GROUP BY carCode,carUserName");
        List<String> regionList = new ArrayList<>();
        while(rs.next()){
            regionList.add(rs.getString("carCode")+":"+rs.getString("carUserName"));
        }
        stmt.close();
        conn.close();
        return regionList;
    }
    public List<OnlineResultVo> queryAllOnlineResult()throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM onlineresult order by carCode ,carUserName,shouKuanCode,case numberStr\n" +
                "WHEN '一期' THEN 1\n" +
                "WHEN '二期' THEN 2\n" +
                "WHEN '三期' THEN 3\n" +
                "WHEN '四期' THEN 4\n" +
                "WHEN '五期' THEN 5\n" +
                "WHEN '六期' THEN 6\n" +
                "WHEN '七期' THEN 7\n" +
                "WHEN '八期' THEN 8\n" +
                "WHEN '九期' THEN 9\n" +
                "WHEN '十期' THEN 10\n" +
                "WHEN '十一期' THEN 11\n" +
                "WHEN '十二期' THEN 12\n" +
                "WHEN '十三期' THEN 13\n" +
                "WHEN '十四期' THEN 14\n" +
                "WHEN '十五期' THEN 15\n" +
                "WHEN '十六期' THEN 16\n" +
                "WHEN '十七期' THEN 17\n" +
                "WHEN '十八期' THEN 18\n" +
                "WHEN '十九期' THEN 19\n" +
                "WHEN '二十期' THEN 20\n" +
                "WHEN '二十一期' THEN 21\n" +
                "WHEN '二十二期' THEN 22\n" +
                "WHEN '二十三期' THEN 23\n" +
                "WHEN '二十四期' THEN 24\n" +
                "WHEN '二十五期' THEN 25\n" +
                "WHEN '二十六期' THEN 26\n" +
                "WHEN '二十七期' THEN 27\n" +
                "WHEN '二十八期' THEN 28\n" +
                "WHEN '二十九期' THEN 29\n" +
                "WHEN '三十期' THEN 30\n" +
                "ELSE 0 END ");
        List<OnlineResultVo> itemList = new ArrayList<>();
        while(rs.next()){
            OnlineResultVo ysSjVo = new OnlineResultVo();
            ysSjVo.setCarCode(rs.getString("carCode"));
            ysSjVo.setShouKuanCode(rs.getString("shouKuanCode"));
            ysSjVo.setCarUserName(rs.getString("carUserName"));
            ysSjVo.setMonthRentAll(rs.getString("monthRentAll"));
            ysSjVo.setCash(rs.getString("cash"));
            ysSjVo.setMonthRentNow(rs.getString("monthRentNow"));
            ysSjVo.setNumberStr(rs.getString("NumberStr"));
            ysSjVo.setRentOfMonth(rs.getString("rentOfMonth"));
            ysSjVo.setRentPayMethod(rs.getString("RentPayMethod"));
            ysSjVo.setRemark(rs.getString("Remark"));
            ysSjVo.setOtherCost(rs.getString("OtherCost"));
            ysSjVo.setOtherCostAmount(rs.getString("OtherCostAmount"));
            ysSjVo.setOtherCostDate(rs.getString("OtherCostDate"));
            ysSjVo.setOtherCostPayMethod(rs.getString("OtherCostPayMethod"));
            ysSjVo.setRemarkOne(rs.getString("RemarkOne"));
            ysSjVo.setOtherCostVoucher(rs.getString("OtherCostVoucher"));
            ysSjVo.setOtherCostCode(rs.getString("OtherCostCode"));
            itemList.add(ysSjVo);
        }
        stmt.close();
        conn.close();
        return itemList;
    }
    public List<OnlineResultVo> queryOnlineResultForCarCode(String carCode,String userncme)throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM onlineresult WHERE carCode ='"+carCode+"' and carUserName='"+userncme+"'");
        List<OnlineResultVo> itemList = new ArrayList<>();
        while(rs.next()){
            OnlineResultVo ysSjVo = new OnlineResultVo();
            ysSjVo.setCarCode(rs.getString("carCode"));
            ysSjVo.setShouKuanCode(rs.getString("shouKuanCode"));
            ysSjVo.setCarUserName(rs.getString("carUserName"));
            ysSjVo.setMonthRentAll(rs.getString("monthRentAll"));
            ysSjVo.setCash(rs.getString("cash"));
            ysSjVo.setMonthRentNow(rs.getString("monthRentNow"));
            ysSjVo.setNumberStr(rs.getString("NumberStr"));
            ysSjVo.setRentOfMonth(rs.getString("rentOfMonth"));
            ysSjVo.setRentPayMethod(rs.getString("RentPayMethod"));
            ysSjVo.setRemark(rs.getString("Remark"));
            ysSjVo.setOtherCost(rs.getString("OtherCost"));
            ysSjVo.setOtherCostAmount(rs.getString("OtherCostAmount"));
            ysSjVo.setOtherCostDate(rs.getString("OtherCostDate"));
            ysSjVo.setOtherCostPayMethod(rs.getString("OtherCostPayMethod"));
            ysSjVo.setRemarkOne(rs.getString("RemarkOne"));
            ysSjVo.setOtherCostVoucher(rs.getString("OtherCostVoucher"));
            ysSjVo.setOtherCostCode(rs.getString("OtherCostCode"));
            itemList.add(ysSjVo);
        }
        stmt.close();
        conn.close();
        return itemList;
    }
    public List<OnlineResultVo> queryOnlineResultForUser(String userName)throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM onlineresult WHERE carUserName like '%"+userName+"%'");
        List<OnlineResultVo> itemList = new ArrayList<>();
        while(rs.next()){
            OnlineResultVo ysSjVo = new OnlineResultVo();
            ysSjVo.setCarCode(rs.getString("carCode"));
            ysSjVo.setShouKuanCode(rs.getString("shouKuanCode"));
            ysSjVo.setCarUserName(rs.getString("carUserName"));
            ysSjVo.setMonthRentAll(rs.getString("monthRentAll"));
            ysSjVo.setCash(rs.getString("cash"));
            ysSjVo.setMonthRentNow(rs.getString("monthRentNow"));
            ysSjVo.setNumberStr(rs.getString("NumberStr"));
            ysSjVo.setRentOfMonth(rs.getString("rentOfMonth"));
            ysSjVo.setRentPayMethod(rs.getString("RentPayMethod"));
            ysSjVo.setRemark(rs.getString("Remark"));
            ysSjVo.setOtherCost(rs.getString("OtherCost"));
            ysSjVo.setOtherCostAmount(rs.getString("OtherCostAmount"));
            ysSjVo.setOtherCostDate(rs.getString("OtherCostDate"));
            ysSjVo.setOtherCostPayMethod(rs.getString("OtherCostPayMethod"));
            ysSjVo.setRemarkOne(rs.getString("RemarkOne"));
            ysSjVo.setOtherCostVoucher(rs.getString("OtherCostVoucher"));
            ysSjVo.setOtherCostCode(rs.getString("OtherCostCode"));
            itemList.add(ysSjVo);
        }
        stmt.close();
        conn.close();
        return itemList;
    }

//    public void insertRegion(List<RegionVo> regionVos)throws Exception{
//        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
//        Statement stmt = conn.createStatement();
//        regionVos.forEach(i->{
//            try {
//                stmt.executeUpdate("INSERT INTO region VALUES('"+i.getRegion()+"', '"+i.getArea()+"','"+i.getRegionCode()+"')");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        });
//        stmt.close();
//        conn.close();
//    }

//    public void insertCost(List<SkuPriceVo> skuPriceVo)throws Exception{
//        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
//        Statement stmt = conn.createStatement();
//        skuPriceVo.forEach(i->{
//            try {
//                stmt.executeUpdate("INSERT INTO cost VALUES('"+i.getSku()+"', '"+i.getPrice()+"','"+i.getDateStr()+"','"+i.getMark()+"')");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        });
//        stmt.close();
//        conn.close();
//    }
//    public void insertItemEnum(String item)throws Exception{
//        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
//        Statement stmt = conn.createStatement();
//        try {
//            stmt.executeUpdate("INSERT INTO item VALUES('"+item+"')");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        stmt.close();
//        conn.close();
//    }
//    public void insertPrice(PriceVo priceVo)throws Exception{
//        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
//        Statement stmt = conn.createStatement();
//            try {
//                stmt.executeUpdate("INSERT INTO price VALUES('"+priceVo.getItemName()+"', '"+priceVo.getItemPrice()+"','"+priceVo.getRegionCode()+"','"+priceVo.getItemNum()+"')");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        stmt.close();
//        conn.close();
//    }

//    public List<String> queryAllRegion()throws Exception{
//        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT distinct(region) region FROM region");
//        List<String> regionList = new ArrayList<>();
//        while(rs.next()){
//            regionList.add(rs.getString("region"));
//        }
//        stmt.close();
//        conn.close();
//        return regionList;
//    }
//    public List<String> queryAllItem()throws Exception{
//        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT itemname FROM item");
//        List<String> itemList = new ArrayList<>();
//        while(rs.next()){
//            itemList.add(rs.getString("itemname"));
//        }
//        stmt.close();
//        conn.close();
//        return itemList;
//    }
//    public List<SkuPriceVo> queryAllCost()throws Exception{
//        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT sku,price,datestr,mark FROM cost ");
//        List<SkuPriceVo> skuPriceVoList = new ArrayList<>();
//        while(rs.next()){
//            SkuPriceVo skuPriceVo = new SkuPriceVo();
//            skuPriceVo.setSku(rs.getString("sku"));
//            skuPriceVo.setPrice(rs.getString("price"));
//            skuPriceVo.setDateStr(rs.getString("datestr"));
//            skuPriceVo.setMark(rs.getString("mark"));
//            skuPriceVoList.add(skuPriceVo);
//        }
//        stmt.close();
//        conn.close();
//        return skuPriceVoList;
//    }
//    public List<String> queryAllSkc(String mark)throws Exception{
//        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT sku FROM cost WHERE mark='"+mark+"'");
//        List<String> itemList = new ArrayList<>();
//        while(rs.next()){
//            itemList.add(rs.getString("sku"));
//        }
//        stmt.close();
//        conn.close();
//        return itemList;
//    }
//    public void removeSku(String sku,String mark)throws Exception{
//        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
//        Statement stmt = conn.createStatement();
//        boolean rs = stmt.execute("DELETE FROM cost WHERE sku='"+sku+"' AND mark='"+mark+"'");
//        stmt.close();
//        conn.close();
//    }
//    public List<SkuPriceVo> queryCostBySku(String sku)throws Exception{
//        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT sku,price,datestr,mark FROM cost  where sku like '%"+sku+"%'");
//        List<SkuPriceVo> skuPriceVoList = new ArrayList<>();
//        while(rs.next()){
//            SkuPriceVo skuPriceVo = new SkuPriceVo();
//            skuPriceVo.setSku(rs.getString("sku"));
//            skuPriceVo.setPrice(rs.getString("price"));
//            skuPriceVo.setDateStr(rs.getString("datestr"));
//            skuPriceVo.setMark(rs.getString("mark"));
//            skuPriceVoList.add(skuPriceVo);
//        }
//        stmt.close();
//        conn.close();
//        return skuPriceVoList;
//    }
//    public List<SkuPriceVo> queryCostByMark(String mark)throws Exception{
//        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT sku,price,datestr,mark FROM cost  where mark = '"+mark+"'");
//        List<SkuPriceVo> skuPriceVoList = new ArrayList<>();
//        while(rs.next()){
//            SkuPriceVo skuPriceVo = new SkuPriceVo();
//            skuPriceVo.setSku(rs.getString("sku"));
//            skuPriceVo.setPrice(rs.getString("price"));
//            skuPriceVo.setDateStr(rs.getString("datestr"));
//            skuPriceVo.setMark(rs.getString("mark"));
//            skuPriceVoList.add(skuPriceVo);
//        }
//        stmt.close();
//        conn.close();
//        return skuPriceVoList;
//    }


//    public List<RegionVo> queryAreaByRegion(String region)throws Exception{
//        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT region,area,regioncode FROM region where region='"+region+"'");
//        List<RegionVo> regionList = new ArrayList<>();
//        while(rs.next()){
//            RegionVo regionVo = new RegionVo();
//            regionVo.setRegion(rs.getString("region"));
//            regionVo.setArea(rs.getString("area"));
//            regionVo.setRegionCode(rs.getString("regioncode"));
//            regionList.add(regionVo);
//        }
//        stmt.close();
//        conn.close();
//        return regionList;
//    }
//
//    public List<PriceVo> queryPriceByRegion(String regionCode)throws Exception{
//        Connection conn = DriverManager.getConnection("jdbc:sqlite:zking.db");
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT itemname,itemprice,regioncode,itemnum FROM price where regioncode='"+regionCode+"' ORDER BY itemname");
//        List<PriceVo> regionList = new ArrayList<>();
//        while(rs.next()){
//            PriceVo regionVo = new PriceVo();
//            regionVo.setItemName(rs.getString("itemname"));
//            regionVo.setItemPrice(rs.getInt("itemprice"));
//            regionVo.setRegionCode(rs.getString("regioncode"));
//            regionVo.setItemNum(rs.getInt("itemnum"));
//            regionList.add(regionVo);
//        }
//        stmt.close();
//        conn.close();
//        return regionList;
//    }


}
