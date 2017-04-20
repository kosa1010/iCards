package com.example.kosa1010.icards.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kosa1010.icards.R;
import com.example.kosa1010.icards.ShowCardDetailsActivity;
import com.example.kosa1010.icards.model.Card;
import com.example.kosa1010.icards.repository.OrmCardsRepository;

/**
 * Created by kosa1010 on 15.03.17.
 */

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    FragmentManager fm;
    private ListView lvCards;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_layout, container, false);
        fm = getFragmentManager();
        lvCards = (ListView) rootView.findViewById(R.id.lvCards);
        lvCards.setAdapter(new CardsListAdapter());
        lvCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), ShowCardDetailsActivity.class);
                System.out.println("__________________________________________________________");
                System.out.println(id);
                System.out.println(position);
                i.putExtra("CardId",String.valueOf(id));
                startActivity(i);
            }
        });
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
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_card, null);
            }

            TextView name = (TextView) convertView.findViewById(R.id.cardName);
            Card item = getItem(position);
            name.setText(item.getName());
            return convertView;
        }
    }
}
