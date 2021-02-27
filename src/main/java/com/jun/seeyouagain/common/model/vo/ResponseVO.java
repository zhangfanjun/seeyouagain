package com.jun.seeyouagain.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseVO<T> {
    private int code;
    private String msg;
    private T content;
    private String timeStamp;

    public static final int CODE_SUCCESS = 200;
    public static final int CODE_FALSE = 400;
    public static final int CODE_EXCEPTION = 500;
}
