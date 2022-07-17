package com.pro.reggie.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.reggie.DTO.SetmealDto;
import com.pro.reggie.common.CustomException;
import com.pro.reggie.entity.Setmeal;
import com.pro.reggie.entity.SetmealDish;
import com.pro.reggie.mapper.SetMealMapper;
import com.pro.reggie.service.SetMealDishService;
import com.pro.reggie.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, Setmeal> implements SetMealService {
    @Autowired
    private SetMealDishService setMealDishService;

    /**
     * add new combo save the dish
     * @param setmealDto
     */
    @Transactional
    public void saveWithDish(SetmealDto setmealDto){
        //save INFO, do insert
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
           item.setSetmealId(setmealDto.getId());
           return item;
        }).collect(Collectors.toList());
        //save relation
        setMealDishService.saveBatch(setmealDishes);
    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);
        if(count > 0){
            throw new CustomException("combo sale now");
        }

        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.in(SetmealDish::getSetmealId,ids);
        setMealDishService.remove(queryWrapper2);
    }
}
