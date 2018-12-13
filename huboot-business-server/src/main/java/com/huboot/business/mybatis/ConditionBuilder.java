package com.huboot.business.mybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 
* @ClassName: ConditionBuilder 
* @Description: 查询条件构造工具
* @author zhangtiebin@hn-zhixin.com
* @date 2015年7月1日 下午11:16:36 
*
 */
public class ConditionBuilder {
	private static final Logger logger = LoggerFactory.getLogger(ConditionBuilder.class);
	@Deprecated
	public static  final String direction = "direction";
	public static  final String group_by = "group_by";
	public static  final String max_count = "max_count";
	@Deprecated
	public static  final String order = "order";
	public static  final String page = "page";
	public static  final String sort = "sort";
	public static  final String per_page = "per_page";
	public static  final String random = "random";
	public static  final String show_fileds = "show_fileds";
	public static  final String conditon_split="__";
	public static  final String value_split="|";
	public static  final String old_conditon_split="_";
	public static  final String old_contains_name="constains";
	/***
	 * 默认分页大小
	 */
	private static final Integer DEFAULT_PERPAGE = 40;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	/**
	 * 从request中构造查询条件
	 * @param request
	 * @param clazz
	 * @return
	 */
	public static QueryCondition buildCondition(HttpServletRequest request, Class clazz){
		QueryCondition queryCondition = new QueryCondition();
		Enumeration<String> names = request.getParameterNames();
 		if(names != null){
 			String queryKey  = null;
 			String queryValue = null;
 			List<Condition> conditionList = new ArrayList<Condition>();
 			Condition contion = null;
 			while (names.hasMoreElements()) {
 				queryKey = (String) names.nextElement();
 				//抛弃name为空的查询条件
 				if(StringUtils.isEmpty(queryKey) || "_".equals(queryKey)){
 					continue;
 				}
 				queryValue = request.getParameter(queryKey);
 				if("api_key".equalsIgnoreCase(queryKey)){
 					continue;
 				}
				if("Authorization".equalsIgnoreCase(queryKey)){
					continue;
				}
 				if(direction.equals(queryKey)){
 					if("asc".equalsIgnoreCase(queryValue) || "desc".equalsIgnoreCase(queryValue)){
 						queryCondition.setDirection(queryValue.toLowerCase());
 					}else{
 						throw new ApiException(ErrorCodeEnum.ParamsError, "排序只支持asc,desc");
 					}
 					continue;
 				}else if(group_by.equals(queryKey)){
 					if(StringUtils.isEmpty(queryValue)){
 						throw new ApiException(ErrorCodeEnum.ParamsError, "groupby不能为空");
 					}else{
 						queryCondition.setGroup_by(queryValue.split("\\"+value_split));
 					} 
 					continue;
 				}else if(max_count.equals(queryKey)){
 					int mx = 0;
 					try {
						mx = Integer.parseInt(queryValue);
					} catch (NumberFormatException e) {
					  throw new ApiException(ErrorCodeEnum.ParamsError, "max_count必须为数字");
					}
 					queryCondition.setMax_count(mx);
 					continue;
 				}else if(order.equals(queryKey)){
 					if(StringUtils.isEmpty(queryValue)){
 						throw new ApiException(ErrorCodeEnum.ParamsError, "order不能为空！");
 					}
 					queryCondition.setOrder(queryValue.split("\\"+value_split));
 					continue;
 				}else if(page.equals(queryKey)){
 					int pg = 0;
 					try {
 						pg = Integer.parseInt(queryValue);
					} catch (NumberFormatException e) {
					  throw new ApiException(ErrorCodeEnum.ParamsError, "page必须为数字");
					}
 					queryCondition.setPage(pg);
 					continue;
 				}else if(per_page.equals(queryKey)){
 					int pp = 0;
 					try {
 						pp = Integer.parseInt(queryValue);
					} catch (NumberFormatException e) {
					  throw new ApiException(ErrorCodeEnum.ParamsError, "per_page必须为数字");
					}
 					queryCondition.setPer_page(pp);
 					continue;
 				}else if(random.equals(queryKey)){
 					boolean b = false;
 					try {
						b =  Boolean.valueOf(queryValue);
					} catch (Exception e) {
						 throw new ApiException(ErrorCodeEnum.ParamsError, "random必须为true/false");
					}
 					queryCondition.setRandom(b);
 					continue;
 				}else if(show_fileds.equals(queryKey)){
 					if(StringUtils.isEmpty(queryValue)){
 						throw new ApiException(ErrorCodeEnum.ParamsError, "show_fileds不能为空！");
 					}
 					queryCondition.setShow_fileds(queryValue.split("\\"+value_split));
 					continue;
 				}else if(sort.equals(queryKey)){  
 					if(!StringUtils.isEmpty(queryValue)){
 						queryCondition.setCompositeOrderList(buildCompositeOrder(queryValue));
 					}
 					continue;
 				}
 				contion = ConditionBuilder.buildCondition(queryKey, queryValue);
 				//要挑出GroupBy等非过滤条件
 				if(contion != null){
 	 				conditionList.add(contion);
 				}
			}
 			if(queryCondition.getPer_page() == null	){
 				queryCondition.setPer_page(DEFAULT_PERPAGE);
 			}
 			if(queryCondition.getPage()==null){
 				queryCondition.setPage(1);
 			}
 			queryCondition.setConditionList(conditionList); 
 			ConditionBuilder.checkCondition(clazz, queryCondition);
 		}
		return queryCondition;
	}
	/**
	 * 构造组合排序条件
	 * @param value
	 * @return
	 */
	private static List<CompositeOrder> buildCompositeOrder(String value){
		 String[] array  = value.split("\\"+value_split);
		 List<CompositeOrder>  compositeOrderList = new ArrayList<CompositeOrder>();
		 String fieldName = null;
		 CompositeOrder compositeOrder = null;
		 for(String str :  array){
			 if(!StringUtils.isEmpty(str) && str.length()>1){
				 fieldName = str.substring(1,str.length());
				 compositeOrder = new CompositeOrder();
				 compositeOrder.setFieldName(fieldName);
				 if(str.charAt(0) == '+'){
					 compositeOrder.setDirection("asc");
				 }else if( str.charAt(0) == '-'){
					 compositeOrder.setDirection("desc");
				 }else{
					 throw new ApiException(ErrorCodeEnum.ParamsError, "排序参数["+fieldName+"]前请指定排序方式[+表示升序，-表示降序]!");
				 }
				 compositeOrderList.add(compositeOrder);
			 }else{
				 throw new ApiException(ErrorCodeEnum.ParamsError, "非法的排序指定["+str+"]!");
			 }
		 }
		 return compositeOrderList;
	}
	/**
	 * 根据Key-value构造一个查询条件
	 * @param key
	 * @param value
	 * @return
	 */
	public static  Condition buildCondition(String key,String value){
		if(StringUtils.isEmpty(key)){
			throw new ApiException(ErrorCodeEnum.ParamsError, "查询参数"+key+"格式错误!");
		}
		String realkey = null;
		String op = null;
		//判断是否属于其他控制参数(分页，show_fields等)
		Condition con = new Condition();
		if(StringUtils.isEmpty(value)){
//			throw new  ApiException(ErrorCodeEnum.ParamsError, "查询参数"+key+"的值不能为空!");
			return null;
		} /*else{
			try {
				value = new String(value.getBytes("iso-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			};
		}*/
		//兼容以前的split
		int length = 0;
		int lastIndx =  key.lastIndexOf(conditon_split);
		if(lastIndx<0){ //如果不存在，则检测旧的，如果旧的切分op不存在，则使用新的，默认op为eq
			 lastIndx =  key.lastIndexOf(old_conditon_split);
			 if(lastIndx>0){
				 try {
					op = key.substring(lastIndx+1,key.length());
					//兼容以前的
					if(old_contains_name.equals(op) || QueryOperatorEnum.valueOf(op) != null){
						 //合理的
						length = 1;
					} 
				} catch (Exception e) {
					lastIndx = -1;
					op =  null;
				}  
			 }
		}else{
			length = 2;
		}
		
		if(lastIndx<0){  //默认为eq
			realkey = key;
			op = QueryOperatorEnum.eq.name();
		}else{
			realkey = key.substring(0,lastIndx);
			op = key.substring(lastIndx+length,key.length());
		}
		
		con.setName(realkey); 
		try {
			//兼容以前的
			if(old_contains_name.equals(op)){
				con.setOp(QueryOperatorEnum.contains);
			}else{
				con.setOp(QueryOperatorEnum.valueOf(op));
			}
		} catch (Exception e) {
			throw new ApiException(ErrorCodeEnum.ParamsError, "查询参数["+key+"]算子["+op	+"]错误!");
		} 
		switch (con.getOp()) {
			case in: 
				con.setValue(value.split("\\"+value_split));  
				break;
			case contains: 
				con.setValue("%"+value+"%");  
				break;
			case containsList: 
				String[] array = value.split("\\"+value_split);
				for(String str : array){
					str = "%"+str+"%";
				}
				con.setValue(array);  
				break;
		default:
			con.setValue(value);
			break;
		}
		return con;
	} 
	/**
	 * 检查是否合法
	 * @param clazz
	 * @param queryCondition
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static  boolean checkCondition(Class clazz,QueryCondition queryCondition){
		List<Condition> conditionList = queryCondition.getConditionList();
		String filedName = null;
		 if(clazz == null){ 
			 throw new ApiException(ErrorCodeEnum.ParamsError, "请指定配置Bean");
		 }
		 if(!StringUtils.isEmpty(queryCondition.getDirection()) && ( queryCondition.getOrder() == null || queryCondition.getOrder().length == 0)){
//			 throw new ApiException(ErrorCodeEnum.ParamsError, "指定了排序方式必须指定排序条件");
			 logger.info("指定了排序方式必须但没有指定排序条件");
			 queryCondition.setDirection(null);//置空排序
		 }
		 //如果没有指定排序方式，默认desc
		 if(queryCondition.getOrder() != null && queryCondition.getOrder().length>0){
			 if(StringUtils.isEmpty(queryCondition.getDirection())){
				 queryCondition.setDirection("desc");
			 }
		 }
		//构建注解map
		 Map<String,QueryConfig> fileConfigs = new HashMap<String,QueryConfig>();
		 Field[] fieldArray = null;
		 QueryConfig config =  null;
		 //获取直接父类属性配置
		 Class superClass = clazz.getSuperclass();
		 if(superClass != null){
			 fieldArray= superClass.getDeclaredFields();
			 for(Field field : fieldArray){
				    config = field.getAnnotation(QueryConfig.class);
				    fileConfigs.put(field.getName(), config);
			 }
		 }
		 //获取当前类的属性配置
		 fieldArray = clazz.getDeclaredFields();
		 for(Field field : fieldArray){
			    config = field.getAnnotation(QueryConfig.class);
			    fileConfigs.put(field.getName(), config);
		 }
		 
		 if(conditionList != null && conditionList.size() >= 0){
			//检查查询条件以及算子是否合法
				for(Condition condition : conditionList){
					filedName = condition.getName();
					config = fileConfigs.get(filedName);
					if(config == null){
						 throw new ApiException(ErrorCodeEnum.ParamsError, "不支参数["+condition.getName()+"]的查询");
					}
					if(config != null){
						QueryOperatorEnum[] supportOpArray = config.supportOps();
						boolean support = false;
						for(QueryOperatorEnum op : supportOpArray){
							 if(op == QueryOperatorEnum.all ){
								 support = true;
								 break;
							 }
							 if(op == condition.getOp()){
								 support = true;
								 break;
							 }
						}
						if(!support){
							 throw new ApiException(ErrorCodeEnum.ParamsError, "参数["+condition.getName()+"]不支持算子["+condition.getOp().getOp()+"]");
						}
						//设置字段名
						condition.setColName(config.colname());
					}
				 }
		 }
		String[] groupBy = queryCondition.getGroup_by();
		List<String> realColumnGroupByList = new ArrayList<String>();
		//检查GroupBy条件是否合法
		if(groupBy!=null&&groupBy.length>0){
			//检查GroupBy条件是否合法
			for(String name : groupBy){
					config = fileConfigs.get(name);
					if(config == null){
						 throw new ApiException(ErrorCodeEnum.ParamsError, "不支参数["+name+"]的分组");
					}
					if(!config.supportGroupBy()){
						throw new ApiException(ErrorCodeEnum.ParamsError, "不支参数["+name+"]的分组");
					}
					realColumnGroupByList.add(config.colname()); 					
			 }
			queryCondition.setGroup_by(realColumnGroupByList.toArray(new String[]{}));
		}
		
		//检查OrderBy条件是否合法
		String[] orderBy = queryCondition.getOrder();
		List<String> realColumnOrderByList = new ArrayList<String>();
		if(orderBy!=null&&orderBy.length>0){
			//检查orderBy条件是否合法
			for(String name : orderBy){
					config = fileConfigs.get(name);					
					if(config == null){
						 throw new ApiException(ErrorCodeEnum.ParamsError, "不支参数["+name+"]的排序");
					}
					if(config != null){
						if(!config.supportOrder()){
							throw new ApiException(ErrorCodeEnum.ParamsError, "不支参数["+name+"]的排序");
						}
					} 
					realColumnOrderByList.add(config.colname());
			 }
			queryCondition.setOrder(realColumnOrderByList.toArray(new String[]{}));
		}
		//检查show_fields条件是否合法
		String[] show_fields = queryCondition.getShow_fileds();
		if(show_fields!=null&&show_fields.length>0){
			//检查show_fields条件是否合法
			for(String name : show_fields){
					config = fileConfigs.get(name);
					if(config == null){
						 throw new ApiException(ErrorCodeEnum.ParamsError, "没有key=["+name+"]的返回值");
					} 
			 }
		} 
		//检查组合排序
		 List<CompositeOrder> compositeOrderList = queryCondition.getCompositeOrderList();
		 if(!CollectionUtils.isEmpty(compositeOrderList)){
			 for(CompositeOrder compositeOrder : compositeOrderList){
				 config = fileConfigs.get(compositeOrder.getFieldName());					
				if(config == null){
					 throw new ApiException(ErrorCodeEnum.ParamsError, "不支参数["+compositeOrder.getFieldName()+"]的排序");
				}
				if(config != null){
					if(!config.supportOrder()){
						throw new ApiException(ErrorCodeEnum.ParamsError, "不支参数["+compositeOrder.getFieldName()+"]的排序");
					}
				} 
				//设置字段名称
				compositeOrder.setColName(config.colname());
			 }
			 if(queryCondition.getOrder() != null && queryCondition.getOrder().length>0){
				 throw new  ApiException(ErrorCodeEnum.ParamsError, "不能同时使用两种排序[sort排序，order排序],建议使用新的组合排序支持特性sort!");
			 }
		 }
		 
		return true;
	}
	/**
	 * 检查show_fields是否合理
	 * @param clazz
	 * @param show_fields
	 * @return
	 * @throws ApiException
	 */
	@SuppressWarnings("rawtypes")
	public static  boolean checkShowFields(Class clazz,String[] show_fields)throws ApiException{
		 Map<String,QueryConfig> fileConfigs = new HashMap<String,QueryConfig>();
		 Field[] fieldArray = clazz.getDeclaredFields();
		 QueryConfig config =  null;
		 for(Field field : fieldArray){
			    config = field.getAnnotation(QueryConfig.class);
			    fileConfigs.put(field.getName(), config);
		 }
		if(show_fields!=null&&show_fields.length>0){
			//检查show_fields条件是否合法
			for(String name : show_fields){
					config = fileConfigs.get(name);
					if(config == null){
						 throw new ApiException(ErrorCodeEnum.ParamsError, "没有key=["+name+"]的返回值");
					} 
			 }
		} 
		
		return false;
	}
	/**
	 * 根据domainClazz的QueryConfig配置将colname的key转成fieldname的可以
	 * @param listMap
	 * @param domainClazz
	 * @return
	 */
	public static List<Map<String,Object>> changeKeyFromColNameToFieldName(List<Map<String,Object>> listMap,Class<?> domainClazz){
		 if(listMap == null || listMap.isEmpty()){
			 return new ArrayList<Map<String,Object>>();
		 }
		 if(domainClazz == null){
			 throw new ApiException(ErrorCodeEnum.SystemError,"请指定DomainClass!");
		 }
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(listMap.size());
		Field[] fields = domainClazz.getDeclaredFields();
		QueryConfig config = null;
		Map<String,String> namesMap = new HashMap<String,String>();
		if(fields != null){
			for(Field  filed : fields){
				if(filed.isAnnotationPresent(QueryConfig.class)){
					config = filed.getAnnotation(QueryConfig.class);
					if(config.supportGroupBy()){
						namesMap.put(config.colname(), filed.getName());
					}
				}
			}
		}
		if(namesMap!=null && namesMap.size()>0){
			for(Map<String,Object> data : listMap ){
				resultList.add(changeKeys(data,namesMap));
			}
		} 
		return resultList;
	}
	/**
	 * 根据配置，将colname的key转成fieldname的可以
	 * @param data
	 * @param config
	 * @return
	 */
	private static Map<String,Object> changeKeys(Map<String,Object> data,Map<String,String> config){
		Map<String,Object>  result = new HashMap<String,Object>();
		if(data != null){
			Set<String> keyset = data.keySet();
			String fieldName = null;
			for(String key : keyset){
				fieldName =  config.get(key);
				if(!StringUtils.isEmpty(fieldName)){
					result.put(fieldName, data.get(key));
				}else{
					result.put(key, data.get(key));
				}
			}
		}
		return result;
	}
} 
