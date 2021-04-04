package com.codelab.roomwordssample.room;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

/**
 * WordDao
 */
@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    /**
     * Get data wrapped with LiveData<> from SQLite DB.
     * @return DataSource.Factory<Integer, Word>
     */
    @Query("SELECT * from word_table")
    DataSource.Factory<Integer,Word> getAllWords();

    //返回数组是为啥子？
    @Query("SELECT * from word_table LIMIT 1")
    Word[] getAnyWord();

    @Delete
    void deleteWord(Word word);

    @Update
    void updateWord(Word... word);

}
