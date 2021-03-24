package com.codelab.roomwordssample.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.codelab.roomwordssample.Constant;
import com.codelab.roomwordssample.databinding.ActivityNewWordBinding;

import static com.codelab.roomwordssample.Constant.EXTRA_DATA_ID;
import static com.codelab.roomwordssample.Constant.EXTRA_REPLY;
import static com.codelab.roomwordssample.Constant.EXTRA_REPLY_ID;

public class NewWordActivity extends AppCompatActivity {


    private ActivityNewWordBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewWordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    @Override
    public void onResume(){
        super.onResume();
        // 显示用户点击的单词
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String receivedWord = extras.getString(Constant.EDITABLE_WORD, "");
            if (!receivedWord.isEmpty()) {
                binding.editWord.setText(receivedWord);
                binding.editWord.setSelection(receivedWord.length()); //note: nice-to-have
                binding.editWord.requestFocus();
            }
        }

        onSave(extras);

    }

    private void onSave(Bundle extras){
        binding.buttonSave.setOnClickListener(button -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(binding.editWord.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String word = binding.editWord.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, word);

                if (extras != null && extras.containsKey(EXTRA_DATA_ID)) {
                    int id = extras.getInt(EXTRA_DATA_ID, -1);
                    if (id != -1) {
                        replyIntent.putExtra(EXTRA_REPLY_ID, id);
                    }
                }
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }

}