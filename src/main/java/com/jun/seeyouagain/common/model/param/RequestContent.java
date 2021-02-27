package com.jun.seeyouagain.common.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestContent<T> {
    /**
     * content不能为空
     * */
    @NonNull
    private T content;
}
