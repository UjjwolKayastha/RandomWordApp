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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> list;
    TextView txt;
    Button btn;
    EditText et;
    int i;
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

//        list.add("Pukar");
//        list.add("Ujjwol");
//        list.add("Nilesh");
//        list.add("Gasutam");



        txt = findViewById(R.id.txt);
        btn = findViewById(R.id.btn);
        et = findViewById(R.id.et);
        et.setEnabled(false);

        listview = findViewById(R.id.listview);



        preferences = getSharedPreferences("pref", MODE_PRIVATE);
        editor = preferences.edit();

        i = getLastLevel();
        try {
            getWords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        newLetter();

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

    public int getLastLevel(){
        int level = preferences.getInt("level", 0);
        return level;
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

    private void getWords() throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = this.getResources().openRawResource(R.raw.words);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        if(inputStream != null){
            try {
                while ((charString = bufferedReader.readLine()) != null) {
                    list.add(charString);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            }
        }

}

