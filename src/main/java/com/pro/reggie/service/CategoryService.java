package com.pro.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pro.reggie.entity.Category;


public interface CategoryService extends IService<Category> {
    public void remove(Long id);

}
