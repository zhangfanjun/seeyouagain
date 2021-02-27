package com.jun.seeyouagain.controller;

import com.jun.seeyouagain.common.model.Ingredient;
import com.jun.seeyouagain.common.model.exception.BusinessException;
import com.jun.seeyouagain.common.model.param.BaseIdParam;
import com.jun.seeyouagain.common.model.param.RequestContent;
import com.jun.seeyouagain.common.model.vo.ResponseVO;
import com.jun.seeyouagain.repository.IngredientRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 第三章，jdbc原生用法和jdbcTemplate的用法
 * */
@RequestMapping("/data")
@Slf4j
@RestController
public class SeeYouAgain3stChapterController {
    @Resource(name = "jdbc")
    private IngredientRepositoryService jdbcService;

    @Resource(name = "jdbcTemplateMysql")
    private IngredientRepositoryService jdbcTemplateService;

    @Resource(name = "jdbcTemplateH2")
    private IngredientRepositoryService h2Service;

    /**
     * 采用jdbc添加数据
     * */
    @PostMapping("/jdbc/add")
    public ResponseVO<Boolean> jdbcAdd(@RequestBody RequestContent<Ingredient> in){
        final ResponseVO<Boolean> res = new ResponseVO<>();
        if (Objects.isNull(in.getContent())||null==in.getContent().getId()||null==in.getContent().getType()){
            log.info("dataAdd null:{}",in);
            throw new BusinessException("bad param");
        }
        log.info("in:{}",in);
        final boolean save = jdbcService.save(in.getContent());
        res.setContent(save);
        return res;
    }

    /**
     * 采用抛出异常的方式处理参数的异常
     * 响应采用泛型加builder
     * */
    @PostMapping("/jdbc/find/id")
    public ResponseVO<Ingredient> jdbcFindById(@RequestBody RequestContent<BaseIdParam> in){
        if (null==in.getContent()||null==in.getContent().getId()){
            log.info("dataAdd null:{}",in);
            throw new BusinessException("bad param");
        }
        final Ingredient ingredient = jdbcService.findById(in.getContent().getId());
        return ResponseVO.<Ingredient>builder().code(ResponseVO.CODE_SUCCESS).content(ingredient).build();
    }

    @PostMapping("/jdbc/find/all")
    public  ResponseVO<List<Ingredient>> jdbcFindAll(){
        final List<Ingredient> all = jdbcService.findAll();
        return ResponseVO.<List<Ingredient>>builder().code(ResponseVO.CODE_SUCCESS).content(all).build();
    }

    /**
     * 采用jdbcTemplate处理数据
     * */
    @PostMapping("/jdbcTemplate/add")
    public ResponseVO<Boolean> jdbcTemplateAdd(@RequestBody RequestContent<Ingredient> in){
        if (Objects.isNull(in.getContent())||null==in.getContent().getId()||null==in.getContent().getType()){
            log.info("dataAdd null:{}",in);
            throw new BusinessException("bad param");
        }
        log.info("in:{}",in);
        final boolean save = jdbcTemplateService.save(in.getContent());
        return ResponseVO.<Boolean>builder().code(ResponseVO.CODE_SUCCESS).content(save).build();
    }
    @PostMapping("/jdbcTemplate/find/id")
    public ResponseVO<Ingredient> jdbcTemplateFindById(@RequestBody RequestContent<BaseIdParam> in){
        if (null==in.getContent()||null==in.getContent().getId()){
            log.info("dataAdd null:{}",in);
            throw new BusinessException("bad param");
        }
        final Ingredient ingredient = jdbcTemplateService.findById(in.getContent().getId());
        return ResponseVO.<Ingredient>builder().code(ResponseVO.CODE_SUCCESS).content(ingredient).build();
    }
    @PostMapping("/jdbcTemplate/find/all")
    public  ResponseVO<List<Ingredient>> jdbcTemplateFindAll(){
        final List<Ingredient> all = jdbcTemplateService.findAll();
        return ResponseVO.<List<Ingredient>>builder().code(ResponseVO.CODE_SUCCESS).content(all).build();
    }
    /**
     * 采用H2数据库
     * */
    @PostMapping("/h2/jdbcTemplate/add")
    public ResponseVO<Boolean> h2JdbcTemplateAdd(@RequestBody RequestContent<Ingredient> in){
        if (Objects.isNull(in.getContent())||null==in.getContent().getId()||null==in.getContent().getType()){
            log.info("dataAdd null:{}",in);
            throw new BusinessException("bad param");
        }
        log.info("in:{}",in);
        final boolean save = h2Service.save(in.getContent());
        return ResponseVO.<Boolean>builder().code(ResponseVO.CODE_SUCCESS).content(save).build();
    }
    @PostMapping("/h2/jdbcTemplate/find/id")
    public ResponseVO<Ingredient> h2JdbcTemplateFindById(@RequestBody RequestContent<BaseIdParam> in){
        if (null==in.getContent()||null==in.getContent().getId()){
            log.info("dataAdd null:{}",in);
            throw new BusinessException("bad param");
        }
        final Ingredient ingredient = h2Service.findById(in.getContent().getId());
        return ResponseVO.<Ingredient>builder().code(ResponseVO.CODE_SUCCESS).content(ingredient).build();
    }
    @PostMapping("/h2/jdbcTemplate/find/all")
    public  ResponseVO<List<Ingredient>> h2JdbcTemplateFindAll(){
        final List<Ingredient> all = h2Service.findAll();
        return ResponseVO.<List<Ingredient>>builder().code(ResponseVO.CODE_SUCCESS).content(all).build();
    }
}
