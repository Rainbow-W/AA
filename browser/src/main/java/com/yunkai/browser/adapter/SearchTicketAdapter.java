package com.yunkai.browser.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yunkai.browser.R;
import com.yunkai.browser.utils.SearchTicketInfor;

import java.util.List;

public class SearchTicketAdapter extends BaseAdapter {

	private Activity context;
	private List<SearchTicketInfor> list;

	public SearchTicketAdapter(Activity context, List<SearchTicketInfor> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder mViewHolder;
		if(convertView==null){
			mViewHolder = new ViewHolder();
			convertView = context.getLayoutInflater().inflate(R.layout.item_search_info, null);
			mViewHolder.tvTicketName = (TextView) convertView.findViewById(R.id.tv_ticket_name);
			mViewHolder.tvTicketClass = (TextView) convertView.findViewById(R.id.tv_ticket_class);
			mViewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
			
			convertView.setTag(mViewHolder);
			
		}else{
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		
		SearchTicketInfor info = list.get(position);

		mViewHolder.tvTicketName.setText(info.getTicketName());
		mViewHolder.tvTicketClass.setText(info.getTicketClass());
		mViewHolder.tvTime.setText(info.getTime());
		

		// TODO Auto-generated method stub
		return convertView;
	}
	
	
	
	private  class ViewHolder{
		TextView tvTicketName,tvTicketClass,tvTime;
	}

}
