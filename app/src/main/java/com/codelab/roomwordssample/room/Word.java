package com.codelab.roomwordssample.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    @NonNull
    @ColumnInfo(name = "updateTime")
    private Long mUpdateTime;

    public Word(@NonNull String word, @NonNull Long updateTime) {
        this.mWord = word ;
        this.mUpdateTime = updateTime;
    }

    @Ignore
    public Word(int id, @NonNull String word, @NonNull Long mUpdateTime) {
        this.mWord = word ;
        this.id = id;
        this.mUpdateTime = mUpdateTime;
    }

    public String getWord() {
        return mWord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getUpdateTime() {
        return mUpdateTime;
    }

    public void setUpdateTime(Long UpdateTime) {
        this.mUpdateTime = UpdateTime;
    }


}
