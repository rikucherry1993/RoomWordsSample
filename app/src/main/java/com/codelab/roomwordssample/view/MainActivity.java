package com.codelab.roomwordssample.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codelab.roomwordssample.R;
import com.codelab.roomwordssample.databinding.ActivityMainBinding;
import com.codelab.roomwordssample.room.Word;
import com.codelab.roomwordssample.viewmodel.WordViewModel;

import static com.codelab.roomwordssample.Constant.NEW_WORD_ACTIVITY_REQUEST_CODE;

/**
 * MainActivity
 */
public class MainActivity extends AppCompatActivity {

    // binding object
    private ActivityMainBinding binding;

    // view model
    private WordViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // view binding instead of findViewById
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.toolbar);

        // add RecyclerView to MainActivity
        final WordListAdapter mAdapter = new WordListAdapter();
        binding.contentMain.recyclerview.setAdapter(mAdapter);
        binding.contentMain.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        // get a view model from ViewModelProviders class
        //Todo: learn more
        mWordViewModel = new ViewModelProvider(getViewModelStore(),
                getDefaultViewModelProviderFactory()).get(WordViewModel.class);

        // observe lifeData
        mWordViewModel.getAllWords().observe(this, words -> mAdapter.setWords(words));

        binding.fab.setOnClickListener(view1 -> {
            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });


        bindItemTouchHelper(mAdapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            mWordViewModel.insert(word);
        } else {
            Toast.makeText(getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_data) {
            Toast.makeText(this, "Clearing the data...",
                    Toast.LENGTH_SHORT).show();

            mWordViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // note: Swipe to delete!!!!
    private void bindItemTouchHelper(WordListAdapter adapter){
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Word wordToDelete = adapter.getWordAtPosition(position);

                        Toast.makeText(MainActivity.this, "Deleting "
                        + wordToDelete.getWord(), Toast.LENGTH_LONG).show();

                        mWordViewModel.deleteWord(wordToDelete);
                    }
                });
        helper.attachToRecyclerView(binding.contentMain.recyclerview);
    }

}