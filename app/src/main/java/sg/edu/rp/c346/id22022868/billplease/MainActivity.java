package sg.edu.rp.c346.id22022868.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    //Creating variables
    double amount = 0;
    int pax = 0;
    double svsTotalBill = 1.1;
    double gstTotalBill = 1.07;
    double discount = 0;
    double totalBill = 0;
    double payEach = 0;
    double discountedTotalBill = 0;

    //Creating the objects
    EditText amountEntered;
    EditText paxEntered;
    ToggleButton svsBtn;
    ToggleButton gstBtn;
    EditText discountEntered;
    RadioGroup paymentMethod;
    Button splitBtn;
    Button resetBtn;
    TextView finalBill;
    TextView eachPays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Linking the UI elements
        amountEntered = findViewById(R.id.editAmount);
        paxEntered = findViewById(R.id.editPax);
        svsBtn = findViewById(R.id.svsButton);
        gstBtn = findViewById(R.id.gstButton);
        discountEntered = findViewById(R.id.editDiscount);
        paymentMethod = findViewById(R.id.rgPaymentMethod);
        splitBtn = findViewById(R.id.splitButton);
        resetBtn = findViewById(R.id.resetButton);
        finalBill = findViewById(R.id.totalBillView);
        eachPays = findViewById(R.id.eachPaysView);


        //Creating listener for splitting
        splitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Converting string to double + calculating svs charge
                String stringAmount = amountEntered.getText().toString();

                //og amount
                if (svsBtn.isChecked() != true && gstBtn.isChecked() != true) {
                    amount = Double.parseDouble(stringAmount);
                }
                //with svs charge, no gst
                else if (svsBtn.isChecked() == true && gstBtn.isChecked() == false){
                    amount = Double.parseDouble(stringAmount) * 1.1;
                }
                //no svs charge, with gst
                else if (svsBtn.isChecked() == false && gstBtn.isChecked() == true){
                    amount = Double.parseDouble(stringAmount) * 1.07;
                }
                //with both svs charge and gst
                else {
                    amount = Double.parseDouble(stringAmount) * 1.17;
                }

                //with discount
                if (discountEntered.getText().toString() != "") {
                    discount = Double.parseDouble(discountEntered.getText().toString()) / 100;
                    discountedTotalBill = amount * (1 - discount);
                }


                //Splitting the bill
                String paxString = paxEntered.getText().toString();
                pax = Integer.parseInt(paxString);
                payEach = discountedTotalBill / pax;

                //display depending on cash or paynow
                int checkedRadioId = paymentMethod.getCheckedRadioButtonId();
                if (checkedRadioId == R.id.cashRB) {
                    finalBill.setText(String.format("Total Bill: $%.2f", discountedTotalBill));
                    eachPays.setText(String.format("Each Pays: $%.2f in cash", payEach));
                }
                else {
                    finalBill.setText(String.format("Total Bill: $%.2f", discountedTotalBill));
                    eachPays.setText(String.format("Each Pays: $%.2f via Paynow to 912345678", payEach));
                }
            }
        });

        //Listener for resetting
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Changing all values back to 0 or default
                amountEntered.setText("");
                paxEntered.setText("");
                svsBtn.setChecked(false);
                gstBtn.setChecked(false);
                discountEntered.setText("");
                paymentMethod.clearCheck();
                finalBill.setText("Total Bill: $0.00");
                eachPays.setText("Each Pays: $0.00");
            }
        });

    }
}