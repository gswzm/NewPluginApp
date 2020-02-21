package com.gsww.baselibs.net;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.gsww.baselibs.utils.Constant;
import com.gsww.baselibs.utils.DesCrytoUtilty;
import com.gsww.baselibs.utils.StringHelper;
import com.gsww.baselibs.utils.aes.AESUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * RequestBody 对请求体进行加密处理
 */
public class IRequestBodyConverter<T> implements Converter<T, RequestBody> {
	private static final MediaType MEDIA_TYPE = MediaType
			.parse("application/json; charset=UTF-8");
	static final Charset UTF_8 = Charset.forName("UTF-8");
 
	final Gson gson;
	final TypeAdapter<T> adapter;
 
	IRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
		this.gson = gson;
		this.adapter = adapter;
		System.out.println("#IRequestBodyConverter初始化#");
	}
 
	@Override
	public RequestBody convert(T value) throws IOException {
		String json=new Gson().toJson(value);
		return RequestBody.create(MEDIA_TYPE, AESUtils.encrypt(json, Constant.AUTHCODE) );
	}
}