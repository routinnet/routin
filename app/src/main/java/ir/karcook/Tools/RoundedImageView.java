package ir.karcook.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

/**
 * Created by Adak on 16/05/2018.
 */

    public class RoundedImageView extends androidx.appcompat.widget.AppCompatImageView {
        public RoundedImageView(Context context) {
            super(context);
        }

        public RoundedImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public RoundedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        public void setImageDrawable(Drawable drawable) {
            float radius = 0.5f;
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            RoundedBitmapDrawable rid = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            rid.setCornerRadius(bitmap.getWidth() * radius);
            super.setImageDrawable(rid);
        }
}
