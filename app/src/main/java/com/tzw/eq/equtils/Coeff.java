package com.tzw.eq.equtils;

/**
 * 二阶截面矩阵系数sos
 */
public class Coeff {
    private double b0;
    private double b1;
    private double b2;
    private double a0;
    private double a1;
    private double a2;


    public double getB0() {
        return b0;
    }

    public void setB0(double b0) {
        this.b0 = b0;
    }

    public double getB1() {
        return b1;
    }

    public void setB1(double b1) {
        this.b1 = b1;
    }

    public double getB2() {
        return b2;
    }

    public void setB2(double b2) {
        this.b2 = b2;
    }

    public double getA0() {
        return a0;
    }

    public void setA0(double a0) {
        this.a0 = a0;
    }

    public double getA1() {
        return a1;
    }

    public void setA1(double a1) {
        this.a1 = a1;
    }

    public double getA2() {
        return a2;
    }

    public void setA2(double a2) {
        this.a2 = a2;
    }

    public String toStr(){
        return "b0:"+b0+" b1:"+b1+" b2:"+b2+" a0:"+a0+" a1:"+a1+" a2:"+a2;
    }
}
