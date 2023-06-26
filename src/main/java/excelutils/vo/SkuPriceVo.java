package excelutils.vo;

import lombok.Data;

@Data
public class SkuPriceVo {
    private String sku;
    private String price;
    private String dateStr;
    private String mark;
}
