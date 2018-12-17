/**
 * @author SongBin on 2018/12/10.
 */
package com.sqber.personMgr.base;

import org.thymeleaf.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StringUtil {
    /*
     * 是否为空字符串
     * @param str
     * @return
     */
    public static boolean isBlank(String str){
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str){
        return !isBlank(str);
    }
    /**
     * 连接方法 类似于javascript
     * @param join 连接字符串
     * @param strAry 需要连接的集合
     * @return
     */
    public static String join(String join,String[] strAry){
        StringBuffer sb=new StringBuffer();
        for(int i=0,len =strAry.length;i<len;i++){
            if(i==(len-1)){
                sb.append(strAry[i]);
            }else{
                sb.append(strAry[i]).append(join);
            }
        }
        return sb.toString();
    }

    public static String join(String join,List<String> listStr){
        StringBuffer sb=new StringBuffer();
        for(int i=0,len =listStr.size();i<len;i++){
            if(i==(len-1)){
                sb.append(listStr.get(i));
            }else{
                sb.append(listStr.get(i)).append(join);
            }
        }
        return sb.toString();
    }
}
