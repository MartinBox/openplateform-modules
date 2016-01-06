package com.open.jersey.rest;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * REST 主应用
 * The class RestApplication.
 *
 * Description: 
 *
 * @author: liuheng
 * @since: 2015年12月29日	
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public class RestApplication extends ResourceConfig {

	public RestApplication() {

		//服务类所在的包路径  
		packages("com.open.jersey.rest.resource");
		//注册 MultiPart
		register(MultiPartFeature.class);
		//注册 JSON 转换器  
		register(MoxyJsonFeature.class);
		// 输入请求日志。生产环境可以去掉
		register(LoggingFilter.class);
	}
}
