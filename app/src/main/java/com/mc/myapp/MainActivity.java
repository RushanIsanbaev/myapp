package com.mc.myapp;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {
    TextInputEditText priseEstate;
    TextInputEditText initialPayment;
    TextInputEditText interestRate;
    TextInputEditText term;

    private TextView resLoanAmount;
    private TextView resMonthlyPayment;
    private TextView resRequiredIncome;
    private TextView resAmountOfInterest;


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
                try { // это первое исключение
                    if (priseEstate.getText().toString().equals("") |
                            initialPayment.getText().toString().equals("") |
                            interestRate.getText().toString().equals("") |
                            term.getText().toString().equals("")) {
                        throw new NumberFormatException();
                    } else {

                        double priseEstateValue = Double.parseDouble(priseEstate.getText().toString());
                        double initialPaymentValue = Double.parseDouble(initialPayment.getText().toString());
                        double interestRateValue = Double.parseDouble(interestRate.getText().toString());
                        double termValue = Double.parseDouble(term.getText().toString());

                        try {/* это второе он был вне первого, а после
                        поэтому он продолжил выполняться дальше и попадался на исключительный случай
                        при присваивании пустого поля к double (java.lang.NumberFormatException: empty String)*/
                            if (priseEstateValue <= 0 | initialPaymentValue <= 0 | interestRateValue <= 0 | termValue <= 0) {
                                throw new ArithmeticException();
                            } else {
                                BigDecimal priseEV = valueOf(priseEstateValue);
                                BigDecimal initialPV = valueOf(initialPaymentValue);
                                BigDecimal interestRV = valueOf(interestRateValue).divide(valueOf(100), 2, RoundingMode.HALF_UP);
                                BigDecimal termV = valueOf(termValue).multiply(twelve).setScale(0, RoundingMode.HALF_UP);

                                BigDecimal loanAmount = priseEV.subtract(initialPV);//сумма кредита
                                resLoanAmount.setText(String.valueOf(loanAmount.setScale(2, RoundingMode.HALF_UP)));

                                BigDecimal iTR = interestRV.divide(twelve, 10, RoundingMode.HALF_UP);
                                BigDecimal pow = ONE.add(iTR).pow(termV.intValue());
                                BigDecimal tUp = iTR.multiply(pow).setScale(10, RoundingMode.HALF_UP);
                                BigDecimal tDown = pow.subtract(ONE);
                                BigDecimal divide = tUp.divide(tDown, 10, RoundingMode.HALF_UP);
                                BigDecimal monthlyPayment = loanAmount.multiply(divide);// ежемесячный платёж
                                resMonthlyPayment.setText(String.valueOf(monthlyPayment.setScale(2, RoundingMode.HALF_UP)));

                                BigDecimal requiredIncome = monthlyPayment.multiply(two);// необходимый доход
                                resRequiredIncome.setText(String.valueOf(requiredIncome.setScale(2, RoundingMode.HALF_UP)));

                                BigDecimal aOI = monthlyPayment.multiply(termV).setScale(10, RoundingMode.HALF_UP);
                                BigDecimal amountOfInterest = aOI.subtract(loanAmount).setScale(2, RoundingMode.HALF_UP);//сумма процентов
                                resAmountOfInterest.setText(String.valueOf(amountOfInterest));
                            }

                        } catch (ArithmeticException | NumberFormatException arithmeticException) {
                            showInfoZero();
                        }
                    }
                } catch (NumberFormatException exception) {
                    showInfoEmpty();
                }
            }
        });
    }

    private void showInfoZero() {
        Toast.makeText(this, "Что - то пошло не так.\n\rПараметр не может быть 0.", Toast.LENGTH_LONG).show();
    }

    private void showInfoEmpty() {
        Toast.makeText(this, "Что - то пошло не так.\n\rПоле не может быть пустым.", Toast.LENGTH_LONG).show();
    }
}