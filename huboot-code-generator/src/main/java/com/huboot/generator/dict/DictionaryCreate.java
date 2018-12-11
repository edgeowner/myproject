package com.huboot.generator.dict;

import com.huboot.generator.dict.DictionaryUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DictionaryCreate extends AbstractDict {

    //胡哥
    public final static String HQR_ROOT = "D:\\huboot\\code\\api";

    //切换root路径
    public final static String TARGET = HQR_ROOT + "\\huboot-framework\\src\\main\\java\\com\\huboot\\framework\\model\\enums\\dict\\";

    //模块
    public final static String MODEL = "product";


    public static void main(String[] args) {

        //字典id
        List<Integer> idList = Arrays.asList(55);

        DictionaryUtils.generator(MODEL, idList);
    }

}
