package info.jakedavies.innav.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;


import info.jakedavies.innav.R;
import info.jakedavies.innav.adapter.SectionListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class SectionSelectFragment extends Fragment {

    final static String[] sections = {
            "Produce",
            "Deli",
            "Candy",
            "Bakery",
            "Drinks"
    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_section_select, container, false);

        //set location name
        String location = getArguments().getString("location_name");
        final TextView sectionName = (TextView) v.findViewById(R.id.location_name);
        sectionName.setText(location);

        // set up section list view
        final ListView sectionListView = (ListView) v.findViewById(R.id.sections);
        ArrayAdapter<String> sectionViewAdapter = new SectionListAdapter(this.getContext(), R.layout.section_item, sections);
        sectionListView.setAdapter(sectionViewAdapter);
        sectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Fragment newFragment = new BlindNavigationFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        transaction.replace(R.id.fragment_container, newFragment);
                        transaction.addToBackStack(null);

                        // Commit the transaction
                        transaction.commit();
            }
        });
        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
    }
    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
