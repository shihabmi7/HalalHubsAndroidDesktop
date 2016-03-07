package com.datatech.halalhubs.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.datatech.halalhubs.R;
import com.datatech.halalhubs.view.SearchFoodFragment;
import com.datatech.halalhubs.view.TrackFoodFragment;

import java.util.HashMap;

/**
 * Created by shihab uddin on 14-Aug-15.
 */
public class HomeActivity extends CustomWindow {


    TabHost mTabHost;
    TabManager mTabManager;
    TabWidget tabWidget;
    String TAG = "HomeActivity";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeSlidingMenu();
        try {

            mTabHost = (TabHost) findViewById(android.R.id.tabhost);
            tabWidget = (TabWidget) findViewById(android.R.id.tabs);

            mTabHost.setup();

            mTabManager = new TabManager(this, mTabHost, android.R.id.tabcontent);

            mTabManager.addTab(mTabHost.newTabSpec("Search Food").
                            setIndicator(getTabIndicator("Search Food", R.mipmap.ic_search_food)),
                    SearchFoodFragment.class, null);
            mTabManager.addTab(mTabHost.newTabSpec("Track Food").
                            setIndicator(getTabIndicator("Track Food", R.mipmap.ic_track_food)),
                    TrackFoodFragment.class, null);

            mTabManager.addTab(mTabHost.newTabSpec("Pre Order").
                            setIndicator(getTabIndicator("Pre-Order", R.mipmap.ic_preorder)),
                    TrackFoodFragment.class, null);

            if (savedInstanceState != null) {
                mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
            }

        } catch (NullPointerException e) {


            e.printStackTrace();


        } catch (RuntimeException e) {

            e.printStackTrace();

        }



    }


    public View getTabIndicator(String labelId, int drawableId) {

        View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, tabWidget, false);
        TextView title = (TextView) tabIndicator.findViewById(R.id.title);
        title.setText(labelId);
        ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
        icon.setImageResource(drawableId);

        return tabIndicator;

    }

    @Override
    protected void onResume() {

        super.onResume();
        doIncrease();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(getLocalClassName(), "onConfigurationChanged");

    }

    public static class TabManager implements TabHost.OnTabChangeListener {
        private final FragmentActivity mActivity;
        private final TabHost mTabHost;
        private final int mContainerId;
        private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
        TabInfo mLastTab;

        public TabManager(FragmentActivity activity, TabHost tabHost, int containerId) {
            mActivity = activity;
            mTabHost = tabHost;
            mContainerId = containerId;
            mTabHost.setOnTabChangedListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mActivity));
            String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);

            // Check to see if we already have a fragment for this tab, probably
            // from a previously saved state.  If so, deactivate it, because our
            // initial state is that a tab isn't shown.
            info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
            if (info.fragment != null && !info.fragment.isDetached()) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                ft.detach(info.fragment);
                ft.commit();
            }

            mTabs.put(tag, info);
            mTabHost.addTab(tabSpec);
        }

        @Override
        public void onTabChanged(String tabId) {
            TabInfo newTab = mTabs.get(tabId);
            if (mLastTab != newTab) {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                if (mLastTab != null) {
                    if (mLastTab.fragment != null) {
                        ft.detach(mLastTab.fragment);
                    }
                }
                if (newTab != null) {
                    if (newTab.fragment == null) {
                        newTab.fragment = Fragment.instantiate(mActivity,
                                newTab.clss.getName(), newTab.args);
                        ft.add(mContainerId, newTab.fragment, newTab.tag);
                    } else {
                        ft.attach(newTab.fragment);
                    }
                }

                mLastTab = newTab;
                ft.commit();
                mActivity.getSupportFragmentManager().executePendingTransactions();
            }
        }

        static final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;
            private Fragment fragment;

            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        static class DummyTabFactory implements TabHost.TabContentFactory {
            private final Context mContext;

            public DummyTabFactory(Context context) {
                mContext = context;
            }

            @Override
            public View createTabContent(String tag) {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }
    }
}
