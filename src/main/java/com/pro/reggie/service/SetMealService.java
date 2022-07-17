package com.pro.reggie.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pro.reggie.DTO.SetmealDto;
import com.pro.reggie.entity.Setmeal;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SetMealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * remove dish
     * @param ids
     */
    public void removeWithDish(List<Long> ids);
}
