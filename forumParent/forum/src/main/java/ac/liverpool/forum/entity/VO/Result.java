package ac.liverpool.forum.entity.VO;

import ac.liverpool.forum.enums.AppHttpCodeEnum;
import lombok.Data;

import java.io.Serializable;


@Data
public class Result implements Serializable {

    private Integer code; // status code
    private String msg;   // message about the result
    private Object data;  // data returned to the client

    // defaultly success
    public Result() {
        this.code = AppHttpCodeEnum.SUCCESS.getCode();
        this.msg = AppHttpCodeEnum.SUCCESS.getMsg();
    }

    // with status code and message
    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // with status code and message and data
    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //
    public Result(AppHttpCodeEnum appHttpCodeEnum) {
        this.code = appHttpCodeEnum.getCode();
        this.msg = appHttpCodeEnum.getMsg();
    }

    public Result(AppHttpCodeEnum appHttpCodeEnum, Object data) {
        this.code = appHttpCodeEnum.getCode();
        this.msg = appHttpCodeEnum.getMsg();
        this.data = data;
    }


    public static Result success() {
        return new Result(AppHttpCodeEnum.SUCCESS);
    }

    public static Result success(Object data) {
        return new Result(AppHttpCodeEnum.SUCCESS, data);
    }

    // return failure and message
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
