package com.tzw.eq;

import java.text.NumberFormat;

public class CrMathUtils {
    public CrMathUtils() {
    }

    /**
     * int 数据转换为byte数组，小端顺序
     * @param data
     * @return
     */
    public static byte[] int2bytes(int data) {
        byte[] bytes = new byte[]{(byte) (data & 255), (byte) ((data & '\uff00') >> 8), (byte) ((data & 16711680) >> 16), (byte) ((data & -16777216) >> 24)};
        return bytes;
    }

    public static byte[] long2bytes(long data) {
        byte[] bytes = new byte[]{(byte) ((int) (data & 255L)), (byte) ((int) (data >> 8 & 255L)), (byte) ((int) (data >> 16 & 255L)), (byte) ((int) (data >> 24 & 255L)), (byte) ((int) (data >> 32 & 255L)), (byte) ((int) (data >> 40 & 255L)), (byte) ((int) (data >> 48 & 255L)), (byte) ((int) (data >> 56 & 255L))};
        return bytes;
    }

    public static byte[] float2bytes(float data) {
        int intBits = Float.floatToIntBits(data);
        return int2bytes(intBits);
    }

    public static byte[] short2Byte(int res) {
        byte[] targets = new byte[]{(byte) (res & 255), (byte) (res >>> 8 & 255)};
        return targets;
    }

    public static int byte2short(byte[] res) {
        if (res == null) {
            return 0;
        } else {
            int targets = res[0] & 255 | res[1] << 8 & '\uff00';
            return targets;
        }
    }

    public static int byte2int(byte[] res) {
        if (res == null) {
            return 0;
        } else {
            int targets = res[0] & 255 | res[1] << 8 & '\uff00' | res[2] << 24 >>> 8 | res[3] << 24;
            return targets;
        }
    }

    public static int byte2short(byte[] res, int offset) {
        if (res == null) {
            return 0;
        } else {
            int targets = res[offset] & 255 | res[offset + 1] << 8 & '\uff00';
            return targets;
        }
    }

    /**
     * 字节转换为浮点
     *
     * @param b 字节（至少4个字节）
     * @param index 开始位置
     * @return
     */
    public static float byte2float(byte[] b, int index) {
        int l;
        l = b[index];
        l &= 0xff;
        l |= ((long) b[index + 1] << 8);
        l &= 0xffff;
        l |= ((long) b[index + 2] << 16);
        l &= 0xffffff;
        l |= ((long) b[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }

    public static String formatNum(float x, int n) {
        NumberFormat instance = NumberFormat.getNumberInstance();
        instance.setMaximumFractionDigits(n);
        instance.setMinimumFractionDigits(n);
        return instance.format((double) x);
    }

    public static String formatNum(float x, int m, int n) {
        NumberFormat instance = NumberFormat.getNumberInstance();
        instance.setMaximumFractionDigits(n);
        instance.setMinimumFractionDigits(n);
        instance.setMaximumIntegerDigits(m);
        return instance.format((double) x);
    }

    public static float formatNum2Num(float x, int n) {
        double d = Math.pow(10.0D, (double) n);
        float y = (float) ((double) Math.round((double) x * d) / d);
        return y;
    }

    public static final String getStringFromBytes(byte[] bytes) {
        if (bytes == null) {
            return "null";
        } else {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; bytes != null && i < bytes.length; ++i) {

               // sb.append(Integer.toHexString(bytes[i] & 255));
                sb.append(bytes[i]& 255);
                sb.append("_");
            }

            return sb.toString();
        }
    }

    public static final String getStringFromInts(int[] values) {
        if (values == null) {
            return "null";
        } else {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; values != null && i < values.length; ++i) {
                sb.append(values[i]);
                sb.append("_");
            }

            return sb.toString();
        }
    }

    public static final String getStringFromBytesWithBlank(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; bytes != null && i < bytes.length; ++i) {
            sb.append(Integer.toHexString(bytes[i] & 255));
            sb.append(" ");
        }

        return sb.toString();
    }

  /*  public static void main(String[] arg) {
        System.out.print("xxx");
    }
*/
    public static long byteArrayToLong(byte[] byteArray) {
        byte[] a = new byte[8];
        int i = a.length - 1;

        for (int j = byteArray.length - 1; i >= 0; --j) {
            if (j >= 0) {
                a[i] = byteArray[j];
            } else {
                a[i] = 0;
            }

            --i;
        }

        long v0 = (long) (a[7] & 255) << 56;
        long v1 = (long) (a[6] & 255) << 48;
        long v2 = (long) (a[5] & 255) << 40;
        long v3 = (long) (a[4] & 255) << 32;
        long v4 = (long) (a[3] & 255) << 24;
        long v5 = (long) (a[2] & 255) << 16;
        long v6 = (long) (a[1] & 255) << 8;
        long v7 = (long) (a[0] & 255);
        return v0 + v1 + v2 + v3 + v4 + v5 + v6 + v7;
    }

    public static float getFloat(byte[] b, int index) {
        int l = b[index + 0];
        l = l & 255;
        l = (int) ((long) l | (long) b[index + 1] << 8);
        l &= 65535;
        l = (int) ((long) l | (long) b[index + 2] << 16);
        l &= 16777215;
        l = (int) ((long) l | (long) b[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }

    public static boolean isNumeric(String str) {
        int i = str.length();

        do {
            --i;
            if (i < 0) {
                return true;
            }
        } while (Character.isDigit(str.charAt(i)));

        return false;
    }

    public static final boolean isSameByteArray(byte[] cmd1, byte[] cmd2) {
        if (cmd1 != null && cmd2 != null) {
            if (cmd1.length != cmd2.length) {
                return false;
            } else {
                for (int i = 0; i < cmd1.length; ++i) {
                    if (cmd1[i] != cmd2[i]) {
                        return false;
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public static int getComplementValue(byte b1, byte b2) {
        int v1 = (255 & b1) << 8;
        int v2 = b2 & 255;
        return getComplement(v1 + v2);
    }

    private static int getComplement(int b) {
        int v = b;
        if (b >= 32768) {
            v = b - 65536;
        }

        return v;
    }


    /**
     * Hex字符串转byte
     * @param inHex 待转换的Hex字符串
     * @return  转换后的byte
     */
    public static byte hexToByte(String inHex){
        return (byte) Integer.parseInt(inHex,16);
    }

}