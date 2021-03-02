package com.jun.seeyouagain.common.model;

import com.sun.istack.internal.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    /**
     * RequiredArgsConstructor：要求需要全部成员变量，会生成一个包含常量，和标识了NotNull的变量的构造方法。生成的构造方法是私有的private。
     * 在这个类中，都采用了常量，
     * 另外在类的内部创建了静态枚举
     * */
    @NotNull
    private  String id;
    @Getter
    @Setter
    private  String name;
    private  Type type;

    public static enum Type{
        WRAP,PROTEIN,VEGGIES,CHEESE,SAUCE
    }
}
