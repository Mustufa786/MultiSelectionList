package com.codixlab.multiselectionlist;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.codixlab.multiselectionlist.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding bi;
    List<Inbox> list;
    ListAdapter adapter;
    ActionMode actionMode;
    ActionCallback actionCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(bi.toolbar);
        getSupportActionBar().setTitle("Inbox");

        init();
    }

    private void init() {

        list = Util.getInboxData(this);
        list.addAll(Util.getInboxData(this));
        adapter = new ListAdapter(this, list);
        bi.emailList.setLayoutManager(new LinearLayoutManager(this));
        bi.emailList.setHasFixedSize(true);
        bi.emailList.setAdapter(adapter);
        adapter.setItemClick(new ListAdapter.OnItemClick() {
            @Override
            public void onItemClick(View view, Inbox inbox, int position) {
                if (adapter.selectedItemCount() > 0) {
                    toggleActionBar(position);
                } else {
                    Toast.makeText(MainActivity.this, "clicked " + inbox.from, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onLongPress(View view, Inbox inbox, int position) {

                toggleActionBar(position);

            }
        });

        actionCallback = new ActionCallback();
    }

    private void toggleActionBar(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        adapter.toggleSelection(position);
        int count = adapter.selectedItemCount();
        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    public void deleteItemFromInbox() {
        List<Integer> items = adapter.getSelectedItemPositions();
        for (int i = items.size() - 1; i >= 0; i--) {
            adapter.removeItems(items.get(i));
        }
        adapter.notifyDataSetChanged();
    }


    private class ActionCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            Util.toggleStatusBarColor(MainActivity.this, R.color.blue_grey_700);
            mode.getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delteItem:
                    deleteItemFromInbox();
                    mode.finish();
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelection();
            actionMode = null;
            Util.toggleStatusBarColor(MainActivity.this, R.color.colorPrimary);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
