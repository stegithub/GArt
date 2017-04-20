package com.example.stefano.gart;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

//www.101apps.co.za
public class PicassoGalleryActivity extends Activity {


    protected Cursor mCursor;
    protected int columnIndex;
    protected GridView mGridView;
    protected ImagePicAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso_gallery);


        // Get all the images on phone
        String[] projection = {
                MediaStore.Images.Thumbnails._ID,
                MediaStore.Images.Thumbnails.IMAGE_ID
        };
        mCursor = getContentResolver().query(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.Thumbnails.IMAGE_ID + " DESC"
        );

        columnIndex = mCursor.getColumnIndexOrThrow(projection[0]);

        // Get the GridView layout
        mGridView = (GridView) findViewById(R.id.gridview);
        mAdapter = new ImagePicAdapter(this);
        mGridView.setAdapter(mAdapter);


//        GridView gridview = (GridView) findViewById(R.id.gridview);
//        gridview.setAdapter(new ImageAdapter(this));

        /*gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PicassoGalleryActivity.this, ActivityTwo.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });*/
    }

    //    our custom adapter
    private class ImagePicAdapter extends BaseAdapter {
        private Context mContext;

        public ImagePicAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return mCursor.getCount();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;


            int imageID = 0;

            // Move cursor to current position
            mCursor.moveToPosition(position);
            // Get the current value for the requested column
            imageID = mCursor.getInt(columnIndex);


//            check to see if we have a view
            if (convertView == null) {
//                no view - so create a new one
                imageView = new ImageView(mContext);
            } else {
//                use the recycled view object
                imageView = (ImageView) convertView;
            }

//            Picasso.with(MainActivity.this).setDebugging(true);
            /*Picasso.with(PicassoGalleryActivity.this)
                    .load(mThumbIds[position])
                    .placeholder(R.raw.place_holder)
                    .error(R.raw.big_problem)
                    .noFade().resize(150, 150)
                    .centerCrop()
                    .into(imageView);
            return imageView;*/





            Uri uri = Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + imageID);
                    Picasso.with(PicassoGalleryActivity.this).load(uri)
                            .resize(150, 150).centerCrop().into(imageView);
             return imageView;

        }
    }
}
