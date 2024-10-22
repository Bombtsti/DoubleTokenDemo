package com.example.demo.result;

import lombok.Getter;

/**
 * 统一返回结果状态信息类
 *
 * @author xiaozhao
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "成功"),
    FAIL(400, "失败"),

    TOKEN_FAIL(401,"token无效"),
    TOKEN_EXPIRE(501, "token过期"),

    NOT_NULL(10001, "为空");

    private final Integer code;

    private final String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
