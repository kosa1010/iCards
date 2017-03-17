package com.example.kosa1010.icards.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kosa1010.icards.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by kosa1010 on 15.03.17.
 */


public class AddCardFragment extends Fragment {

    public static TextView txtLogin;
    public static String codeContent;
    public static String codeFormat;
    private TextView txtPass;
    private TextView txtCardName;
    private Spinner spinner;
    private ImageButton scanQR;
    private Button btnAddCard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_card_layout, container, false);
        txtLogin = (TextView) view.findViewById(R.id.txtLogin);
        txtPass = (TextView) view.findViewById(R.id.txtPass);
        txtCardName = (TextView) view.findViewById(R.id.txtCardName);
        btnAddCard = (Button) view.findViewById(R.id.btnAddCard);
        btnAddCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Uzupełnić zapisem do bazy danych
            }
        });
        scanQR = (ImageButton) view.findViewById(R.id.scanCode);
        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            getView().findViewById(R.id.content_vitay);
//            Intent i = new Intent(getActivity(), VitayActivity.class);
//            startActivity(i);
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });
        spinner = (Spinner) view.findViewById(R.id.spinner);
        selectItem();
        return view;
    }

    public void selectItem() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (spinner.getSelectedItem() == spinner.getItemAtPosition(2)) {
                    txtLogin.setVisibility(View.GONE);
                    txtPass.setVisibility(View.INVISIBLE);
                    txtCardName.setVisibility(View.VISIBLE);
                } else {
                    txtLogin.setVisibility(View.VISIBLE);
                    txtPass.setVisibility(View.VISIBLE);
                    txtCardName.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                spinner.setSelection(0);
//                Toast.makeText(parentView, "nie ma nicz", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
