package com.tag.tai.tag.Common;

import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;

/**
 * Created by Jam on 25-03-2018.
 */

public class TAGSpinner extends AppCompatSpinner {

    public TAGSpinner(Context context) {
        super(context);
    }

    public TAGSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TAGSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSelection(int position, boolean animate) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position, animate);

        if(sameSelected){
            getOnItemSelectedListener().onItemSelected(this,getSelectedView(),position,getSelectedItemId());
        }
    }

    @Override
    public void setSelection(int position) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position);

        if(sameSelected){
            getOnItemSelectedListener().onItemSelected(this,getSelectedView(),position,getSelectedItemId());
        }
    }
}
