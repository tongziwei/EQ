package com.tzw.eq.compressor;

import android.util.Log;

import java.util.Arrays;

public class CompressorUtil {
    /**
     * @param u 数组，默认传入空数组
     * @param t Threshold（dB）：范围：-80~0，精度：1
     * @param r Ratio：0~100，精度：1
     * @param w Knee Width（dB）：0~80，精度：1
     * @return
     */
    public static double[] computeGain(double[] u, int t, int r, int w) {
        int ulength = u.length;
        double[] d = new double[ulength];
        int[] cond = new int[ulength];
        double[] g1 = new double[ulength];
        double[] g2 = new double[ulength];
        double[] g = new double[ulength];
        int w2 = w / 2;
        int s = 1 / r - 1;
        for (int i = 0; i < ulength; i++) {
            d[i] = u[i] - t;
            cond[i] = Math.abs(d[i]) < w2 ? 1 : 0;
            g1[i] = s * Math.pow(d[i] + w2, 2) / (2 * w);
            g2[i] = Math.min(d[i] * s, 0);
            if (w != 0) {
                if(cond[i]==1){
                    g[i] = g1[i];
                }else{
                    g[i] = g2[i];
                }
               // g[i] = g1[i] * cond[i] + g2[i] * (~cond[i]);
            } else {
                g[i] = g2[i];
            }
        }

        Log.d("tzw", "computeGain:g "+ Arrays.toString(g));
        return g;
    }


}
