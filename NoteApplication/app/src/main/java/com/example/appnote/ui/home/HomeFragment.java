package com.example.appnote.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.appnote.ContentMainNav;
import com.example.appnote.MainActivity;
import com.example.appnote.R;

import com.example.appnote.ui.Database.Entity.Status;
import com.example.appnote.ui.Database.NoteDatabase;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
   // Database database;
    List<Status> status=new ArrayList();
    int[] statusofnotes= new int[100];
    int[] results= new int[100];
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ContentMainNav activity = (ContentMainNav) getActivity();
       // database= activity.getMyData();

    //    final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                getDataNote(1,"Note");
//                textView.setText(statusofnotes[0]);
//            }
//
//
//            thongke[i]=temp;
//        }
        getDataNote();
        getDataStatus();
      final AnyChartView anyChartView = root.findViewById(R.id.anyChartView);
        Pie pie= AnyChart.pie();
        List<DataEntry> data = new ArrayList<>();
          for (int i=0;i<status.size();i++)
          {
              int temp=0;
                for(int j=0;j<statusofnotes.length;j++)
                {
                   if(statusofnotes[j]==status.get(i).getId())
                        temp++;
                }
                results[i]=temp;
            }
        for (int i=0;i<status.size();i++) {
            data.add(new ValueDataEntry(status.get(i).getName(), results[i]));
        }

        pie.data(data);

        anyChartView.setChart(pie);
        return root;

    }

    private void getDataNote()
    {
       // String result = "";
       statusofnotes= NoteDatabase.getInstance(getActivity()).getNoteDao().getStatusId(MainActivity.IDCurrent);
    }
    private void getDataStatus()
    {
       status=NoteDatabase.getInstance(getActivity()).getStatusDao().getAll(MainActivity.IDCurrent);
    }
}



