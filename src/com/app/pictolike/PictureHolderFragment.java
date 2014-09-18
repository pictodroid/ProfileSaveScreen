package com.app.pictolike;

import com.app.pictolike.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class PictureHolderFragment extends Fragment implements View.OnTouchListener {

	int mCurrentPage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/** Getting the arguments to the Bundle object */
		Bundle data = getArguments();

		/** Getting integer data of the key current_page from the bundle */
		mCurrentPage = data.getInt("current_page", 0);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.pictureholderfragment_layout,
				container, false);

		ImageView Images = (ImageView) v.findViewById(R.id.likePic);
//
		if (mCurrentPage == 1) {

			Images.setImageResource(R.drawable.like_pic);
		} else if (mCurrentPage == 2) {

			Images.setImageResource(R.drawable.like_pic2);

		} else if (mCurrentPage == 3) {

			Images.setImageResource(R.drawable.like_pic3);

		} else if (mCurrentPage == 4) {

			Images.setImageResource(R.drawable.like_pic4);

		} else if (mCurrentPage == 5) {

			Images.setImageResource(R.drawable.like_pic);

		}

		return v;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
    	Toast.makeText(this.getActivity(), "Vertical", Toast.LENGTH_SHORT).show();

		return false;
	}
}
