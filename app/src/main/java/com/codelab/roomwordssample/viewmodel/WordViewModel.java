package com.codelab.roomwordssample.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.codelab.roomwordssample.SortUtils;
import com.codelab.roomwordssample.repository.WordRepository;
import com.codelab.roomwordssample.room.Word;

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;
    private LiveData<PagedList<Word>> mAllWords;
    private DataSource.Factory<Integer, Word> mSourceList;

    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public LiveData<PagedList<Word>> getAllWords(SortUtils.SORT_KEY sortKey ) {

        switch (sortKey) {
            case TIME_DESC:
                mSourceList = mRepository.getAllWords().mapByPage(SortUtils.sortByTimeDesc);
                break;
            case TIME_ASC:
                mSourceList = mRepository.getAllWords().mapByPage(SortUtils.sortByTimeAsc);
                break;
            case ID_ASC:
                mSourceList = mRepository.getAllWords().mapByPage(SortUtils.sortByIdAsc);
                break;
            default:
                mSourceList = mRepository.getAllWords().mapByPage(SortUtils.sortByWordsAsc);
                break;
        }

        mAllWords = new LivePagedListBuilder<>(mSourceList,/*page size*/ 10)
                .setInitialLoadKey(1)
                .build();
        return mAllWords;
    }

    public void insert(Word word) {
        mRepository.insert(word);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteWord(Word word) {
        mRepository.deleteWord(word);
    }

    public void updateWord(Word word) { mRepository.updateWord(word);}

}
