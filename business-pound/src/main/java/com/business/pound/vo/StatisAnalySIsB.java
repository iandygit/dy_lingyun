package com.business.pound.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Getter
@Setter
@ToString
public class StatisAnalySIsB {

    private String name;
    private Double y;
    @JsonIgnore
    private Long  total;
    @JsonIgnore
    private Long count;
    @JsonIgnore
    DecimalFormat df = new DecimalFormat("0.00");//格式化小数
    public StatisAnalySIsB(String name,Long count,Long total){
        this.name=name;
        this.count=count;
        this.total=total;

        this.y=(new BigDecimal((float)count/total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())*100;
    }

}
