package excelutils.vo;

import lombok.Data;
import util.ExcelExport;
import util.ExcelImport;

@Data
public class OnlineFinalVo {
    //司机姓名
    @ExcelImport("司机姓名")
    @ExcelExport("司机姓名")
    private String carUserName;
    //已交月租金
    @ExcelImport("已交月租金")
    @ExcelExport("已交月租金")
    private String monthRentNow;
    //期数
    @ExcelExport("车牌号")
    @ExcelImport("车牌号")
    private String carCode;
    //期数
    @ExcelExport("期数")
    private String numberStr;
    //当月租金所属月份
    @ExcelExport("当月租金所属月份")
    private String rentOfMonth;

}

