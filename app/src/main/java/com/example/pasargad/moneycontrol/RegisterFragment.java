package com.example.pasargad.moneycontrol;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.Calendar;
import java.util.Date;


public class RegisterFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText editText;
    TextView inputPrice;
    TextView inputDate;
    TextView inputDetailes;
    TextView inputTitle;
    Button registerbtn;
    RadioButton RbtnH;
    RadioButton RbtnD;
    long pickedTime = 0;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_register, container, false);

        EditText etDOB=view.findViewById(R.id.inputDate);

        inputDate = view.findViewById(R.id.inputDate);

        inputPrice = view.findViewById(R.id.inputPrice);

        inputTitle=view.findViewById(R.id.inputTitle);
        inputDetailes=view.findViewById(R.id.inputDetailes);
        RbtnH=view.findViewById(R.id.RbtnH);
        RbtnD=view.findViewById(R.id.RbtnD);
        registerbtn=view.findViewById(R.id.registerbtn);


        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity(), "Status:idk", Toast.LENGTH_SHORT).show();

                String title = inputTitle.getText().toString();
                String details  = inputDetailes.getText().toString();
                int amount = Integer.parseInt(inputPrice.getText().toString());
                boolean isIncome = !RbtnH.isChecked();
               // long submittedAt = System.currentTimeMillis();
                long submittedAt = pickedTime;

                TransactionModel tm = new TransactionModel(title, details, isIncome, submittedAt, pickedTime, amount);

                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                FirebaseDatabase.getInstance().getReference().child("transactions")
                        .child(currentFirebaseUser.getUid())
                        .child(Long.toString(submittedAt))
                        .setValue(tm).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast toast=Toast.makeText(getContext(), "  تراکنش ثبت شد  ", Toast.LENGTH_SHORT);
                        View view=toast.getView();
                        view.setBackgroundResource(R.drawable.toastbackground);
                        toast.show();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "ثبت نشد!" , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        ImageView openDatePicker = view.findViewById(R.id.openDatePicker);

        openDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersianCalendar persianCalendar = new PersianCalendar();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        RegisterFragment.this,
                        persianCalendar.getPersianYear(),
                        persianCalendar.getPersianMonth(),
                        persianCalendar.getPersianDay()
                );
                datePickerDialog.show(getActivity().getFragmentManager(), "تاریخ");
            }
        });

        return view;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        inputDate.setText("" + year + "/" + monthOfYear + "/" + dayOfMonth);
        PersianCalendar persianCalendar = new PersianCalendar();
        persianCalendar.setPersianDate(year, monthOfYear, dayOfMonth);
        pickedTime = persianCalendar.getTimeInMillis();
    }
}
