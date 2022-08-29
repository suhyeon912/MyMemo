package com.example.mymemo;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class Fragment1 extends Fragment {
    EditText editDiary;
    Button btnWrite;

    String fileName; //저장
    String str; //임시저장

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        DatePicker dp = view.findViewById(R.id.datePicker);

        editDiary = view.findViewById(R.id.editDiary);
        btnWrite = view.findViewById(R.id.btnWrite);

        int year = dp.getYear();
        int month = dp.getMonth();
        int day = dp.getDayOfMonth();

        fileName = year + "_" + (month + 1) + "_" + day + ".txt";

        dp.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                fileName = year + "_" + (month + 1) + "_" + day + ".txt";
                Log.d("Today", fileName);

                str = readDiary(fileName);
                editDiary.setText(str);
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open
                try {
                    FileOutputStream outFs = getContext().openFileOutput(fileName, Context.MODE_PRIVATE);

                    //write
                    str = editDiary.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(getContext(), fileName + "에 저장했습니다.", Toast.LENGTH_SHORT).show();
                }


                //close
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        return view;
    }

    private String readDiary(String fileName) {
        //open, read and close

        FileInputStream inFs = null;
        try {
            inFs = getContext().openFileInput(fileName);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();

            str = new String(txt);

        } catch (IOException e) {
            str="";
            editDiary.setHint("일기 없음");
        }
        return str;
    }
}