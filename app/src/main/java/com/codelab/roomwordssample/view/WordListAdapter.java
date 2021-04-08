package com.codelab.roomwordssample.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codelab.roomwordssample.R;
import com.codelab.roomwordssample.databinding.RecyclerviewItemBinding;
import com.codelab.roomwordssample.room.Word;

/**
 *  WordListAdapter
 */
public class WordListAdapter extends PagedListAdapter<Word, WordListAdapter.ViewHolder> {

    private static final String TAG = WordListAdapter.class.getSimpleName();

    private MyOnClickListener mListener;

    public interface MyOnClickListener {
        void onClickItem(Word word);
    }

    public WordListAdapter(MyOnClickListener listener){
        super(DIFF_CALLBACK);
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // view binding (class name = layout's name + suffix(=Binding))
        RecyclerviewItemBinding listBinding = DataBindingUtil.inflate(LayoutInflater
                .from(parent.getContext()), R.layout.recyclerview_item, parent, false);

        return new ViewHolder(listBinding);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

            Word current = getItem(position);
            if (current != null) {
                holder.listBinding.textView.setText(current.getWord());

                //note:避开在adapter传递，而是改在activity传递
                holder.listBinding.wordItem.setOnClickListener(view -> {
                    mListener.onClickItem(current);
                });
            } else {
                holder.listBinding.textView.setText("No Word");
            }
    }

    public Word getWordAtPosition(int position) {
        return getItem(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        final RecyclerviewItemBinding listBinding;

        public ViewHolder(RecyclerviewItemBinding listBinding) {
            super(listBinding.getRoot());

            this.listBinding = listBinding;

        }
    }

    private static DiffUtil.ItemCallback<Word> DIFF_CALLBACK = new DiffUtil.ItemCallback<Word>() {
        @Override
        public boolean areItemsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
            return oldItem.getWord().equals(newItem.getWord());
        }
    };

}