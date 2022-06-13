package com.tzw.eq.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.tzw.eq.R;
import com.tzw.eq.adapter.AudioTunningEQAdapter;
import com.tzw.eq.databinding.ActivityEQBinding;
import com.tzw.eq.equtils.AudioTunningEQ;
import com.tzw.eq.equtils.BiquadDesign;
import com.tzw.eq.equtils.Coeff;
import com.tzw.eq.equtils.FrequencyResponse;
import com.tzw.eq.view.LineView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EQActivity extends AppCompatActivity {
    private static final String TAG = "EQActivity";
    private List<AudioTunningEQ> mEqList = new ArrayList<>();
    private AudioTunningEQAdapter mEqAdapter;
    private ActivityEQBinding binding;
    private List<Coeff> mCoeffList = new ArrayList<>();
    private int fs = 48000;
    private LineView mLineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_e_q);
        initData();
        mEqAdapter = new AudioTunningEQAdapter(mEqList,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.rvEq.setLayoutManager(linearLayoutManager);
        binding.rvEq.setAdapter(mEqAdapter);
        binding.etSampleRate.setText(String.valueOf(fs));
        mLineView = new LineView(EQActivity.this);
        binding.llChartView.addView(mLineView.execute2("Frequency(Hz)","Magnitude(dB)"),new FrameLayout.LayoutParams
                (FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));


        binding.btnVisualizeResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                computeCoeffs();
                visualizeResponse(mCoeffList,fs);
            }
        });



    }

    private void initData(){
        mEqList.clear();
        for(int i=0;i<5;i++){
            AudioTunningEQ eq = new AudioTunningEQ();
            eq.setGain(0);
            eq.setFc(0);
            eq.setBw(0);
            eq.setType(0);
            eq.setBypass(false);
            mEqList.add(eq);
        }
    }

    private void computeCoeffs(){
        mCoeffList.clear();
        fs =  Integer.parseInt(binding.etSampleRate.getText().toString());
        for(AudioTunningEQ eq:mEqList){
            Log.d(TAG, "gain:"+eq.getGain()+" fc:"+eq.getFc()+" bw:"+eq.getBw()+" type:"+eq.getType()+" bypass:"+ eq.isBypass());
            Coeff coeff = BiquadDesign.getSectionsMatrix(eq.getGain(),eq.getFc(),eq.getBw(),eq.getType(),eq.isBypass(),fs);
            Log.d(TAG, "coeff:"+coeff.toStr());
            mCoeffList.add(coeff);
        }

    }

    private void visualizeResponse(List<Coeff> coeffList,int fs){
        List<Coeff> validCoeffList = new ArrayList<>();
        int n =200;
        double startF = 20;
        double endF = 20000;
        double logStep = (Math.log10(20000)-Math.log10(20))/n;
        double[] f = new double[n];
        double step = Math.pow(10,logStep);
        for(int i=0;i<200;i++){
            f[i]= startF*Math.pow(step,i); //按对数划分为200个点
        }
        double[] semilogf = new double[n];
        for(int i=0;i<200;i++){
            semilogf[i]= Math.log10(f[i]);  //半对数绘制时，对f取以10为底的对数
        }

        Log.d(TAG, "f: "+ Arrays.toString(f));
        List<double[]> xValues = new ArrayList<>();
        List<double[]> yValues = new ArrayList<>();
       // int size = coeffList.size();
        List<String> titleList = new ArrayList<>();
        for(int i=0;i<coeffList.size();i++){
            Coeff coeff = coeffList.get(i);
            if(!Double.isNaN(coeff.getB0()) &&!Double.isNaN(coeff.getB1()) && !Double.isNaN(coeff.getB2()) && !Double.isNaN(coeff.getA0()) && !Double.isNaN(coeff.getA1()) && !Double.isNaN(coeff.getA2())){
                validCoeffList.add(coeff);
                double[] h = FrequencyResponse.getFreqzn(coeff,fs,f);
                Log.d(TAG, "h: "+Arrays.toString(h));
                xValues.add(semilogf);
                yValues.add(h);
                titleList.add("Band"+i);
            }
        }
        if(yValues.size() > 1 ){
            double[] overall = FrequencyResponse.getFreqzn(validCoeffList,fs,f);
            Log.d(TAG, "overall: "+Arrays.toString(overall));
            xValues.add(semilogf);
            yValues.add(overall);
            titleList.add("Overall");
        }

        if(titleList.size()!=0 && xValues.size()!=0 && yValues.size()!=0){
            mLineView.updateLines(titleList.toArray(new String[1]),xValues,yValues,true); //半对数绘制
        }

    }

}