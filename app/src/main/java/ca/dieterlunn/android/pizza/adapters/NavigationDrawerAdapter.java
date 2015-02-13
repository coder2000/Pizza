package ca.dieterlunn.android.pizza.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ca.dieterlunn.android.pizza.NavigationItem;
import ca.dieterlunn.android.pizza.R;
import ca.dieterlunn.android.pizza.callbacks.NavigationDrawerCallbacks;

/**
 * Created by Dieter on 12/22/2014.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {

    private List<NavigationItem> mMenuItems;
    private NavigationDrawerCallbacks mNavigationDrawerCallbacks;
    private int mSelectedPosition;
    private int mTouchedPosition = -1;

    public NavigationDrawerAdapter(List<NavigationItem> menuItems) {
        mMenuItems = menuItems;
    }

    public NavigationDrawerCallbacks getNavigationDrawerCallbacks() {
        return mNavigationDrawerCallbacks;
    }

    public void setNavigationDrawerCallbacks(NavigationDrawerCallbacks callbacks) {
        mNavigationDrawerCallbacks = callbacks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int row) {
        viewHolder.textView.setText(mMenuItems.get(row).getText());
        viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(mMenuItems.get(row).getIcon(), null, null, null);

        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchPosition(row);
                        return false;
                    case MotionEvent.ACTION_CANCEL:
                        touchPosition(-1);
                        return false;
                    case MotionEvent.ACTION_MOVE:
                        return false;
                    case MotionEvent.ACTION_UP:
                        touchPosition(-1);
                        return false;
                }
                return true;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mNavigationDrawerCallbacks != null) {
                    mNavigationDrawerCallbacks.onNavigationDrawerItemSelected(row);
                }
            }
        });
    }

    private void touchPosition(int position) {
        int lastPosition = mTouchedPosition;
        mTouchedPosition = position;

        if (lastPosition >= 0) {
            notifyItemChanged(lastPosition);
        }

        if (position >= 0) {
            notifyItemChanged(position);
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
        return mMenuItems != null ? mMenuItems.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View viewItem) {
            super(viewItem);

            textView = (TextView) viewItem.findViewById(R.id.list_item);
        }
    }
}
