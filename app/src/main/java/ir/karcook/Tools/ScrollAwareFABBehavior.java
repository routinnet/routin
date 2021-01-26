package ir.karcook.Tools;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Created by John Cale on 2/22/2018.
 */

public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {
    private static final String TAG = "ScrollAwareFABBehavior";
    private boolean hide = false;

    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super();
    }

    public boolean onStartNestedScroll(CoordinatorLayout parent, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, final FloatingActionButton child, View dependency) {
        if (dependency instanceof RecyclerView) {
            ((RecyclerView) dependency).addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy >0)
                        hideFloating(child);
                    else if (dy <0)
                        showFloating(child);
                }
            });
            return true;
        }

        return false;
    }

    private void hideFloating(FloatingActionButton child){
//        YoYo.with(Techniques.Landing)
        if (hide == false) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(child, "translationY", 0, 600);
            animator.setDuration(200);
            animator.start();
            hide = true;
        }
    }

    private void showFloating(FloatingActionButton child){
//        YoYo.with(Techniques.Landing)
        if (hide) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
            animator.setDuration(200);
            animator.start();
            hide = false;
        }
    }

/*    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout,
                               FloatingActionButton child, View target, int dxConsumed,
                               int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        //
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed);


        Log.e("ddddddd","dyC : "+dyConsumed+"      dyUn"+dyUnconsumed);
        Log.e("ddddddd","dxC : "+dxConsumed+"      dxUn"+dxUnconsumed);

//        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
//            child.hide();
//        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
//            child.show();
//        }
    }*/

}

