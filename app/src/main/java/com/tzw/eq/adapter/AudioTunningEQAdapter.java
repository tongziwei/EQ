package com.tzw.eq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;


import com.tzw.eq.R;
import com.tzw.eq.databinding.EqListItemBinding;
import com.tzw.eq.equtils.AudioTunningEQ;

import java.util.List;

import androidx.annotation.NonNull;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class AudioTunningEQAdapter extends RecyclerView.Adapter<AudioTunningEQAdapter.VH> {
    private List<AudioTunningEQ> mEqList;
    private Context mContext;

    public static class VH extends RecyclerView.ViewHolder {
        private EqListItemBinding binding;


        public VH(ViewDataBinding binding) {
            super(binding.getRoot());

            this.binding = (EqListItemBinding) binding;
        }
    }

    public AudioTunningEQAdapter(List<AudioTunningEQ> mEqList,Context context) {
        this.mEqList = mEqList;
        this.mContext = context;
    }

    public void setEqList(List<AudioTunningEQ> mEqList) {
        this.mEqList = mEqList;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EqListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.eq_list_item, parent, false);
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        AudioTunningEQ eq = mEqList.get(position);
        holder.binding.setEq(eq);
        String[] mEQTypeShow = mContext.getResources().getStringArray(R.array.eq_types);
       // String[] mEQTypeShow = {"peak","lowshelf","highshelf","lowpass","highpass","bandpass","notch","allpass"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mEQTypeShow);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        holder.binding.spinnerType.setAdapter(adapter);
        holder.binding.spinnerType.setSelection(eq.getType());
        //添加事件Spinner事件监听
        holder.binding.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eq.setType(position);
                holder.binding.setEq(eq);
              //  notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //设置默认值
        holder.binding.spinnerType.setVisibility(View.VISIBLE);
        holder.binding.cbBypass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                eq.setBypass(isChecked);
                holder.binding.setEq(eq);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mEqList.size();
    }
}
