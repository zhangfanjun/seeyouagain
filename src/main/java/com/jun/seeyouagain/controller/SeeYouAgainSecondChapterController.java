package com.jun.seeyouagain.controller;

import com.jun.seeyouagain.common.model.Ingredient;
import com.jun.seeyouagain.common.model.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("")
@Slf4j
@Controller
public class SeeYouAgainSecondChapterController {

    /**
     * 这里使用的Model设置值后会复制到Servlet Response
     * */
    @GetMapping("/design")
    public String showDesignForm(Model model){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("id-1", "name-1", Ingredient.Type.CHEESE),
                new Ingredient("id-2", "name-2", Ingredient.Type.PROTEIN),
                new Ingredient("id-3", "name-3", Ingredient.Type.SAUCE),
                new Ingredient("id-4", "name-4", Ingredient.Type.WRAP)
        );
        Ingredient.Type[] values = Ingredient.Type.values();
        for (Ingredient.Type type : values) {
            model.addAttribute(type.toString().toLowerCase(),filterByTpe(ingredients,type));
        }
        model.addAttribute("design",new Taco());
        return "design";
    }

    private List<Ingredient> filterByTpe(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream()
                .filter(x->x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
