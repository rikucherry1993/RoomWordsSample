package com.codelab.roomwordssample.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codelab.roomwordssample.R;
import com.codelab.roomwordssample.databinding.RecyclerviewItemBinding;
import com.codelab.roomwordssample.room.Word;

import java.util.List;

/**
 *  WordListAdapter
 */
public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {

    private static final String TAG = WordListAdapter.class.getSimpleName();
    private List<Word> mList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // view binding (class name = layout's name + suffix(=Binding))
        RecyclerviewItemBinding listBinding = DataBindingUtil.inflate(LayoutInflater
                .from(parent.getContext()), R.layout.recyclerview_item, parent, false);

        return new ViewHolder(listBinding);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (mList != null) {
            Word current = mList.get(position);
            holder.listBinding.textView.setText(current.getWord());
        } else {
            holder.listBinding.textView.setText("No Word");
        }
    }


    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }

        return mList.size();
    }


    public void setWords(List<Word> list) {
        mList = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        final RecyclerviewItemBinding listBinding;

        public ViewHolder(RecyclerviewItemBinding listBinding) {
            super(listBinding.getRoot());

            this.listBinding = listBinding;

        }
    }

}