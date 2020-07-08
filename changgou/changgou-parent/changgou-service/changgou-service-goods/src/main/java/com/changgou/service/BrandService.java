package com.changgou.service;

import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;


import java.util.List;

public interface BrandService {
    /***
     * 根据ID查询
     */
    Brand findById(Integer id);

    /***
     * 查询所有，返回list集合
     */
    List<Brand> findAll();

    /***
     * 增加品牌
     */
    void add(Brand brand);

    /***
     * 修改品牌
     */
    void update(Brand brand);

    /***
     * 删除品牌，根据ID删除
     */
    void delete(Integer id);

    /***
     * 根据品牌信息多条件查询
     */
    List<Brand> findList(Brand brand);

    /***
     * 条件搜索，，分页查询，page当前页,size条数
     */
    PageInfo<Brand> findPage(Integer page,Integer size);

    /***
     * 分页+条件搜索，，分页查询，page当前页,size条数,Brand是搜索条件
     */
    PageInfo<Brand> findPage(Brand brand,Integer page,Integer size);
}
