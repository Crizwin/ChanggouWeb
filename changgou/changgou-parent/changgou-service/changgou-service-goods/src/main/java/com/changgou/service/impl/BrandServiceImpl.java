package com.changgou.service.impl;

import com.changgou.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public Brand findById(Integer id) {
        //根据ID查询，通用mapper，根据主键查询
        return brandMapper.selectByPrimaryKey(id);
    }

    /***
     * 查询所有
     * @return
     */
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    /***
     * 使用通用mapper,insertSelective实现增加
     * 方法里面但凡带有selective会忽略空值
     * 例如 brand:name 有值,letter有值， insert into tb_brand(name,letter) value(?,?)
     * @param brand
     */
    @Override
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    public void update(Brand brand) {
        //根据ID修改品牌,使用通用mapperr
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void delete(Integer id) {
        //根据ID删除品牌
        brandMapper.deleteByPrimaryKey(id);
    }

    //多条件搜索
    @Override
    public List<Brand> findList(Brand brand) {
        //调用方法构建条件
        Example example = createExample(brand);
        return brandMapper.selectByExample(example);
    }

    /***
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Brand> findPage(Integer page, Integer size) {
        //分页实现，后面的查询紧跟集合查询
        //当前页
        PageHelper.startPage(page,size);
        //每页显示多少条

        //查询集合
        List<Brand> brands = brandMapper.selectAll();

        //封装PageInfo
        return new PageInfo<Brand>(brands);
    }

    @Override
    public PageInfo<Brand> findPage(Brand brand, Integer page, Integer size) {
        //1先分页，2搜索数据，3封装PageInfo<Brand>
        PageHelper.startPage(page,size);
        //条件搜索，name不为空，根据name模糊搜索，letter不为空，根据letter搜索
        Example example = createExample(brand);
        List<Brand> brands = brandMapper.selectByExample(example);
        return new PageInfo<Brand>(brands);
    }

    public Example createExample(Brand brand){
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        //组装条件
        if(brand!=null){


            if(!StringUtils.isEmpty(brand.getName())){
                //1)输入name-根据name查询[模糊查询]   select * from tb_brand wehere name like '%brand.getName%'
                criteria.andLike("name","%"+brand.getName()+"%");
            }

            if(!StringUtils.isEmpty(brand.getLetter())){
                //2)输入了letter-根据letter查询       select * from tb_brand where letter= 'brand.getLetter'
                criteria.andEqualTo("letter",brand.getLetter());
            }
        }
        return example;
    }
}
