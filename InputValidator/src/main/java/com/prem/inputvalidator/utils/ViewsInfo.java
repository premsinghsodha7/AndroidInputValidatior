package com.prem.inputvalidator.utils;

import android.content.res.ColorStateList;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;

public class ViewsInfo {

    private final int mIndex;
    private final ViewGroup mParent;
    private final LinearLayout mNewContainer;
    private final EditText mEditText;
    private final ColorStateList mColorStateList;
    private boolean mIsOriginal = false;

    public ViewsInfo(int index, ViewGroup parent, LinearLayout newContainer, EditText editText) {
        mIndex = index;
        mParent = parent;
        mNewContainer = newContainer;
        mEditText = editText;
        mColorStateList = ViewCompat.getBackgroundTintList(editText);
    }

    public void restoreViews() {
        if (!mIsOriginal) {
            ViewCompat.setBackgroundTintList(mEditText, mColorStateList);
            mEditText.requestFocus();
            mNewContainer.removeView(mEditText);
            mParent.removeView(mNewContainer);
            mParent.addView(mEditText, mIndex);
            mIsOriginal = true;
        }
    }

    public int getIndex() {
        return mIndex;
    }

    public ViewGroup getParent() {
        return mParent;
    }

    public LinearLayout getNewContainer() {
        return mNewContainer;
    }

    public EditText getEditText() {
        return mEditText;
    }

}