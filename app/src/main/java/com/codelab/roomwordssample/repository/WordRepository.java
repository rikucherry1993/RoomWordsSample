package com.codelab.roomwordssample.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.paging.DataSource;

import com.codelab.roomwordssample.room.Word;
import com.codelab.roomwordssample.room.WordDao;
import com.codelab.roomwordssample.room.WordRoomDatabase;

@SuppressWarnings("ALL")
public class WordRepository {

    private WordDao mWordDao;
    private DataSource.Factory<Integer, Word> mSourceList;

    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mSourceList = mWordDao.getAllWords();
    }

    // Pass word list from dao(room db) to the front layers(viewModel, views...).
    public DataSource.Factory<Integer,Word> getAllWords(){
        return mSourceList;
    }

    // insert one word
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


    // delete all
    public void deleteAll() {
        new deleteAllWordsAsyncTask(mWordDao).execute();
    }

    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }


    // delete one word
    public void deleteWord(Word word){
        new deleteWordAsyncTask(mWordDao).execute(word);
    }

    private static class deleteWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;

        deleteWordAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }


    // update word
    public void updateWord(Word word){
        new updateWordAsyncTask(mWordDao).execute(word);
    }

    private static class updateWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;

        updateWordAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.updateWord(params[0]);
            return null;
        }
    }


}
