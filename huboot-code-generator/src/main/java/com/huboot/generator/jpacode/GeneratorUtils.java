package com.huboot.generator.jpacode;

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
    private final String ENTITY_PATH;
    private final String DTO_PATH;
    private final String CONTROLLER_PATH;
    private final String REPOSITORY_PATH;

    private BeanFactory beanFactory;
    private SqlSession sqlsession;

    public GeneratorUtils(String ROOT) {
        this.DAO_PATH = ROOT;
        this.ENTITY_PATH = ROOT;
        this.SERVICE_PATH = ROOT;
        this.DTO_PATH = ROOT;
        this.CONTROLLER_PATH = ROOT;
        this.REPOSITORY_PATH = ROOT;

        // 初始化spring
        beanFactory = new ClassPathXmlApplicationContext(
                "classpath:spring-context.xml");

        // 获取数据库链接
        sqlsession = (SqlSession) beanFactory
                .getBean("information_schemaSqlsession");
    }

    @SuppressWarnings({"resource", "rawtypes", "unchecked"})
    public void generator(String servicemodule, String tablename, String classname, String moduleName,
                          String tableDesc, String db_name, List<String> generatorList)
            throws Exception {

        String daoBasePackage = "com.huboot.business."+servicemodule+"."+moduleName+".dao";
        String entityBasePackage = "com.huboot.business."+servicemodule+"."+moduleName+".entity";
        String serviceBasePackage = "com.huboot.business."+servicemodule+"."+moduleName+".service";
        String repositoryBasePackage = "com.huboot.business."+servicemodule+"."+moduleName+".repository";
        String dtoBasePackage = "com.huboot.business."+servicemodule+"."+moduleName+".dto";
        String controllerBasePackage = "com.huboot.business."+servicemodule+"."+moduleName+".controller";

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
        context.put("serviceModule", servicemodule);
        context.put("tablename", tablename);
        context.put("classname", classname);
        context.put("tableDesc", tableDesc);
        context.put("newclassname", classname.substring(0, 1).toLowerCase()
                + classname.substring(1));

        context.put("daoBasePackage", daoBasePackage);
        context.put("repositoryBasePackage", repositoryBasePackage);
        context.put("entityBasePackage", entityBasePackage);
        context.put("serviceBasePackage", serviceBasePackage);
        context.put("dtoBasePackage", dtoBasePackage);
        context.put("controllerBasePackage", controllerBasePackage);
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
                data.get(i).put("coltype", "LocalDate");
                addImport(context, "java.time.LocalDate");
                continue;
            }
            if (dataType.equals("datetime")) {
                data.get(i).put("coltype", "LocalDateTime");
                addImport(context, "java.time.LocalDateTime");
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


        if(generatorList.contains("genertorEntity")) {
            // 生成ENTITY
            genertorEntity(context, classname);
        }
        if(generatorList.contains("genertorDTO")) {
            // 生成DTO
            genertorDTO(context, classname);
        }
        if(generatorList.contains("genertorQueryDTO")) {
            // 生成DTO
            genertorQueryDTO(context, classname);
        }
        if(generatorList.contains("genertorRepository")) {
            // 生成Repository
            genertorRepository(context, classname);
        }
        if(generatorList.contains("genertorService")) {
            // 生成SERVICE
            genertorService(context, classname);
        }
        if(generatorList.contains("genertorController")) {
            // 生成CONTROLLER
            genertorController(context, classname);
        }
        if(generatorList.contains("genertorDao")) {
            // 生成DAO
            genertorDao(context, classname);
        }
    }

    private void genertorDao(Context context, String classname) throws FileNotFoundException,
            UnsupportedEncodingException {

        Velocity.setProperty("input.encoding", "utf-8");
        Velocity.setProperty("output.encoding", "utf-8");
        Velocity.setProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init();

        // 生成dao层
        String type = "dao";
        Template template = Velocity.getTemplate("com/huboot/generator/jpacode/" + type + ".vm");
        File domainFile = new File(DAO_PATH + "/src/main/java/"
                + context.get("daoBasePackage").toString().replaceAll("\\.", "/") + "/" + "/I" + classname + "Dao.java");
        if (!domainFile.getParentFile().exists()) {
            domainFile.getParentFile().mkdirs();
        }
        PrintWriter writer = new PrintWriter(domainFile, "utf-8");
        template.merge(context, writer);
        writer.flush();
        writer.close();

        type = "dao.impl";
        template = Velocity.getTemplate("com/huboot/generator/jpacode/" + type + ".vm");
        domainFile = new File(DAO_PATH + "/src/main/java/"
                + context.get("daoBasePackage").toString().replaceAll("\\.", "/") + "/" + "/impl/" + classname + "DaoImpl.java");
        if (!domainFile.getParentFile().exists()) {
            domainFile.getParentFile().mkdirs();
        }
        writer = new PrintWriter(domainFile, "utf-8");
        template.merge(context, writer);
        writer.flush();
        writer.close();


    }

    private void genertorRepository(Context context, String classname) throws FileNotFoundException,
            UnsupportedEncodingException {

        Velocity.setProperty("input.encoding", "utf-8");
        Velocity.setProperty("output.encoding", "utf-8");
        Velocity.setProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init();

        String type = "repository";
        Template template = Velocity.getTemplate("com/huboot/generator/jpacode/" + type + ".vm");
        File domainFile = new File(REPOSITORY_PATH + "/src/main/java/"
                + context.get("repositoryBasePackage").toString().replaceAll("\\.", "/") + "/I"+ classname + "Repository.java");
        if (!domainFile.getParentFile().exists()) {
            domainFile.getParentFile().mkdirs();
        }
        PrintWriter writer = new PrintWriter(domainFile, "utf-8");
        template.merge(context, writer);
        writer.flush();
        writer.close();

    }


    private void genertorEntity(Context context, String classname)
            throws Exception {
        String type = "entity";
        Velocity.setProperty("input.encoding", "utf-8");
        Velocity.setProperty("output.encoding", "utf-8");
        Velocity.setProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init();
        // 生成dao层
        Template template = Velocity.getTemplate("com/huboot/generator/jpacode/" + type + ".vm");
        File domainFile = new File(ENTITY_PATH + "/src/main/java/"
                + context.get("entityBasePackage").toString().replaceAll("\\.", "/")
                + "/" + classname + "Entity.java");
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
        //
        Template template = Velocity.getTemplate("com/huboot/generator/jpacode/" + type + ".vm");
        File dtoFile = new File(DTO_PATH + "/src/main/java/" + context.get("dtoBasePackage").toString().replaceAll("\\.", "/") + "/" + classname + "DTO.java");
        if (!dtoFile.getParentFile().exists()) {
            dtoFile.getParentFile().mkdirs();
        }
        PrintWriter writer = new PrintWriter(dtoFile, "utf-8");
        template.merge(context, writer);
        writer.flush();
        writer.close();

    }

    private void genertorQueryDTO(Context context, String classname)
            throws Exception {
        String type = "querydto";
        Velocity.setProperty("input.encoding", "utf-8");
        Velocity.setProperty("output.encoding", "utf-8");
        Velocity.setProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init();
        Template template = Velocity.getTemplate("com/huboot/generator/jpacode/" + type + ".vm");
        File dtoFile = new File(DTO_PATH + "/src/main/java/" + context.get("dtoBasePackage").toString().replaceAll("\\.", "/") + "/" + classname + "QueryDTO.java");
        if (!dtoFile.getParentFile().exists()) {
            dtoFile.getParentFile().mkdirs();
        }
        PrintWriter writer = new PrintWriter(dtoFile, "utf-8");
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
        Template template = Velocity.getTemplate("com/huboot/generator/jpacode/" + type
                + ".vm");
        File domainFile = new File(SERVICE_PATH + "/src/main/java/"
                + context.get("serviceBasePackage").toString().replaceAll("\\.", "/") + "/"
                + "/I" + classname + "Service.java");
        if (!domainFile.getParentFile().exists()) {
            domainFile.getParentFile().mkdirs();
        }
        PrintWriter writer = new PrintWriter(domainFile, "utf-8");
        template.merge(context, writer);
        writer.flush();
        writer.close();
        type = "service.impl";
        template = Velocity.getTemplate("com/huboot/generator/jpacode/" + type + ".vm");
        domainFile = new File(SERVICE_PATH + "/src/main/java/"
                + context.get("serviceBasePackage").toString().replaceAll("\\.", "/") + "/"
                + "/impl/" + classname + "ServiceImpl.java");
        if (!domainFile.getParentFile().exists()) {
            domainFile.getParentFile().mkdirs();
        }
        writer = new PrintWriter(domainFile, "utf-8");
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
        Template template = Velocity.getTemplate("com/huboot/generator/jpacode/" + type + ".vm");
        File domainFile = new File(CONTROLLER_PATH + "/src/main/java/" + context.get("controllerBasePackage").toString().replaceAll("\\.", "/") + "/" + classname + "Controller.java");
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
