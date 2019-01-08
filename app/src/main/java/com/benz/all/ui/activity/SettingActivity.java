package com.benz.all.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v7.widget.Toolbar;

import com.benz.all.R;
import com.benz.all.mvp.BaseActivity;
import com.benz.all.utils.ToastUtils;

import butterknife.BindView;

/**
 * 设置
 * Created by xubenliang on 2017/6/10.
 */
public class SettingActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_view;
    }

    @Override
    protected void initViewsAndEvents() {
        initToolBar(toolbar, true, "设置");
    }

    public static class SettingFragement extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

        SwitchPreference auto_refresh;
        SwitchPreference auto_loadmore;
        Preference haircut;
        Preference about;
        Preference clear_cache;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting);

            auto_refresh = (SwitchPreference) findPreference("auto_refresh");
            auto_refresh.setOnPreferenceChangeListener(this);
            auto_loadmore = (SwitchPreference) findPreference("auto_loadmore");
            auto_loadmore.setOnPreferenceChangeListener(this);

            haircut = findPreference("haircut");
            haircut.setOnPreferenceClickListener(this);

            clear_cache = findPreference("clear_cache");
            clear_cache.setOnPreferenceClickListener(this);
            about = findPreference("about");
            about.setOnPreferenceClickListener(this);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (preference == auto_refresh) {

            } else if (preference == auto_loadmore) {

            }
            return true;
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            if(preference == haircut){
                startActivity(new Intent(getActivity(), HaircutActivity.class));
            }else if (preference == clear_cache) {
                ToastUtils.show("已清理");
            } else if (preference == about) {
                ToastUtils.show("关于我");
            }
            return false;
        }
    }

}
