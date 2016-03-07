package com.datatech.halalhubs.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.datatech.halalhubs.R;
import com.datatech.halalhubs.model.MenuItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

public class MenuListAdapter extends BaseAdapter {

    private final Context mContext;
    ArrayList<MenuItem> menuItems=new ArrayList<MenuItem>();
    DisplayImageOptions options;

    // we use universal image loader library
/*
    private int iamge_Id[] = {R.mipmap.ic_launcher_h_h, R.mipmap.ic_launcher_h_h,
            R.mipmap.ic_launcher_h_h, R.mipmap.ic_launcher_h_h, R.mipmap.ic_launcher_h_h, R.mipmap.ic_launcher_h_h};

    private String menu_Text[] = {"Memu", "Memu", "Memu", "Memu", "Memu", "Memu"};*/

    public MenuListAdapter(Context context, ArrayList<MenuItem> list) {

        mContext = context;
        this.menuItems = list;

    }

    @Override
    public int getCount() {
        // return 100;
        return menuItems.size();

    }

    @Override
    public Object getItem(int position) {

        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.listview_menu_item, parent, false);

            holder = new ViewHolder();

            holder.menu_name = (TextView) convertView
                    .findViewById(R.id.menu_name);

            holder.menu_image = (ImageView) convertView
                    .findViewById(R.id.imageView_menu);

            convertView.setTag(holder);


        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        MenuItem aMenuItem = menuItems
                .get(position);

        holder.menu_name.setText(aMenuItem.getName());
        holder.menu_image.setImageResource(aMenuItem.getPicture());

        return convertView;
    }

    class ViewHolder {

        private TextView menu_name;
        private ImageView menu_image;

    }

}
