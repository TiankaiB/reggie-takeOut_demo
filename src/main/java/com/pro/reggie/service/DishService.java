package com.pro.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pro.reggie.DTO.DishDto;
import com.pro.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    //add new dish and its flavor (two tables - dish,dish-flavor)
    public void saveWithFlavor(DishDto dishDto);

    //search dish inform by id
    public DishDto getByIdWithFlavor(Long id);

    //update dish
    public void updateWithFlavor(DishDto dishDto);
}
