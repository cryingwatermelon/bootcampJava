package com.watermelon.beatckc.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Result {
    private HttpStatus code; // 内部业务状态码
    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public void setMsg(final String msg) {
        this.msg = msg+"🤭";
    }
    public static Result success() {
        Result result = new Result();
        result.setCode(HttpStatus.OK);
        result.setMsg("请求成功");
        return result;
    }

}