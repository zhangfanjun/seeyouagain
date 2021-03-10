package com.jun.seeyouagain.controller;

import com.jun.seeyouagain.common.model.Ingredient;
import com.jun.seeyouagain.common.model.Order;
import com.jun.seeyouagain.common.model.Taco;
import com.jun.seeyouagain.common.model.dto.TaskGetDTO;
import com.jun.seeyouagain.common.model.dto.UpdateNameAndCodeDTO;
import com.jun.seeyouagain.common.model.exception.BusinessException;
import com.jun.seeyouagain.common.model.jpa.TaskInfo;
import com.jun.seeyouagain.common.model.param.BaseIdParam;
import com.jun.seeyouagain.common.model.param.RequestContent;
import com.jun.seeyouagain.common.model.vo.ResponseVO;
import com.jun.seeyouagain.repository.IngredientRepositoryService;
import com.jun.seeyouagain.repository.OrderRepositoryService;
import com.jun.seeyouagain.repository.TacoRepositoryService;
import com.jun.seeyouagain.repository.jpa.TaskInfoRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 第三章，jdbc原生用法和jdbcTemplate的用法
 */
@RequestMapping("/data")
@Slf4j
@RestController
@SessionAttributes("order")
public class SeeYouAgain3stChapterController {
    @Resource(name = "jdbc")
    private IngredientRepositoryService jdbcService;

    @Resource(name = "jdbcTemplateMysql")
    private IngredientRepositoryService jdbcTemplateService;

    @Resource(name = "jdbcTemplateH2")
    private IngredientRepositoryService h2Service;

    /**
     * 采用jdbc添加数据
     */
    @PostMapping("/ingredient/jdbc/add")
    public ResponseVO<Boolean> jdbcAdd(@RequestBody RequestContent<Ingredient> in) {
        final ResponseVO<Boolean> res = new ResponseVO<>();
        if (Objects.isNull(in.getContent()) || null == in.getContent().getId() || null == in.getContent().getType()) {
            log.info("dataAdd null:{}", in);
            throw new BusinessException("bad param");
        }
        log.info("in:{}", in);
        final boolean save = jdbcService.save(in.getContent());
        res.setContent(save);
        return res;
    }

    /**
     * 采用抛出异常的方式处理参数的异常
     * 响应采用泛型加builder
     */
    @PostMapping("/ingredient/jdbc/find/id")
    public ResponseVO<Ingredient> jdbcFindById(@RequestBody RequestContent<BaseIdParam> in) {
        if (null == in.getContent() || null == in.getContent().getId()) {
            log.info("dataAdd null:{}", in);
            throw new BusinessException("bad param");
        }
        final Ingredient ingredient = jdbcService.findById(in.getContent().getId());
        return ResponseVO.<Ingredient>builder().code(ResponseVO.CODE_SUCCESS).content(ingredient).build();
    }

    @PostMapping("/ingredient/jdbc/find/all")
    public ResponseVO<List<Ingredient>> jdbcFindAll() {
        final List<Ingredient> all = jdbcService.findAll();
        return ResponseVO.<List<Ingredient>>builder().code(ResponseVO.CODE_SUCCESS).content(all).build();
    }

    /**
     * 采用jdbcTemplate处理数据
     */
    @PostMapping("/ingredient/jdbcTemplate/add")
    public ResponseVO<Boolean> jdbcTemplateAdd(@RequestBody RequestContent<Ingredient> in) {
        if (Objects.isNull(in.getContent()) || null == in.getContent().getId() || null == in.getContent().getType()) {
            log.info("dataAdd null:{}", in);
            throw new BusinessException("bad param");
        }
        log.info("in:{}", in);
        final boolean save = jdbcTemplateService.save(in.getContent());
        return ResponseVO.<Boolean>builder().code(ResponseVO.CODE_SUCCESS).content(save).build();
    }

    @PostMapping("/ingredient/jdbcTemplate/find/id")
    public ResponseVO<Ingredient> jdbcTemplateFindById(@RequestBody RequestContent<BaseIdParam> in) {
        if (null == in.getContent() || null == in.getContent().getId()) {
            log.info("dataAdd null:{}", in);
            throw new BusinessException("bad param");
        }
        final Ingredient ingredient = jdbcTemplateService.findById(in.getContent().getId());
        return ResponseVO.<Ingredient>builder().code(ResponseVO.CODE_SUCCESS).content(ingredient).build();
    }

    @PostMapping("/ingredient/jdbcTemplate/find/all")
    public ResponseVO<List<Ingredient>> jdbcTemplateFindAll() {
        final List<Ingredient> all = jdbcTemplateService.findAll();
        return ResponseVO.<List<Ingredient>>builder().code(ResponseVO.CODE_SUCCESS).content(all).build();
    }

    /**
     * 采用H2数据库
     */
    @PostMapping("/ingredient/h2/jdbcTemplate/add")
    public ResponseVO<Boolean> h2JdbcTemplateAdd(@RequestBody RequestContent<Ingredient> in) {
        if (Objects.isNull(in.getContent()) || null == in.getContent().getId() || null == in.getContent().getType()) {
            log.info("dataAdd null:{}", in);
            throw new BusinessException("bad param");
        }
        log.info("in:{}", in);
        final boolean save = h2Service.save(in.getContent());
        return ResponseVO.<Boolean>builder().code(ResponseVO.CODE_SUCCESS).content(save).build();
    }

    @PostMapping("/ingredient/h2/jdbcTemplate/find/id")
    public ResponseVO<Ingredient> h2JdbcTemplateFindById(@RequestBody RequestContent<BaseIdParam> in) {
        if (null == in.getContent() || null == in.getContent().getId()) {
            log.info("dataAdd null:{}", in);
            throw new BusinessException("bad param");
        }
        final Ingredient ingredient = h2Service.findById(in.getContent().getId());
        return ResponseVO.<Ingredient>builder().code(ResponseVO.CODE_SUCCESS).content(ingredient).build();
    }

    @PostMapping("/ingredient/h2/jdbcTemplate/find/all")
    public ResponseVO<List<Ingredient>> h2JdbcTemplateFindAll() {
        final List<Ingredient> all = h2Service.findAll();
        return ResponseVO.<List<Ingredient>>builder().code(ResponseVO.CODE_SUCCESS).content(all).build();
    }

    /**
     * --------------------------------------------------------------------------------------------------
     * 以下接口是第三章中教材接口
     */

    @Autowired
    private TacoRepositoryService designRepo;

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    /**
     * 展示
     */
    @PostMapping("/order/jdbcTemplate/show")
    public String showDesignForm2(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        //查找所有的ingredient
        jdbcService.findAll().forEach(i -> ingredients.add(i));
        //遍历type，attribute保存不同type对应的ingredient集合
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
        //返回到design
        return "design";
    }

    /**
     * 处理来自请求的异常
     */
    @PostMapping("/processDesign")
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
        //访问接口出现的异常
        if (errors.hasErrors()) {
            return "design";
        }
        //保存taco
        Taco saved = designRepo.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Autowired
    private OrderRepositoryService orderRepositoryService;

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors,
                               SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        orderRepositoryService.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }


    /**
     * ------------------------------------------------------------------------------
     * SpringDataJPA
     */
    @Autowired
    private TaskInfoRepositoryService taskInfoRepositoryService;

    @PostMapping("/jpa/get")
    @ResponseBody
    public Object queryByName(@Valid @RequestBody TaskGetDTO taskGetDTO, Errors errors) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        log.info("taskName:{}", taskGetDTO.getTaskName());
        TaskInfo taskInfo1 = taskInfoRepositoryService.queryByName(taskGetDTO.getTaskName());
        TaskInfo taskInfo2 = taskInfoRepositoryService.queryByName2(taskGetDTO.getTaskName());
        System.out.println(taskInfo1.getUsers());
        log.info("query1:{},query2:{}", taskInfo1, taskInfo2);
        final ArrayList<TaskInfo> taskInfos = Lists.newArrayList(taskInfo1, taskInfo2);
        return taskInfos;
    }

    @PostMapping("/jpa/update")
    @ResponseBody
    public String updateNameAndCode(@Valid UpdateNameAndCodeDTO update, Errors errors) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        taskInfoRepositoryService.updateNameAndCode1(update.getTaskName() + "1", update.getCode() + "1");
        taskInfoRepositoryService.updateNameAndCode1(update.getTaskName() + "2", update.getCode() + "2");

        return "redirect:/";
    }
}
