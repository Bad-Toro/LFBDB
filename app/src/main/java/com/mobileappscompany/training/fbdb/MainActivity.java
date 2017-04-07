package com.mobileappscompany.training.fbdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAC_Tag";
    DatabaseReference myRef;
    EditText eT;
    TextView tV;
    Client ct;
    List<Client> cts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRef = FirebaseDatabase.getInstance().getReference("message");
        eT = (EditText) findViewById(R.id.editText);
        tV = (TextView) findViewById(R.id.textView);
        ct = new Client();
        cts = new ArrayList<>();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                Client c = dataSnapshot.getValue(Client.class);
                cts.add(c);

                StringBuilder sb = new StringBuilder();
                for(Client ct: cts){
                    sb.append(ct.getName() + "\n");
                }
                tV.setText(sb.toString());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                //
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                //
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
            }
        };
        myRef.addChildEventListener(childEventListener);


//        PostDetailActivity.java
//        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    Client c1 = postSnapshot.getValue(Client.class);
//                    cts.add(c1);
//                }
//                //ct = dataSnapshot.getValue(Client.class);
//                //if(ct == null) return;
//                tV.setText(cts.get(0).getName());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("MAC_Tag", "Failed to read value.", error.toException());
//            }
//        });

    }

    public void onClick(View view) {
        if(ct == null){
            ct = new Client();
        }
        String key = myRef.push().getKey();
        ct.setName(eT.getText().toString());
        myRef.child(key).setValue(ct);
    }
}
