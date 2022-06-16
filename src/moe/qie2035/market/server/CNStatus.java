package moe.qie2035.market.server;

import fi.iki.elonen.NanoHTTPD.Response.IStatus;
import fi.iki.elonen.NanoHTTPD.Response.Status;
import lombok.Getter;
import moe.qie2035.market.Const;

public enum CNStatus implements IStatus {

    OK(200, Const.SUCCESS),
    CREATED(201, "已创建"),
    BAD_REQUEST(400, "参数错误"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "没有权限"),
    NOT_FOUND(404, "未找到"),
    METHOD_NOT_ALLOWED(405, "请求方法不被允许"),
    INTERNAL_ERROR(500, "服务器内部错误");

    private final int code;
    @Getter
    private final String msg;

    CNStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getDescription() {
        return Status.lookup(code).getDescription();
    }

    @Override
    public int getRequestStatus() {
        return code;
    }
}
