package com.morfix.dutiesmanager.views.dutyList;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.morfix.dutiesmanager.ListAdapter;
import com.morfix.dutiesmanager.R;
import com.morfix.dutiesmanager.models.Duty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DutyListActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private SwipeRefreshLayout swipeLayout;
    private RecyclerView dutiesRecycler;
    private LifecycleRegistry lifecycleRegistry;
    private DrawerLayout mDrawerLayout;

    private DutyListViewModel dutiesViewModel;

    public DutyListActivity() {
        lifecycleRegistry = new LifecycleRegistry(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_duties);
        initComponents();

        dutiesViewModel = ViewModelProviders.of(this).get(DutyListViewModel.class);
        dutiesViewModel.getDutyList()
                       .observe(this, duties -> ((ListAdapter<Duty>) dutiesRecycler.getAdapter()).setItems(duties));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_all_duties, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initComponents() {
        initSwipeLayout();
        initToolbar();
        initRecycler();
        initFab();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Integer> a() {
        return Arrays.stream(R.id.class.getDeclaredFields()).map(x -> {
            try {
                return x.getInt(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return null;
        }).collect(Collectors.toList());
    }

    private void initSwipeLayout() {
        swipeLayout = findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> swipeLayout.setRefreshing(false), 500));
    }

    private void initToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            mDrawerLayout.closeDrawers();

            return true;
        });
    }

    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(DutyListActivity.this);
            builder.setMessage(R.string.create_duty_confirm)
                    .setPositiveButton(R.string.yes, (dialog, which) -> dutiesViewModel.insert(new Duty(generateString(new Random().nextInt(20)))))
                    .setNegativeButton(R.string.no, null);

            builder.create().show();
        });
    }

    private void initRecycler() {
        dutiesRecycler = findViewById(R.id.duties_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dutiesRecycler.getContext(), layoutManager.getOrientation());

        dutiesRecycler.setLayoutManager(layoutManager);
        dutiesRecycler.setItemAnimator(new DefaultItemAnimator());
        dutiesRecycler.addItemDecoration(dividerItemDecoration);
        bindRecyclerListeners();
    }

    private void bindRecyclerListeners() {
        ListAdapter<Duty> listAdapter = new ListAdapter<>(new ArrayList<>(), R.layout.single_duty);
        listAdapter.setItemBindListener((holder, position) -> {
            ((TextView) ((ListAdapter.ViewHolder)holder).getView().findViewById(R.id.single_duty))
                    .setText(listAdapter.getData().get(position).name);

            ((ListAdapter.ViewHolder)holder).getView().getRootView().setOnLongClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(DutyListActivity.this);
                builder.setMessage(R.string.delete_duty_confirm)
                        .setPositiveButton(R.string.yes, (dialog, which) -> dutiesViewModel.delete(listAdapter.getData().get(position)))
                        .setNegativeButton(R.string.no, null);
                builder.create().show();

                return true;
            });
        });

        dutiesRecycler.setAdapter(listAdapter);
    }

    private String generateString(int length) {
        String str = "";

        for (int i = 0; i < length; i++) {
            str += (char) (new Random().nextInt(26) + 'a');
        }

        return str;
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
