package com.example.android.firestone;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private EditText editTextTitle;
    private EditText editTextDescription;
    private TextView textViewData;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference itemRef = db.collection("Information");
    private DocumentReference infoRef = db.document("Information/Item information");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        textViewData = findViewById(R.id.text_view_data);

    }

    @Override
    protected void onStart() {
        super.onStart();
        /*infoRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null){
                    Toast.makeText(MainActivity.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                    return;
                }
                if (documentSnapshot.exists()){
                    *//*String title = documentSnapshot.getString(KEY_TITLE);
                    String description = documentSnapshot.getString(KEY_DESCRIPTION);
                    //Map<String, Object> info = documentSnapshot.getData();
                    textViewData.setText("Title: " + title + "\n" + "Description: " + description);*//*
                    Item item = documentSnapshot.toObject(Item.class);
                    String title = item.getTitle();
                    String description = item.getDescription();
                    textViewData.setText("Title: " + title + "\n" + "Description: " + description);
                }else {
                    textViewData.setText("There is no Document because you killed it!");
                }
            }
        });*/
        itemRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(MainActivity.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                    return;
                }

                String data = "";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Item item = documentSnapshot.toObject(Item.class);
                    item.setId(documentSnapshot.getId());
                    String id = item.getId();
                    String title = item.getTitle();
                    String description = item.getDescription();
                    data += "Id: " + id + "\nTitle: " + title + "\nDescription: " + description + "\n\n";
                }
                textViewData.setText(data);
            }
        });
    }

    public void addItem(View v){
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        /*Map<String, Object> info = new HashMap<>();
        info.put(KEY_TITLE, title);
        info.put(KEY_DESCRIPTION, description);*/
        Item item = new Item();
        item.setTitle(title);
        item.setDescription(description);
        /*infoRef.set(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Information saved!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error occured while saving!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });*/
        itemRef.add(item);
    }

    public void loadInfo(View v){
        /*infoRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            *//*String title = documentSnapshot.getString(KEY_TITLE);
                            String description = documentSnapshot.getString(KEY_DESCRIPTION);
                            //Map<String, Object> info = documentSnapshot.getData();*//*
                            Item item = documentSnapshot.toObject(Item.class);
                            String title = item.getTitle();
                            String description = item.getDescription();
                            textViewData.setText("Title: " + title + "\n" + "Description: " + description);
                        }else{
                            Toast.makeText(MainActivity.this, "There is no such document!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error loading!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });*/
        itemRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            Item item = documentSnapshot.toObject(Item.class);
                            item.setId(documentSnapshot.getId());
                            String id = item.getId();
                            String title = item.getTitle();
                            String description = item.getDescription();
                            data += "Id: " + id + "\nTitle: " + title + "\nDescription: " + description + "\n\n";
                        }
                        textViewData.setText(data);
                    }
                });
    }

   /* public void updateDescription(View v){
        String description = editTextDescription.getText().toString();
        //Map<String , Object> info = new HashMap<>();
        //info.put(KEY_DESCRIPTION, description);
        //infoRef.set(info, SetOptions.merge());
        infoRef.update(KEY_DESCRIPTION, description);
    }

    public void deleteDescription(View v){
        *//*Map<String,Object> info = new HashMap<>();
        info.put(KEY_DESCRIPTION, FieldValue.delete());
        infoRef.update(info);*//*
        infoRef.update(KEY_DESCRIPTION, FieldValue.delete());
    }

    public void deleteItem(View v){
        infoRef.delete();
    }*/
}
