package excelutils.vo;

import lombok.Data;
import util.ExcelExport;
import util.ExcelImport;

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

}

