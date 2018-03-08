package com.android.rivchat.adapter;

import android.support.v7.util.SortedList;

import com.android.rivchat.model.Conversation;

/**
 * Created by mark on 3/8/2018.
 */

public class MessageAdapter extends SortedList<Conversation>{
    public MessageAdapter(Class<Conversation> klass, Callback<Conversation> callback) {
        super(klass, callback);
    }
}
