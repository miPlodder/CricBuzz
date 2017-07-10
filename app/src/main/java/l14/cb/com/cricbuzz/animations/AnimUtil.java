package l14.cb.com.cricbuzz.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by ip510 feih on 10-07-2017.
 */

public class AnimUtil {

    public static void animate(RecyclerView.ViewHolder holder, boolean goesDown) {

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator translateY = ObjectAnimator.ofFloat(holder.itemView, "translationY",  (goesDown) ? 50 : -50 , 0);
        ObjectAnimator translateX = ObjectAnimator.ofFloat(holder.itemView, "translationX", -50, 50, -40, 40, -30, 30, -20, 20, -10, 10, 0);

        translateX.setDuration(1000);
        translateY.setDuration(1000);

        animatorSet.playTogether(translateX, translateY);
        animatorSet.start();

    }


}
