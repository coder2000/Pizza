package ca.dieterlunn.android.pizza;

import android.graphics.drawable.Drawable;

public class NavigationItem {
    private String mText;
    private Drawable mIcon;

    public NavigationItem(String text, Drawable icon) {
        mText = text;
        mIcon = icon;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public void setIcon(Drawable icon) {
        mIcon = icon;
    }
}
