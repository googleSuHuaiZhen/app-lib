package com.zhilink.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author xiemeng
 * @des StringUtils
 * @date 2018-8-16
 */
public class StringUtils {
    /**
     * 计算字符串的中文长度
     */
    public static int getChineseNum(String s) {
        int num = 0;
        char[] myChar = s.toCharArray();
        for (int i = 0; i < myChar.length; i++) {
            if ((char) (byte) myChar[i] != myChar[i]) {
                num++;
            }
        }
        return num;
    }

    public static String objToString(Object obj) {
        if (obj != null && !String.valueOf(obj).equals("") && !String.valueOf(obj).equals("null")) {
            return String.valueOf(obj);
        } else {
            return "";
        }
    }

    /**
     * 转换字符串，如果字符串是NULL,或者“null”，或者"",统一返回""
     *
     * @param str 需要转换的字符串
     * @return String 统一返回空字符串
     */
    public static String toString(String str) {
        if (null != str && !str.equals("") && !str.equals("null")) {
            return str;
        } else {
            return "0";
        }
    }

    /**
     * 检查字符串是否是空白：<code>null</code>、空字符串<code>""</code>或只有空白字符。
     * <p>
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str 要检查的字符串
     * @return 如果为空白, 则返回<code>true</code>
     */
    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || str.equals("null") || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断字符串是否为空
     *
     * @param string 设置字符串
     * @return boolean 返回是否为空
     */
    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    /**
     * 格式化数字返回整数型
     */
    public static int parseInt(String number) {
        int intNumber = 0;
        try {
            intNumber = Integer.parseInt(StringUtils.deleteZero(number.trim()));
        } catch (NumberFormatException err) {
            intNumber = 0;
            err.printStackTrace();
        } catch (Exception err) {
            intNumber = 0;
            err.printStackTrace();
        }
        return intNumber;
    }


    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            int i = Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * double 相加
     */
    public static double sum(double d1, double d2) {
        try {
            BigDecimal bd1 = new BigDecimal(Double.toString(d1));
            BigDecimal bd2 = new BigDecimal(Double.toString(d2));
            return bd1.add(bd2).doubleValue();
        } catch (Exception e) {
            return d1;
        }
    }

    /**
     * string 相加
     */
    public static String sum(String str1, String str2) {
        BigDecimal bd1;

        BigDecimal bd2;
        try {
            bd1 = new BigDecimal(str1);
        } catch (Exception e) {
            e.printStackTrace();
            bd1 = new BigDecimal("0");
        }
        try {
            bd2 = new BigDecimal(str2);
        } catch (Exception e) {
            e.printStackTrace();
            bd2 = new BigDecimal("0");
        }
        return String.valueOf(bd1.add(bd2).doubleValue());
    }

    /**
     * double 相减
     */
    public static double sub(double d1, double d2) {
        try {
            BigDecimal bd1 = new BigDecimal(Double.toString(d1));
            BigDecimal bd2 = new BigDecimal(Double.toString(d2));
            return bd1.subtract(bd2).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            return d1;
        }
    }

    /**
     * string 相减
     */
    public static String sub(String d1, String d2) {
        BigDecimal bd1;

        BigDecimal bd2;
        try {
            bd1 = new BigDecimal(d1);
        } catch (Exception e) {
            e.printStackTrace();
            bd1 = new BigDecimal("0");
        }
        try {
            bd2 = new BigDecimal(d2);
        } catch (Exception e) {
            e.printStackTrace();
            bd2 = new BigDecimal("0");
        }
        return String.valueOf(bd1.subtract(bd2).doubleValue());
    }

    /**
     * double 乘法
     */
    public static double mul(double d1, double d2) {
        try {
            BigDecimal bd1 = new BigDecimal(Double.toString(d1));
            BigDecimal bd2 = new BigDecimal(Double.toString(d2));
            return bd1.multiply(bd2).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            return d1;
        }
    }

    /**
     * double 乘法
     */
    public static String mul(String d1, String d2) {
        try {
            BigDecimal bd1 = new BigDecimal(d1);
            BigDecimal bd2 = new BigDecimal(d2);
            return String.valueOf(bd1.multiply(bd2).doubleValue());
        } catch (Exception e) {
            return d1;
        }
    }

    /**
     * double 乘法
     */
    public static String mul(String d1, String d2, int scale) {
        try {
            BigDecimal bd1 = new BigDecimal(d1);
            BigDecimal bd2 = new BigDecimal(d2);
            double result = bd1.multiply(bd2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
            return String.valueOf(result);
        } catch (Exception e) {
            return d1;
        }
    }
    /**
     * double 除法
     */
    public static String div(String d1, String d2) {
        try {
            BigDecimal bd1 = new BigDecimal(d1);
            BigDecimal bd2 = new BigDecimal(d2);
            return String.valueOf(bd1.multiply(bd2).doubleValue());
        } catch (Exception e) {
            return d1;
        }
    }
    /**
     * double 除法
     */
    public static String div(String d1, String d2, int scale) {
        try {
            BigDecimal bd1 = new BigDecimal(d1);
            BigDecimal bd2 = new BigDecimal(d2);
            return String.valueOf(bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue());
        } catch (Exception e) {
            return d1;
        }
    }

    /**
     * double 除法
     */
    public static double div(double d1, double d2) {
        try {
            BigDecimal bd1 = new BigDecimal(Double.toString(d1));
            BigDecimal bd2 = new BigDecimal(Double.toString(d2));
            return bd1.divide(bd2).doubleValue();
        } catch (Exception e) {
            return d1;
        }
    }

    /**
     * 两个整数相除
     */
    public static int div(int a, int b) {
        int num = 0;
        try {
            if (a % b == 0) {
                num = a / b;
            } else {
                num = a / b + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 转换STRING成Double
     */
    public static double string2Double(String string) {
        double a = 0d;
        try {
            if (!StringUtils.isBlank(string)) {
                a = Double.valueOf(string);
            }
        } catch (Exception e) {
            a = 0;
        }
        return a;
    }


    /**
     * 根据，拆分
     */
    public static List<String> split(String str, String splitCode) {
        List<String> list = new ArrayList<String>();
        if (null != str) {
            String[] split = str.split(splitCode);
            list.addAll(Arrays.asList(split));
        }
        return list;
    }

    /**
     * 把后台传过来的\\n替换为\n
     */
    public static String lineChange(String str) {
        if (null != str) {
            return str.replace("\\n", "\n");
        }
        return str;
    }

    /**
     * 整数去0
     */
    public static String deleteZero(String str) {
        if (null != str) {
            if (str.indexOf(".") > 0) {
                //去掉多余的0
                str = str.replaceAll("0+?$", "");
                //如最后一位是.则去掉
                str = str.replaceAll("[.]$", "");
                return str;
            }
        }
        return str;
    }

    /**
     * 取出其中小的数量
     */
    public static String getMinQty(String numb1, String numb2) {
        return StringUtils.string2Double(numb1) < StringUtils.string2Double(numb2) ? numb1 : numb2;
    }


    /**
     * 格式化小数位为6位
     */
    public static String formatNum(String str) {
        try {
            double d = Double.parseDouble(str);
            DecimalFormat df = new DecimalFormat("0.000000");
            str = df.format(d);
        } catch (Exception e) {
            return str;
        }

        return str;
    }

    public static boolean isTime(String time) {
        Pattern pTime = compile("((((0?[0-9])|([1][0-9])|([2][0-4]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Pattern p = compile("((^\\+{0,1}[1-9]\\d*\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        return p.matcher(time).matches() || pTime.matcher(time).matches();
    }

}
