package util;

public class StringUtil {
    private static final String UNDERLINE = "_";
    public static boolean isEmpty(Object str){
        if(null == str){
            return true;
        }else if(str.toString().trim().equals("")||str.toString().trim().equals("null")){
            return true;
        }
        return false;
    }
    public static boolean isNotEmpty(Object str){
        if(null == str){
            return false;
        }else if(str.toString().trim().equals("")||str.toString().trim().equals("null")){
            return false;
        }
        return true;
    }
    /**
     * 多个字符串使用下划线拼接
     * @param strs 字符串
     * @return 连接后的字符串
     */
    public static String appendStr(String ... strs) {
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str).append(UNDERLINE);
        }
        return sb.toString();
    }
    public static String monthStr(int x){
        if(x<10){
            return "0"+x;
        }else {
            return ""+x;
        }
    }
    public static String nvl(Object obj){
        if(isEmpty(obj)){
            return "";
        }else{
            return obj.toString();
        }
    }
    public static String nvl(Object obj,String str){
        if(isEmpty(obj)){
            return str;
        }else{
            return obj.toString();
        }
    }
}
