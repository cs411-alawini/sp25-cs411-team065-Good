package g65.exception.handler;

import g65.exception.BizException;
import g65.response.Response;
import g65.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle custom business exceptions
     */
    @ExceptionHandler(BizException.class)
    public Response<?> handleBizException(BizException ex) {
        return Response.builder()
                .code(ex.getCode())
                .msg(ex.getMessage())
                .build();
    }

    /**
     * Handle general runtime exceptions
     */
    @ExceptionHandler(RuntimeException.class)
    public Response<?> handleRuntimeException(RuntimeException ex) {
        log.error("[RuntimeException] A runtime error occurred: {}", ex.getMessage(), ex);
        return Response.builder()
                .code(ResponseCode.SYS_RUNTIME_ERROR.getCode())
                .msg(ResponseCode.SYS_RUNTIME_ERROR.getMessage() + ": " + ex.getMessage())
                .build();
    }

    /**
     * Handle any unknown exceptions
     */
    @ExceptionHandler(Throwable.class)
    public Response<?> handleThrowable(Throwable ex) {
        log.error("[Throwable] An unknown error occurred: {}", ex.getMessage(), ex);
        return Response.builder()
                .code(ResponseCode.SYS_UNKNOWN_ERROR.getCode())
                .msg(ResponseCode.SYS_UNKNOWN_ERROR.getMessage())
                .build();
    }
}
