package com.example;

import com.afollestad.materialdialogs.MaterialDialog;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyGenerateClass {

    public static final String DAO_PATH = "../pushclient/src/main/java-gen";
    public static final String PACKAGE_NAME = "com.example.greendao_fc";
    public static final int DATA_VERSION_CODE = 1;
    public static void main(String[] args) throws Exception {
        System.out.print("dsds");
        Schema schema = new Schema(DATA_VERSION_CODE, PACKAGE_NAME);

        addCache(schema, "CacheBean");

        //生成Dao文件路径
        new DaoGenerator().generateAll(schema, DAO_PATH);
    }
    private static void addCache(Schema schema, String tableName) {

        Entity joke = schema.addEntity(tableName);


        joke.addStringProperty("JsonKey");
        //请求结果
        joke.addStringProperty("JsonCache");
       /* //页数
        joke.addIntProperty("page");*/
        //插入时间，暂时无用
        joke.addLongProperty("time");

    }
}
