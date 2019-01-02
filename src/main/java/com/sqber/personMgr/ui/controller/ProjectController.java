package com.sqber.personMgr.ui.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.sqber.personMgr.base.BaseResponse;
import com.sqber.personMgr.base.PagedResponse;
import com.sqber.personMgr.bll.IProjectService;
import com.sqber.personMgr.entity.Project;
import com.sqber.personMgr.entity.Project;
import com.sqber.personMgr.entity.query.ProjectQuery;

@Controller
public class ProjectController {
    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private IProjectService projectService;

    @GetMapping("project/projectList")
    public String projectList(){
        return "project/projectList";
    }
    
    @ResponseBody
    @GetMapping("project/getList")
    public BaseResponse<PagedResponse<Project>> getList(int currentPage, String name) {

        BaseResponse<PagedResponse<Project>> result = new BaseResponse<>();

        try {

            int pageSize = 10;

            ProjectQuery query = new ProjectQuery();
            query.setProjectName(name);

            PageHelper.startPage(currentPage, pageSize);
            List<Project> list = projectService.getList(query);

            PagedResponse<Project> pagedResponse = new PagedResponse<>(list,currentPage,pageSize);

            result.setData(pagedResponse);

        } catch (Exception e) {
            result.setCode(500);
            result.setMsg("服务器错误");

            log.error(e.getMessage() + e.getStackTrace());
        }

        return result;
    }
    
    @GetMapping("project/projectAdd")
    public String projectAdd(){
        return "project/projectAdd";
    }
    
    @ResponseBody
    @PostMapping("project/saveProject")
    public BaseResponse<String> saveProject(Project model){

        BaseResponse<String> result = new BaseResponse<>();

        try{
            System.out.println(model.getProjectid());
            if(model.getProjectid()!=null && model.getProjectid()!=0){
                Project dbModel = projectService.getByID(model.getProjectid());

                //编码不能修改
                //dbModel.setCode();
                dbModel.setName(model.getName());
                dbModel.setStarttime(model.getStarttime());
                dbModel.setEndtime(model.getEndtime());

                projectService.updateById(dbModel);
            }else{

                projectService.addProject(model);
                result.setData(String.valueOf(model.getProjectid()));
            }

        }catch (Exception e) {
            result.setCode(500);
            result.setMsg("服务器错误");

            log.error(e.getMessage() + e.getStackTrace());
        }

        return result;
    }

    @ResponseBody
    @GetMapping("project/getById")
    public BaseResponse<Project> getById(int id) {

        BaseResponse<Project> result = new BaseResponse<>();

        try {
            Project model = projectService.getByID(id);
            result.setData(model);

        } catch (Exception e) {
            result.setCode(500);
            result.setMsg("服务器错误");

            log.error(e.getMessage() + e.getStackTrace());
        }

        return result;
    }
}
