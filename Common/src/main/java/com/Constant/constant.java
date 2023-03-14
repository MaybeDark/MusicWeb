package com.Constant;

import java.io.File;

//常数类
public class constant {
    public static String[] illegal_character = {" or ",":","\\"," "};   //敏感字符
    public static String   os_name = "unknown";                         //操作系统
    public static String   local_resources_address = "";                //本地资源地址
    static {
        os_name = System.getProperty("os.name").toLowerCase();
        if(os_name.startsWith("linux")){
            local_resources_address = File.separator+"music"+File.separator;
        }
        if(os_name.startsWith("window")){
            local_resources_address = "D:\\music\\";
        }
    }
}
