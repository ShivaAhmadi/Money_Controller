package com.example.pasargad.moneycontrol;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

public class EditActivity extends AppCompatActivity {

    Button backbtn;
    Button editbtn;
    EditText inputTitle,inputPrice,inputDate,inputDetailes;
    private DatabaseReference Regref;
    TransactionModel tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        inputTitle=findViewById(R.id.inputTitle);
        inputPrice=findViewById(R.id.inputPrice);
        inputDate=findViewById(R.id.inputDate);
        inputDetailes=findViewById(R.id.inputDetailes);

        Bundle extras = getIntent().getExtras();

        String keyId = extras.getString("key", null);

        if (keyId ==  null) {
            finish();
            return;
        }

        backbtn=findViewById(R.id.backbtn);
        editbtn=findViewById(R.id.editbtn);

        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        String currentUserId=mAuth.getCurrentUser().getUid();
        DatabaseReference Regref=FirebaseDatabase.getInstance().getReference().child("transactions").child(currentUserId);

        Regref.child(keyId).get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditActivity.this, "Shit", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                tm = dataSnapshot.getValue(TransactionModel.class);
                Toast.makeText(EditActivity.this, "We got it: " + tm.title, Toast.LENGTH_SHORT).show();
                String regTitle=tm.title;
                String regPrice=Integer.toString(tm.price);
                String regDetails=tm.details;
                PersianCalendar persianCalendar = new PersianCalendar(tm.submittedAt);
                String regDate=persianCalendar.getPersianLongDateAndTime();
                boolean isIncome = tm.isIncome;
                String regIncome= isIncome ? "درآمد" : "هزینه";

                inputDetailes.setText(regDetails);
                inputDate.setText(regDate);
                inputPrice.setText(regPrice);
                inputTitle.setText(regTitle);

            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent=new Intent(EditActivity.this,HomeActivity.class);
                finish();
            }
        });

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tm.title = inputTitle.getText().toString();
                Regref.child(keyId).setValue(tm, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(EditActivity.this, "Well done", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }

}
