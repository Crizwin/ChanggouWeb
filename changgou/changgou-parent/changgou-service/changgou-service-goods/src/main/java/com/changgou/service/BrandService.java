package com.changgou.service;

import com.changgou.goods.pojo.Brand;

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

}
