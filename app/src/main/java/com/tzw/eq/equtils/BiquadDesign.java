package com.tzw.eq.equtils;

import static java.lang.Math.PI;
import static java.lang.Math.sqrt;

public class BiquadDesign {
    public static final int TYPE_PEAK = 0;
    public static final int TYPE_LOWSHELF = 1;
    public static final int TYPE_HIGHSHELF = 2;
    public static final int TYPE_LOWPASS = 3;
    public static final int TYPE_HIGHPASS = 4;
    public static final int TYPE_BANDPASS = 5;
    public static final int TYPE_NOTCH = 6;
    public static final int TYPE_ALLPASS = 7;


    /**
     * @param gain   Gain（dB）：范围：-15~15，精度：1
     * @param fc     Fc（Hz）：范围：20~20000，精度：1
     * @param bw     BW（Hz）：范围：10~20000，精度：1
     * @param type   Type：peak、lowshelf、highshelf、lowpass、highpass、bandpass、notch、allpass（0-7）
     * @param bypass bypass :选中1，未选中0；
     * @param fs     采样频率
     * @return 截面矩阵系数sos
     */
    public static Coeff getSectionsMatrix(int gain, int fc, int bw, int type, boolean bypass, int fs) {

        double w = (double) 2 * fc / fs;
        double q = (double) fc / bw;
        int bypassStatus = bypass?1:0;
        int bqtype = type + (int)bypassStatus * 10;
        double sA = Math.pow(10, (double) gain / 40);
        double sqrt_sA = sqrt(sA);
        double sin_w = Math.sin(PI * w);
        double cos_w = Math.cos(PI * w);
        double alpha = sin_w / (2 * q);
        double b0;
        double b1;
        double b2;
        double a0;
        double a1;
        double a2;
        switch (bqtype){
            case TYPE_PEAK:
                b0 = 1 + alpha * sA;
                b1 = -2 * cos_w;
                b2 = 1 - alpha * sA;
                a0 = 1 + alpha / sA;
                a1 = b1;
                a2 = 1 - alpha / sA;
                break;
            case TYPE_LOWSHELF:
                b0 = sA * ((sA + 1) - (sA - 1) * cos_w + 2 * sqrt_sA * alpha);
                b1 = 2 * sA * ((sA - 1) - (sA + 1) * cos_w);
                b2 = sA * ((sA + 1) - (sA - 1) * cos_w - 2 * sqrt_sA * alpha);
                a0 = (sA + 1) + (sA - 1) * cos_w + 2 * sqrt_sA * alpha;
                a1 = -2 * ((sA - 1) + (sA + 1) * cos_w);
                a2 = (sA + 1) + (sA - 1) * cos_w - 2 * sqrt_sA * alpha;
                break;
            case TYPE_HIGHSHELF:
                b0 = sA * ((sA + 1) + (sA - 1) * cos_w + 2 * sqrt_sA * alpha);
                b1 = -2 * sA * ((sA - 1) + (sA + 1) * cos_w);
                b2 = sA * ((sA + 1) + (sA - 1) * cos_w - 2 * sqrt_sA * alpha);
                a0 = (sA + 1) - (sA - 1) * cos_w + 2 * sqrt_sA * alpha;
                a1 = 2 * ((sA - 1) - (sA + 1) * cos_w);
                a2 = (sA + 1) - (sA - 1) * cos_w - 2 * sqrt_sA * alpha;
                break;
            case TYPE_LOWPASS:
                b0 = (1 - cos_w) / 2;
                b1 = 1 - cos_w;
                b2 = b0;
                a0 = 1 + alpha;
                a1 = -2 * cos_w;
                a2 = 1 - alpha;
                break;
            case TYPE_HIGHPASS:
                b0 = (1 + cos_w) / 2;
                b1 = -(1 + cos_w);
                b2 = b0;
                a0 = 1 + alpha;
                a1 = -2 * cos_w;
                a2 = 1 - alpha;
                break;
            case TYPE_BANDPASS:
                b0 = alpha;
                b1 = 0;
                b2 = -alpha;
                a0 = 1 + alpha;
                a1 = -2 * cos_w;
                a2 = 1 - alpha;
                break;
            case TYPE_NOTCH:
                b0 = 1;
                b1 = -2 * cos_w;
                b2 = 1;
                a0 = 1 + alpha;
                a1 = -2 * cos_w;
                a2 = 1 - alpha;
                break;
            case TYPE_ALLPASS:
                b0 = 1 - alpha;
                b1 = -2 * cos_w;
                b2 = 1 + alpha;
                a0 = b2;
                a1 = b1;
                a2 = b0;
                break;
            default:
                b0 = 1;
                b1 = 0;
                b2 = 0;
                a0 = 1;
                a1 = 0;
                a2 = 0;
                break;
        }

        Coeff coeff = new Coeff();
        coeff.setB0(b0/a0);
        coeff.setB1(b1/a0);
        coeff.setB2(b2/a0);
        coeff.setA0(a0/a0);
        coeff.setA1(a1/a0);
        coeff.setA2(a2/a0);
        return coeff;
    }
}
