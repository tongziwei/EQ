package com.tzw.eq.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tzw.eq.equtils.Coeff;
import com.tzw.eq.equtils.Filter;
import com.tzw.eq.equtils.FrequencyResponse;
import com.tzw.eq.R;
import com.tzw.eq.view.LineView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int LowShelf = 0;
    private static final int PeakShelf = 1;
    private static final int HighShelf = 2;
    private Button mBtnComputeCoeff;
    private EditText mEtF;
    private EditText mEtQ;
    private EditText mEtGain;
    private EditText mEtFs;
    private RadioGroup mRgFilter;
    private RadioButton mRbtnLowShelf;
    private RadioButton mRbtnPeakShelf;
    private RadioButton mRbtnHighShelf;
    private TextView mTvCoeffShow;
    private int mFilterType = LowShelf;
  //  private TextView mTvH;
    private LinearLayout mLlChartView;
    private LineView mLineView;

    private Button mBtnComputeCoeffs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnComputeCoeff = (Button)findViewById(R.id.btn_compute_coeff);
        mEtF = (EditText)findViewById(R.id.et_f);
        mEtQ = (EditText)findViewById(R.id.et_Q);
        mEtGain = (EditText)findViewById(R.id.et_gain);
        mEtFs = (EditText)findViewById(R.id.et_fs);
        mRgFilter = (RadioGroup)findViewById(R.id.rg_filter);
        mRbtnLowShelf = (RadioButton)findViewById(R.id.rbtn_lowshelf);
        mRbtnHighShelf = (RadioButton)findViewById(R.id.rbtn_highshelf);
        mRbtnPeakShelf = (RadioButton)findViewById(R.id.rbtn_peakshelf);
        mTvCoeffShow = (TextView)findViewById(R.id.tv_coeff_show);
       // mTvH = (TextView)findViewById(R.id.tv_h);
        mLlChartView = (LinearLayout)findViewById(R.id.ll_chart_view);
        mLineView = new LineView(MainActivity.this);
        mLlChartView.addView(mLineView.execute("W","H"),new FrameLayout.LayoutParams
                (FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));

        mBtnComputeCoeff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEtF.getText().toString().isEmpty() && !mEtQ.getText().toString().isEmpty() && !mEtGain.getText().toString().isEmpty() && !mEtFs.getText().toString().isEmpty() ){
                    Coeff coeff =  computeCoeff(mFilterType);
                    mTvCoeffShow.setText(coeff.toStr());
                    double[] h = FrequencyResponse.getFreqzn(coeff,100);
                    double[] z = FrequencyResponse.getW(100);
                    Log.d("tzw", "h:"+Arrays.toString(h));
                    Log.d("tzw", "z: "+Arrays.toString(z));
                    // mTvH.setText(Arrays.toString(h));
                    mLineView.updateLine("FreqRes",z,h);
                }else{
                    Toast.makeText(MainActivity.this,"请先输入数据",Toast.LENGTH_SHORT).show();
                }

            }
        });

        mRgFilter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbtn_lowshelf){
                    mFilterType = LowShelf;
                }else if(checkedId == R.id.rbtn_peakshelf){
                    mFilterType = PeakShelf;
                }else{
                    mFilterType = HighShelf;
                }
            }
        });

        mBtnComputeCoeffs = (Button)findViewById(R.id.btn_compute_coeffs);
        mBtnComputeCoeffs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] h = FrequencyResponse.getFreqzn(generateCoeffs(),100);
                double[] z = FrequencyResponse.getW(100);
                Log.d("tzw", "h:"+Arrays.toString(h));
                Log.d("tzw", "z: "+Arrays.toString(z));
                // mTvH.setText(Arrays.toString(h));
                mLineView.updateLine("FreqRes",z,h);
            }
        });

    }

    private Coeff computeCoeff(int type){
        double f =Double.parseDouble(mEtF.getText().toString());
        double q =Double.parseDouble(mEtQ.getText().toString());
        double gain =Double.parseDouble(mEtGain.getText().toString());
        int fs =Integer.parseInt(mEtFs.getText().toString());
        Coeff coeff = new Coeff();
        switch (type){
            case LowShelf:
                coeff = Filter.getLowShelfEQ(f,q,gain,fs);
                break;
            case PeakShelf:
                coeff = Filter.getPeakEQ(f,q,gain,fs);
                break;
            case HighShelf:
                coeff = Filter.getHighShelfEQ(f,q,gain,fs);
                break;
        }
        return coeff;
    }

    private List<Coeff> generateCoeffs(){
        List<Coeff> coeffList = new ArrayList<>();
        Coeff coeff1 = new Coeff();
        coeff1.setB0(0.05634);
        coeff1.setB1(0.05634);
        coeff1.setB2(0);
        coeff1.setA0(1);
        coeff1.setA1(-0.683);
        coeff1.setA2(0);
        coeffList.add(coeff1);
        Coeff coeff2 = new Coeff();
        coeff2.setB0(1); //1 -1.0166 1
        coeff2.setB1(-1.0166);
        coeff2.setB2(1);
        coeff2.setA0(1); //1 -1.4461 0.7957
        coeff2.setA1(-1.4461);
        coeff2.setA2(0.7957);
        coeffList.add(coeff2);
        return coeffList;
    }




}