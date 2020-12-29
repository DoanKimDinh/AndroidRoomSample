 package com.example.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

 public class MainActivity extends AppCompatActivity {


    EditText editText,editSearch;
    Button btAdd, btReset, btSearch;
    RecyclerView recyclerView;
    List<MainData> dataList = new ArrayList<>();
    static List<String> listString = new ArrayList<>();
    Spinner spinner;

    LinearLayoutManager linearLayoutManager;
    static RoomDB database;

    MainAdapter adapter;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        btAdd = findViewById(R.id.bt_add);
        btReset = findViewById(R.id.btn_reset);
        recyclerView = findViewById(R.id.recycler_view);
        btSearch = findViewById(R.id.bt_search);
        editSearch = findViewById(R.id.edit_search);
        spinner = findViewById(R.id.spinner);

         database = RoomDB.getInstance(this);
         ten();

         ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listString);
         arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinner.setAdapter(arrayAdapter);

         spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 Toast.makeText(MainActivity.this, listString.get(position),Toast.LENGTH_SHORT).show();
             }
             @Override
             public void onNothingSelected(AdapterView<?> parent) {
             }
         });
        //Store data value in datalist
         dataList = database.mainDao().getAll();
         linearLayoutManager = new LinearLayoutManager(this);
         recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MainAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get string from edit text
                String sText = editText.getText().toString().trim();
                if(!sText.equals("")){
                    MainData data = new MainData();
                    data.setText(sText);
                    database.mainDao().insert(data);
                    editText.setText("");
                    //Notify
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();

                    listString.clear();
                    for (MainData main: database.mainDao().getAll()) {
                        listString.add(main.getText());
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sText = editSearch.getText().toString();
                if(!sText.equals("")){
                    dataList.clear();
                    List<MainData> listtam = new ArrayList<MainData>();
                    //dataList.addAll(database.mainDao().getAll());
                    listtam.addAll(database.mainDao().getAll());
                    for (MainData d : listtam) {
                        if(d.getText().contains(sText)){
                            dataList.add(d);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //database.mainDao().reset(dataList);

                //Notify
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();

            }
        });
    }
    public static void ten(){
        listString.clear();
        for (MainData main: database.mainDao().getAll()) {
            listString.add(main.getText());
        }
    }
}