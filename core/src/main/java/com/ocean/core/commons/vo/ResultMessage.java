package com.ocean.core.commons.vo;

import com.ocean.core.commons.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *   接口返回数据格式
 */
@Data
@ApiModel(value="接口返回对象", description="接口返回对象")
public class ResultMessage<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 成功标志
	 */
	@ApiModelProperty(value = "成功标志")
	private boolean success = true;

	/**
	 * 返回处理消息
	 */
	@ApiModelProperty(value = "返回处理消息")
	private String message = "操作成功！";

	/**
	 * 返回代码
	 */
	@ApiModelProperty(value = "返回代码")
	private Integer code = 0;

	/**
	 * 返回数据对象 data
	 */
	@ApiModelProperty(value = "返回数据对象")
	private T result;

	/**
	 * 时间戳
	 */
	@ApiModelProperty(value = "时间戳")
	private long timestamp = System.currentTimeMillis();

	public ResultMessage() {
		
	}
	
	public ResultMessage<T> success(String message) {
		this.message = message;
		this.code = CommonConstant.RESULT_CODE_200;
		this.success = true;
		return this;
	}

	
	public static ResultMessage<Object> ok() {
		ResultMessage<Object> r = new ResultMessage<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.RESULT_CODE_200);
		r.setMessage("success");
		return r;
	}
	
	public static ResultMessage<Object> ok(String msg) {
		ResultMessage<Object> r = new ResultMessage<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.RESULT_CODE_200);
		r.setMessage(msg);
		return r;
	}
	public static ResultMessage<Object> ok(String msg,Object data) {
		ResultMessage<Object> r = new ResultMessage<>();
		r.setSuccess(true);
		r.setCode(CommonConstant.RESULT_CODE_200);
		r.setResult(data);
		r.setMessage(msg);
		return r;
	}
	public static ResultMessage<Object> ok(Object data) {
		ResultMessage<Object> r = new ResultMessage<>();
		r.setSuccess(true);
		r.setCode(CommonConstant.RESULT_CODE_200);
		r.setResult(data);
		return r;
	}
	
	public static ResultMessage<Object> error(String msg) {
		return error(CommonConstant.RESULT_CODE_ERROR_500, msg);
	}
	
	public static ResultMessage<Object> error(int code, String msg) {
		ResultMessage<Object> r = new ResultMessage<Object>();
		r.setCode(code);
		r.setMessage(msg);
		r.setSuccess(false);
		return r;
	}

	public ResultMessage<T> error500(String message) {
		this.message = message;
		this.code = CommonConstant.RESULT_CODE_ERROR_500;
		this.success = false;
		return this;
	}

}