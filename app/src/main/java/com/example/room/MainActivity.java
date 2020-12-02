 package com.example.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

 public class MainActivity extends AppCompatActivity {


    EditText editText;
    Button btAdd, btReset;
    RecyclerView recyclerView;
    List<MainData> dataList = new ArrayList<>();

    LinearLayoutManager linearLayoutManager;
    RoomDB database;

    MainAdapter adapter;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        btAdd = findViewById(R.id.bt_add);
        btReset = findViewById(R.id.btn_reset);
        recyclerView = findViewById(R.id.recycler_view);

        database = RoomDB.getInstance(this);

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
                }

            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.mainDao().reset(dataList);

                //Notify
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();

            }
        });


    }
}