package by.bstu.fit.lyolia.lab5_providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 14.03.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "College";
    static final String STUDENTS_TABLE_NAME = "students";
    private static final int DATABASE_VERSION = 3;
    private static final String CREATE_DB_TABLE =
            " CREATE TABLE " + STUDENTS_TABLE_NAME +
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " name TEXT NOT NULL, " +
                    " grade TEXT NOT NULL," +
                    " adress TEXT NOT NULL);";

    DatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
