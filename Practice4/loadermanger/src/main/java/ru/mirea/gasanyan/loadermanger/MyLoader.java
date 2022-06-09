package ru.mirea.gasanyan.loadermanger;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MyLoader extends AsyncTaskLoader<String> {
    private String editText;
    public static final String ARG_WORD = "word";

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args != null)
            editText = args.getString(ARG_WORD);
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        StringBuilder line = new StringBuilder();
        ArrayList<String> chars = new ArrayList<>(Arrays.asList(editText.split("")));

        for (int i=0;i<editText.length();i++)
        {
            int j = (new Random()).nextInt(chars.size());
            if (j == 0) j++;
            line.append(chars.get(j));
            chars.remove(j);
        }
        return line.toString();
    }
}
