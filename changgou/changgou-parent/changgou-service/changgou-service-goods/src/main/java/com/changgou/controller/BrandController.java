package com.changgou.controller;


import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
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
        //int w=10/0;
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

    /***
     * 增加品牌
     */
    @PostMapping
    public Result add(@RequestBody Brand brand){
        //调用service实现增加操作
        brandService.add(brand);
        return new Result(true, StatusCode.OK,"增加品牌成功");
    }

    //品牌修改实现
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable(value = "id")Integer id,@RequestBody Brand brand){
        //将ID给brand
        brand.setId(id);
        //调用service实现修改
        brandService.update(brand);
        return new Result(true, StatusCode.OK,"修改品牌成功");
    }

    //根据ID删除实现
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id")Integer id){
        //调用service
        brandService.delete(id);
        return new Result(true, StatusCode.OK,"删除品牌成功");
    }

    //条件查询
    @PostMapping(value = "/search")
    public Result<List<Brand>> findList(@RequestBody(required = false) Brand brand){
        //调用service
        List<Brand> brands = brandService.findList(brand);
        return new Result<List<Brand>>(true, StatusCode.OK,"条件查询品牌成功",brands);
    }

    /***
     * 分页查询
     */
    @GetMapping (value = "/search/{page}/{size}")
    public Result<PageInfo<Brand>> findPage(@PathVariable(value = "page")Integer page,
                                    @PathVariable(value = "size")Integer size){
        //调用service实现查询
        PageInfo<Brand> pageInfo = brandService.findPage(page,size);
        return new Result<PageInfo<Brand>>(true, StatusCode.OK,"分页查询品牌成功",pageInfo);
    }

    /***
     * 条件分页查询
     */
    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageInfo<Brand>> findPage(@RequestBody Brand brand,
                                            @PathVariable(value = "page")Integer page,
                                            @PathVariable(value = "size")Integer size){
        //调用service实现查询
        PageInfo<Brand> pageInfo = brandService.findPage(brand,page,size);
        return new Result<PageInfo<Brand>>(true, StatusCode.OK,"按条件分页查询品牌成功",pageInfo);
    }
}
