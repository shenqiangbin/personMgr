package com.sqber.personMgr.ui.controller;

import com.github.pagehelper.PageHelper;
import com.sqber.personMgr.base.BaseResponse;
import com.sqber.personMgr.base.DDLItem;
import com.sqber.personMgr.base.PagedResponse;
import com.sqber.personMgr.bll.ITaskService;
import com.sqber.personMgr.entity.Task;
import com.sqber.personMgr.entity.TaskListItem;
import com.sqber.personMgr.entity.query.TaskQuery;
import com.sqber.personMgr.enums.TaskStatusEnum;
import com.sqber.personMgr.enums.TaskTypeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    @GetMapping("task/calview")
    public String calview(){
        return "task/calView";
    }
    
    @ResponseBody
    @GetMapping("task/getList")
    public BaseResponse<PagedResponse<TaskListItem>> getList(int currentPage, String content) {

        BaseResponse<PagedResponse<TaskListItem>> result = new BaseResponse<>();

        try {

            int pageSize = 10;

            TaskQuery query = new TaskQuery();
            query.setCurrentPage(currentPage);
            query.setPageSize(pageSize);
            query.setContent(content);

            PagedResponse<TaskListItem> list = taskService.getItemList(query);
            result.setData(list);

        } catch (Exception e) {
            result.setCode(500);
            result.setMsg("服务器错误");

            log.error(e.getMessage() + e.getStackTrace());
        }

        return result;
    }

    @ResponseBody
    @GetMapping("task/getCalList")
    public BaseResponse<PagedResponse<TaskListItem>> getCalList(String content,String month) {

        BaseResponse<PagedResponse<TaskListItem>> result = new BaseResponse<>();

        try {

            int pageSize = Integer.MAX_VALUE;
            int currentPage = 1;

            TaskQuery query = new TaskQuery();
            query.setCurrentPage(currentPage);
            query.setPageSize(pageSize);
            query.setContent(content);

            Date theDate = new SimpleDateFormat("yyyy-MM").parse(month);
            Calendar cal = Calendar.getInstance();
            cal.setTime(theDate);

            cal.add(Calendar.MONTH,1);
            Date theDate2 = cal.getTime();
            String endDate = new SimpleDateFormat("yyyy-MM").format(theDate2)+"-01";

            String startDate = new SimpleDateFormat("yyyy-MM").format(theDate)+"-01";

            query.setStartDate(startDate);
            query.setEndDate(endDate);

            PagedResponse<TaskListItem> list = taskService.getItemList(query);
            result.setData(list);

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
    @GetMapping("task/tasktypes")
    public BaseResponse<List<DDLItem>> getTaskTypes() {
    	BaseResponse<List<DDLItem>> result = new BaseResponse<List<DDLItem>>();
    	try {
    		result.setData(TaskTypeEnum.toDDLItems());
    	}catch (Exception e) {
            result.setCode(500);
            result.setMsg("服务器错误");

            log.error(e.getMessage() + e.getStackTrace());
        }
    	return result;
    }
    
    @ResponseBody
    @GetMapping("task/taskstatuss")
    public BaseResponse<List<DDLItem>> getTaskStatuss() {
    	BaseResponse<List<DDLItem>> result = new BaseResponse<List<DDLItem>>();
    	try {
    		result.setData(TaskStatusEnum.toDDLItems());
    	}catch (Exception e) {
            result.setCode(500);
            result.setMsg("服务器错误");

            log.error(e.getMessage() + e.getStackTrace());
        }
    	return result;
    }
    
    @ResponseBody
    @PostMapping("task/saveTask")
    public BaseResponse<String> saveTask(@RequestBody Task model){

        BaseResponse<String> result = new BaseResponse<>();

        try{
            System.out.println(model.getTaskid());
            if(model.getTaskid()!=null && model.getTaskid()!=0){
                Task dbModel = taskService.getByID(model.getTaskid());
                
                dbModel.setTitle(model.getTitle());
                dbModel.setProjectcode(model.getProjectcode());
                dbModel.setDemandor(model.getDemandor());
                dbModel.setPuttime(model.getPuttime());
                dbModel.setTaskstatus(model.getTaskstatus());
                dbModel.setTasktype(model.getTasktype());
                dbModel.setScheduledstart(model.getScheduledstart());
                dbModel.setScheduledend(model.getScheduledend());
                dbModel.setContent(model.getContent());
                dbModel.setRealstart(model.getRealstart());
                dbModel.setRealend(model.getRealend());
                
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

    @ResponseBody
    @PostMapping("task/removeTask")
    public BaseResponse<Boolean> removeTask(String ids){

        BaseResponse<Boolean> result = new BaseResponse<>();

        try{

            taskService.removeTask(ids);

        }catch (Exception e) {
            result.setCode(500);
            result.setMsg("服务器错误");

            log.error(e.getMessage() + e.getStackTrace());
        }

        return result;
    }	
}
