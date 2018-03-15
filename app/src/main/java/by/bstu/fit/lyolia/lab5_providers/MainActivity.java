package by.bstu.fit.lyolia.lab5_providers;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText nameEt, gradeEt, adressEt;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEt = (EditText) findViewById(R.id.nameEt);
        gradeEt = (EditText) findViewById(R.id.gradeEt);
        adressEt = (EditText) findViewById(R.id.adressEt);
        listView = (ListView) findViewById(R.id.listView);
    }

    public void onAddClick(View view) {
        ContentValues values = new ContentValues();

        String name = nameEt.getText().toString();
        String grade = gradeEt.getText().toString();
        String adress = adressEt.getText().toString();

        if(!name.isEmpty() && !grade.isEmpty() && !adress.isEmpty()) {

            values.put(StudentsProvider.NAME, name);
            values.put(StudentsProvider.GRADE, grade);
            values.put(StudentsProvider.ADRESS, adress);

            Uri uri = getContentResolver().insert(StudentsProvider.CONTENT_URI, values);

            if (uri != null) {
                Toast.makeText(this.getApplicationContext(), uri.toString(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRetrieveClick(View view) {
        List<Student> studentsList = new ArrayList<>();
        String URL = StudentsProvider.URL;
        Uri students = Uri.parse(URL);
        Cursor c = getContentResolver().query(students, null, null , null, "name" );

        if (c != null && c.moveToFirst()) {
            do {
                studentsList.add(new Student(
                        c.getString(c.getColumnIndex(StudentsProvider.NAME)),
                        c.getString(c.getColumnIndex(StudentsProvider.GRADE)),
                        c.getString(c.getColumnIndex(StudentsProvider.ADRESS))
                ));
            } while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }

        listView.setAdapter(new ListAdapter(this, studentsList));

    }
}
