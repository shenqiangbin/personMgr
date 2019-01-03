package com.sqber.personMgr.ui.controller;

import com.github.pagehelper.PageHelper;
import com.sqber.personMgr.base.BaseResponse;
import com.sqber.personMgr.base.PagedResponse;
import com.sqber.personMgr.bll.ITaskService;
import com.sqber.personMgr.entity.Task;
import com.sqber.personMgr.entity.query.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private ITaskService taskService;

    @GetMapping("task/taskList")
    public String taskList(){
        return "task/taskList";
    }
    
    @ResponseBody
    @GetMapping("task/getList")
    public BaseResponse<PagedResponse<Task>> getList(int currentPage, String content) {

        BaseResponse<PagedResponse<Task>> result = new BaseResponse<>();

        try {

            int pageSize = 10;

            TaskQuery query = new TaskQuery();
            query.setContent(content);

            PageHelper.startPage(currentPage, pageSize);
            List<Task> list = taskService.getList(query);

            PagedResponse<Task> pagedResponse = new PagedResponse<>(list,currentPage,pageSize);

            result.setData(pagedResponse);

        } catch (Exception e) {
            result.setCode(500);
            result.setMsg("服务器错误");

            log.error(e.getMessage() + e.getStackTrace());
        }

        return result;
    }
    
    @GetMapping("task/taskAdd")
    public String taskAdd(){
        return "task/taskAdd";
    }
    
    @ResponseBody
    @PostMapping("task/saveTask")
    public BaseResponse<String> saveTask(@RequestBody Task model){

        BaseResponse<String> result = new BaseResponse<>();

        try{
            System.out.println(model.getTaskid());
            if(model.getTaskid()!=null && model.getTaskid()!=0){
                Task dbModel = taskService.getByID(model.getTaskid());

                //编码不能修改
                //dbModel.setCode();
//                dbModel.setName(model.getName());
//                dbModel.setStarttime(model.getStarttime());
//                dbModel.setEndtime(model.getEndtime());

                taskService.updateById(dbModel);
            }else{

                taskService.addTask(model);
                result.setData(String.valueOf(model.getTaskid()));
            }

        }catch (Exception e) {
            result.setCode(500);
            result.setMsg("服务器错误");

            log.error(e.getMessage() + e.getStackTrace());
        }

        return result;
    }

    @ResponseBody
    @GetMapping("task/getById")
    public BaseResponse<Task> getById(int id) {

        BaseResponse<Task> result = new BaseResponse<>();

        try {
            Task model = taskService.getByID(id);
            result.setData(model);

        } catch (Exception e) {
            result.setCode(500);
            result.setMsg("服务器错误");

            log.error(e.getMessage() + e.getStackTrace());
        }

        return result;
    }

}
