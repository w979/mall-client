package com.wry.dao;

import com.wry.domain.Cart;
import com.wry.domain.CartExample.Criteria;
import com.wry.domain.CartExample.Criterion;
import com.wry.domain.CartExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class CartSqlProvider {
    public String countByExample(CartExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("mall_cart");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(CartExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("mall_cart");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Cart record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("mall_cart");
        
        if (record.getUserid() != null) {
            sql.VALUES("userid", "#{userid,jdbcType=INTEGER}");
        }
        
        if (record.getGoodsid() != null) {
            sql.VALUES("goodsid", "#{goodsid,jdbcType=INTEGER}");
        }
        
        if (record.getPrice() != null) {
            sql.VALUES("price", "#{price,jdbcType=DECIMAL}");
        }
        
        if (record.getNums() != null) {
            sql.VALUES("nums", "#{nums,jdbcType=INTEGER}");
        }
        
        if (record.getAddtime() != null) {
            sql.VALUES("addtime", "#{addtime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String selectByExample(CartExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("userid");
        sql.SELECT("goodsid");
        sql.SELECT("price");
        sql.SELECT("nums");
        sql.SELECT("addtime");
        sql.FROM("mall_cart");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Cart record = (Cart) parameter.get("record");
        CartExample example = (CartExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("mall_cart");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getUserid() != null) {
            sql.SET("userid = #{record.userid,jdbcType=INTEGER}");
        }
        
        if (record.getGoodsid() != null) {
            sql.SET("goodsid = #{record.goodsid,jdbcType=INTEGER}");
        }
        
        if (record.getPrice() != null) {
            sql.SET("price = #{record.price,jdbcType=DECIMAL}");
        }
        
        if (record.getNums() != null) {
            sql.SET("nums = #{record.nums,jdbcType=INTEGER}");
        }
        
        if (record.getAddtime() != null) {
            sql.SET("addtime = #{record.addtime,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("mall_cart");
        
        sql.SET("id = #{record.id,jdbcType=INTEGER}");
        sql.SET("userid = #{record.userid,jdbcType=INTEGER}");
        sql.SET("goodsid = #{record.goodsid,jdbcType=INTEGER}");
        sql.SET("price = #{record.price,jdbcType=DECIMAL}");
        sql.SET("nums = #{record.nums,jdbcType=INTEGER}");
        sql.SET("addtime = #{record.addtime,jdbcType=TIMESTAMP}");
        
        CartExample example = (CartExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Cart record) {
        SQL sql = new SQL();
        sql.UPDATE("mall_cart");
        
        if (record.getUserid() != null) {
            sql.SET("userid = #{userid,jdbcType=INTEGER}");
        }
        
        if (record.getGoodsid() != null) {
            sql.SET("goodsid = #{goodsid,jdbcType=INTEGER}");
        }
        
        if (record.getPrice() != null) {
            sql.SET("price = #{price,jdbcType=DECIMAL}");
        }
        
        if (record.getNums() != null) {
            sql.SET("nums = #{nums,jdbcType=INTEGER}");
        }
        
        if (record.getAddtime() != null) {
            sql.SET("addtime = #{addtime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, CartExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}