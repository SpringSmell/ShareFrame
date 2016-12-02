package com.future.sharelibrary.widgets;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.widget.EditText;

import com.future.sharelibrary.tools.EmojiUtils;

/**
 * Created by Administrator on 2016/6/23.
 */
public class EditTextEmojiView extends EditText {

    private Context mContext;

    public EditTextEmojiView(Context context){
        this(context,null);
    }

    public EditTextEmojiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }

    @Override
    public Editable getText() {
        return super.getText().append(toEmoji());
    }

    /**
     * 过滤表情符号
     * @return
     */
    public String toEmoji(){
        return EmojiUtils.emojiToUnicode(super.getText().toString());
    }
}
