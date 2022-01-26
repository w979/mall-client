package com.wry.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String orderno;

    private Integer userid;

    private Date ordertime;

    private String accept;

    private String telphone;

    private String address;

    private BigDecimal money;

    private Integer paytype;

    private Date paytime;

    private Date delivertime;

    private Date receivetime;

    private Integer status;

    private String isdel;

    //用户对象属性
    private User user;

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", orderno='" + orderno + '\'' +
                ", userid=" + userid +
                ", ordertime=" + ordertime +
                ", accept='" + accept + '\'' +
                ", telphone='" + telphone + '\'' +
                ", address='" + address + '\'' +
                ", money=" + money +
                ", paytype=" + paytype +
                ", paytime=" + paytime +
                ", delivertime=" + delivertime +
                ", receivetime=" + receivetime +
                ", status=" + status +
                ", isdel='" + isdel + '\'' +
                '}';
    }
}