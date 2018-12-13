package com.huboot.business.mybatis;


import java.lang.annotation.*;
/**
 * 
* @ClassName: QueryConfig 
* @Description: 查询条件注解配置工具
* @author zhangtiebin@hn-zhixin.com
* @date 2015年7月1日 下午10:51:07 
*
 */

/**
 * 
* @ClassName: QueryConfig 
* @Description: 查询条件注解配置工具
* @author zhangtiebin@hn-zhixin.com
* @date 2015年7月1日 下午10:51:07 
*
 */
@Documented
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.FIELD)
public @interface QueryConfig {
	/**
	 * 支持的主键类型(默认支持所有类型)
	 * @return
	 */
	public QueryOperatorEnum[] supportOps() default {QueryOperatorEnum.all};
	
	/**
	 * 数据库字段名称（默认会使用当前field）
	 * @return
	 */
	public String colname(); 
	
	/**
	 * 是否支持排序
	 * @return
	 */
	public boolean supportOrder() default false;
	
	/**
	 * 是否支持分组
	 * @return
	 */
	public boolean supportGroupBy() default false;
	
}
