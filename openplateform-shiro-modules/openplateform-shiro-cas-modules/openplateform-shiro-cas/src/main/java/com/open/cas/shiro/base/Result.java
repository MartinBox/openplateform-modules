package com.open.cas.shiro.base;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;


@SuppressWarnings("unchecked")
public class Result extends HashMap<String, Object> {
	private static final long serialVersionUID = -812425749886169637L;

	public static final String DATA = "data";
	public static final boolean FALSE = false;
	public static final String MESSAGE = "msg";
	public static final String RESULT = "result";

	public static final String SUCCESS = "success";
	public static final boolean TRUE = true;

	public Result() {
	}

	public Result(boolean success) {
		this.put(SUCCESS, success ? TRUE : FALSE);
	}

	public static Result failure() {
		Result result = new Result();
		result.put(SUCCESS, FALSE);
		return result;
	}

	public static Result failure(String message) {
		Result result = failure();
		if (StringUtils.hasText(message)) {
			result.put(MESSAGE, message);
		}
		return result;
	}

	public static Result failure(String message, boolean i118n) {
		Result result = failure();
		result.put(MESSAGE, i118n ? toI118n(message) : message);
		return result;
	}

	public static Result success() {
		Result result = new Result();
		result.put(SUCCESS, TRUE);
		return result;
	}

	public static Result success(Object data) {
		Result result = success();
		result.put(DATA, data);
		return result;
	}

	public static Result successWithMessage(String message) {
		Result result = success();
		result.put(MESSAGE, message);
		return result;
	}

	protected static String toI118n(String key) {
		return key;
	}

	public static Result toMap(Page page) {
		return toMap(page, RESULT);
	}

	public static Result toMap(Object data, String resultKey) {
		Result result = new Result();
		result.put(resultKey, data);
		return result;
	}

	public static Result toMap(Page page, String resultKey) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put(resultKey, page.getContent());
		pageMap.put("pageNo", page.getNumber());
		pageMap.put("pageSize", page.getSize());
		pageMap.put("totalPages", page.getTotalPages());
		pageMap.put("totalItems", page.getNumberOfElements());
		return success(pageMap);
	}

	private Result append(StringBuilder sb, Object obj) {
		sb.append(obj);
		return this;
	}

	private Result bracketEnd(StringBuilder sb) {
		sb.append("}");
		return this;
	}

	private Result bracketStart(StringBuilder sb) {
		sb.append("{");
		return this;
	}

	private Result colon(StringBuilder sb) {
		sb.append(" ： ");
		return this;
	}

	private Result comma(StringBuilder sb) {
		sb.append(", ");
		return this;
	}

	private Result quoteAppend(StringBuilder sb, Object obj) {
		sb.append("\"").append(obj).append("\"");
		return this;
	}

	public String toJsonString() {
		StringBuilder sb = new StringBuilder();

		bracketStart(sb).quoteAppend(sb, SUCCESS).colon(sb).append(sb,
				get(SUCCESS)).comma(sb).quoteAppend(sb, MESSAGE).colon(sb)
				.quoteAppend(sb, get(MESSAGE)).bracketEnd(sb);
		return sb.toString();
	}
}
