package com.mobdeve.s16.bainto.john.freshnesscheck;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    TabLayout mainMenuTab;
    private ArrayList<String> data = new ArrayList<String>();
    private ArrayList<String> itemExpirations = new ArrayList<String>();
    private ArrayList<String> currentItemExpirations = new ArrayList<String>();
    private ArrayList<String> currentItemData = new ArrayList<String>();
    private ArrayList<String> currentListData = new ArrayList<String>();
    private ArrayList<Item> itemData = new ArrayList<Item>();
    private Calendar myCalendar = new GregorianCalendar();
    
    private RecyclerView recyclerView;
    private ItemAdapter adapter;

    private FloatingActionButton addBtn;
    private MenuItem filterByExpiration;
    private SearchView searchExpiration;

    private int itemNameOrder = 0;
    private int itemExpOrder = 0;
    private int listNameOrder = 0;
    private int tabPosition = 0;

    private DbHelper dbHelper;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private ActivityResultLauncher<Intent> newItemResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d(TAG, "onActivityResult: ");
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        dbHelper = DbHelper.getInstance(MainActivity.this);

                        dbHelper.insertItem(new Item(
                                null,
                                result.getData().getStringExtra(AddItemActivity.NEW_ITEM_NAME_KEY),
                                result.getData().getStringExtra(AddItemActivity.NEW_ITEM_CATEGORY_KEY),
                                result.getData().getStringExtra(AddItemActivity.NEW_ITEM_EXPIRATION_KEY)
                        ));

                        updateItemAdapter();
                        Log.d(TAG, "onActivityResult: recent id = " + dbHelper.getMostRecentId());

                        Alarm alarm = new Alarm(dbHelper.getMostRecentId(),
                                result.getData().getIntExtra(AddItemActivity.NEW_ITEM_YEAR_KEY, -1),
                                result.getData().getIntExtra(AddItemActivity.NEW_ITEM_MONTH_KEY, -1),
                                result.getData().getIntExtra(AddItemActivity.NEW_ITEM_DAY_KEY, -1),
                                result.getData().getStringExtra(AddItemActivity.NEW_ITEM_NAME_KEY));

                        alarm.schedule(MainActivity.this);

                    }
                    else if(result.getResultCode() == Activity.RESULT_CANCELED) {
                        Log.v(TAG, "Result Cancelled.");
                    }
                    else if(result.getResultCode() == AddListActivity.ADD_LIST_OK)
                    {
                        Log.d(TAG, "onActivityResult: " + result.getData().getStringExtra(AddListActivity.NAME_KEY));

                        updateListAdapter();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new ItemAdapter(data, newItemResultLauncher);
        recyclerView.setAdapter(adapter);

        addBtn = findViewById(R.id.addItemFab);

        addBtn.setOnClickListener(v -> {
            if(tabPosition == 0)
            {
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                newItemResultLauncher.launch(intent);
            }
            else if(tabPosition == -1)
            {
                Intent intent = new Intent(MainActivity.this, AddListActivity.class);
                newItemResultLauncher.launch(intent);
            }

        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                dbHelper = DbHelper.getInstance(MainActivity.this);

                itemData = dbHelper.getAllItemsAscExpiration();
                data = getItemNames(itemData);
                itemExpirations = getItemExpirations(itemData);

                currentItemData = getItemNames(itemData);
                currentListData = getListNames(dbHelper.getAllListsDefault());
                Log.d(TAG, "All lists default & size: " + currentListData + " " + dbHelper.getAllListsDefault().size());
                Log.d(TAG, "All lists desc & size: " + getListNames(dbHelper.getAllListsDescName()) + " " + dbHelper.getAllListsDescName().size());
                currentItemExpirations = itemExpirations;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ItemAdapter(data, newItemResultLauncher);
                        recyclerView.setAdapter(adapter);
                        adapter.setData(new ArrayList<>(data), itemExpirations);
                    }
                });
            }
        });

        mainMenuTab = findViewById(R.id.mainMenuTab);
        mainMenuTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        dbHelper = DbHelper.getInstance(MainActivity.this);

                        if(tab.getPosition() == 0) {
                            adapter.setType('i');
                            data = currentItemData;
                            itemExpirations = currentItemExpirations;
                            tabPosition++;
                            Log.d(TAG, "run: tabPosition = " + tabPosition);
                        }
                        else {
                            adapter.setType('l');
                            data = currentListData;
                            tabPosition--;
                            Log.d(TAG, "run: tabPosition = " + tabPosition);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(tab.getPosition() == 0){
                                    adapter.setData(new ArrayList<>(data), itemExpirations);
                                }
                                else {
                                    adapter.setData(new ArrayList<>(data));
                                }
                            }
                        });
                    }
                });

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        this.printAllData();
        onNewIntent(getIntent());
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                dbHelper = DbHelper.getInstance(MainActivity.this);

                itemData = dbHelper.getAllItemsAscExpiration();
                currentItemData = getItemNames(itemData);
                data = currentItemData;
                itemExpirations = getItemExpirations(itemData);
                currentItemExpirations = itemExpirations;

                currentListData = getListNames(dbHelper.getAllListsDefault());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(tabPosition == 0) {
                                adapter.setType('i');
                                adapter.setData(new ArrayList<>(data), currentItemExpirations);
                                recyclerView.setAdapter(adapter);
                            }
                            else {
                                adapter.setType('l');
                                data = currentListData;
                                adapter.setData(new ArrayList<>(data));
                                recyclerView.setAdapter(adapter);
                            }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem filterByName = menu.findItem(R.id.filterByName);
        SearchView searchName = (SearchView) filterByName.getActionView();
        searchName.setQueryHint("");

        MenuItem filterByCategory = menu.findItem(R.id.filterByCategory);
        SearchView searchCategory = (SearchView) filterByCategory.getActionView();
        searchCategory.setQueryHint("");

        MenuItem filterByExpiration = menu.findItem(R.id.filterByExpiration);
        SearchView searchExpiration = (SearchView) filterByExpiration.getActionView();
        searchExpiration.setQueryHint("");

        filterByExpiration.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                return true;
            }
        });

        searchName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.compareTo("") == 0) {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            dbHelper = DbHelper.getInstance(MainActivity.this);

                            itemData = dbHelper.getAllItemsAscExpiration();

                            currentItemData = getItemNames(itemData);
                            data = currentItemData;

                            currentItemExpirations = getItemExpirations(itemData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.setType('i');
                                    adapter.setData(new ArrayList<>(data), currentItemExpirations);
                                }
                            });
                        }
                    });
                }
                else {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            dbHelper = DbHelper.getInstance(MainActivity.this);

                            itemData = dbHelper.filterItemsByName(newText);

                            currentItemData = getItemNames(itemData);
                            data = currentItemData;

                            currentItemExpirations = getItemExpirations(itemData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.setType('i');
                                    adapter.setData(new ArrayList<>(data), currentItemExpirations);
                                }
                            });
                        }
                    });
                }
            return false;
            }
        });

        searchCategory.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.compareTo("") == 0) {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            dbHelper = DbHelper.getInstance(MainActivity.this);

                            itemData = dbHelper.getAllItemsAscExpiration();

                            currentItemData = getItemNames(itemData);
                            data = currentItemData;

                            currentItemExpirations = getItemExpirations(itemData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.setType('i');
                                    adapter.setData(new ArrayList<>(data), currentItemExpirations);
                                }
                            });
                        }
                    });
                }
                else {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            dbHelper = DbHelper.getInstance(MainActivity.this);

                            itemData = dbHelper.filterItemsByCategory(newText);

                            currentItemData = getItemNames(itemData);
                            data = currentItemData;

                            currentItemExpirations = getItemExpirations(itemData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.setType('i');
                                    adapter.setData(new ArrayList<>(data), currentItemExpirations);
                                }
                            });
                        }
                    });
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(tabPosition == 0) {
            adapter.setType('i');


            if(id == R.id.sort) {
                SubMenu sortMenu = item.getSubMenu();
                MenuItem sortExpiration = sortMenu.getItem(1);
                sortExpiration.setVisible(true);
            }
            else if(id == R.id.filterBy) {
                SubMenu filterMenu = item.getSubMenu();
                MenuItem filterName = filterMenu.getItem(0);
                MenuItem filterCategory = filterMenu.getItem(1);
                MenuItem filterExpiration = filterMenu.getItem(2);

                filterName.setVisible(true);
                filterCategory.setVisible(true);
                filterExpiration.setVisible(true);
            }
            else if (id == R.id.sortName) {
                if(itemNameOrder == 0) {
                    itemData = dbHelper.getAllItemsDefault();
                    currentItemData = getItemNames(itemData);

                    currentItemExpirations = getItemExpirations(itemData);
                    itemExpirations = currentItemExpirations;

                    data = currentItemData;

                    adapter.setData(new ArrayList<>(data), itemExpirations);

                    itemNameOrder++;
                }
                else {
                    itemData = dbHelper.getAllItemsDescName();
                    currentItemData = getItemNames(itemData);

                    currentItemExpirations = getItemExpirations(itemData);
                    itemExpirations = currentItemExpirations;

                    data = currentItemData;

                    adapter.setData(new ArrayList<>(data), itemExpirations);

                    itemNameOrder--;
                }
            }
            else if (id == R.id.sortExpiration) {
                if(itemExpOrder == 0) {
                    itemData = dbHelper.getAllItemsAscExpiration();
                    currentItemData = getItemNames(itemData);

                    currentItemExpirations = getItemExpirations(itemData);
                    itemExpirations = currentItemExpirations;

                    data = currentItemData;

                    adapter.setData(new ArrayList<>(data), itemExpirations);

                    itemExpOrder++;
                }
                else {
                    itemData = dbHelper.getAllItemsDescExpiration();
                    currentItemData = getItemNames(itemData);

                    currentItemExpirations = getItemExpirations(itemData);
                    itemExpirations = currentItemExpirations;

                    data = currentItemData;

                    adapter.setData(new ArrayList<>(data), itemExpirations);

                    itemExpOrder--;
                }
            }
        }
        else {
            adapter.setType('l');

            if(id == R.id.sort) {
                SubMenu sortMenu = item.getSubMenu();
                MenuItem sortExpiration = sortMenu.getItem(1);
                sortExpiration.setVisible(false);
            }
            else if(id == R.id.filterBy) {
                SubMenu filterMenu = item.getSubMenu();
                MenuItem filterName = filterMenu.getItem(0);
                MenuItem filterCategory = filterMenu.getItem(1);
                MenuItem filterExpiration = filterMenu.getItem(2);

                filterName.setVisible(false);
                filterCategory.setVisible(false);
                filterExpiration.setVisible(false);
            }
            else if(id == R.id.sortName) {
                if (listNameOrder == 0) {
                    currentListData = getListNames(dbHelper.getAllListsDescName());
                    data = currentListData;

                    adapter.setData(new ArrayList<>(data));

                    listNameOrder++;
                } else {
                    currentListData = getListNames(dbHelper.getAllListsDefault());
                    data = currentListData;

                    adapter.setData(new ArrayList<>(data));

                    listNameOrder--;
                }
            }
        }

        return true;
    }

    public void updateItemAdapter() {
        itemData = dbHelper.getAllItemsAscExpiration();
        data = getItemNames(itemData);

        currentItemData = getItemNames(itemData);
        currentItemExpirations = getItemExpirations(itemData);

        adapter.setData(new ArrayList<>(data), currentItemExpirations);
    }

    public void updateListAdapter() {
        currentListData = getListNames(dbHelper.getAllListsDefault());
        data = currentListData;

        adapter.setData(new ArrayList<>(data));
    }

    public ArrayList<String> getItemNames(ArrayList<Item> data) {
        ArrayList<String> names = new ArrayList<>();

        for(int i = 0; i < data.size(); i++)
            names.add(data.get(i).getName());

        return names;
    }

    public ArrayList<String> getListNames(ArrayList<ItemList> data) {
        ArrayList<String> names = new ArrayList<>();

        for(int i = 0; i < data.size(); i++) {
            String name = data.get(i).getListName();
            if(i > 0) {
                if(name.compareTo(data.get(i - 1).getListName()) != 0)
                    names.add(name);
            }
            else {
                names.add(name);
            }

        }

        for(String l : names) {
            Log.d("MainActivity", "getListNames: " + names);
        }

        return names;
    }

    public ArrayList<String> getItemExpirations(ArrayList<Item> data) {
        ArrayList<String> expirations = new ArrayList<>();

        for(int i = 0; i < data.size(); i++)
            expirations.add(data.get(i).getDate());

        return expirations;
    }

    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }
    };

    private void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                dbHelper = DbHelper.getInstance(MainActivity.this);

                itemData = dbHelper.filterItemsByExpiration(sdf.format(myCalendar.getTime()));
                data = getItemNames(itemData);

                itemExpirations = getItemExpirations(itemData);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setType('i');
                        adapter.setData(new ArrayList<>(data), itemExpirations);
                    }
                });
            }
        });
    }

    private void printAllData() {
        Log.d("MainActivity", "printAllData: start");
        for(Item i : itemData) {
            Log.d("MainActivity", "printAllData: " + i.toString());
        }
//        for(ItemList il : listData) {
//            Log.d("MainActivity", "printAllData: " + il.toString());
//        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent: ");

        Bundle extras = intent.getExtras();
        if(extras != null && extras.containsKey(Alarm.NOTIF_FLAG)){
            boolean isFromNotif = extras.getBoolean(Alarm.NOTIF_FLAG, false);
            Log.d(TAG, "onNewIntent: isFromNotif = " + isFromNotif);
            if(isFromNotif){
                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);

                showExpiryWarning();
            }
        }
    }

    private void showExpiryWarning(){
        new AlertDialog.Builder(this)
                .setTitle("Freshness checker")
                .setMessage("an item has expired")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}