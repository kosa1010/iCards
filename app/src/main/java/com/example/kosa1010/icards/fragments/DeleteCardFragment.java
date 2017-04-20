package com.example.kosa1010.icards.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kosa1010.icards.R;
import com.example.kosa1010.icards.model.Card;
import com.example.kosa1010.icards.repository.OrmCardsRepository;

/**
 * Created by kosa1010 on 15.03.17.
 */

public class DeleteCardFragment extends Fragment {

    public DeleteCardFragment() {
    }

    private ListView lvCardsToDel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.delete_layout, container, false);
        lvCardsToDel = (ListView) rootView.findViewById(R.id.lvCardsToDel);
        lvCardsToDel.setAdapter(new DeleteCardFragment.CardsListAdapter());

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
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_card_del, null);
            }

            TextView name = (TextView) convertView.findViewById(R.id.cardName);
            ImageButton btnDel = (ImageButton) convertView.findViewById(R.id.btnDelete);
            final Card item = getItem(position);
            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    OrmCardsRepository.deleteCard(getActivity(), item);
                                    lvCardsToDel.invalidateViews();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Czy jesteś pewien że chcesz usunąć " + item.getName() +
                            "?").setPositiveButton("Tak", dialogClickListener)
                            .setNegativeButton("Nie", dialogClickListener).show();
                }
            });
            name.setText(item.getName());
            return convertView;
        }
    }
}
