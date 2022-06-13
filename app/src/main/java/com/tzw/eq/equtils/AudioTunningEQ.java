package com.tzw.eq.equtils;

public class AudioTunningEQ {
    private int gain; // Gain（dB）：范围：-15~15，精度：1
    private int fc; //Fc（Hz）：范围：20~20000，精度：1
    private int bw; // BW（Hz）：范围：10~20000，精度：1
    private int type;// Type：peak、lowshelf、highshelf、lowpass、highpass、bandpass、notch、allpass（0-7）
    private boolean bypass; //bypass :选中1，未选中0；

    public int getGain() {
        return gain;
    }

    public void setGain(int gain) {
        this.gain = gain;
    }

    public int getFc() {
        return fc;
    }

    public void setFc(int fc) {
        this.fc = fc;
    }

    public int getBw() {
        return bw;
    }

    public void setBw(int bw) {
        this.bw = bw;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isBypass() {
        return bypass;
    }

    public void setBypass(boolean bypass) {
        this.bypass = bypass;
    }
}
