package com.mc.myapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.RoundingMode;
import java.math.BigDecimal;

import static java.math.BigDecimal.*;

import java.util.*;
import java.lang.*;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        EditText priseEstate = findViewById(R.id.priseEstate);

        EditText initialPayment = findViewById(R.id.initialPayment);

        EditText interestRate = findViewById(R.id.interestRate);

        EditText term = findViewById(R.id.term);

        Button learn = findViewById(R.id.learn);

        TextView resLoanAmount = findViewById(R.id.resLoanAmount);
        TextView resMonthlyPayment = findViewById(R.id.resMonthlyPayment);
        TextView resRequiredIncome = findViewById(R.id.resRequiredIncome);
        TextView resAmountOfInterest = findViewById(R.id.resAmountOfInterest);

        //DecimalFormat df = new DecimalFormat("#,##");

        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                BigDecimal priseEstate = (BigDecimal) priseEstate.getText();
                BigDecimal initialPayment = (BigDecimal) initialPayment.getText();
                BigDecimal interestRate = (BigDecimal) interestRate.getText();
                BigDecimal term = (BigDecimal) term.getText();
*/
                BigDecimal two = new BigDecimal("2");
                BigDecimal twelve = new BigDecimal("12");
                BigDecimal lessZero = new BigDecimal("-1");

                //5600000 1960000 10 30

                double priseEstateValue = Double.parseDouble(priseEstate.getText().toString());
                double initialPaymentValue = Double.parseDouble(initialPayment.getText().toString());
                double interestRateValue = Double.parseDouble(interestRate.getText().toString());
                double termValue = Double.parseDouble(term.getText().toString());

                BigDecimal priseEV = BigDecimal.valueOf(priseEstateValue);
                BigDecimal initialPV = BigDecimal.valueOf(initialPaymentValue);
                BigDecimal interestRV = BigDecimal.valueOf(interestRateValue).divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_UP);
                BigDecimal termV = BigDecimal.valueOf(termValue).multiply(twelve).setScale(0,RoundingMode.HALF_UP);;


                BigDecimal loanAmount = priseEV.subtract(initialPV);//сумма кредита
                resLoanAmount.setText(String.valueOf(loanAmount.setScale(2,RoundingMode.HALF_UP)));

                BigDecimal iTR = interestRV.divide(twelve,2,RoundingMode.HALF_UP);
                BigDecimal pow = ONE.add(iTR).pow(termV.intValue());
                BigDecimal tUp = iTR.multiply(pow).setScale(2,RoundingMode.HALF_UP);
                BigDecimal tDown = pow.subtract(lessZero);
                BigDecimal divide = tUp.divide(tDown, 2, RoundingMode.HALF_UP);
                BigDecimal monthlyPayment = loanAmount.multiply(divide);// ежемесячный платёж
                resMonthlyPayment.setText(String.valueOf(monthlyPayment.setScale(2,RoundingMode.HALF_UP)));

                BigDecimal requiredIncome = monthlyPayment.multiply(two);// необходимый доход
                resRequiredIncome.setText(String.valueOf(requiredIncome.setScale(2,RoundingMode.HALF_UP)));

                BigDecimal aOI = monthlyPayment.multiply(termV).setScale(2,RoundingMode.HALF_UP);
                BigDecimal amountOfInterest = aOI.subtract(loanAmount).setScale(2,RoundingMode.HALF_UP);//сумма процентов
                resAmountOfInterest.setText(String.valueOf(amountOfInterest));

//                double resLA = LoanAmount(priseEstateValue, initialPaymentValue);
//                df.format(resLA);
//                resLoanAmount.setText(String.valueOf(resLA));
//                double resMP = MonthlyPayment(interestRateValue, priseEstateValue, initialPaymentValue, termValue);
//                df.format(resMP);
//                resMonthlyPayment.setText(String.valueOf(resMP));
//                double resRI = RequiredIncome(interestRateValue, priseEstateValue, initialPaymentValue, termValue);
//                df.format(resRI);
//                resRequiredIncome.setText(String.valueOf(resRI));
//                double resAOfI = AmountOfInterest(interestRateValue, termValue, priseEstateValue, initialPaymentValue);
//                df.format(resAOfI);
//                resAmountOfInterest.setText(String.valueOf(resAOfI));
            }
        });
    }

//    public double LoanAmount(double priseEstateValue, double initialPaymentValue) {
//        return priseEstateValue - initialPaymentValue;
//    }
//
//    public double InterestRate(double interestRateValue) {
//        return interestRateValue / 12;
//    }
//
//    public double MonthlyPayment(double interestRateValue, double priseEstateValue, double initialPaymentValue, double termValue) {
//        return (LoanAmount(priseEstateValue, initialPaymentValue) * ((InterestRate(interestRateValue) * Math.pow((1 + InterestRate(interestRateValue)), termValue)) / ((1 + InterestRate(interestRateValue)) - Math.pow((1 + InterestRate(interestRateValue)), termValue))));
//    }
//
//    public double RequiredIncome(double interestRateValue, double priseEstateValue, double initialPaymentValue, double termValue) {
//        return 2 * MonthlyPayment(interestRateValue, priseEstateValue, initialPaymentValue, termValue);
//    }
//
//    public double AmountOfInterest(double interestRateValue, double termValue, double priseEstateValue, double initialPaymentValue) {
//        return (MonthlyPayment(interestRateValue, priseEstateValue, initialPaymentValue, termValue) * termValue) - LoanAmount(priseEstateValue, initialPaymentValue);
//    }
}

