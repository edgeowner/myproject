package com.huboot.generator.jpacode.weixin;



import com.xiehua.generator.jpacode.AbstractGenerator;
import com.xiehua.generator.jpacode.GeneratorUtils;

import java.util.ArrayList;
import java.util.List;

public class MiniAppCodeTempalteGenerator extends AbstractGenerator {
    public static void main(String[] args) {
        String db_name = "huboot-uat";
        String systemModule="base_model"; //服务模块
        String tablename = "huboot";
        String classname = "WeixinMimiappCodeTemplate";
        String serviceModule = "weixin_service";
        String tableDesc = "小程序代码模板信息表";
        try {
            GeneratorUtils generator = new GeneratorUtils("");

            List<String> generatorList = new ArrayList<String>();
            generatorList.add("genertorController");// 生成ENTITY
            generatorList.add("genertorEntity");// 生成ENTITY
            //generatorList.add("genertorDTO");// 生成DTO
            //generatorList.add("genertorQueryDTO");// 生成QueryDTO
            generatorList.add("genertorService");// 生成SERVICE
            generatorList.add("genertorRepository");// 生成Repository
            //generatorList.add("genertorDao");// 生成DAO

            generator.generator(systemModule, tablename, classname, serviceModule, tableDesc, db_name, generatorList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
