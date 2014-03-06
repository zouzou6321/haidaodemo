package com.example.android.lifecycle;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import android.util.Log;  

public class ListViewAdapter extends BaseAdapter {

	//列宽
	private int cWidth = 120;
	//水平间距
	private int hSpacing = 10;
	private Context mContext;
	private ArrayList<HashMap<String, Integer>> mList;
	private ArrayList<HashMap<String, Integer>> mGist;
    private ArrayList<ArrayList<String>> dataList;
	//要展示view的id
	private int viewId;
	//要展示的layout id
	private int layoutId;
	public ListViewAdapter(Context context,
			ArrayList<HashMap<String, Integer>> list,
			ArrayList<HashMap<String, Integer>> gist,int viewId,int layoutId,ArrayList<ArrayList<String>> dataList) {

		this.mContext = context;
		this.mList = list;
		this.mGist = gist;
		this.viewId = viewId;
		this.layoutId = layoutId;
		this.dataList = dataList;
	}
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					layoutId, arg2, false);
			//holder.iv = (ImageView) convertView.findViewById(R.id.iv);
			holder.gv = (ScrollView) convertView.findViewById(viewId);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//holder.iv.setImageResource(mList.get(arg0).get("list"));

		ScrollViewAdapter ga = new ScrollViewAdapter(mContext, mGist);
		int ii = ga.getCount();
		
		//LayoutParams params = new LayoutParams(ii * (48 + 10),
		//		LayoutParams.FILL_PARENT);
		//holder.gv.setLayoutParams(params);
		//holder.gv.setColumnWidth(cWidth);
		//holder.gv.setHorizontalSpacing(hSpacing);
		//holder.gv.setStretchMode(GridView.NO_STRETCH);
		//holder.gv.setNumColumns(ii);
		//holder.gv.setAdapter(ga);
		setDataToView(convertView);
		return convertView;
	}

	private void setDataToView(View targetView)
	{
		Iterator<ArrayList<String>> it = this.dataList.iterator();
		while(it.hasNext())
		{
			ArrayList<String > tempArray = (ArrayList<String >)it.next();
			
			int targetViewId = Integer.parseInt(tempArray.get(0));
			String content = tempArray.get(1);
			String type = tempArray.get(2);
			
			if(type.equals("text"))
			{
			TextView textView = (TextView) targetView.findViewById(targetViewId);
	  		textView.setText(content);
	  		
			}else if (type.equals("image"))
			{
				ImageView imageView = (ImageView) targetView.findViewById(targetViewId);
				Bitmap bitmap =getHttpBitmap(content);
				imageView.setImageBitmap(bitmap);
				Log.i("setDataToView", type);
			}
		}
     	
	}
	public static class ViewHolder {

		//ImageView iv;
		ScrollView gv;
	}
	
		/**
	    * 从服务器取图片
	    * @param url
	    * @return
	    */
	    public static Bitmap getHttpBitmap(String url) {
	         URL myFileUrl = null;
	         Bitmap bitmap = null;
	         try {
	              myFileUrl = new URL(url);
	         } catch (MalformedURLException e) {
	              e.printStackTrace();
	         }
	         try {
	              HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
	              conn.setConnectTimeout(0);
	              conn.setDoInput(true);
	              conn.connect();
	              InputStream is = conn.getInputStream();
	              bitmap = BitmapFactory.decodeStream(is);
	              is.close();
	         } catch (IOException e) {
	              e.printStackTrace();
	         }
	         return bitmap;
	    }
}
