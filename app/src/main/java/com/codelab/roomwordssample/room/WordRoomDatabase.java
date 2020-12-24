package com.codelab.roomwordssample.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * WordRoomDatabase
 */
@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    // create the object as a singleton to prevent multiple DB accessing.
    private static WordRoomDatabase INSTANCE;

    public static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            // ★synchronized block --> synchronized (instance) : not allowed to be executed through multiple threads.
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    // TODO: Learn more.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .fallbackToDestructiveMigration() // ★migration strategy（销毁与创建）
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract WordDao wordDao();

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     * Populate the database in the background.
     */
    @Deprecated //Todo:alternatives?
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;
        // Initial words
        String[] words = {"Pancake","Cake","Cola"};

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database when it is first created
            mDao.deleteAll();

            for (int i = 0; i <= words.length - 1; i++) {
                Word word = new Word(words[i]);
                mDao.insert(word);
            }
            return null;
        }
    }


}
