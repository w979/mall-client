package com.wry.dao;

import com.wry.domain.Goods;
import com.wry.domain.GoodsExample;
import java.util.List;

import com.wry.utils.MybatisUtils;
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

public interface GoodsDao {
    //显示最新商品列表
    @Select("select *from mall_goods order by uptime desc limit 0,12")
    List<Goods> findNewGoods();

    //热卖商品列表
    @Select("select g.* from mall_order_item o left join mall_goods g on o.goodsid=g.id group by o.goodsid order by sum(nums) desc limit 0,10")
    List<Goods> findHotGoods();

    //根据商品类别查询商品
    default List<Goods> getGoodsByCategory(Integer categoryid){
        GoodsExample example = new GoodsExample();
        example.createCriteria().andCategoryidEqualTo(categoryid);
        return this.selectByExample(example);
    }

    //模糊查询
   default List<Goods> queryGoods(String goodname) throws Exception {
       GoodsExample example = new GoodsExample();
       example.createCriteria().andNameLike("%" + goodname + "%");
       return this.selectByExample(example);
   }
    @SelectProvider(type=GoodsSqlProvider.class, method="countByExample")
    long countByExample(GoodsExample example);

    @DeleteProvider(type=GoodsSqlProvider.class, method="deleteByExample")
    int deleteByExample(GoodsExample example);

    @Delete({
        "delete from mall_goods",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into mall_goods (name, goodsno, ",
        "author, publisher, ",
        "pubtime, categoryid, ",
        "image, stock, marketprice, ",
        "salesprice, uptime, ",
        "downtime, status, ",
        "description)",
        "values (#{name,jdbcType=VARCHAR}, #{goodsno,jdbcType=VARCHAR}, ",
        "#{author,jdbcType=VARCHAR}, #{publisher,jdbcType=VARCHAR}, ",
        "#{pubtime,jdbcType=VARCHAR}, #{categoryid,jdbcType=INTEGER}, ",
        "#{image,jdbcType=VARCHAR}, #{stock,jdbcType=INTEGER}, #{marketprice,jdbcType=DECIMAL}, ",
        "#{salesprice,jdbcType=DECIMAL}, #{uptime,jdbcType=TIMESTAMP}, ",
        "#{downtime,jdbcType=TIMESTAMP}, #{status,jdbcType=INTEGER}, ",
        "#{description,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Goods record);

    @InsertProvider(type=GoodsSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(Goods record);

    @SelectProvider(type=GoodsSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="goodsno", property="goodsno", jdbcType=JdbcType.VARCHAR),
        @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
        @Result(column="publisher", property="publisher", jdbcType=JdbcType.VARCHAR),
        @Result(column="pubtime", property="pubtime", jdbcType=JdbcType.VARCHAR),
        @Result(column="categoryid", property="categoryid", jdbcType=JdbcType.INTEGER),
        @Result(column="image", property="image", jdbcType=JdbcType.VARCHAR),
        @Result(column="stock", property="stock", jdbcType=JdbcType.INTEGER),
        @Result(column="marketprice", property="marketprice", jdbcType=JdbcType.DECIMAL),
        @Result(column="salesprice", property="salesprice", jdbcType=JdbcType.DECIMAL),
        @Result(column="uptime", property="uptime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="downtime", property="downtime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="description", property="description", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Goods> selectByExampleWithBLOBs(GoodsExample example);

    @SelectProvider(type=GoodsSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="goodsno", property="goodsno", jdbcType=JdbcType.VARCHAR),
        @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
        @Result(column="publisher", property="publisher", jdbcType=JdbcType.VARCHAR),
        @Result(column="pubtime", property="pubtime", jdbcType=JdbcType.VARCHAR),
        @Result(column="categoryid", property="categoryid", jdbcType=JdbcType.INTEGER),
        @Result(column="image", property="image", jdbcType=JdbcType.VARCHAR),
        @Result(column="stock", property="stock", jdbcType=JdbcType.INTEGER),
        @Result(column="marketprice", property="marketprice", jdbcType=JdbcType.DECIMAL),
        @Result(column="salesprice", property="salesprice", jdbcType=JdbcType.DECIMAL),
        @Result(column="uptime", property="uptime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="downtime", property="downtime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER)
    })
    List<Goods> selectByExample(GoodsExample example);

    @Select({
        "select",
        "id, name, goodsno, author, publisher, pubtime, categoryid, image, stock, marketprice, ",
        "salesprice, uptime, downtime, status, description",
        "from mall_goods",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="goodsno", property="goodsno", jdbcType=JdbcType.VARCHAR),
        @Result(column="author", property="author", jdbcType=JdbcType.VARCHAR),
        @Result(column="publisher", property="publisher", jdbcType=JdbcType.VARCHAR),
        @Result(column="pubtime", property="pubtime", jdbcType=JdbcType.VARCHAR),
        @Result(column="categoryid", property="categoryid", jdbcType=JdbcType.INTEGER),
        @Result(column="image", property="image", jdbcType=JdbcType.VARCHAR),
        @Result(column="stock", property="stock", jdbcType=JdbcType.INTEGER),
        @Result(column="marketprice", property="marketprice", jdbcType=JdbcType.DECIMAL),
        @Result(column="salesprice", property="salesprice", jdbcType=JdbcType.DECIMAL),
        @Result(column="uptime", property="uptime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="downtime", property="downtime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="description", property="description", jdbcType=JdbcType.LONGVARCHAR)
    })
    Goods selectByPrimaryKey(Integer id);

    @UpdateProvider(type=GoodsSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Goods record, @Param("example") GoodsExample example);

    @UpdateProvider(type=GoodsSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Goods record, @Param("example") GoodsExample example);

    @UpdateProvider(type=GoodsSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Goods record, @Param("example") GoodsExample example);

    @UpdateProvider(type=GoodsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Goods record);

    @Update({
        "update mall_goods",
        "set name = #{name,jdbcType=VARCHAR},",
          "goodsno = #{goodsno,jdbcType=VARCHAR},",
          "author = #{author,jdbcType=VARCHAR},",
          "publisher = #{publisher,jdbcType=VARCHAR},",
          "pubtime = #{pubtime,jdbcType=VARCHAR},",
          "categoryid = #{categoryid,jdbcType=INTEGER},",
          "image = #{image,jdbcType=VARCHAR},",
          "stock = #{stock,jdbcType=INTEGER},",
          "marketprice = #{marketprice,jdbcType=DECIMAL},",
          "salesprice = #{salesprice,jdbcType=DECIMAL},",
          "uptime = #{uptime,jdbcType=TIMESTAMP},",
          "downtime = #{downtime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=INTEGER},",
          "description = #{description,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(Goods record);

    @Update({
        "update mall_goods",
        "set name = #{name,jdbcType=VARCHAR},",
          "goodsno = #{goodsno,jdbcType=VARCHAR},",
          "author = #{author,jdbcType=VARCHAR},",
          "publisher = #{publisher,jdbcType=VARCHAR},",
          "pubtime = #{pubtime,jdbcType=VARCHAR},",
          "categoryid = #{categoryid,jdbcType=INTEGER},",
          "image = #{image,jdbcType=VARCHAR},",
          "stock = #{stock,jdbcType=INTEGER},",
          "marketprice = #{marketprice,jdbcType=DECIMAL},",
          "salesprice = #{salesprice,jdbcType=DECIMAL},",
          "uptime = #{uptime,jdbcType=TIMESTAMP},",
          "downtime = #{downtime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Goods record);
}