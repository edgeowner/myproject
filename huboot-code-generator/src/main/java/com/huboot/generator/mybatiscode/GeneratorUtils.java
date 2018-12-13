package com.huboot.generator.mybatiscode;

import org.apache.ibatis.session.SqlSession;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ****
 * 数据提交
 *
 * @author xueyu
 */
public class GeneratorUtils {
    private final String DAO_PATH;
    private final String SERVICE_PATH;
    private final String DOMAIN_PATH;
    private final String VO_PATH;
    private final String DTO_PATH;
    private final String CONTROLLER_PATH;
    private final String MANAGER_PATH;

    private BeanFactory beanFactory;
    private SqlSession sqlsession;

    public GeneratorUtils(String ROOT) {
        this.DAO_PATH = ROOT;
        this.DOMAIN_PATH = ROOT;
        this.SERVICE_PATH = ROOT;
        this.VO_PATH = ROOT;
        this.DTO_PATH = ROOT;
        this.CONTROLLER_PATH = ROOT;
        this.MANAGER_PATH = ROOT;

        // 初始化spring
        beanFactory = new ClassPathXmlApplicationContext(
                "classpath:spring-context.xml");

        // 获取数据库链接
        sqlsession = (SqlSession) beanFactory
                .getBean("information_schemaSqlsession");
    }

    @SuppressWarnings({"resource", "rawtypes", "unchecked"})
    public void generator(String serviceodule, String tablename, String classname, String moduleName,
                          String tableDesc, String db_name, List<String> generatorList)
            throws Exception {

        String daoBasePackage = "com.huboot.business."+serviceodule+".dao";
        String domainBasePackage = "com.huboot.business."+serviceodule+".domain";
        String serviceBasePackage = "com.huboot.business."+serviceodule+".service";
        String managerBasePackage = "com.huboot.business."+serviceodule+".manager";
        String dtoBasePackage = "com.huboot.business."+serviceodule+".dto";
        String voBasePackage = "com.huboot.business."+serviceodule+".vo";
        String controllerBasePackage = "com.huboot.business."+serviceodule+".controller";

        if (classname != null) {
            classname = classname.substring(0, 1).toUpperCase()
                    + classname.substring(1);
        }
        // 生成 dao层，service层，controll层
        Map<String, String> param = new HashMap<String, String>();
        param.put("table_name", tablename);
        param.put("db_name", db_name);

        // 获取表结构
        List<Map> data = sqlsession.selectList(
                "com.xiehua.generator.getColumnInfo", param);
        System.out.println(data);
        Context context = new VelocityContext();
        context.put("collist", data);
        context.put("moduleName", moduleName);
        context.put("tablename", tablename);
        context.put("classname", classname);
        context.put("tableDesc", tableDesc);
        context.put("newclassname", classname.substring(0, 1).toLowerCase()
                + classname.substring(1));

        context.put("daoBasePackage", daoBasePackage);
        context.put("domainBasePackage", domainBasePackage);
        context.put("serviceBasePackage", serviceBasePackage);
        context.put("dtoBasePackage", dtoBasePackage);
        context.put("voBasePackage", voBasePackage);
        context.put("controllerBasePackage", controllerBasePackage);
        context.put("managerBasePackage", managerBasePackage);
        // 生成实体bean
        for (int i = 0; i < data.size(); i++) {
            data.get(i).put(
                    "newcolumn_name",
                    underlineProccess(
                            data.get(i).get("column_name").toString(), true));
            data.get(i)
                    .put("newfield_name",
                            underlineProccess(data.get(i).get("column_name")
                                    .toString()));
            /*//注释字典转换成枚举
            if (StringUtils.isEmpty(data.get(i).get("column_comment"))) {
                throw new Exception("请添加字段的注释");
            }
            String comment = data.get(i).get("column_comment").toString().toLowerCase().replaceFirst("code=", "enum=");
            data.get(i)
                    .put("newcolumn_comment",
                            underlineProccess(comment, true));*/

            String dataType = data.get(i).get("data_type").toString();
            String columnType = data.get(i).get("column_type").toString();
            if (dataType.equals("varchar")) {
                data.get(i).put("coltype", "String");
                continue;
            }
            if (dataType.equals("smallint")) {
                data.get(i).put("coltype", "Integer");
                continue;
            }
            if (dataType.equals("char")) {
                data.get(i).put("coltype", "String");
                continue;
            }
            if (dataType.equals("int")) {
                data.get(i).put("coltype", "Integer");
                continue;
            }
            if (dataType.equals("bigint") || dataType.equals("mediumint")) {
                data.get(i).put("coltype", "Long");
                continue;
            }

            if (dataType.equals("longtext") || dataType.equals("text") || dataType.equals("tinytext")) {
                data.get(i).put("coltype", "String");
                continue;
            }
            if (dataType.equals("double") || dataType.equals("decimal")) {
                data.get(i).put("coltype", "BigDecimal");
                addImport(context, "java.math.BigDecimal");
                // context.put("import", "import java.math.BigDecimal;");
                continue;
            }
            if (dataType.equals("date")) {
                data.get(i).put("coltype", "Date");
                addImport(context, "java.util.Date");
                continue;
            }
            if (dataType.equals("datetime")) {
                data.get(i).put("coltype", "Date");
                addImport(context, "java.util.Date");
                continue;
            }
            if (dataType.equals("timestamp")) {
                data.get(i).put("coltype", "Timestamp");
                addImport(context, "java.sql.Timestamp");
                continue;
            }
            if (dataType.equals("tinyint")) {
                if (columnType.equals("tinyint(1)")) {
                    data.get(i).put("coltype", "Boolean");
                } else {
                    data.get(i).put("coltype", "Integer");
                }
                continue;
            }
        }
        if (context.get("import") == null) {
            context.put("import", "");
        }


        if(generatorList.contains("genertorDomain")) {
            // DOMAIN
            genertorDomain(context, classname);
        }
        if(generatorList.contains("genertorDao")) {
            // 生成DAO
            genertorDao(context, classname);
        }
        if(generatorList.contains("genertorDomainXML")) {
            // 生成SQL
            genertorDomainXML(context, classname);
        }
        if(generatorList.contains("genertorDTO")) {
            // 生成DTO
            genertorDTO(context, classname);
        }
        if(generatorList.contains("genertorService")) {
            // 生成SERVICE
            genertorService(context, classname);
        }
        if(generatorList.contains("genertorManager")) {
            // 生成MANAGER
            genertorManager(context, classname);
        }
        if(generatorList.contains("genertorVo")) {
            // 生成VO
            genertorVo(context, classname);
        }
        if(generatorList.contains("genertorQueryVo")) {
            // 生成VO QUERY
            genertorQueryVo(context, classname);
        }
        if(generatorList.contains("genertorController")) {
            // 生成CONTROLLER
            genertorController(context, classname);
        }
    }

    private void genertorDao(Context context, String classname) throws FileNotFoundException,
            UnsupportedEncodingException {
        String type = "dao";
        Velocity.setProperty("input.encoding", "utf-8");
        Velocity.setProperty("output.encoding", "utf-8");
        Velocity.setProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init();
        // 生成dao层
        Template template = Velocity.getTemplate("com/huboot/generator/code/" + type
                + ".vm");
        File domainFile = new File(DAO_PATH + "/src/main/java/"
                + context.get("daoBasePackage").toString().replaceAll("\\.", "/") + "/" + context.get("moduleName")
                + "/I" + classname + "Dao.java");
        if (!domainFile.getParentFile().exists()) {
            domainFile.getParentFile().mkdirs();
        }
        PrintWriter writer = new PrintWriter(domainFile, "utf-8");
        template.merge(context, writer);
        writer.flush();
        writer.close();
        type = "dao.impl";
        template = Velocity.getTemplate("com/huboot/generator/code/" + type + ".vm");
        domainFile = new File(DAO_PATH + "/src/main/java/"
                + context.get("daoBasePackage").toString().replaceAll("\\.", "/") + "/" + context.get("moduleName")
                + "/impl/" + classname + "DaoImpl.java");
        if (!domainFile.getParentFile().exists()) {
            domainFile.getParentFile().mkdirs();
        }
        writer = new PrintWriter(domainFile, "utf-8");
        template.merge(context, writer);
        writer.flush();
        writer.close();

    }

    private void genertorDomainXML(Context context, String classname) throws FileNotFoundException,
            UnsupportedEncodingException {
        String type = "xml";
        Velocity.setProperty("input.encoding", "utf-8");
        Velocity.setProperty("output.encoding", "utf-8");
        Velocity.setProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init();
        // 生成dao层
        Template template = Velocity.getTemplate("com/huboot/generator/code/" + type
                + ".vm");
        File domainFile = new File(DAO_PATH + "/src/main/java/"
                + context.get("daoBasePackage").toString().replaceAll("\\.", "/") + "/" + context.get("moduleName")
                + "/impl/" + classname + ".map.xml");
        if (!domainFile.getParentFile().exists()) {
            domainFile.getParentFile().mkdirs();
        }
        System.out.println("generating Domain XML: "
                + domainFile.getAbsolutePath());
        PrintWriter writer = new PrintWriter(domainFile, "utf-8");
        template.merge(context, writer);

        writer.flush();
        writer.close();
    }

    private void genertorDomain(Context context, String classname)
            throws Exception {
        String type = "domain";
        Velocity.setProperty("input.encoding", "utf-8");
        Velocity.setProperty("output.encoding", "utf-8");
        Velocity.setProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init();
        // 生成dao层
        Template template = Velocity.getTemplate("com/huboot/generator/code/" + type + ".vm");
        File domainFile = new File(DOMAIN_PATH + "/src/main/java/"
                + context.get("domainBasePackage").toString().replaceAll("\\.", "/")
                + "/" + context.get("moduleName")
                + "/" + classname + "Domain.java");
        if (!domainFile.getParentFile().exists()) {
            domainFile.getParentFile().mkdirs();
        }
        PrintWriter writer = new PrintWriter(domainFile, "utf-8");
        template.merge(context, writer);
        writer.flush();
        writer.close();
    }

    private void genertorDTO(Context context, String classname)
            throws Exception {
        String type = "dto";
        Velocity.setProperty("input.encoding", "utf-8");
        Velocity.setProperty("output.encoding", "utf-8");
        Velocity.setProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init();
        // 生成dao层
        Template template = Velocity.getTemplate("com/huboot/generator/code/" + type + ".vm");
        File dtoFile = new File(DTO_PATH + "/src/main/java/" + context.get("dtoBasePackage").toString().replaceAll("\\.", "/") + "/" + context.get("moduleName")
                + "/" + classname + "DTO.java");
        if (!dtoFile.getParentFile().exists()) {
            dtoFile.getParentFile().mkdirs();
        }
        PrintWriter writer = new PrintWriter(dtoFile, "utf-8");
        template.merge(context, writer);
        writer.flush();
        writer.close();
    }

    private void genertorManager(Context context, String classname)
            throws FileNotFoundException, UnsupportedEncodingException {
        String type = "manager";
        Velocity.setProperty("input.encoding", "utf-8");
        Velocity.setProperty("output.encoding", "utf-8");
        Velocity.setProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init();

        // 生成dao层
        Template template = Velocity.getTemplate("com/huboot/generator/code/" + type
                + ".vm");
        File domainFile = new File(MANAGER_PATH + "/src/main/java/"
                + context.get("managerBasePackage").toString().replaceAll("\\.", "/") + "/" + context.get("moduleName")
                + "/I" + classname + "Manager.java");
        if (!domainFile.getParentFile().exists()) {
            domainFile.getParentFile().mkdirs();
        }
        PrintWriter writer = new PrintWriter(domainFile, "utf-8");
        template.merge(context, writer);
        writer.flush();
        writer.close();
        type = "manager.impl";
        template = Velocity.getTemplate("com/huboot/generator/code/" + type + ".vm");
        domainFile = new File(MANAGER_PATH + "/src/main/java/"
                + context.get("managerBasePackage").toString().replaceAll("\\.", "/") + "/" + context.get("moduleName")
                + "/impl/" + classname + "ManagerImpl.java");
        if (!domainFile.getParentFile().exists()) {
            domainFile.getParentFile().mkdirs();
        }
        writer = new PrintWriter(domainFile, "utf-8");
        template.merge(context, writer);
        writer.flush();
        writer.close();

    }

    private void genertorService(Context context, String classname)
            throws FileNotFoundException, UnsupportedEncodingException {
        String type = "service";
        Velocity.setProperty("input.encoding", "utf-8");
        Velocity.setProperty("output.encoding", "utf-8");
        Velocity.setProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init();

        // 生成dao层
        Template template = Velocity.getTemplate("com/huboot/generator/code/" + type
                + ".vm");
        File domainFile = new File(SERVICE_PATH + "/src/main/java/"
                + context.get("serviceBasePackage").toString().replaceAll("\\.", "/") + "/" + context.get("moduleName")
                + "/I" + classname + "Service.java");
        if (!domainFile.getParentFile().exists()) {
            domainFile.getParentFile().mkdirs();
        }
        PrintWriter writer = new PrintWriter(domainFile, "utf-8");
        template.merge(context, writer);
        writer.flush();
        writer.close();
        type = "service.impl";
        template = Velocity.getTemplate("com/huboot/generator/code/" + type + ".vm");
        domainFile = new File(SERVICE_PATH + "/src/main/java/"
                + context.get("serviceBasePackage").toString().replaceAll("\\.", "/") + "/" + context.get("moduleName")
                + "/impl/" + classname + "ServiceImpl.java");
        if (!domainFile.getParentFile().exists()) {
            domainFile.getParentFile().mkdirs();
        }
        writer = new PrintWriter(domainFile, "utf-8");
        template.merge(context, writer);
        writer.flush();
        writer.close();
    }

    private void genertorVo(Context context, String classname)
            throws FileNotFoundException, UnsupportedEncodingException {
        String type = "vo";
        Velocity.setProperty("input.encoding", "utf-8");
        Velocity.setProperty("output.encoding", "utf-8");
        Velocity.setProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init();
        // 生成dao层
        Template template = Velocity.getTemplate("com/huboot/generator/code/" + type + ".vm");
        File domainFile = new File(VO_PATH + "/src/main/java/" + context.get("voBasePackage").toString().replaceAll("\\.", "/") + "/" + context.get("moduleName")
                + "/" + classname + "VO.java");
        if (!domainFile.getParentFile().exists()) {
            domainFile.getParentFile().mkdirs();
        }
        PrintWriter writer = new PrintWriter(domainFile, "utf-8");
        template.merge(context, writer);
        writer.flush();
        writer.close();
    }

    private void genertorQueryVo(Context context, String classname)
            throws FileNotFoundException, UnsupportedEncodingException {
        String type = "queryvo";
        Velocity.setProperty("input.encoding", "utf-8");
        Velocity.setProperty("output.encoding", "utf-8");
        Velocity.setProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init();
        // 生成dao层
        Template template = Velocity.getTemplate("com/huboot/generator/code/" + type + ".vm");
        File domainFile = new File(VO_PATH + "/src/main/java/" + context.get("voBasePackage").toString().replaceAll("\\.", "/") + "/" + context.get("moduleName")
                + "/" + classname + "QueryVO.java");
        if (!domainFile.getParentFile().exists()) {
            domainFile.getParentFile().mkdirs();
        }
        PrintWriter writer = new PrintWriter(domainFile, "utf-8");
        template.merge(context, writer);
        writer.flush();
        writer.close();
    }

    private void genertorController(Context context, String classname)
            throws Exception {
        String type = "controller";
        Velocity.setProperty("input.encoding", "utf-8");
        Velocity.setProperty("output.encoding", "utf-8");
        Velocity.setProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init();
        // 生成dao层
        Template template = Velocity.getTemplate("com/huboot/generator/code/" + type + ".vm");
        File domainFile = new File(CONTROLLER_PATH + "/src/main/java/" + context.get("controllerBasePackage").toString().replaceAll("\\.", "/") + "/" + context.get("moduleName")
                + "/" + classname + "Controller.java");
        if (!domainFile.getParentFile().exists()) {
            domainFile.getParentFile().mkdirs();
        }
        PrintWriter writer = new PrintWriter(domainFile, "utf-8");
        template.merge(context, writer);
        writer.flush();
        writer.close();
    }

    private void addImport(Context context, String importConent) {
        if (context.get("import") != null) {
            if (context.get("import").toString().indexOf(importConent) < 0)
                context.put("import", context.get("import") + "\nimport "
                        + importConent + ";");
        } else {
            context.put("import", "import " + importConent + ";");
        }
    }

    /**
     * 去掉下划线， 下划线后面第一个字符大写， 第一个字符大写
     */
    public String underlineProccess(String str, boolean firstToUpperCase) {
        if (str != null) {
            str = str.toLowerCase();
            str = strProccess("_", str);
            if (firstToUpperCase)
                str = str.substring(0, 1).toUpperCase() + str.substring(1);

            return str;
        }
        return null;
    }

    private String strProccess(String replaceStr, String str) {
        if (str != null) {
            int index = str.indexOf(replaceStr);
            if (index > -1) {
                String header = str.substring(0, index);
                String tail = str.substring(index + replaceStr.length());
                tail = tail.substring(0, 1).toUpperCase() + tail.substring(1);
                str = strProccess(replaceStr, header + tail);
            }
        }
        return str;
    }

    /**
     * 去掉下划线， 下划线后面第一个字符大写， 第一个字符大写
     */
    public String underlineProccess(String str) {
        return underlineProccess(str, false);
    }

}
