package com.example.tickerwatchlist;

import android.icu.text.IDNA;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TickerListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TickerListFragment extends Fragment {

    ListView lv_ticker;
    ArrayAdapter<String> adapter;
    //String [] entries = {"PTON", "DIS", "JPM"};
    List<String> entries = new ArrayList<String>();
    InfoWebFragment infoWebFragment;
    String SMS;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TickerListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TickerListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TickerListFragment newInstance(String param1, String param2) {
        TickerListFragment fragment = new TickerListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        entries.add("PTON");
        entries.add("DIS");
        entries.add("JPM");
        View view = inflater.inflate(R.layout.fragment_ticker_list, container, false);
        lv_ticker = (ListView) view.findViewById(R.id.lv_tickerlist);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, entries);
        lv_ticker.setAdapter(adapter);
        lv_ticker.setClickable(true);

        lv_ticker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //int itemSelected = lv_ticker.getSelectedItemPosition();
                int itemSelected = position;

                String selectedTicker = entries.get(position);

                String link1 = "https://seekingalpha.com/symbol/";
                String finalLink = link1 + selectedTicker + "?source%3Dcontent_type%3Areact%7Csource%3Asearch-basic";
                infoWebFragment = (InfoWebFragment) getFragmentManager().findFragmentById(R.id.frag_web);
                infoWebFragment.openWebsite(finalLink);
                if (itemSelected == 0) {

                    Toast.makeText(getActivity().getApplicationContext(), "Selected item 0", Toast.LENGTH_SHORT).show();
                }
                else if (itemSelected == 1)
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Selected item 1", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Selected " + itemSelected, Toast.LENGTH_SHORT).show();
                }
            }

        });
        //validateSMS(SMS);
        return view;
    }

    public void validateSMS(String SMS)
    {
        String subFormat = "Ticker:<";
        char[] SMSChar = SMS.toCharArray();
        String SMSTicker = "";

        char lastChar = SMSChar[SMSChar.length - 1];
        //Toast.makeText(getActivity().getApplicationContext(), "Entries length: " + entries.size(), Toast.LENGTH_SHORT ).show();
        //Toast.makeText(getActivity().getApplicationContext(), "Inside validateSMS: " + SMS, Toast.LENGTH_LONG ).show();


        if (SMS.substring(0, 8).equals(subFormat)) // making sure it starts with "Ticker:<"
        {
            if (lastChar == '>') // making sure it ENDS with ">"
            {
                // correct formatting at this point
                // now check if the string has only letters
                for (char c: SMSChar)
                {
                    if (!Character.isLetter(c))
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "Ticker is invalid", Toast.LENGTH_SHORT ).show();
                    }
                }
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(), "No valid watchlist entry was found.", Toast.LENGTH_SHORT ).show();
            }

            SMSTicker = SMS.substring(8, SMSChar.length - 1).toUpperCase();

            //Toast.makeText(getActivity().getApplicationContext(), "Entries length: " + entries.size(), Toast.LENGTH_SHORT ).show();

            // ---------------------------- ISSUES HERE -------------------------------
            if (entries.size() == 5)
            {
                Toast.makeText(getActivity().getApplicationContext(), "Inside IF", Toast.LENGTH_SHORT ).show();
                entries.set(4, SMSTicker); // replacing last item in list
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(), "Inside ELSE", Toast.LENGTH_SHORT ).show();
                entries.add(SMSTicker);
            }

            Toast.makeText(getActivity().getApplicationContext(), "Entries length: " + entries.size(), Toast.LENGTH_SHORT ).show();
            //Toast.makeText(getActivity().getApplicationContext(), "Ticker: " + SMSTicker, Toast.LENGTH_SHORT ).show();


        }
        else
        {
            Toast.makeText(getActivity().getApplicationContext(), "No valid watchlist entry was found.", Toast.LENGTH_SHORT ).show();
        }

        String link1 = "https://seekingalpha.com/symbol/";
        String finalLink = link1 + SMSTicker + "?source%3Dcontent_type%3Areact%7Csource%3Asearch-basic";
        infoWebFragment = (InfoWebFragment) getFragmentManager().findFragmentById(R.id.frag_web);
        infoWebFragment.openWebsite(finalLink);

    }


}