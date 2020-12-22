package com.muzasarali.dmwp1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddCourseTeachers extends AppCompatActivity {

    private Button btnStore;
    private EditText etcourse, etphone;
    AlertDialog.Builder builder;
    private DatabaseHelperTeacher databaseHelperTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_teachers);
        builder = new AlertDialog.Builder(this);
        databaseHelperTeacher = new DatabaseHelperTeacher(this);

        btnStore = (Button) findViewById(R.id.btnstore);
        final EditText etname = (EditText) findViewById(R.id.et_name);
        etcourse = (EditText) findViewById(R.id.et_course);
        final EditText etemail = (EditText) findViewById(R.id.et_email);
        etphone = (EditText) findViewById(R.id.et_phone);



        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etname.getText().toString();
                if (TextUtils.isEmpty(name)){
                    etname.setError("Enter Name");
                    etname.requestFocus();
                    return;
                }

                databaseHelperTeacher.addTeachersDetail(
                        etname.getText().toString(),
                        etcourse.getText().toString(),
                        etemail.getText().toString(),
                        etphone.getText().toString());

                etcourse.setText("");
                etphone.setText("");

                Toast.makeText(AddCourseTeachers.this, "Stored Successfully!", Toast.LENGTH_SHORT).show();

                showAlret("Data stored Successfully","Alert Box");
                Intent intent = new Intent(AddCourseTeachers.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    public void showAlret(String message, String title){
        builder.setMessage(message) .setTitle(title);

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to close this application ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("AlertDialogExample");
        alert.show();
    }
}