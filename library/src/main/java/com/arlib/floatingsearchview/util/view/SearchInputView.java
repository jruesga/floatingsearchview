package com.arlib.floatingsearchview.util.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class SearchInputView extends EditText {

    private boolean mCanHandleLongPress;
    private OnLongClickListener mLongClickListener;

    private OnKeyboardSearchKeyClickListener mSearchKeyListener;

    private OnKeyboardDismissedListener mOnKeyboardDismissedListener;

    private OnKeyListener mOnKeyListener = new OnKeyListener() {
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

            if (keyCode == KeyEvent.KEYCODE_ENTER && mSearchKeyListener != null) {
                mSearchKeyListener.onSearchKeyClicked();
                return true;
            }
            return false;
        }
    };

    public SearchInputView(Context context) {
        super(context);
        init();
    }

    public SearchInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchInputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOnKeyListener(mOnKeyListener);
        super.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final boolean handled = !mCanHandleLongPress;
                return !handled && mLongClickListener != null ?
                        mLongClickListener.onLongClick(v) : handled;
            }
        });
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent ev) {
        if (ev.getKeyCode() == KeyEvent.KEYCODE_BACK && mOnKeyboardDismissedListener != null) {
            mOnKeyboardDismissedListener.onKeyboardDismissed();
        }
        return super.onKeyPreIme(keyCode, ev);
    }

    public void setOnKeyboardDismissedListener(OnKeyboardDismissedListener onKeyboardDismissedListener) {
        mOnKeyboardDismissedListener = onKeyboardDismissedListener;
    }

    public void setOnSearchKeyListener(OnKeyboardSearchKeyClickListener searchKeyListener) {
        mSearchKeyListener = searchKeyListener;
    }

    public interface OnKeyboardDismissedListener {
        void onKeyboardDismissed();
    }

    public interface OnKeyboardSearchKeyClickListener {
        void onSearchKeyClicked();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        mCanHandleLongPress = focused;
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener cb) {
        mLongClickListener = cb;
    }
}
