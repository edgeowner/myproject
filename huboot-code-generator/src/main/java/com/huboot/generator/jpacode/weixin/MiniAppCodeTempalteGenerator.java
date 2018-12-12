package com.huboot.generator.jpacode.weixin;



import com.huboot.generator.jpacode.AbstractGenerator;
import com.huboot.generator.jpacode.GeneratorUtils;

import java.util.ArrayList;
import java.util.List;

public class MiniAppCodeTempalteGenerator extends AbstractGenerator {
    public static void main(String[] args) {
        String db_name = "huboot-dev";
        String systemModule="base_model"; //服务模块
        String tablename = "weixin_nimiapp_code_template";
        String classname = "WeixinMimiappCodeTemplate";
        String serviceModule = "test_weixin_service";
        String tableDesc = "小程序代码模板信息表";
        try {
            GeneratorUtils generator = new GeneratorUtils(HQR_ROOT);

            List<String> generatorList = new ArrayList<String>();
//            generatorList.add("genertorController");// 生成ENTITY
            generatorList.add("genertorEntity");// 生成ENTITY
            //generatorList.add("genertorDTO");// 生成DTO
            //generatorList.add("genertorQueryDTO");// 生成QueryDTO
//            generatorList.add("genertorService");// 生成SERVICE
            generatorList.add("genertorRepository");// 生成Repository
            //generatorList.add("genertorDao");// 生成DAO

            generator.generator(systemModule, tablename, classname, serviceModule, tableDesc, db_name, generatorList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
