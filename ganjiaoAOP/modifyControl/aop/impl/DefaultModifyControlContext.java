package com.hpe.modifyControl.aop.impl;

import com.hpe.log.aop.common.OperationType;
import com.hpe.log.util.BeanUtils;
import com.hpe.modifyControl.aop.common.AbstractModifyControlContext;
import com.hpe.modifyControl.aop.common.SpringContext;
import com.hpe.modifyControl.entity.ModifyControlSetting;
import com.hpe.modifyControl.service.ModifyControlSettingRepository;
import com.hpe.util.DateUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description:
 *
 * @author hqr
 * @version 1.0
 */
public class DefaultModifyControlContext extends AbstractModifyControlContext {

    private static final Logger logger = LoggerFactory.getLogger(DefaultModifyControlContext.class);

    private List<ModifyControlSetting> modifyControlSettings;
    private EntityManager entityManager;
    private static final Map<OperationType, List<String>> OPERATION_METHODS;
    private boolean controlEnabled;

    private String tableName;
    private Class<?> domainEntity;
    private Method method;
    private Object[] args;

    static {
        OPERATION_METHODS = new HashMap<>();
        OPERATION_METHODS.put(OperationType.ADD, Collections.singletonList("save"));
        OPERATION_METHODS.put(OperationType.UPDATE, Collections.singletonList("save"));
        OPERATION_METHODS.put(OperationType.DELETE, Collections.singletonList("delete"));
        OPERATION_METHODS.put(OperationType.QUERY, Collections.singletonList("find"));
    }

    public DefaultModifyControlContext(List<ModifyControlSetting> modifyControlSettings, EntityManager entityManager, JoinPoint joinPoint) {
        this.modifyControlSettings = modifyControlSettings;
        this.entityManager = entityManager;
        setJoinPoint(joinPoint);
        initContext();
    }
    private void initContext() {
        controlEnabled = false;
        domainEntity = getDomainEntity();

        if (domainEntity == null) {
            return;
        }

        method = getMethod();
        args = joinPoint.getArgs();

        Table table = domainEntity.getAnnotation(Table.class);
        if (table == null) {
            throw new IllegalArgumentException(domainEntity.getName());
        }
        tableName = table.name();

        Optional<ModifyControlSetting> modifyControlSettingOptional = modifyControlSettings.stream()
                .filter(modifyControlSetting -> modifyControlSetting.getTableName().equals(tableName))
                .findAny();

        modifyControlSettingOptional.ifPresent(modifyControlSetting -> {

            List<String> methods = OPERATION_METHODS.get(OperationType.UPDATE);
            if(methods.stream().anyMatch(methodName -> this.method.getName().startsWith(methodName))){
                controlEnabled = true;
            }
        });

    }

    @Override
    public Object process() {
        Object returnValue = null;
        ProceedingJoinPoint proceedingJoinPoint = (ProceedingJoinPoint) this.joinPoint;
        try {
            returnValue = proceedingJoinPoint.proceed();
        } catch (Throwable t) {
            logger.error("JoinPoint proceed fail.", t);
        }
        return returnValue;
    }

    @Override
    public boolean isControlEnabled() {
        return controlEnabled;
    }

    @Override
    public boolean checkIsModify() {
        boolean isModify = false;
        try{
            Map<String, Object> oldMap = getOldProperties();
            if(BeanUtils.transToMap(args[0]).get("modifyDate") != null && oldMap.get("modifyDate") != null){
                //修改时页面数据当时的修改时间
                String pageModifyDate = BeanUtils.transToMap(args[0]).get("modifyDate").toString();
                SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                pageModifyDate = sdf2.format(sdf1.parse(pageModifyDate));
                //获取当前数据库的修改时间
                String oldModifyDate = oldMap.get("modifyDate").toString();
                oldModifyDate = DateUtil.formatDateTime(DateUtil.parseStrToDate(oldModifyDate));
                    if(!pageModifyDate.equals(oldModifyDate)){
                        isModify = true;
                    }
            }else if(BeanUtils.transToMap(args[0]).get("modifyDate") == null && oldMap.get("modifyDate") != null){
                //针对数据刚新增，之前都没有修改过，所以，修改时间为空的情况
                 isModify = true;
            }

        }catch (Exception e){
            logger.error("---------------------切面校验修改时间报错----------------", e);
        }

        return isModify;
    }

    private Class<?> getDomainEntity() {
        Type[] interfaces = joinPoint.getTarget().getClass().getGenericInterfaces();
        if (interfaces == null || interfaces.length == 0) {
            return null;
        }
        Type[] parameterizedTypes = ((Class<?>) interfaces[0]).getGenericInterfaces();
        if (parameterizedTypes == null || parameterizedTypes.length == 0) {
            return null;
        }
        Type[] typeArguments = ((ParameterizedType) parameterizedTypes[0]).getActualTypeArguments();

        if (typeArguments.length == 2) {
            return (Class<?>) typeArguments[0];
        } else {
            return null;
        }
    }

    private Map<String, Object> getOldProperties() {
        for (Object arg : args) {
            if (arg.getClass().isAssignableFrom(domainEntity)) {
                Object id = BeanUtils.transToMap(arg).get("id");

                try {
                    entityManager.detach(arg);
                    @SuppressWarnings("unchecked")
                    Method findOneMethod = joinPoint.getSignature().getDeclaringType().getMethod("findOne", Serializable.class);
                    Object result = findOneMethod.invoke(joinPoint.getTarget(), id);

                    return BeanUtils.transToMap(result);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    logger.error("Invoke findOne method fail.", e);
                }
            }
        }
        return null;
    }

    @Override
    public Object setModifyDateProcess(Date date) {
        Object returnValue = null;
        try {
            ProceedingJoinPoint proceedingJoinPoint = (ProceedingJoinPoint) this.joinPoint;
            Map<String,Object> map =BeanUtils.transToMap(args[0]);
            if(map.get("modifyDate") != null){
                map.put("modifyDate",date);
                args[0]=BeanUtils.transToBean(args[0].getClass(),map);
            }
            returnValue = proceedingJoinPoint.proceed(args);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return returnValue;
    }


    /*public static void main(String[] args){
        try{

            String pageModifyDate = "Tue Mar 12 14:13:05 CST 2019";
            SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date f = sdf1.parse(pageModifyDate);
            SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            pageModifyDate = sdf2.format(f);
            System.out.print(pageModifyDate);
        }catch (Exception e){

        }
    }*/

}
