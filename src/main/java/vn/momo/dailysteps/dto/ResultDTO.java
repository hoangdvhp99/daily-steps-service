package vn.momo.dailysteps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDTO {
    private int code;
    private String message;
    private Object data;

    public ResultDTO(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultDTO(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public ResultDTO() {

    }

    public static ResultDTO ok(Object data) {
        return new ResultDTO(200, "SUCCESS", data);
    }
}
