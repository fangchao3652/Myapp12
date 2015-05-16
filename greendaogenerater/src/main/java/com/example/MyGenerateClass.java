package com.example;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyGenerateClass {

//    public static final String DAO_PATH = "../pushclient/src/main/java-gen";
    public static final String DAO_PATH = "D:\\DevelopTools\\AndroidStudio\\workspace_github\\MyApplication\\pushclient\\src\\main\\java";
    public static final String PACKAGE_NAME = "com.example.greendao_fc";
    public static final int DATA_VERSION_CODE = 1;
    public static void main(String[] args) throws Exception {
        System.out.print("dsds");
        Schema schema = new Schema(DATA_VERSION_CODE, PACKAGE_NAME);

        addCache(schema, "CacheBean1");

        //生成Dao文件路径
        new DaoGenerator().generateAll(schema, DAO_PATH);
    }
    private static void addCache(Schema schema, String tableName) {
        Entity joke = schema.addEntity(tableName);


        joke.addStringProperty("JsonKey").primaryKey();

        joke.addStringProperty("JsonCache");

        joke.addLongProperty("time");

    }
}
