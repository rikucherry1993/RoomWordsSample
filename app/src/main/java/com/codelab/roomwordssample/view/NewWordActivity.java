package com.codelab.roomwordssample.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.codelab.roomwordssample.Constant;
import com.codelab.roomwordssample.databinding.ActivityNewWordBinding;

import static com.codelab.roomwordssample.Constant.EXTRA_OLD_WORD;
import static com.codelab.roomwordssample.Constant.EXTRA_REPLY;

public class NewWordActivity extends AppCompatActivity {


    private ActivityNewWordBinding binding;
    private String receivedWord;

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
        receivedWord = null;
        receivedWord = getIntent().getStringExtra(Constant.EDITABLE_WORD);
        if (!TextUtils.isEmpty(receivedWord)) {
            binding.editWord.setText(receivedWord);
        }

        onSave();

    }

    private void onSave(){
        binding.buttonSave.setOnClickListener(button -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(binding.editWord.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String word = binding.editWord.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, word);
                replyIntent.putExtra(EXTRA_OLD_WORD, receivedWord);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }

}