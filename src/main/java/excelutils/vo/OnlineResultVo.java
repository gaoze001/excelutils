package excelutils.vo;

import lombok.Data;
import org.apache.commons.collections.map.HashedMap;
import util.ExcelExport;
import util.ExcelImport;

import java.util.Map;

@Data
public class OnlineResultVo {
    //车牌号
    //车牌号
    @ExcelImport("车牌号")
    @ExcelExport("车牌号")
    private String carCode;
    //收款日期
    @ExcelImport("收款日期")
    @ExcelExport("收款日期")
    private String shouKuanCode;
    //司机姓名
    @ExcelImport("司机姓名")
    @ExcelExport("司机姓名")
    private String carUserName;
    //应交月租金
    @ExcelImport("应交月租金")
    @ExcelExport("应交月租金")
    private String monthRentAll;
    //押金
    @ExcelImport("押金")
    @ExcelExport("押金")
    private String cash;
    //已交月租金
    @ExcelImport("已交月租金")
    @ExcelExport("已交月租金")
    private String monthRentNow;
    //期数
    @ExcelImport("期数")
    @ExcelExport("期数")
    private String numberStr;
    //当月租金所属月份
    @ExcelImport("当月租金所属月份")
    @ExcelExport("当月租金所属月份")
    private String rentOfMonth;
    //租金收款方式
    @ExcelImport("租金收款方式")
    @ExcelExport("租金收款方式")
    private String rentPayMethod;
    //备注
    @ExcelImport("备注")
    @ExcelExport("备注")
    private String remark;
    //其他费用
    @ExcelImport("其他费用")
    @ExcelExport("其他费用")
    private String otherCost;
    //其他费用金额
    @ExcelImport("其他费用金额")
    @ExcelExport("其他费用金额")
    private String otherCostAmount;
    //其他费用收款日期
    @ExcelImport("其他费用收款日期")
    @ExcelExport("其他费用收款日期")
    private String otherCostDate;
    //其他费用收款方式
    @ExcelImport("其他费用收款方式")
    @ExcelExport("其他费用收款方式")
    private String otherCostPayMethod;
    //备注
    private String remarkOne;
    //其他费用收款凭证
    @ExcelImport("其他费用收款凭证")
    @ExcelExport("其他费用收款凭证")
    private String otherCostVoucher;
    //其他费用收款凭证编号
    @ExcelImport("其他费用收款凭证编号")
    @ExcelExport("其他费用收款凭证编号")
    private String otherCostCode;

    public static int compareByNameThenAge(OnlineResultVo h1, OnlineResultVo h2) {
        if (h2.getShouKuanCode().equals(h1.getShouKuanCode())) {
            return StringCountInt(h2.getNumberStr()).compareTo(StringCountInt(h1.getNumberStr()));
        }
        return h2.getShouKuanCode().compareTo(h1.getShouKuanCode());
    }

    private static Long StringCountInt(String s){
        Map<String,Long> map = new HashedMap();
        map.put("一期",1L);
        map.put("二期",2L);
        map.put("三期",3L);
        map.put("四期",4L);
        map.put("五期",5L);
        map.put("六期",6L);
        map.put("七期",7L);
        map.put("八期",8L);
        map.put("九期",9L);
        map.put("十期",10L);
        map.put("十一期",11L);
        map.put("十二期",12L);
        map.put("十三期",13L);
        map.put("十四期",14L);
        map.put("十五期",15L);
        map.put("十六期",16L);
        map.put("十七期",17L);
        map.put("十八期",18L);
        map.put("十九期",19L);
        map.put("二十期",20L);
        map.put("二十一期",21L);
        map.put("二十二期",22L);
        map.put("二十三期",23L);
        map.put("二十四期",24L);
        map.put("二十五期",25L);
        map.put("二十六期",26L);
        map.put("二十七期",27L);
        map.put("二十八期",28L);
        map.put("二十九期",29L);
        map.put("三十期",30L);

        return map.get(s);
    }

}

