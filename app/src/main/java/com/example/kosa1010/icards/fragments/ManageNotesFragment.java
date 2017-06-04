package com.example.kosa1010.icards.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kosa1010.icards.EditCardActivity;
import com.example.kosa1010.icards.R;
import com.example.kosa1010.icards.ShowCardDetailsActivity;
import com.example.kosa1010.icards.model.Card;
import com.example.kosa1010.icards.repository.OrmCardsRepository;
import com.google.zxing.integration.android.IntentIntegrator;

/**
 * Created by kosa1010 on 15.03.17.
 */

public class ManageNotesFragment extends Fragment {

    public ManageNotesFragment() {
    }

    FragmentManager fm;
    private ListView lvCards;
    private String m_Text = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.notes_layout, container, false);
        fm = getFragmentManager();
        lvCards = (ListView) rootView.findViewById(R.id.lvCards);
        lvCards.setAdapter(new ManageNotesFragment.CardsListAdapter());
        return rootView;
    }

    private class CardsListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return OrmCardsRepository.findAll(getActivity()).size();
        }

        @Override
        public Card getItem(int position) {
            return OrmCardsRepository.findAll(getActivity()).get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_card_note, null);
            }
            ImageButton btnAdd = (ImageButton) convertView.findViewById(R.id.btnAdd);
            ImageButton btnDel = (ImageButton) convertView.findViewById(R.id.btnDel);
            TextView name = (TextView) convertView.findViewById(R.id.cardName);
            final Card item = getItem(position);
            if (item.getNote() == "" || item.getNote() == null) {
                btnDel.setVisibility(View.INVISIBLE);
                btnAdd.setVisibility(View.VISIBLE);
            } else {
                btnAdd.setVisibility(View.INVISIBLE);
                btnDel.setVisibility(View.VISIBLE);
            }

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Dodawanie notatki");
// Set up the input
                    final EditText input = new EditText(getActivity());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);
// Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_Text = input.getText().toString();
                            OrmCardsRepository.addNote(getActivity(), item, m_Text);
                            lvCards.invalidateViews();
                        }
                    });
                    builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            });

            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    OrmCardsRepository.deleteNote(getActivity(), item);
                                    lvCards.invalidateViews();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Czy jesteś pewien że chcesz usunąć notatkę z karty " + item.getName() +
                            "?").setPositiveButton("Tak", dialogClickListener)
                            .setNegativeButton("Nie", dialogClickListener).show();
                }
            });

            name.setText(item.getName());
            return convertView;
        }
    }
}
