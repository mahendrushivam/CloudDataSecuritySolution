package com.example.shivam.datcompressorfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class SampleAdapter extends BaseExpandableListAdapter {

	private final Context mContext;
	private final LayoutInflater mLayoutInflater;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
public  static final String MEMORYCHOICE2="MEMORYCHOICE2";
	private final String[] mGroups = {
			"Memory Type",
			"Progress Bar ",


	};

	private final int[] mGroupDrawables = {
			R.mipmap.ic_launcher,
			R.mipmap.ic_launcher};
	int check1=0,check2=0,check3=0;
int pos;
	private final String[][] mChilds = {
			{"Internal Storage","External Storage","RAM Information"},
			{}};

	public SampleAdapter(Context context,int pos) {
		mContext = context;
		this.pos=pos;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getGroupCount() {
		return mGroups.length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mGroups[groupPosition];
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if(convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.sample_activity_list_group_item, parent, false);
		}

		//final ImageView image = (ImageView) convertView.findViewById(R.id.sample_activity_list_group_item_image);


		final TextView text = (TextView) convertView.findViewById(R.id.sample_activity_list_group_item_text);
		text.setText(mGroups[groupPosition]);
		
		final ImageView expandedImage = (ImageView) convertView.findViewById(R.id.sample_activity_list_group_expanded_image);
		final int resId = isExpanded ? R.drawable.uparrow :R.drawable.arrow ;
		expandedImage.setImageResource(resId);

		if(getChildrenCount(groupPosition)>0)
		{
			expandedImage.setVisibility(View.VISIBLE);
		}
		else
		{
			expandedImage.setVisibility(View.INVISIBLE);

		}

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mChilds[groupPosition].length;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mChilds[groupPosition][childPosition];
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		//check3=check2=check1=0;
		if (groupPosition==0) {
			convertView = mLayoutInflater.inflate(R.layout.group1layout, parent, false);
		}
		else
		{
			convertView = mLayoutInflater.inflate(R.layout.sample_activity_list_child_item, parent, false);
		}

		if (groupPosition != 0) {
			final TextView text = (TextView) convertView.findViewById(R.id.memorytext);

			text.setText(mChilds[groupPosition][childPosition]);
			//RadioButton checkBox = (RadioButton) convertView.findViewById(R.id.checkbox);
			//checkBox.setVisibility(View.INVISIBLE);
			//return convertView;
		} else if (groupPosition == 0) {
			final TextView text = (TextView) convertView.findViewById(R.id.memorytext);
			text.setPadding(50, 0, 0, 0);
			CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
			checkBox.setVisibility(View.VISIBLE);
			//checkBox.setChecked(false);
			if(childPosition==pos)
				checkBox.setChecked(true);

			text.setText(mChilds[groupPosition][childPosition]);}
		//return convertView;}
		
		return convertView;
	}

	public void updateposition(int pos)
	{
		this.pos=pos;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
