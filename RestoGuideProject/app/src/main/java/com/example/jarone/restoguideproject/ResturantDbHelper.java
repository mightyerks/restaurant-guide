package com.example.jarone.restoguideproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ResturantDbHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "resturant.db";

    public ResturantDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ResturantContract.SQL_CREATE_POSTS);
        Log.d("DB-TEST", "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ResturantContract.SQL_DROP_POSTS);
        onCreate(db);
        Log.d("DB-TEST", "Database upgraded");
    }

    public long addPost(SQLiteDatabase db, Resturant post) {
        ContentValues values = new ContentValues();
        values.put(ResturantContract.PostEntry.COL_NAME_NAME, post.getName());
        values.put(ResturantContract.PostEntry.COL_NAME_ADDRESS, post.getAddress());
        values.put(ResturantContract.PostEntry.COL_NAME_PHONE, post.getPhone());
        values.put(ResturantContract.PostEntry.COL_NAME_DESCRIPTION, post.getDescription());
        values.put(ResturantContract.PostEntry.COL_NAME_TAGS, post.getTags());
        values.put(ResturantContract.PostEntry.COL_NAME_RATING, post.getRating());

        return db.insert(ResturantContract.PostEntry.TABLE_NAME, null, values);
    }

    public int updatePost(SQLiteDatabase db, Resturant post, int id) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(ResturantContract.PostEntry.COL_NAME_NAME, post.getName());
        updateValues.put(ResturantContract.PostEntry.COL_NAME_ADDRESS, post.getAddress());
        updateValues.put(ResturantContract.PostEntry.COL_NAME_PHONE, post.getPhone());
        updateValues.put(ResturantContract.PostEntry.COL_NAME_DESCRIPTION, post.getDescription());
        updateValues.put(ResturantContract.PostEntry.COL_NAME_TAGS, post.getTags());
        updateValues.put(ResturantContract.PostEntry.COL_NAME_RATING, post.getRating());

        return db.update(ResturantContract.PostEntry.TABLE_NAME, updateValues, ResturantContract.PostEntry._ID + "=" + id, null);
    }

    public int deletePost(SQLiteDatabase db, int id) {
        return db.delete(ResturantContract.PostEntry.TABLE_NAME, ResturantContract.PostEntry._ID + "=" + id, null);
    }

    public Resturant getPost(SQLiteDatabase db, int postId) {
        String[] projection = {
                ResturantContract.PostEntry._ID,
                ResturantContract.PostEntry.COL_NAME_NAME,
                ResturantContract.PostEntry.COL_NAME_ADDRESS,
                ResturantContract.PostEntry.COL_NAME_PHONE,
                ResturantContract.PostEntry.COL_NAME_DESCRIPTION,
                ResturantContract.PostEntry.COL_NAME_TAGS,
                ResturantContract.PostEntry.COL_NAME_RATING
        };

        String selection = ResturantContract.PostEntry._ID + "= ? ";
        String[] selectionArgs = {Integer.toString(postId)};
        Cursor cursor = db.query(
                ResturantContract.PostEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ResturantContract.PostEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ResturantContract.PostEntry.COL_NAME_NAME));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(ResturantContract.PostEntry.COL_NAME_ADDRESS));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(ResturantContract.PostEntry.COL_NAME_PHONE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(ResturantContract.PostEntry.COL_NAME_DESCRIPTION));
            String tags = cursor.getString(cursor.getColumnIndexOrThrow(ResturantContract.PostEntry.COL_NAME_TAGS));
            float rating = cursor.getFloat(cursor.getColumnIndexOrThrow(ResturantContract.PostEntry.COL_NAME_RATING));

            Resturant post = new Resturant(id, name, address, phone, description, tags, rating);
            return post;
        }

        return null;
    }

    public Resturant getPost(SQLiteDatabase db, String qName) {
        String[] projection = {
                ResturantContract.PostEntry._ID,
                ResturantContract.PostEntry.COL_NAME_NAME,
                ResturantContract.PostEntry.COL_NAME_ADDRESS,
                ResturantContract.PostEntry.COL_NAME_PHONE,
                ResturantContract.PostEntry.COL_NAME_DESCRIPTION,
                ResturantContract.PostEntry.COL_NAME_TAGS,
                ResturantContract.PostEntry.COL_NAME_RATING
        };

        String selection = ResturantContract.PostEntry.COL_NAME_NAME + "= ? ";
        String[] selectionArgs = {qName};
        Cursor cursor = db.query(
                ResturantContract.PostEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ResturantContract.PostEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ResturantContract.PostEntry.COL_NAME_NAME));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(ResturantContract.PostEntry.COL_NAME_ADDRESS));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(ResturantContract.PostEntry.COL_NAME_PHONE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(ResturantContract.PostEntry.COL_NAME_DESCRIPTION));
            String tags = cursor.getString(cursor.getColumnIndexOrThrow(ResturantContract.PostEntry.COL_NAME_TAGS));
            float rating = cursor.getFloat(cursor.getColumnIndexOrThrow(ResturantContract.PostEntry.COL_NAME_RATING));

            Resturant post = new Resturant(id, name, address, phone, description, tags, rating);
            return post;
        }

        return null;
    }

}
//
//    /////////////////// FOR SEARCH
//    private void loadDictionary() {
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    loadWords();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }).start();
//    }
//
//    private void loadWords() throws IOException {
//        final Resources resources = mHelperContext.getResources();
//        InputStream inputStream = resources.openRawResource(R.raw.definitions);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//
//        try {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] strings = TextUtils.split(line, "-");
//                if (strings.length < 2) continue;
//                long id = addWord(strings[0].trim(), strings[1].trim());
//                if (id < 0) {
//                    Log.e(TAG, "unable to add word: " + strings[0].trim());
//                }
//            }
//        } finally {
//            reader.close();
//        }
//    }
//
//    public long addWord(String word, String definition) {
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(COL_WORD, word);
//        initialValues.put(COL_DEFINITION, definition);
//
//        return mDatabase.insert(FTS_VIRTUAL_TABLE, null, initialValues);
//    }
//
//
//    public Cursor getWordMatches(String query, String[] columns) {
//        String selection = COL_WORD + " MATCH ?";
//        String[] selectionArgs = new String[] {query+"*"};
//
//        return query(selection, selectionArgs, columns);
//    }
//
//    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
//        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
//        builder.setTables(FTS_VIRTUAL_TABLE);
//
//        Cursor cursor = builder.query(mDatabaseOpenHelper.getReadableDatabase(),
//                columns, selection, selectionArgs, null, null, null);
//
//        if (cursor == null) {
//            return null;
//        } else if (!cursor.moveToFirst()) {
//            cursor.close();
//            return null;
//        }
//        return cursor;
//    }

