package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText birthDate;
    private Button save;
    private RadioButton maleRadio;
    private RadioButton femaleRadio;
    // creating our dataBase reference
    private DatabaseReference dbRootRef;
    // creating our dataBase object
    private FirebaseDatabase mDatabase;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.editTextName);
        birthDate = (EditText) findViewById(R.id.editTextBirthDate);
        save = (Button) findViewById(R.id.buttonSave);
        maleRadio = (RadioButton) findViewById(R.id.radioButtonMale);
        femaleRadio = (RadioButton) findViewById(R.id.radioButtonFemale);

        // for initializing our DB
        mDatabase = FirebaseDatabase.getInstance();
        // for getting the reference to the DB to the root
        dbRootRef = mDatabase.getReference();

        //updateChildren();
        //readingFromDB();


    }


    // from xml we reference this on click to the button
    public void onSaveButtonClick(View view) {

        Client client = new Client(name.getText().toString(), birthDate.getText().toString(), femaleRadio.isChecked() ? femaleRadio.getText().toString() : maleRadio.getText().toString(), "");
        // now this user gets a unique key from the FireBase.
        client.id = dbRootRef.push().getKey();
        // clients its a sub tree of the root (if not exist,so create it)
        // .child is to go down a level in the tree
        // in the level of "clients" we put client.id (its the unique key), and in this node we put the client object
        // completion Listener its a listener to know if the action succeeded
        dbRootRef.child("clients").child(client.id).setValue(client, completionListener);

    }

    public void readingFromDB() {
        dbRef = mDatabase.getReference("/clients/");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.v("elad",snapshot.getChildrenCount()+"");

                for (DataSnapshot child : snapshot.getChildren()) {
                    // first option -  get all the data into Client object
                    //Client client = child.getValue(Client.class);
                   // Toast.makeText(getApplicationContext(), client.name, Toast.LENGTH_LONG).show();

                    // second option to print directly all the data from DB
                    Toast.makeText(getApplicationContext(), child.getKey(), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),((Client)child.getValue(Client.class)).toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateChildren() {

        dbRef = mDatabase.getReference("/clients/-MLrHjw6VnJ29zdNfRLb");

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("birthDate", "10.6.93");
        childUpdates.put("name","sivan");
        dbRef.updateChildren(childUpdates);
    }

    // now defining the completionListener
    DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
            if (error != null) { // means we get an error.
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show(); // suppose to be in another activity , not in the mainActivity
            } else { // action succeed
                Toast.makeText(MainActivity.this, "User Created Succeesfully", Toast.LENGTH_LONG).show();
            }
        }
    };


}