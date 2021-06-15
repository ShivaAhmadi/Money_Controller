package com.example.pasargad.moneycontrol;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class WalletService {

    protected DatabaseReference databaseReference;
    protected int totalIncome = 0;
    protected int totalExpense = 0;
    protected int sum = 0;

    public WalletService() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("transactions").child(currentUserId);
    }

    public void fetchData(OnFailureListener onFailureListener, OnSuccessListener<DataSnapshot> onSuccessListener, OnCompleteListener onCompleteListener) {
        databaseReference
                .get()
                .addOnFailureListener(onFailureListener)
                .addOnCompleteListener(onCompleteListener)
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            TransactionModel tr = data.getValue(TransactionModel.class);
                            if (tr.isIncome) {
                                totalIncome += tr.price;
                            } else {
                                totalExpense += tr.price;
                            }
                            int earn=(totalIncome-totalExpense);
                            sum+=earn;
                        }
                        onSuccessListener.onSuccess(dataSnapshot);
                    }
                });
    }


    public int getTotalIncome() {
        return totalIncome;
    }



    public int getTotalExpense() {
        return totalExpense;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
