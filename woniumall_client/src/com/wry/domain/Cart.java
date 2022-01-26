package com.wry.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer userid;

    private Integer goodsid;

    private BigDecimal price;

    private Integer nums;

    private Date addtime;

    //用户对象属性
    private User user;
    //商品属性
    private Goods goods;



    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userid=" + userid +
                ", goodsid=" + goodsid +
                ", price=" + price +
                ", nums=" + nums +
                ", addtime=" + addtime +
                '}'+"\n";
    }
}