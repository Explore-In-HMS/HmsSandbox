/*
 *
 *   Copyright 2020. Explore in HMS. All rights reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   You may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.genar.hmssandbox.huawei.baseapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewpager2.widget.ViewPager2;

import com.genar.hmssandbox.huawei.R;
import com.genar.hmssandbox.huawei.Util;
import com.genar.hmssandbox.huawei.baseapp.adapter.BaseViewPagerAdapter;
import com.genar.hmssandbox.huawei.baseapp.fragment.HmsKitSearchFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.huawei.hms.mlsdk.text.MLText;

public class MainActivityTab extends BaseActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    FragmentContainerView searchFragmentContainer;
    HmsKitSearchFragment searchFragment;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_tab);
       // setupToolbar();
        toolbar=getToolbar();
        initUI();
        initAdapter();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main_tab;
    }

    @Override
    protected String getToolbarTitle() {
        return "Hms Sandbox";
    }

    @Override
    protected String getDocumentationLink() {
        return "https://developer.huawei.com/en/";
    }


/*    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Util.setToolbar(this, toolbar, "https://developer.huawei.com/en/");

    }*/

    private void initUI(){
        tabLayout = findViewById(R.id.tl_main);
        viewPager = findViewById(R.id.vp_main);
        searchFragmentContainer = findViewById(R.id.search_container);

        searchFragment = new HmsKitSearchFragment();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.search_container, searchFragment, null)
                .commit();
    }

    private void initAdapter(){
        viewPager.setAdapter(new BaseViewPagerAdapter(MainActivityTab.this.getSupportFragmentManager(), getLifecycle()));

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout,viewPager,true, (tab, position) -> {
            if (position == 0){
                tab.setText("Hms Core");
            }

            else if (position == 1){
                tab.setText("AGC");
            }

        });
        tabLayoutMediator.attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem btnSearch = menu.findItem(R.id.btn_searchKits);

        SearchView sv = (SearchView) btnSearch.getActionView();
        int id = sv.getContext()
                .getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) sv.findViewById(id);
        textView.setTextColor(Color.WHITE);
        sv.setMaxWidth(Integer.MAX_VALUE);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String searchStr = newText.toLowerCase();

                searchFragment.FilterKitList(searchStr);

                if(newText.length()>0){
                    searchFragmentContainer.setVisibility(View.VISIBLE);
                }else{
                    searchFragmentContainer.setVisibility(View.GONE);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btn_searchKits) {
            return true;
        }

        if(id == R.id.ivInfo){
            Toast.makeText(this,"info tiklandi",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
