package com.xiehua.generator.dict;

import org.apache.ibatis.session.SqlSession;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictionaryUtils extends AbstractDict {

    private static final String ENUM_BASE_PACKAGE = "com.xiehua.framework.model.enums.dict";

    public static void generator(String model, List<Integer> idList) {

        if(model == null || model == "") {
            throw new RuntimeException("字典模块不能为空");
        }
        String pmodel = "." + model;

        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "classpath:spring-context.xml");

        // 获取数据库链接
        SqlSession sqlsession = (SqlSession) beanFactory
                .getBean("information_schemaSqlsession");

        // 获取字典
        List<Map> dicts = sqlsession.selectList(
                "com.xiehua.generator.getDictionarys", idList);


        Map<String, Object> param = new HashMap<String, Object>();

        for (Map dict : dicts) {
            param.put("dictId", dict.get("id"));
            dict.put("codeName", convertKey(dict.get("code")));
            List<Map> items = sqlsession.selectList("com.xiehua.generator.getDictionaryItems", param);
            for (Map item : items) {
                item.put("nameEn", convertVale(item.get("name_en")));
            }
            dict.put("items", items);
        }

        for (Map dict : dicts) {
            Context context = new VelocityContext();
            Velocity.setProperty("input.encoding", "utf-8");
            Velocity.setProperty("output.encoding", "utf-8");
            Velocity.setProperty("file.resource.loader.class",
                    "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            context.put("dict", dict);
            context.put("enumBasePackage", ENUM_BASE_PACKAGE + pmodel);

            Velocity.init();
            Template template = Velocity.getTemplate("com/xiehua/generator/dict/" + "dictionary_enum"
                    + ".vm");
            OutputStreamWriter out = null;
            try {
                out = new OutputStreamWriter(new FileOutputStream(DictionaryCreate.TARGET + "\\" + model + "\\" + dict.get("codeName") + ".java"));
                template.merge(context, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    /***
     * 转换名称
     * @param key
     * @return
     */
    private static Object convertKey(Object key) {
        String va = (String) key;
        String[] sp = va.split("\\_+");
        String out = "";
        for (String v : sp) {
            out += (v.substring(0, 1) + v.toLowerCase().substring(1));
        }
        return out + "Enum";
    }

    private static String convertVale(Object obj) {
        String val = (String) obj;
        String[] sk = val.split("\\s+");
        String vals = "";
        for (String v : sk) {

            vals += v.replaceAll("\\-", "_").replaceAll("\\(", "_").replaceAll("\\)", "");
        }
        return vals;
    }


}
