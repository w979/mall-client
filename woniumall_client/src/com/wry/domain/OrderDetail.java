package com.wry.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private Integer orderid;

    private Integer goodsid;

    private Integer nums;

    private BigDecimal price;
    //商品对象
    private Goods goods;
    //订单对象
    private Orders orders;

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", orderid=" + orderid +
                ", goodsid=" + goodsid +
                ", nums=" + nums +
                ", price=" + price +
                '}'+"\n";
    }
}