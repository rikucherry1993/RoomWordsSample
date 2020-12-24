package com.codelab.roomwordssample.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codelab.roomwordssample.R;
import com.codelab.roomwordssample.databinding.ActivityNewWordBinding;

public class NewWordActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "com.example.android.roomwordssample.REPLY";

    private ActivityNewWordBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewWordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.buttonSave.setOnClickListener(button -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(binding.editWord.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String word = binding.editWord.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, word);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}