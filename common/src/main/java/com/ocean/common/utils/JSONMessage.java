package com.ocean.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;


public class JSONMessage extends JSONObject {
    private static final long serialVersionUID = 1L;
    public static final Object EMPTY_OBJECT = new Object();

    public static JSONMessage success(String resultMsg) {
        return new JSONMessage("0000", resultMsg);
    }

    public static JSONMessage success() {
        return success(null, null);
    }

    public static JSONMessage success(Object data) {
        return new JSONMessage("0000", null, data);
    }

    public static JSONMessage success(String resultMsg, Object data) {
        return new JSONMessage("0000", resultMsg, data);
    }


    public static JSONMessage error(Exception e) {
        return new JSONMessage(1000, "请求异常", e.getMessage());
    }
    public JSONMessage() {
    }
    public JSONMessage(String errCode, String resultMsg, Object data) {
        setResultCode(errCode);
        setErrCode(errCode);
        setResultMsg(resultMsg);
        setDetailMsg(resultMsg);
        setData(data);
        setCurrentTime(new Date());
    }

    public JSONMessage(int resultCode, String resultMsg) {
        setResultCode(resultCode);
        setResultMsg(resultMsg);
        setCurrentTime(new Date());
    }

    public JSONMessage(int resultCode, String resultMsg, String detailMsg) {
        setResultCode(resultCode);
        setResultMsg(resultMsg);
        setDetailMsg(detailMsg);
        setCurrentTime(new Date());
    }

    public JSONMessage(int resultCode, String resultMsg, Object data) {
        setResultCode(resultCode);
        setResultMsg(resultMsg);
        setData(data);
        setCurrentTime(new Date());
    }

    public JSONMessage(String groupCode, String serviceCode, String nodeCode,
                       String resultMsg) {
        setResultCode(new StringBuffer().append(groupCode).append(serviceCode)
                .append(nodeCode).toString());
        setResultMsg(resultMsg);
    }

    public JSONMessage(String errCode, String resultMsg) {
        setResultCode(errCode);
        setErrCode(errCode);
        setResultMsg(resultMsg);
        setDetailMsg(resultMsg);
        setData(new Object());
        setCurrentTime(new Date());
    }

    public Object getCurrentTime() {
        return get("currentTime");
    }

    public void setCurrentTime(Object currentTime) {
        put("currentTime", currentTime);
    }

    public Object getErrCode() {
        return get("errCode");
    }

    public void setErrCode(Object errCode) {
        put("errCode", errCode);
    }

    public Object getResultCode() {
        return get("resultCode");
    }

    public void setResultCode(Object resultCode) {
        put("resultCode", resultCode);
    }

    public String getResultMsg() {
        return getString("resultMsg");
    }

    public void setResultMsg(String resultMsg) {
        put("resultMsg", resultMsg);
    }

    public String getDetailMsg() {
        return getString("detailMsg");
    }

    public void setDetailMsg(String detailMsg) {
        put("detailMsg", detailMsg);
    }

    public Object getData() {
        return get("data");
    }

    public void setData(Object data) {
        put("data", data);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


}