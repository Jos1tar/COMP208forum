package ac.liverpool.forum.entity;

import ac.liverpool.forum.enums.AppHttpCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回结果
 */
@Data
public class Result implements Serializable {

    private Integer code; // 状态码
    private String msg;   // 信息
    private Object data;  // 数据

    // 默认成功
    public Result() {
        this.code = AppHttpCodeEnum.SUCCESS.getCode();
        this.msg = AppHttpCodeEnum.SUCCESS.getMsg();
    }

    // 传入状态码和消息
    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 传入状态码、消息和数据
    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // **基于 AppHttpCodeEnum 进行构造**
    public Result(AppHttpCodeEnum appHttpCodeEnum) {
        this.code = appHttpCodeEnum.getCode();
        this.msg = appHttpCodeEnum.getMsg();
    }

    public Result(AppHttpCodeEnum appHttpCodeEnum, Object data) {
        this.code = appHttpCodeEnum.getCode();
        this.msg = appHttpCodeEnum.getMsg();
        this.data = data;
    }

    // **成功返回**
    public static Result success() {
        return new Result(AppHttpCodeEnum.SUCCESS);
    }

    public static Result success(Object data) {
        return new Result(AppHttpCodeEnum.SUCCESS, data);
    }

    // **失败返回**
    public static Result error(AppHttpCodeEnum appHttpCodeEnum) {
        return new Result(appHttpCodeEnum);
    }

    public static Result error(AppHttpCodeEnum appHttpCodeEnum, String customMsg) {
        return new Result(appHttpCodeEnum.getCode(), customMsg);
    }

    public static Result error(AppHttpCodeEnum appHttpCodeEnum, Object data) {
        return new Result(appHttpCodeEnum.getCode(), appHttpCodeEnum.getMsg(), data);
    }
}
