package com.xiehua.generator.mybatiscode.base;

import com.xiehua.generator.mybatiscode.AbstractGenerator;
import com.xiehua.generator.mybatiscode.GeneratorUtils;

import java.util.ArrayList;
import java.util.List;

public class SystemSerialNumberGenerator extends AbstractGenerator {
    public static void main(String[] args) {
        String db_name = "huboot-dev";

        String serviceModule="base"; //服务模块

        String tablename = "zb_system_serial_number";
        String classname = "SerialNumber";
        String moduleName = "system";
        String tableDesc = "系统中心-编号表";
        try {
            GeneratorUtils generator = new GeneratorUtils(ZYJ_ROOT);

            List<String> generatorList = new ArrayList<String>();
            //generatorList.add("genertorDomain");// DOMAIN
            //generatorList.add("genertorDao");// 生成DAO
            generatorList.add("genertorDomainXML");// 生成SQL
            //generatorList.add("genertorDTO");// 生成DTO
            //generatorList.add("genertorService");// 生成SERVICE
            //generatorList.add("genertorManager");// 生成MANAGER
            //generatorList.add("genertorVo");// 生成VO
            //generatorList.add("genertorQueryVo");// 生成VO QUERY
            //generatorList.add("genertorController");// 生成CONTROLLER

            generator.generator(serviceModule, tablename, classname, moduleName, tableDesc, db_name, generatorList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
