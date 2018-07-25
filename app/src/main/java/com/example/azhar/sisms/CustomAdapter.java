package com.example.azhar.sisms;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.azhar.sisms.CursorAdapter;
import com.example.azhar.sisms.R;


public class CustomAdapter extends CursorAdapter<ConversationViewHolder> {

    public ConversationViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_element_text_view, viewGroup, false);

        return new ConversationViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ConversationViewHolder viewHolder, final int position) {
//        String result = (String) listOfContacts.keySet().toArray()[position];
//        String result2 = (String) listOfContacts.values().toArray()[position];
        // Get element from your dataset at this position and replace the contents of the view
        // with that
//        viewHolder.getHeadView().setText(result1.get(position));
//        Log.d(TAG + "position", Integer.toString(position));
//        viewHolder.getTextView().setText(result2.get(position));
        getItem(position);
        if(mCursor != null) {

            //viewHolder.thread_id = mCursor.getInt(mCursor.getColumnIndexOrThrow("thread_id"));
            viewHolder.thread_id = mCursor.getInt(0);
            //viewHolder.getHeadView().setText(mCursor.getString(mCursor.getColumnIndexOrThrow("address")));
            viewHolder.getHeadView().setText(mCursor.getString(1));
            //viewHolder.getTextView().setText(mCursor.getString(mCursor.getColumnIndexOrThrow("body")));
            viewHolder.getTextView().setText(mCursor.getString(4));

            //String imageUri = mCursor.getString(mCursor.getColumnIndexOrThrow("imageUri"));
            String imageUri = null;
            if(imageUri != null){
                viewHolder.getImageView().setImageURI(Uri.parse(imageUri));
            }else{
                viewHolder.getImageView().setImageResource(R.drawable.message_rectangle);
            }
        }else{
            viewHolder.getTextView().setText("null");
        }
    }

    public CustomAdapter(Cursor c){
        super(c);
    }
}

class ConversationViewHolder extends RecyclerView.ViewHolder {

    /*This one is supposed to be strictly to hold each conversation*/
    public ImageView getImageView() {
        return imageView;
    }
    public int thread_id;
    private final ImageView imageView;
    private final TextView textView;
    private final TextView headView;

    public ConversationViewHolder(View v) {
        super(v);
        //v.setOnClickListener(new ClickListener(this));
        // Define click listener for the ViewHolder's View.
        textView = (TextView) v.findViewById(R.id.textView);
        headView = (TextView) v.findViewById(R.id.textView2);
        imageView = (ImageView) v.findViewById(R.id.imageView);
    }

    public TextView getTextView() {
        return textView;
    }
    public TextView getHeadView() {
        return headView;
    }
}
