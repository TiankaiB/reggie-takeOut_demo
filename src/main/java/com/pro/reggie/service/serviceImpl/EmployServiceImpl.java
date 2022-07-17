package com.pro.reggie.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pro.reggie.entity.Employee;
import com.pro.reggie.mapper.EmployMapper;
import com.pro.reggie.service.EmployService;
import org.springframework.stereotype.Service;

@Service
public class EmployServiceImpl extends ServiceImpl<EmployMapper, Employee> implements EmployService {


}
