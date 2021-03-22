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
                    // ★register a database#Callback in the database builder
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

    // note: 每次都忘记这里为什么可以new！new的不是内部抽象类本身（抽象类不可以实例化），而是一个继承该抽象类的实现类！
    // note: 内部静态抽象类：顶层类不可以为static，此必为内部类
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };


    // note：这里拆分成继承、实例化两部分，和上面效果相同
//    private static class Callback extends RoomDatabase.Callback {
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db){
//            super.onOpen(db);
//            new PopulateDbAsync(INSTANCE).execute();
//        }
//    }
//
//    private static RoomDatabase.Callback sRoomDatabaseCallback = new Callback();

    /**
     * Populate the database in the background.
     */
    @Deprecated //Todo:alternatives?
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;
        // Initial words
        String[] words = {"Pancake","Cake","Cola"};

        // constructor
        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database when it is first created
//            mDao.deleteAll();

            // 只在db为空的时候插入默认数据
            if (mDao.getAnyWord().length < 1) {
                for (int i = 0; i <= words.length - 1; i++) {
                    Word word = new Word(words[i]);
                    mDao.insert(word);
                }
            }
            return null;
        }
    }


}
