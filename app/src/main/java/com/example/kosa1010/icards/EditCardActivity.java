package com.example.kosa1010.icards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kosa1010.icards.model.Card;
import com.example.kosa1010.icards.repository.OrmCardsRepository;
import com.google.zxing.integration.android.IntentIntegrator;
import com.example.kosa1010.icards.fragments.EditCardFragment;
import com.google.zxing.integration.android.IntentResult;

public class EditCardActivity extends AppCompatActivity {

    public EditCardActivity() {
    }

    long id;
    public TextView txtLogin;
    public static String codeContent;
    public static String codeFormat;
    private TextView txtPass;
    private TextView txtCardName;
    private Spinner spinner;
    private ImageButton scanQR;
    private Button saveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);
        id = Long.parseLong(getIntent().getStringExtra("CardId"));
        final Card c = OrmCardsRepository.findById(this, id);
        setTitle("Edycja katry " + c.getName());
        txtLogin = (TextView) findViewById(R.id.txtLoginEdit);
        txtPass = (TextView) findViewById(R.id.txtPassEdit);
        txtCardName = (TextView) findViewById(R.id.txtCardNameEdit);
        txtCardName.setText(c.getName());
        codeContent = c.getCode();
        codeFormat = c.getType();
        saveChanges = (Button) findViewById(R.id.btnSaveChanges);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateCard(c);
                EditCardFragment.refreshList();
            }
        });
        scanQR = (ImageButton) findViewById(R.id.scanCodeEdit);
        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(EditCardActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });
        spinner = (Spinner) findViewById(R.id.spinnerEdit);
        spinner.setSelection(spinner.getCount() - 1);
        for (int i = 0; i < spinner.getCount(); i++) {
            if (c.getName() == spinner.getItemAtPosition(i).toString()
                    && spinner.getItemAtPosition(i).toString() != "Inne") {
                spinner.setSelection(i);
            }
        }
        selectItem();
    }

    public void selectItem() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                if (spinner.getSelectedItem() == spinner.getItemAtPosition(2)) {
//                    txtLogin.setVisibility(View.GONE);
//                    txtPass.setVisibility(View.INVISIBLE);
//                    txtCardName.setVisibility(View.VISIBLE);
//                } else {
//                    txtLogin.setVisibility(View.VISIBLE);
//                    txtPass.setVisibility(View.VISIBLE);
//                    txtCardName.setVisibility(View.GONE);
//                }
                if (spinner.getSelectedItemPosition() == 2) {
                    txtLogin.setVisibility(View.GONE);
                    txtPass.setVisibility(View.INVISIBLE);
                    txtCardName.setVisibility(View.VISIBLE);
                } else {
                    txtLogin.setVisibility(View.GONE);
                    txtPass.setVisibility(View.INVISIBLE);
                    txtCardName.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                spinner.setSelection(0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        codeContent = scanningResult.getContents();
        codeFormat = scanningResult.getFormatName();
    }

    private void updateCard(Card cardToEdit) {
        cardToEdit.setName(txtCardName.getText().toString());
        if (spinner.getSelectedItemPosition() != 2) {
            txtCardName.setText(spinner.getSelectedItem().toString());
        }
        cardToEdit.setPass(txtPass.getText().toString());
        cardToEdit.setLogin(txtLogin.getText().toString());
        cardToEdit.setType(codeFormat);
        cardToEdit.setCode(codeContent);

        OrmCardsRepository.updateCard(getBaseContext(), cardToEdit);
        Toast.makeText(this, "Katra została zaktualizowana", Toast.LENGTH_SHORT).show();
        txtCardName.setText("");
    }
}
