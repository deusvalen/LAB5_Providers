package by.bstu.fit.lyolia.lab5_providers;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Objects;

import static by.bstu.fit.lyolia.lab5_providers.DatabaseHelper.STUDENTS_TABLE_NAME;

/**
 * Created by User on 14.03.2018.
 */

public class StudentsProvider extends android.content.ContentProvider {

    static final String PROVIDER_NAME = "by.bstu.fit.lyolia.lab5_providers.College";
    static final String URL = "content://" + PROVIDER_NAME + "/students";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "id";
    static final String NAME = "name";
    static final String GRADE = "grade";
    static final String ADRESS = "adress";

    private static HashMap<String,String> STUDENTS_PROJECTION_MAP;

    static final int STUDENTS = 1;
    static final int STUDENTS_ID = 2;

    static  final UriMatcher uriMatcher;

    private SQLiteDatabase db;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "students", STUDENTS);
        uriMatcher.addURI(PROVIDER_NAME, "students/#", STUDENTS_ID);

    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return db != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(STUDENTS_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case STUDENTS: {
                qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
                break;
            }
            case STUDENTS_ID: {
                qb.appendWhere(_ID + "=" + uri.getPathSegments().get(1));
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown Uri" + uri);
            }
        }
        if(s1 == null || Objects.equals(s1, "")) {
            s1 = NAME;
        }
        Cursor c = qb.query(db, strings, s, strings1, null, null, s1);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case STUDENTS: {
                return "vnd.android.cursor.dir/vnd.lab5.students";
            }
            case STUDENTS_ID: {
                return "vnd.android.cursor.item/vnd.lab5.students";
            }
            default: {
                throw new IllegalArgumentException("Unsupported URI" + uri);
            }
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long rowId = db.insert(STUDENTS_TABLE_NAME, "", contentValues);

        if(rowId > 0) {
            Uri uri1 = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(uri1, null);
            return  uri1;
        }
        throw  new SQLException("Failed to add a record into " + uri );
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        int count = 0;

        switch (uriMatcher.match(uri)) {
            case STUDENTS : {
                count = db.delete(STUDENTS_TABLE_NAME, s, strings);
                break;
            }
            case STUDENTS_ID : {
                String id = uri.getPathSegments().get(1);
                count = db.delete(STUDENTS_TABLE_NAME, _ID + " = " + id +
                        (!TextUtils.isEmpty(s) ? " AND (" + s + ")" : ""), strings);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {

        int count = 0;

        switch (uriMatcher.match(uri)) {

            case STUDENTS: {
                count = db.update(STUDENTS_TABLE_NAME, contentValues, s, strings);
                break;
            }
            case  STUDENTS_ID: {
                count = db.update(STUDENTS_TABLE_NAME, contentValues, _ID +
                " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(s) ? " AND (" +
                                s + ")" : ""), strings);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown URI" + uri);
            }
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

}
