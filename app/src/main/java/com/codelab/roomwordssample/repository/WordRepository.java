package com.codelab.roomwordssample.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.codelab.roomwordssample.room.Word;
import com.codelab.roomwordssample.room.WordDao;
import com.codelab.roomwordssample.room.WordRoomDatabase;

import java.util.List;

@SuppressWarnings("ALL")
public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;


    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    // Pass word list from dao(room db) to the front layers(viewModel, views...).
    public LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }

    // TODO: Learn more about AsyncTask.(the latest alternatives since it's deprecated)
    public void insert (Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }



}
