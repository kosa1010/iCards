package com.example.kosa1010.icards.fragments;

import android.content.Intent;
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

import com.example.kosa1010.icards.EditCardActivity;
import com.example.kosa1010.icards.R;
import com.example.kosa1010.icards.model.Card;
import com.example.kosa1010.icards.repository.OrmCardsRepository;

/**
 * Created by kosa1010 on 15.03.17.
 */

public class EditCardFragment extends Fragment {

    private static ListView lvCardsToEdit;

    public EditCardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.edit_layout, container, false);
        lvCardsToEdit = (ListView) rootView.findViewById(R.id.lvCardsToEdit);
        lvCardsToEdit.setAdapter(new EditCardFragment.CardsListAdapter());
        return rootView;
    }

    public static void refreshList() {
        lvCardsToEdit.invalidateViews();
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
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_card_edit, null);
            }

            TextView name = (TextView) convertView.findViewById(R.id.cardName);
            ImageButton btnEdit = (ImageButton) convertView.findViewById(R.id.btnEdit);
            final Card item = getItem(position);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), EditCardActivity.class);
                    System.out.println("__________________________________________________________");
                    System.out.println(item.getId());
                    i.putExtra("CardId", String.valueOf(item.getId()));
                    startActivity(i);

                }
            });
//            Card item = getItem(position);
            name.setText(item.getName());
            return convertView;
        }
    }
}
