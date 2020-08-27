package com.example.tipandsavingscalculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    private static final NumberFormat currencyFormatValue=NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormatValue=NumberFormat.getPercentInstance();

    private double billAmount=0.0;
    private double tipPercent=0.25;
    private double totalSalary=0.0;
    private double savingPercent=0.25;

    private TextView txtBillAmount,txtTotalBillAmount,txtTipPercent,txtTip,txtSavePercent,txtMoneySaved;

    private EditText edtMoneyAmount,edtSalary;

    private SeekBar seekBarPercent,seekBarSavePercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBillAmount=findViewById(R.id.txtBillAmount);
        txtTotalBillAmount=findViewById(R.id.txtTotalBillAmount);
        txtTipPercent=findViewById(R.id.txtTipPercent);
        txtTip=findViewById(R.id.txtTip);
        txtSavePercent=findViewById(R.id.txtSavePercent);
        txtMoneySaved=findViewById(R.id.txtMoneySaved);

        edtMoneyAmount=findViewById(R.id.edtMoneyAmount);
        edtSalary=findViewById(R.id.edtSalary);

        seekBarPercent=findViewById(R.id.seekBarPercent);
        seekBarSavePercent=findViewById(R.id.seekBarSavePercent);


        txtTip.setText(currencyFormatValue.format(0));
        txtTotalBillAmount.setText(currencyFormatValue.format(0));
        txtMoneySaved.setText(currencyFormatValue.format(0));


        edtMoneyAmount.addTextChangedListener(tipEdtMoneyAmountTextWatcher);
        seekBarPercent.setOnSeekBarChangeListener(tipSeekBarChangeListener);


        edtSalary.addTextChangedListener(edtSalaryChangedTextWatcher);
        seekBarSavePercent.setOnSeekBarChangeListener(seekBarSavingPercentChangeListener);


    }

    //Tip Calculator Logic

    private final TextWatcher tipEdtMoneyAmountTextWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try{

                billAmount=Double.parseDouble(s.toString())/100.0;
                txtBillAmount.setText(currencyFormatValue.format(billAmount));

            }catch (NumberFormatException e){
                txtBillAmount.setText("");
                billAmount=0.0;
            }

            calculateTip();
        }

        @Override
        public void afterTextChanged(Editable s) {


        }
    };

    private final SeekBar.OnSeekBarChangeListener tipSeekBarChangeListener=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            tipPercent=progress/100.00;
            calculateTip();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void calculateTip(){

        txtTipPercent.setText(percentFormatValue.format(tipPercent));
        Toast.makeText(this, tipPercent+"", Toast.LENGTH_SHORT).show();

        double tipValue=billAmount*tipPercent;
        double totalValue=billAmount+tipValue;

        txtTip.setText(currencyFormatValue.format(tipValue));
        txtTotalBillAmount.setText(currencyFormatValue.format(totalValue));
    }


       //savings Calculator Logic

    private final TextWatcher edtSalaryChangedTextWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try{

                totalSalary=Double.parseDouble(s.toString());
            }catch (NumberFormatException e){
                totalSalary=0.0;
            }
            calculateSavings();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private final SeekBar.OnSeekBarChangeListener seekBarSavingPercentChangeListener=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


            savingPercent=progress/100.0;
            calculateSavings();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void calculateSavings(){

        txtSavePercent.setText(percentFormatValue.format(savingPercent));
        double savedMoney=(totalSalary*savingPercent);
        txtMoneySaved.setText(currencyFormatValue.format(savedMoney));
    }


}