package com.changgou.controller;

import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import entity.Result;
import entity.StatusCode;
import io.swagger.models.auth.In;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/brand")
/***
 * 跨域：A域访问B域名的数据
 * 域名或者请求端口或者协议不一致，即跨域
 */
@CrossOrigin
public class BrandController {


    @Autowired
    private BrandService brandService;
    /***
     * 根据主键ID查询
     */
    @GetMapping(value = "/{id}")
    public Result<Brand> findById(@PathVariable(value = "id")Integer id){
        Brand brand = brandService.findById(id);
        //x响应数据封装
        return new Result<Brand>(true, StatusCode.OK,"根据ID查询品牌成功",brand);
    }

    /***
     * 查询所有品牌
     */
    @GetMapping
    public Result<List<Brand>> findAll(){

        //查询所有品牌
        List<Brand> brands = brandService.findAll();
        return new Result<List<Brand>>(true, StatusCode.OK,"查询品牌成功",brands);
    }

}
