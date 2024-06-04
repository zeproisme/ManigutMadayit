package com.example.manigutmadayit;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
public class UploadDocx extends AppCompatActivity {
    FloatingActionButton fab;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<EventDataClass> dataList;
    EventsAdapter adapter;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.docx_files);
        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        //WORKS
        GridLayoutManager gridLayoutManager = new GridLayoutManager(UploadDocx.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadDocx.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();
        adapter = new EventsAdapter(UploadDocx.this, dataList);

        recyclerView.setAdapter(adapter);
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://manigut-madayit-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = database.getReference("test");
        Log.d("abcdddd","hellooooooo");
        dialog.show();
        Log.d("absdasdc","helloasdasd");
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("abccccccc","helloasdasd");
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    EventDataClass dataClass = itemSnapshot.getValue(EventDataClass.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() { //WORKS
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UploadDocx.this, UploadEvents.class);
                startActivity(intent);
            }
        });
    }
    public void searchList(String text){
        ArrayList<EventDataClass> searchList = new ArrayList<>();
        for (EventDataClass dataClass: dataList){
            if (dataClass.getDataTitle().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
    }
}