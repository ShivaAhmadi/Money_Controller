package com.example.pasargad.moneycontrol;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.opencensus.metrics.export.Summary;
import kotlin.reflect.KFunction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;


public class ReportFragment extends Fragment {

    RecyclerView recview;
    private DatabaseReference registerRef,Regref;
    private FirebaseAuth mAuth;
    private String currentUserId;

    TextView showtype;
    ImageView backbtn;


    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_report, container, false);

        recview=(RecyclerView)view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        showtype=view.findViewById(R.id.showType);
        backbtn=view.findViewById(R.id.backbtn);


        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        Regref=FirebaseDatabase.getInstance().getReference().child("transactions").child(currentUserId);



        return view;
    }

    public void onStart(){
        super.onStart();

        FirebaseRecyclerOptions options= new FirebaseRecyclerOptions.Builder<TransactionModel>()
                .setQuery(Regref,TransactionModel.class).build();
        FirebaseRecyclerAdapter<TransactionModel,RegisterViewHolser> adapter
                =new FirebaseRecyclerAdapter<TransactionModel, RegisterViewHolser>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RegisterViewHolser registerViewHolser, int i, @NonNull TransactionModel transactionModel) {

                String regId=getRef(i).getKey();
                Regref.child(regId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.hasChild("title")){
                            String regTitle=snapshot.child("title").getValue().toString();
                            String regPrice=snapshot.child("price").getValue().toString();
                            String regDetails=snapshot.child("details").getValue().toString();
                            PersianCalendar persianCalendar = new PersianCalendar((long) snapshot.child("submittedAt").getValue());
                            String regDate=persianCalendar.getPersianLongDateAndTime();
                            boolean isIncome = (boolean) snapshot.child("isIncome").getValue();
                            String regIncome= isIncome ? "درآمد" : "هزینه";


                            registerViewHolser.showTitle.setText(regTitle);
                            registerViewHolser.showPrice.setText(regPrice);
                            registerViewHolser.shoeDetailes.setText(regDetails);
                            registerViewHolser.showDate.setText(regDate);
                            registerViewHolser.showType.setText(regIncome);
                            getView().findViewById(R.id.idProgressBar).setVisibility(View.GONE);
                            registerViewHolser.deleteImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getRef(i).removeValue(new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                            Toast.makeText(getActivity(), "Done!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });

                            registerViewHolser.editImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(getActivity(), EditActivity.class);
                                    i.putExtra("key", regId);
                                    startActivity(i);
                                }
                            });

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });

            }

            @NonNull
            @Override
            public RegisterViewHolser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
                RegisterViewHolser viewHolser=new RegisterViewHolser(view);
                return viewHolser;
            }
        };
        recview.setAdapter(adapter);
        adapter.startListening();

    }

    public void scrollTo(int position) {
        if (recview == null) return;
        recview.scrollToPosition(position);
    }

    public static class RegisterViewHolser extends RecyclerView.ViewHolder{

        TextView showTitle,shoeDetailes,showType,showDate,showPrice;
        ImageView editImage, deleteImage;

        public RegisterViewHolser(@NonNull View itemView) {
            super(itemView);
            showTitle=itemView.findViewById(R.id.showTitle);
            shoeDetailes=itemView.findViewById(R.id.shoeDetailes);
            showType=itemView.findViewById(R.id.showType);
            showDate=itemView.findViewById(R.id.showDate);
            showPrice=itemView.findViewById(R.id.showPrice);

            deleteImage = itemView.findViewById(R.id.imgDelete);
            editImage = itemView.findViewById(R.id.imgEdit);
        }
    }

    private void onStarClicked (DatabaseReference postRef){
        postRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {

                return null;
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }


}
