package govirtual360.firestoredemo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
        EditText first,second;
        Button send,get;
    TextView textView;
    private final String TAG="data";
    private DocumentReference mdoc=FirebaseFirestore.getInstance().document("StoreDemo/Demo");

    @Override
    protected void onStart() {
        super.onStart();
        mdoc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    textView.setText(documentSnapshot.getString("First")+"-"+documentSnapshot.getString("Second"));
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        first=findViewById(R.id.editText);
        second=findViewById(R.id.editText2);
        send=findViewById(R.id.button);
        get=findViewById(R.id.button2);
        textView=findViewById(R.id.textView);

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String, Object> user = new HashMap<>();
                    user.put("First", first.getText().toString());
                    user.put("Second", second.getText().toString());
                    mdoc.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG,"Document saved");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG,"Document saved",e);
                        }
                    });
                }
            });

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            textView.setText(documentSnapshot.getString("First")+"-"+documentSnapshot.getString("Second"));
                        }

                    }
                });
            }
        });
    }


}
