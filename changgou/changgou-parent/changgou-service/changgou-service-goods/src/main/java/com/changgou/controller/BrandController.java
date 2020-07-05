package com.changgou.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/brand")
/***
 * 跨域：A域访问B域名的数据
 * 域名或者请求端口或者协议不一致，即跨域
 */
@CrossOrigin
public class BrandController {
}
