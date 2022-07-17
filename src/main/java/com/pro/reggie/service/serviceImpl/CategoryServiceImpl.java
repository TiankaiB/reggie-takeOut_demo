package com.pro.reggie.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.reggie.common.CustomException;
import com.pro.reggie.entity.Category;
import com.pro.reggie.entity.Dish;
import com.pro.reggie.entity.Setmeal;
import com.pro.reggie.mapper.CategoryMapper;
import com.pro.reggie.service.CategoryService;
import com.pro.reggie.service.DishService;
import com.pro.reggie.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetMealService setMealService;
    /**
     * delete by id with condition
     * @param id
     */
    @Override
    public void remove(Long id) {
        //whether link to other suit or throw exception
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //by id
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if(count1 > 0){
            //had linked, throw exception
            throw new CustomException("this dish had linked with other dish! can't be delete!");

        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setMealService.count();
        if(count2 > 0){
            //had linked, throw
            throw new CustomException("this dish had linked with other meal! can't be delete!");
        }

        super.removeById(id);

    }


}
