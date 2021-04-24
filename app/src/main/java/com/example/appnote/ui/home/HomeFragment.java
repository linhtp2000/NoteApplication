package com.example.appnote.ui.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.NavigationUI;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.appnote.ContentMainNav;
import com.example.appnote.Database;
import com.example.appnote.MainActivity;
import com.example.appnote.Note;
import com.example.appnote.R;

import com.example.appnote.Status;
import com.example.appnote.ui.status.StatusFragment;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Database database;
    ArrayList<Status> status=new ArrayList<>();
    int[] statusofnotes= new int[100];
    int[] results= new int[100];
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ContentMainNav activity = (ContentMainNav) getActivity();
        database= activity.getMyData();

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
                   if(statusofnotes[j]==status.get(i).id)
                        temp++;
                }
                results[i]=temp;
            }
        for (int i=0;i<status.size();i++) {
            data.add(new ValueDataEntry(status.get(i).getName(), results[i]));
        }
      //  data.add(new ValueDataEntry("Linh", 1));
        pie.data(data);

        //String[] colors={"wines","pastal","monochrome"};
        //colors.

        anyChartView.setChart(pie);
        return root;

    }

    private void getDataNote()
    {
        String result = "";
        String query = "SELECT * FROM Notes where UserID ="+MainActivity.IDCurrent+"";
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int i=-1;
        while (cursor.moveToNext()) {
            i++;
            statusofnotes[i]= cursor.getInt(4);
        }
        cursor.close();
        db.close();
       // return name;
    }
    private void getDataStatus()
    {
        String query = "SELECT* FROM Status where UserID ="+MainActivity.IDCurrent+"";
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int id=cursor.getInt(0);
            String name=cursor.getString(1);
            String date=cursor.getString(2);
            status.add(new Status(id,name,date));
        }
        cursor.close();
        db.close();
        // return name;
    }
}



