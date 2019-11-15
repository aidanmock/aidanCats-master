package com.example.aidanCatssss;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CatViewHolder extends RecyclerView.ViewHolder {
    LinearLayout linearCat;
    TextView catName;
    public CatViewHolder(View view){
        super(view);
        catName =view.findViewById(R.id.catName);
        linearCat = view.findViewById(R.id.catLinear);
    }
    @Override
    public String toString() {
        return super.toString() + " '" + catName.getText() + "'";
    }

}
