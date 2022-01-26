package com.wry.dao;

import com.wry.domain.Cart;
import com.wry.domain.CartExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface CartDao {
    //查询当前用户购物车信息
    List<Cart> getUserCart(@Param("userid") Integer userid);

    //查询购物车中是否有该商品
    default Cart getGoodByUserId(@Param("goodsid") Integer goodsid,@Param("userid") Integer userid){
        CartExample example = new CartExample();
        example.createCriteria().andGoodsidEqualTo(goodsid).andUseridEqualTo(userid);
        List<Cart> cartList = this.selectByExample(example);
        if (cartList.size() > 0){
            return  cartList.get(0);
        }
        return null;
    }

    //根据购物车id查询商品
    List<Cart> getCartGood(@Param("ids") String[] ids);

    //根据商品id和用户id删除对应购物车中的信息
    default int delCartGoods(@Param("goodsid") List<String> goodsid,@Param("userid") Integer userid) {
        CartExample example = new CartExample();
        example.createCriteria().andUseridEqualTo(userid).andGoodsidIn(goodsid);
       return this.deleteByExample(example);
    }

    @SelectProvider(type=CartSqlProvider.class, method="countByExample")
    long countByExample(CartExample example);

    @DeleteProvider(type=CartSqlProvider.class, method="deleteByExample")
    int deleteByExample(CartExample example);

    @Delete({
        "delete from mall_cart",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into mall_cart (userid, goodsid, ",
        "price, nums, addtime)",
        "values (#{userid,jdbcType=INTEGER}, #{goodsid,jdbcType=INTEGER}, ",
        "#{price,jdbcType=DECIMAL}, #{nums,jdbcType=INTEGER}, #{addtime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Cart record);

    @InsertProvider(type=CartSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(Cart record);

    @SelectProvider(type=CartSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="userid", property="userid", jdbcType=JdbcType.INTEGER),
        @Result(column="goodsid", property="goodsid", jdbcType=JdbcType.INTEGER),
        @Result(column="price", property="price", jdbcType=JdbcType.DECIMAL),
        @Result(column="nums", property="nums", jdbcType=JdbcType.INTEGER),
        @Result(column="addtime", property="addtime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Cart> selectByExample(CartExample example);

    @Select({
        "select",
        "id, userid, goodsid, price, nums, addtime",
        "from mall_cart",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="userid", property="userid", jdbcType=JdbcType.INTEGER),
        @Result(column="goodsid", property="goodsid", jdbcType=JdbcType.INTEGER),
        @Result(column="price", property="price", jdbcType=JdbcType.DECIMAL),
        @Result(column="nums", property="nums", jdbcType=JdbcType.INTEGER),
        @Result(column="addtime", property="addtime", jdbcType=JdbcType.TIMESTAMP)
    })
    Cart selectByPrimaryKey(Integer id);

    @UpdateProvider(type=CartSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Cart record, @Param("example") CartExample example);

    @UpdateProvider(type=CartSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Cart record, @Param("example") CartExample example);

    @UpdateProvider(type=CartSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Cart record);

    @Update({
        "update mall_cart",
        "set userid = #{userid,jdbcType=INTEGER},",
          "goodsid = #{goodsid,jdbcType=INTEGER},",
          "price = #{price,jdbcType=DECIMAL},",
          "nums = #{nums,jdbcType=INTEGER},",
          "addtime = #{addtime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Cart record);
}