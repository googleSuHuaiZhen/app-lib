package com.zhilink.poplibrary;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * dialog
 *
 * @author xiemeng
 * @date 2018-8-20
 */

public class CustomDialog extends Dialog {
    private static final String TAG = "CustomDialog";
    private int height, width;
    private boolean cancelTouchOut;
    /**
     * 按返回键时候消失
     */
    private boolean backCancel;
    private View view;

    public CustomDialog(Builder builder) {
        super(builder.context);
        setWidthAndHeight(builder);
    }

    public CustomDialog(Builder builder, int themeResId) {
        super(builder.context, themeResId);
        setWidthAndHeight(builder);
    }

    private void setWidthAndHeight(Builder builder) {
        Context context = builder.context;
        backCancel = builder.backCancel;
        cancelTouchOut = builder.cancelTouchOut;
        view = builder.view;
        if (builder.height == 0 && builder.width == 0) {
            height = ViewGroup.LayoutParams.WRAP_CONTENT;
            WindowManager manager = ((Activity) context).getWindowManager();
            DisplayMetrics outMetrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(outMetrics);
            width = (int) (outMetrics.widthPixels * 0.8);
        } else {
            height = builder.height;
            width = builder.width;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        setCanceledOnTouchOutside(cancelTouchOut);
        setCancelable(backCancel);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = width;
        lp.height = height;
        window.setAttributes(lp);
    }


    public static final class Builder {
        private Context context;
        private int height, width;
        private boolean cancelTouchOut;
        private boolean backCancel = true;
        private View view;
        private int resStyle = R.style.DialogAnimation;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder view(int resView) {
            try {
                view = LayoutInflater.from(context).inflate(resView, null);
            } catch (Exception e) {
                Log.e(TAG, "Builder view ---> error");
            }
            return this;
        }

        public Builder heightPx(int val) {
            height = val;
            return this;
        }

        public Builder widthPx(int val) {
            width = val;
            return this;
        }


        public Builder heightDimenRes(int dimenRes) {
            height = context.getResources().getDimensionPixelOffset(dimenRes);
            return this;
        }

        public Builder widthDimenRes(int dimenRes) {
            width = context.getResources().getDimensionPixelOffset(dimenRes);
            return this;
        }

        public Builder style(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }

        public Builder cancelTouchOut(boolean val) {
            cancelTouchOut = val;
            return this;
        }

        /**
         * back dialog消失
         *
         * @param val
         * @return
         */
        public Builder backCancelTouchOut(boolean val) {
            backCancel = val;
            return this;
        }

        public Builder addViewOnclick(int viewRes, View.OnClickListener listener) {
            view.findViewById(viewRes).setOnClickListener(listener);
            return this;
        }

        /**
         * 获取editText上文字
         */
        public String getViewText(int viewRes) {
            if (viewRes != 0) {
                TextView editText = (TextView) view.findViewById(viewRes);
                String text = editText.getText().toString().trim();
                return text;
            }
            return "";
        }

        /**
         * 获取dialog中view
         */
        public View getView(int viewRes) {
            if (viewRes != 0) {
                return view.findViewById(viewRes);
            }
            return null;
        }

        /**
         * 背景旋转动画
         */
        public Builder startViewAnim(int viewRes) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(view.findViewById(viewRes), "rotation", 0F, 360F).setDuration(800);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.start();
            return this;
        }

        /**
         * view必须是imageView
         */
        public Builder startViewAnimDrawable(int viewRes) {
            ImageView imageView = (ImageView) view.findViewById(viewRes);
            AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
            drawable.start();
            return this;
        }

        public Builder setViewText(int viewRes, String content) {
            ((TextView) view.findViewById(viewRes)).setText(content);
            return this;
        }

        public Builder setViewTextColor(int viewRes, String content, int resId) {
            TextView textView = (TextView) view.findViewById(viewRes);
            textView.setText(content);
            textView.setTextColor(resId);
            return this;
        }

        public Builder setViewText(int viewRes, int contentId) {
            ((TextView) view.findViewById(viewRes)).setText(contentId);
            return this;
        }

        public CustomDialog build() {
            if (resStyle != -1) {
                return new CustomDialog(this, resStyle);
            } else {
                return new CustomDialog(this);
            }
        }

    }
}
