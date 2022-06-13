package com.tzw.eq.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tzw.eq.R;
import com.tzw.eq.compressor.CompressorUtil;
import com.tzw.eq.databinding.ActivityCompressorBinding;
import com.tzw.eq.view.StaticCharacteristicView;

import java.util.Arrays;

public class CompressorActivity extends AppCompatActivity {
    private ActivityCompressorBinding binding;
  //  private LineView mLineView;
    private int t = -20;
    private int r = 100;
    private int w = 10;
    private StaticCharacteristicView mStaticCharacteristicView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_compressor);
        binding.btnVisualStaticCurve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visualizeStaticCharacteristicCurve();
            }
        });

        binding.etThreshold.setText(String.valueOf(t));
        binding.etRatio.setText(String.valueOf(r));
        binding.etKneewidth.setText(String.valueOf(w));

      /*  mLineView = new LineView(CompressorActivity.this);
        binding.llChartView.addView(mLineView.execute("Input (dB)", "Output(dB)"), new FrameLayout.LayoutParams
                (FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));*/
        /*mStaticCharacteristicView = new StaticCharacteristicView(CompressorActivity.this);
        binding.llChartView.addView(mStaticCharacteristicView,new FrameLayout.LayoutParams
                (FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
*/        mStaticCharacteristicView = (StaticCharacteristicView) binding.staticCharacteristicView;
    }

    private void visualizeStaticCharacteristicCurve() {
        double[] u = new double[601];
        for (int i = 0; i < 601; i++) {
            u[i] = -60 + 0.1 * i;
        }
        Log.d("tzw", "u: " + Arrays.toString(u));
        t = Integer.parseInt(binding.etThreshold.getText().toString());
        r = Integer.parseInt(binding.etRatio.getText().toString());
        w = Integer.parseInt(binding.etKneewidth.getText().toString());
        double[] g = CompressorUtil.computeGain(u, t, r, w);
        double[] y = new double[u.length];
        for (int i = 0; i < g.length; i++) {
            y[i] = u[i] + g[i];
        }
 /*       double[] copy = new double[100];
        System.arraycopy(g, 500, copy, 0, 100);
        Log.d("tzw", "g: "+Arrays.toString(copy));*/
        // Log.d("tzw", "y: "+ Arrays.toString(y));
        double[] tArray = {t};
        double[] tgArray = CompressorUtil.computeGain(tArray, t, r, w);
        double ty = t + tgArray[0];
        Log.d("tzw", "ty:" + ty);

      //  mLineView.updateLine("Static Characteristic Curve", u, y);
        int[] xStr = {-60,-50,-40,-30,-20,-10,0};
        mStaticCharacteristicView.setxStr(xStr);
        mStaticCharacteristicView.setYScope(0,-60);
        mStaticCharacteristicView.setData(u,y,ty,t);

    }


}