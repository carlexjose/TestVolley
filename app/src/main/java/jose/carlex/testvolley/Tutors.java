package jose.carlex.testvolley;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Carlex on 5/1/2017.
 */
public class Tutors extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<TutorPost> arrayList = new ArrayList<>();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        BackgroundTaskTutor backgroundTaskTutor = new BackgroundTaskTutor(getActivity());
        arrayList = backgroundTaskTutor.getList();

        //Toast.makeText(getContext(), arrayList.get(0).getPrice(),Toast.LENGTH_SHORT).show();

        adapter = new RecyclerAdapter(arrayList, getContext());
        recyclerView.setAdapter(adapter);

        getActivity().setTitle("Tutors");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_tutors, container, false);
    }


}
