package com.example.stefano.gart;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class GridView2Activity extends AppCompatActivity {

    protected Cursor mCursor;
    protected int columnIndex;
    protected GridView mGridView;
    protected Image2Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view2);

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
        mGridView = (GridView) findViewById(R.id.gridView);
        mAdapter = new Image2Adapter(this);
        mGridView.setAdapter(mAdapter);




    }



    public class Image2Adapter extends BaseAdapter {

        private Context mContext;

        public Image2Adapter(Context context) {
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

        // Convert DP to PX
        // Source: http://stackoverflow.com/a/8490361
        public int dpToPx(int dps) {
            final float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (dps * scale + 0.5f);

            return pixels;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            int imageID = 0;

            // Want the width/height of the items
            // to be 120dp
            int wPixel = dpToPx(120);
            int hPixel = dpToPx(120);

            // Move cursor to current position
            mCursor.moveToPosition(position);
            // Get the current value for the requested column
            imageID = mCursor.getInt(columnIndex);

            if (convertView == null) {
                // If convertView is null then inflate the appropriate layout file
                convertView = LayoutInflater.from(mContext).inflate(R.layout.conversation_item, null);
            }
            else {

            }

            imageView = (ImageView) convertView.findViewById(R.id.imageView);

            // Set height and width constraints for the image view
            imageView.setLayoutParams(new LinearLayout.LayoutParams(wPixel, hPixel));

            // Set the content of the image based on the provided URI
            imageView.setImageURI(
                    Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + imageID)
            );

            // Image should be cropped towards the center
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Set Padding for images
            imageView.setPadding(8, 8, 8, 8);

            // Crop the image to fit within its padding
            imageView.setCropToPadding(true);

            return convertView;
        }
    }

}
