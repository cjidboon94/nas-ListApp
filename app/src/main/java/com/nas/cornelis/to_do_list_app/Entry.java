package com.nas.cornelis.to_do_list_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class Entry extends Activity {

    private ArrayList<String> list;
    private EditText et;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, R.layout.item_entry, R.id.firstLine ,list);
        ListView listview = (ListView)findViewById(R.id.listOfLists);

        listview.setAdapter(adapter);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        getOldList();
        et = (EditText)findViewById(R.id.editText);
    }
    @Override
    public void onPause() {
        super.onPause();
        try {
            PrintStream out = new PrintStream(openFileOutput("list.txt", MODE_PRIVATE));
            for (int i = 0; i < list.size(); i++) {
                out.println(list.get(i));
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void onClickButton(View view ){
        final String item =  et.getText().toString();
        if(!item.equals("")) {
            adapter.add(item);
            adapter.notifyDataSetChanged();
        }
    }
    private void getOldList() {
            try {
                Scanner scan = new Scanner(openFileInput("list.txt"));
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    adapter.add(line);
                }
                adapter.notifyDataSetChanged();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }
}
