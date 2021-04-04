package com.codelab.roomwordssample;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.arch.core.util.Function;

import com.codelab.roomwordssample.room.Word;

import java.util.Comparator;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class SortUtils {

    public enum SORT_KEY {
        DEFAULT, TIME_DESC, TIME_ASC, WORD_ASC, ID_ASC
    }

    public static Function<List<Word>, List<Word>> sortByTimeDesc = words -> {
        words.sort(Comparator.comparing(Word::getUpdateTime).reversed());
        return words;
    };

    public static Function<List<Word>, List<Word>> sortByTimeAsc = words -> {
        words.sort(Comparator.comparing(Word::getUpdateTime));
        return words;
    };

    public static Function<List<Word>, List<Word>> sortByWordsAsc = words -> {
        words.sort(Comparator.comparing(Word::getWord));
        return words;
    };

    public static Function<List<Word>, List<Word>> sortByIdAsc = words -> {
        words.sort(Comparator.comparing(Word::getId));
        return words;
    };

}
