package com.example.randomwordapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> list;
    TextView txt;
    Button btn;
    EditText et;
    int i=0;
    ListView listview;

    String ans = "";

    String charString;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();

        list.add("Pukar");
        list.add("Ujjwol");
        list.add("Nilesh");
        list.add("Gasutam");

        txt = findViewById(R.id.txt);
        btn = findViewById(R.id.btn);
        et = findViewById(R.id.et);
        et.setEnabled(false);

        listview = findViewById(R.id.listview);

        newLetter();

        preferences = getSharedPreferences("pref", MODE_PRIVATE);
        editor = preferences.edit();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (i < list.size()) {
                    if (et.getText().toString().equals(list.get(i))) {
                        et.setText("");
                        txt.setText(Shuffle(list.get(i)).toString());
                        i++;
                        ans="";
                        newLetter();
                        Toast.makeText(MainActivity.this, "CORRECT ANSWER", Toast.LENGTH_SHORT).show();

                        editor.putInt("level", i).commit();

                    }
                    else {
                        et.setText("Wrong Word");
                        et.setText("");
                        ans="";
                        Toast.makeText(MainActivity.this, "WRONG ANSWER. TRY AGAIN!!", Toast.LENGTH_SHORT).show();


                    }

                }

            }
        });
    }

    public void newLetter(){

        Character[] letters = Shuffle(list.get(i));

        ArrayAdapter<Character> arrayAdapter;
        arrayAdapter = new ArrayAdapter<Character> (this,R.layout.listview_layout, letters );

        listview.setAdapter(arrayAdapter);


        charString = Arrays.toString(letters);

//        txt.setText(Shuffle(list.get(i)).toString());
        txt.setText(charString);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ans += parent.getItemAtPosition(position).toString();
                et.setText(ans);
            }
        });


    }

    private Character[] Shuffle(String words){
        ArrayList<Character> chars = new ArrayList<Character>();
        for(char c : words.toCharArray()){
            chars.add(c);
        }
        Collections.shuffle(chars);
        Character[] shuffled = new Character[chars.size()];

        for (int i = 0; i<chars.size(); i++){
            shuffled[i] = chars.get(i);
        }

        return shuffled;

//        String shuffledWord = new String(shuffled);
//        return shuffledWord;
    }
}

