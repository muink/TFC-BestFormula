package TFCBestFormula;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConfigLoad {    //配置加载类
    public static int itemNum = 0, internalNum = 0, externalNum = 0, internalMetalNum = 0, externalMetalNum = 0;
    public static String path, logText_1, logText_2;
    public static String[] langText;
    public static String[][] forgingText = new String[90][9];
    public static String[][] needMetalText = new String[90][2];
    public static void load(){
        getPath();
        loadLangText();
        loadForgingText();
        loadNeedMetalText();
    }

    public static void getPath(){   //获取项目所在路径
        path = ConfigLoad.class.getProtectionDomain().getCodeSource().getLocation().getFile();  //获取jar状态下路径
        //path = System.getProperty("exe.path");    //获取exe状态下路径
        try {
            path = java.net.URLDecoder.decode(path, "UTF-8");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        int firstIndex = path.indexOf("/") + 1;
        int lastIndex = path.lastIndexOf("/") + 1;
        path = path.substring(firstIndex, lastIndex);
    }

    public static void loadLangText(){  //加载文本文件
        int i;
        langText = new String[27];
        try {
            InputStream inputStream = ConfigLoad.class.getResourceAsStream("config/lang.ini");
            assert inputStream != null;
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            Scanner reader = new Scanner(inputStreamReader);
            for(i = 0; reader.hasNext(); i++){
                langText[i] = reader.next();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void loadForgingText(){   //加载锻造项目文件
        loadInternalForgingText();
        loadExternalForgingText();
        itemNum = internalNum + externalNum;
    }

    public static void loadInternalForgingText(){   //加载内部锻造项目
        int i, j;
        try {
            InputStream inputStream = ConfigLoad.class.getResourceAsStream("config/forging.ini");
            assert inputStream != null;
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            Scanner reader = new Scanner(inputStreamReader);
            for(i = 0; reader.hasNext() && (i < 90); i++){
                for(j = 0; j < 9; j++){
                    forgingText[i][j] = reader.next();
                }
                internalNum++;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void loadExternalForgingText(){   //加载外部锻造项目
        int i, j;
        try {
            FileInputStream fileInputStream = new FileInputStream(path + "config/forging.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            Scanner reader = new Scanner(inputStreamReader);
            for(i = internalNum; reader.hasNext() && (i < 90); i++){
                for(j = 0; j < 9; j++){
                    forgingText[i][j] = reader.next();
                }
                externalNum++;
            }
            logText_1 = langText[17] + externalNum + langText[18] + langText[20];
        }
        catch(FileNotFoundException e){
            logText_1 = langText[19] + langText[20];
        }
    }

    public static void loadNeedMetalText(){ //加载基底金属文件
        loadInternalNeedMetalText();
        loadExternalNeedMetalText();
    }

    public static void loadInternalNeedMetalText(){ //加载内部基底金属
        int i;
        try {
            InputStream inputStream = ConfigLoad.class.getResourceAsStream("config/need_metal.ini");
            assert inputStream != null;
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            Scanner reader = new Scanner(inputStreamReader);
            for(i = 0; reader.hasNext() && (i < 90); i++){
                needMetalText[i][0] = reader.next();
                needMetalText[i][1] = reader.next();
                internalMetalNum++;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void loadExternalNeedMetalText(){ //加载外部基底金属
        int i;
        try {
            FileInputStream fileInputStream = new FileInputStream(path + "config/need_metal.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            Scanner reader = new Scanner(inputStreamReader);
            for(i = internalMetalNum; reader.hasNext() && (i < 90); i++){
                needMetalText[i][0] = reader.next();
                needMetalText[i][1] = reader.next();
                externalMetalNum++;
            }
            logText_2 = langText[17] + externalMetalNum + langText[18] + langText[21];
        }
        catch(FileNotFoundException e){
            logText_2 = langText[19] + langText[21];
        }
    }
}