package g65.exception;

import g65.response.ResponseCode;
import lombok.Getter;

@Getter
public class BizException extends RuntimeException {

    private final String code;

    public BizException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
    }

    @Override
    public String toString() {
        return "g65.exception.BizException [code=" + code + ", msg=" + getMessage() + "]";
    }
}
