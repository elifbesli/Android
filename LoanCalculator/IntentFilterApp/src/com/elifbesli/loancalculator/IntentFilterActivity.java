package com.elifbesli.loancalculator;

import com.elifbesli.loancalculator.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IntentFilterActivity extends Activity {
    
	EditText loanAmountInput,interestRateInput,loanPeriodInput;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void showLoanPayments1(View clickedButton) {
       Uri uri = Uri.parse("loan://coreservlets.com/calc");
       Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    	
        intent.putExtras(LoanBundler.makeRandomizedLoanInfoBundle());
        startActivity(intent);
    }
    
 
    public void showLoanPayments2(View clickedButton) {
        String address = makeLoanAddressFromEditTextInputs();
        if(loanAmountInput.getText().toString().matches("") || interestRateInput.getText().toString().matches("")||loanPeriodInput.getText().toString().matches("")){
    		Toast.makeText(getApplicationContext(),
                    "Lütfen Alanlarý Doldurunuz!", Toast.LENGTH_SHORT).show();
    	} else {
        Uri uri = Uri.parse(address);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);}
        
} 
    public void showTabs(View clickedButton) {
    	String address = makeLoanAddressFromEditTextInputs();
    	if(loanAmountInput.getText().toString().matches("") || interestRateInput.getText().toString().matches("")){
    		Toast.makeText(getApplicationContext(),
                    "Lütfen Alanlarý Doldurunuz!", Toast.LENGTH_SHORT).show();
    	} else {
        Intent activityIntent = new Intent(this, TabbedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("number1",loanAmountInput.getText().toString());
        bundle.putString("number2",interestRateInput.getText().toString());
        activityIntent.putExtras(bundle);
        startActivity(activityIntent);
        }
    }
    
    private String makeLoanAddressFromEditTextInputs() {
       loanAmountInput = (EditText)findViewById(R.id.loan_amount);
        Editable loanAmount = loanAmountInput.getText();
        String loanAmountParam = 
                String.format("loanAmount=%s", loanAmount);
        interestRateInput = (EditText)findViewById(R.id.interest_rate);
        Editable interestRate = interestRateInput.getText();
        String interestRateParam = 
                String.format("annualInterestRateInPercent=%s", interestRate);
       loanPeriodInput = (EditText)findViewById(R.id.loan_period);
        Editable loanPeriod = loanPeriodInput.getText();
        String loanPeriodParam = 
                String.format("loanPeriodInMonths=%s", loanPeriod);
       
        String baseAddress = "loan://coreservlets.com/calc";
        String address =
                String.format("%s?%s&%s&%s", baseAddress, loanAmountParam,
                              interestRateParam, loanPeriodParam);
        return(address);
        
    }
    
}