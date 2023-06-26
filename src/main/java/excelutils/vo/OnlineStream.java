package excelutils.vo;

import lombok.Data;
import util.ExcelImport;

@Data
public class OnlineStream {
    //交易方
    @ExcelImport("交易方")
    private String carUserName;
    //交易金额
    @ExcelImport("交易金额")
    private String monthRentNow;
    //车牌号
    @ExcelImport("车牌号")
    private String carCode;
    //备注
    @ExcelImport("备注")
    private String remark;

}
