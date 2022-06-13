package com.tzw.eq.equtils;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class FrequencyResponse {

    public static double getFreqz(Coeff coeff,double z){
       double hup= coeff.getB0()+coeff.getB1()*Math.pow(z,-1)+coeff.getB2()*Math.pow(z,-2);
       double hdown = coeff.getA0()+coeff.getA1()*Math.pow(z,-1)+coeff.getA2()*Math.pow(z,-2);
       return hup/hdown;
    }

    /**
     * 计算传递函数的频率响应
     *                 jw                 -jw            -jmw
     *          jw  B(e  )    b(0) + b(1)e + .... + b(m)e
     *       H(e) = ---- = ------------------------------------
     *                 jw               -jw              -jnw
     *              A(e  )    a(0) + a(1)e + .... + a(n)e
     * @param coeff 传递函数的系数 b0,b1,b2,a0,a1,a2
     * @param w 角频率
     * @return   频率响应 （复数类型）
     */
    public static Complex getFreqw(Coeff coeff,double w){
        Complex complex = new Complex(cos(w),-sin(w));
        Complex b2 = (complex.mul(complex)).mul(coeff.getB2());
        Complex b1 = complex.mul(coeff.getB1());
        Complex hup =b2.add(b1).add(coeff.getB0());
        Complex a2 = (complex.mul(complex)).mul(coeff.getA2());
        Complex a1 = complex.mul(coeff.getA1());
        Complex hdown =a2.add(a1).add(coeff.getA0());
        Complex res = hup.div(hdown);
       // Log.d("tzw", "getFreqw: "+res.getReal()+"+"+res.getImage()+"i");
        return res;
    }

    /**
     * 计算幅值
     * @param complex 频率响应
     * @return 幅值
     */
    public static double getAmplitude(Complex complex){
        return Math.sqrt(Math.pow(complex.getReal(),2)+Math.pow(complex.getImage(),2));
    }

    /**
     * 计算相位
     * @param complex  频率响应
     * @return 相位（-pi/2~pi/2）
     */
    public static double getPhase(Complex complex){
        return Math.atan(complex.getImage()/complex.getReal());
    }


    /**
     * 计算n 个点的频率响应
     * @param coeff 系数
     * @param n 点数 角频率范围（0，PI）
     * @return n-1个点的频率响应 （dB）
     */
    public static double[] getFreqzn(Coeff coeff,int n){
        double[] h =  new double[n-1];
        for(int i=1;i<n;i++){
            double w = i*(Math.PI/n);
          //  h[i-1]= getFreqz(coeff,z);
            Complex complex = getFreqw(coeff,w);
           // h[i-1] = getAmplitude(complex); //幅值
            h[i-1] = 20*Math.log10(abs(getAmplitude(complex))); //分贝（dB）
        }
        return h;
    }

    /**
     *
     * @param n 个点
     * @return n-1个频率点的值
     */
    public static double[] getW(int n){
        double[] z =  new double[n-1];
        for(int i=1;i<n;i++){
            z[i-1] = i*(Math.PI/n);
        }
        return z;
    }

/*
    public static double[] getFreqzn(Coeff coeff,double[] z){
        int n = z.length;
        double[] h =  new double[n];
        for(int i=0;i<n;i++){
            h[i]= getFreqz(coeff,z[i]);
        }
        return h;
    }



    public static double getFreqz(List<Coeff> coeffList,double z){
        double h = getFreqz(coeffList.get(0),z);
        for(int i=1;i<coeffList.size();i++){
            h= h*getFreqz(coeffList.get(i),z);
        }
        return h;
    }*/

    /**
     * 计算多组滤波器作用下的频响
     * @param coeffList 系数组
     * @param w 频率
     * @return 频响
     */
    public static Complex getFreqw(List<Coeff> coeffList,double w){
        Complex h = getFreqw(coeffList.get(0),w);
        for(int i=1;i<coeffList.size();i++){
            h= h.mul(getFreqw(coeffList.get(i),w));
        }
        return h;
    }

    /**
     * 计算多个滤波器作用下n个点的频率响应
     * @param coeffList 滤波器系数组
     * @param n n个点
     * @return n-1个点的频率响应 （dB）
     */
    public static double[] getFreqzn(List<Coeff> coeffList,int n){
        double[] h =  new double[n-1];
        for(int i=1;i<n;i++){
            double w = i*(Math.PI/n);
            //  h[i-1]= getFreqz(coeff,z);
            Complex complex = getFreqw(coeffList,w);
            // h[i-1] = getAmplitude(complex); //幅值
            h[i-1] = 20*Math.log10(abs(getAmplitude(complex))); //分贝（dB）
        }
        return h;
    }


    /**
     * 计算n 个点的频率响应
     * @param coeff 系数
     * @param fs 采样频率
     * @param f  频率数组
     * @return n-1个点的频率响应 （dB）
     */
    public static double[] getFreqzn(Coeff coeff,int fs,double[] f){
        int n = f.length;
        double[] h =  new double[n];
        for(int i=0;i<n;i++){
            double w = 2*Math.PI*(f[i])/fs;
            //  h[i-1]= getFreqz(coeff,z);
            Complex complex = getFreqw(coeff,w);
            // h[i-1] = getAmplitude(complex); //幅值
            h[i] = 20*Math.log10(abs(getAmplitude(complex))); //分贝（dB）
        }
        return h;
    }

    /**
     * 计算多个滤波器作用下n个点的频率响应
     * @param coeffList 滤波器系数组
     * @param fs 采样频率
     * @param f 频率数组
     * @return n-1个点的频率响应 （dB）
     */
    public static double[] getFreqzn(List<Coeff> coeffList,int fs,double[] f){
        int n = f.length;
        double[] h =  new double[n];
        for(int i=0;i<n;i++){
            double w = 2*Math.PI*(f[i])/fs;;
            //  h[i-1]= getFreqz(coeff,z);
            Complex complex = getFreqw(coeffList,w);
            // h[i-1] = getAmplitude(complex); //幅值
            h[i] = 20*Math.log10(abs(getAmplitude(complex))); //分贝（dB）
        }
        return h;
    }





}
