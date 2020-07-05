package com.changgou.dao;


import com.changgou.goods.pojo.Brand;
import tk.mybatis.mapper.common.Mapper;

/***
 * DAO使用通用Mapper DAO接口需要继承tk.mybaits.mapper.common.Mapper
 * 增加数据，调用Mapper.insert()
 * Mapper.insertSelective()
 *
 * 修改数据，调用Mapper.update(T)
 * Mapper.updateByPrimaryKey(T)
 *
 * 查询数据，根据ID查询：Mapper.selectByPrimaryKey(ID)
 * Mapper.select(T)
 */
public interface BrandMapper extends Mapper<Brand> {

}
