package dominando.android.runfastapp.metas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dominando.android.runfastapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MetasFragment extends Fragment {


    public MetasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_metas, container, false);
    }

}
