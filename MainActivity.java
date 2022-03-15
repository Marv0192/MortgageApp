package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.slider.RangeSlider;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    private double purchasePrice;
    private double downPayment;
    private double interestRate;
    private int repaymentTime;

    private TextView tViewPurchasePrice;
    private TextView tViewDownPaymentPrice;
    private TextView tViewRepaymentTime;
    private TextView tViewInterestRate;
    private TextView tViewLoanAmount;
    private TextView tViewEstimateAmount;

    private Button calculate;
    private Button findHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tViewPurchasePrice = findViewById(R.id.purchasePrice);
        RangeSlider purchasePriceSlider = findViewById(R.id.ppSlider);
        purchasePriceSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            @SuppressLint("RestrictedApi")
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                tViewPurchasePrice.setText(currencyFormat.format(purchasePrice));

            }
            @Override
            @SuppressLint("RestrictedApi")
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                tViewPurchasePrice.setText(currencyFormat.format(purchasePrice));

            }
        });
        purchasePriceSlider.addOnChangeListener((slider, value, fromUser) -> purchasePrice = value);

        tViewDownPaymentPrice = findViewById(R.id.downPaymentPrice);
        RangeSlider downPaymentPriceSlider = findViewById(R.id.dpSlider);
        downPaymentPriceSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            @SuppressLint("RestrictedApi")
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                tViewDownPaymentPrice.setText(currencyFormat.format(downPayment));
            }

            @Override
            @SuppressLint("RestrictedApi")
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                tViewDownPaymentPrice.setText(currencyFormat.format(downPayment));

            }
        });
        downPaymentPriceSlider.addOnChangeListener((slider, value, fromUser) -> downPayment = value);

        tViewRepaymentTime = findViewById(R.id.repaymentTime);
        RangeSlider repaymentTimeSlider = findViewById(R.id.repaymentSlider);
        repaymentTimeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            @SuppressLint("RestrictedApi")
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                tViewRepaymentTime.setText(getString(R.string.repayment, repaymentTime));

            }

            @Override
            @SuppressLint("RestrictedApi")
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                tViewRepaymentTime.setText(getString(R.string.repayment, repaymentTime));

            }
        });
        repaymentTimeSlider.addOnChangeListener(((slider, value, fromUser) -> repaymentTime = (int) value));

        tViewInterestRate = findViewById(R.id.interestRate);
        RangeSlider interestRateSlider = findViewById(R.id.interestSlider);
        interestRateSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            @SuppressLint("RestrictedApi")
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                tViewInterestRate.setText(getString(R.string.interest, interestRate));

            }

            @Override
            @SuppressLint("RestrictedApi")
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                tViewInterestRate.setText(getString(R.string.interest, interestRate));

            }
        });
        interestRateSlider.addOnChangeListener((slider, value, fromUser) -> interestRate = value);

        calculate = findViewById(R.id.calculate);
        calculate.setOnClickListener(view -> {
            if(downPayment > purchasePrice) {
                showDialogue();

            }
            else {
                double loanAmount = purchasePrice - downPayment;
                Loan loan = new Loan(interestRate, repaymentTime, loanAmount);
                tViewLoanAmount = findViewById(R.id.loanAmountPrice);
                tViewLoanAmount.setText(currencyFormat.format(loanAmount));
                tViewEstimateAmount = findViewById(R.id.estimateAmount);
                tViewEstimateAmount.setText(currencyFormat.format(loan.getMonthlyPayment()));

                findHouse = findViewById(R.id.findHouse);
                findHouse.setEnabled(true);
                findHouse.setOnClickListener(view1 -> {
                    openActivity2();

                });
            }
        });

        }
        public void showDialogue() {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialogue);

            Button okButton = dialog.findViewById(R.id.okButton);
            okButton.setOnClickListener(view -> {
                dialog.dismiss();
            });

            dialog.show();

        }

        public void openActivity2() {
            Intent intent = new Intent(this, HouseListActivity.class);
            startActivity(intent);
        }
    }
