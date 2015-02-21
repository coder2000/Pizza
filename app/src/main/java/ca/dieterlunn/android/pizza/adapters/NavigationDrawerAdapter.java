package ca.dieterlunn.android.pizza.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ca.dieterlunn.android.pizza.NavigationItem;
import ca.dieterlunn.android.pizza.R;
import ca.dieterlunn.android.pizza.callbacks.NavigationDrawerCallbacks;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<NavigationItem> mMenuItems;
    private NavigationDrawerCallbacks mNavigationDrawerCallbacks;
    private int mSelectedPosition;
    private int mTouchedPosition = -1;

    private String _name;
    private String _email;
    private int _avatar; // Resource id for avatar

    public NavigationDrawerAdapter(List<NavigationItem> menuItems, String name, String email, int avatar) {
        mMenuItems = menuItems;
        _name = name;
        _email = email;
        _avatar = avatar;
    }

    public NavigationDrawerCallbacks getNavigationDrawerCallbacks() {
        return mNavigationDrawerCallbacks;
    }

    public void setNavigationDrawerCallbacks(NavigationDrawerCallbacks callbacks) {
        mNavigationDrawerCallbacks = callbacks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_ITEM:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_row, viewGroup, false);
            case TYPE_HEADER:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_header, viewGroup, false);
        }

        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int row) {
        switch (viewHolder.holderId) {
            case TYPE_ITEM:
                viewHolder.textView.setText(mMenuItems.get(row).getText());
                viewHolder.imageView.setImageDrawable(mMenuItems.get(row).getIcon());
            case TYPE_HEADER:
                viewHolder.avatar.setImageResource(_avatar);
                viewHolder.name.setText(_name);
                viewHolder.email.setText(_email);
        }
    }

    public void selectPosition(int position) {
        int lastPosition = mSelectedPosition;
        mSelectedPosition = position;

        notifyItemChanged(lastPosition);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mMenuItems != null ? mMenuItems.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int holderId;

        TextView textView;
        ImageView imageView;

        ImageView avatar;
        TextView name;
        TextView email;

        public ViewHolder(View viewItem, int viewType) {
            super(viewItem);

            holderId = viewType;

            switch (viewType) {
                case TYPE_HEADER:
                    name = (TextView) viewItem.findViewById(R.id.drawer_header_name);
                    email = (TextView) viewItem.findViewById(R.id.drawer_header_email);
                    avatar = (ImageView) viewItem.findViewById(R.id.drawer_header_avatar);
                case TYPE_ITEM:
                    textView = (TextView) viewItem.findViewById(R.id.list_item);
                    imageView = (ImageView) viewItem.findViewById(R.id.list_icon);
            }
        }
    }
}
