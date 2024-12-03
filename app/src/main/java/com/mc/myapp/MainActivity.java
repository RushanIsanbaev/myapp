package com.mc.myapp;

import static android.icu.number.NumberRangeFormatter.with;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;

import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class MainActivity extends AppCompatActivity {
    TextInputEditText priseEstate;
    TextInputEditText initialPayment;
    TextInputEditText interestRate;
    TextInputEditText term;

    private TextView resLoanAmount ;
    private TextView resMonthlyPayment ;
    private TextView resRequiredIncome ;
    private TextView resAmountOfInterest ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button learn = findViewById(R.id.learn);

        resLoanAmount = findViewById(R.id.resLoanAmount);
        resMonthlyPayment = findViewById(R.id.resMonthlyPayment);
        resRequiredIncome = findViewById(R.id.resRequiredIncome);
        resAmountOfInterest = findViewById(R.id.resAmountOfInterest);

        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                priseEstate = findViewById(R.id.priseEstate);

                initialPayment = findViewById(R.id.initialPayment);

                interestRate = findViewById(R.id.interestRate);

                term = findViewById(R.id.term);

                BigDecimal two = new BigDecimal("2");
                BigDecimal twelve = new BigDecimal("12");

                //5600000 1960000 10 30

                double priseEstateValue = Double.parseDouble(priseEstate.getText().toString());
                double initialPaymentValue = Double.parseDouble(initialPayment.getText().toString());
                double interestRateValue = Double.parseDouble(interestRate.getText().toString());
                double termValue = Double.parseDouble(term.getText().toString());

                BigDecimal priseEV = valueOf(priseEstateValue);
                BigDecimal initialPV = valueOf(initialPaymentValue);
                BigDecimal interestRV = valueOf(interestRateValue).divide(valueOf(100),2,RoundingMode.HALF_UP);
                BigDecimal termV = valueOf(termValue).multiply(twelve).setScale(0,RoundingMode.HALF_UP);;


                BigDecimal loanAmount = priseEV.subtract(initialPV);//сумма кредита
                resLoanAmount.setText(String.valueOf(loanAmount.setScale(2,RoundingMode.HALF_UP)));

                BigDecimal iTR = interestRV.divide(twelve,10,RoundingMode.HALF_UP);
                BigDecimal pow = ONE.add(iTR).pow(termV.intValue());
                BigDecimal tUp = iTR.multiply(pow).setScale(10,RoundingMode.HALF_UP);
                BigDecimal tDown = pow.subtract(ONE);
                BigDecimal divide = tUp.divide(tDown, 10, RoundingMode.HALF_UP);
                BigDecimal monthlyPayment = loanAmount.multiply(divide);// ежемесячный платёж
                resMonthlyPayment.setText(String.valueOf(monthlyPayment.setScale(2,RoundingMode.HALF_UP)));

                BigDecimal requiredIncome = monthlyPayment.multiply(two);// необходимый доход
                resRequiredIncome.setText(String.valueOf(requiredIncome.setScale(2,RoundingMode.HALF_UP)));

                BigDecimal aOI = monthlyPayment.multiply(termV).setScale(10,RoundingMode.HALF_UP);
                BigDecimal amountOfInterest = aOI.subtract(loanAmount).setScale(2,RoundingMode.HALF_UP);//сумма процентов
                resAmountOfInterest.setText(String.valueOf(amountOfInterest));

            }
        });
    }

}

