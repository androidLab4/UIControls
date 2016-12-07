package com.test.uicontrols;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    TextView textView;
    EditText editText;
    ImageButton btnStartProgress;
    ProgressBar progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    private long fileSize = 0;

    AutoCompleteTextView autocomplete;
    String[] arr = { "Paries,France", "PA,United States","Parana,Brazil",
            "Padua,Italy", "Pasadena,CA,United States"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartProgress = (ImageButton) findViewById(R.id.imageButton1);
        editText = (EditText) findViewById(R.id.editText1);

        autocomplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, arr);
        autocomplete.setThreshold(2);
        autocomplete.setAdapter(adapter);


        textView = ((TextView) findViewById(R.id.textView1));
        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "TextView Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        addListenerOnButton();

        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {   //Convert the Text to String
                String inputText = editText.getText().toString();
                textView.setText(inputText);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Does not do any thing in this case
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Does not do any thing in this case
            }
        });

    }

    public void clickButton(View v) {
        Intent intent = new Intent(getApplicationContext(), ActivityTwo.class);
        startActivity(intent);
    }

    public void addListenerOnButton() {

        btnStartProgress = (ImageButton) findViewById(R.id.imageButton1);
        btnStartProgress.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        // prepare for a progress bar dialog
                        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
                        progressBar.setProgress(0);
                        progressBar.setMax(100);

                        //reset progress bar status
                        progressBarStatus = 0;

                        //reset filesize
                        fileSize = 0;

                        new Thread(new Runnable() {
                            public void run() {

                                while (progressBarStatus < 100) {

                                    // process some tasks
                                    progressBarStatus = doSomeTasks();

                                    // your computer is too fast, sleep 1 second
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    // Update the progress bar
                                    progressBarHandler.post(new Runnable() {
                                        public void run() {
                                            progressBar.setProgress(progressBarStatus);
                                        }
                                    });
                                }

                                // ok, file is downloaded,
                                if (progressBarStatus >= 100) {

                                    // sleep 2 seconds, so that you can see the 100%
                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();

                    }

                });

    }

    // file download simulator... a really simple
    public int doSomeTasks() {

        while (fileSize <= 1000000) {

            fileSize++;

            if (fileSize == 100000) {
                return 10;
            } else if (fileSize == 200000) {
                return 20;
            } else if (fileSize == 300000) {
                return 30;
            }
            // ...add your own

        }

        return 100;

    }

}
