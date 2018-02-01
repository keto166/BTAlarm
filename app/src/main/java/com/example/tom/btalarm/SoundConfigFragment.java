package com.example.tom.btalarm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class SoundConfigFragment extends Fragment {

    private ListView mainList;
    private DeviceListAdapter deviceListAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View temp = super.onCreateView(inflater, container, savedInstanceState);
        View rootView;
        try {
            rootView = inflater.inflate(R.layout.fragmented_list_layout, container, false);
        } catch (Exception e) {
            Toast.makeText(MainActivity.instance.getApplicationContext(), "Loading fragment", Toast.LENGTH_SHORT).show();
            return temp;
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            mainList = (ListView) getView().findViewById(R.id.d_listview);
            ArrayList<String> temp = new ArrayList<>();
            temp.add("HEYY");
            temp.add("HEYY");
            temp.add("HEYY");

            ArrayAdapter<String> testAA = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,temp);
            mainList.setAdapter(testAA);
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }

    public void updateListView() {
        deviceListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getContext(),"HEYYyy11, sound destroyed", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
