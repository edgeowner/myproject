package com.huboot.generator.jpacode.weixin;



import com.huboot.generator.jpacode.AbstractGenerator;
import com.huboot.generator.jpacode.GeneratorUtils;

import java.util.ArrayList;
import java.util.List;

public class WeixinPublicGenerator extends AbstractGenerator {
    public static void main(String[] args) {
        String db_name = "huboot-dev";
        String systemModule="base_model"; //服务模块
        String tablename = "huboot";
        String classname = "WeixinPublic";
        String serviceModule = "weixin_service";
        String tableDesc = "商家微信公众号配置信息表";
        try {
            GeneratorUtils generator = new GeneratorUtils("");

            List<String> generatorList = new ArrayList<String>();
            generatorList.add("genertorController");// 生成ENTITY
            //generatorList.add("genertorEntity");// 生成ENTITY
            //generatorList.add("genertorDTO");// 生成DTO
            //generatorList.add("genertorQueryDTO");// 生成QueryDTO
            generatorList.add("genertorService");// 生成SERVICE
            //generatorList.add("genertorRepository");// 生成Repository
            //generatorList.add("genertorDao");// 生成DAO

            generator.generator(systemModule, tablename, classname, serviceModule, tableDesc, db_name, generatorList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
