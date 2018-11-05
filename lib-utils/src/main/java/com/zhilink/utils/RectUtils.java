package com.zhilink.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * recycleView输入框被遮挡问题处理
 *
 * @author xiemeng
 * @date 2018-11-2 14:40
 */
public class RectUtils {

    private RectUtils() {
    }

    public static RectUtils getInstance() {
        return RectUtils.MainHolder.ZHI_LINK_DIALOG;
    }


    private static class MainHolder {
        private static final RectUtils ZHI_LINK_DIALOG = new RectUtils();
    }

    private int oldLocation;

    public void controlKeyboardLayout(final View root, final Activity context) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                root.getWindowVisibleDisplayFrame(rect);
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                final int[] location = new int[2];
                final View focus = context.getCurrentFocus();
                int   height = 0;
                int scrollHeight;
                if (focus != null) {
                    focus.getLocationInWindow(location);
                    height = location[1] + focus.getHeight();
                }
                //若不可视区域高度大于100，则键盘显示
                if (rootInvisibleHeight > 100) {
                    scrollHeight = height - rect.bottom;
                    oldLocation = scrollHeight;
                    if (rect.bottom < location[1] + focus.getHeight()) {
                        scrollToPosition(root,0,scrollHeight);
                        root.scrollTo(0, scrollHeight);
                    }
                } else {
                    //键盘隐藏
                    root.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollToPosition(root,0,oldLocation);
                            root.scrollTo(0, oldLocation);
                        }
                    });
                }
            }
        });
    }


    public void scrollToPosition(View view, int x, int y) {
        ObjectAnimator xTranslate = ObjectAnimator.ofInt(view, "scrollX", x);
        ObjectAnimator yTranslate = ObjectAnimator.ofInt(view, "scrollY", y);
        AnimatorSet animators = new AnimatorSet();
        animators.setDuration(300L);
        animators.playTogether(xTranslate, yTranslate);
        animators.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator arg0) {
            }
            @Override
            public void onAnimationRepeat(Animator arg0) {
            }
            @Override
            public void onAnimationEnd(Animator arg0) {
            }
            @Override
            public void onAnimationCancel(Animator arg0) {
            }
        });
        animators.start();
    }
}
