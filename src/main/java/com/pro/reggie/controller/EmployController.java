package com.pro.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.reggie.common.R;
import com.pro.reggie.entity.Employee;
import com.pro.reggie.service.EmployService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployController {

    @Autowired
    private EmployService employService;


    /*
     *employee login
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employService.getOne(queryWrapper);

        if(emp == null){
            return R.error("login fall");
        }

        if(!emp.getPassword().equals(password)){
            return R.error("login fall");
        }

        if(emp.getStatus() == 0){
            return R.error("account forbidden");
        }

        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /*logout*/
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("logout success!");
    }


    /*
    * add employee
    */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("new employee {}" , employee.toString());
        //set MD5 ENCODING
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        //get current user id
//
//        Long empId = (Long)request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);

        employService.save(employee);

        return R.success("add new employee success!");
    }

    /**
     * Paging query
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name){
        //paging constructor
        Page pageInfo = new Page(page,pageSize);

        //condition constructor
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //add new select condition

        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //sort condition
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //do select
        employService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * update employee ID
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){

//        Long empID = (Long)request.getSession().getAttribute("employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(empID);

        employService.updateById(employee);
        return R.success("update success!");
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employService.getById(id);
        if(employee != null){
            return R.success(employee);
        }
        return R.error("employee not found");
    }

}
