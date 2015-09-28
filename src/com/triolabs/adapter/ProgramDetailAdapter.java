package com.triolabs.adapter;


import java.util.ArrayList;

import com.triolabs.asynctask.ImageAsyncTask;
import com.triolabs.kaltura.Kaltura;
import com.triolabs.model.Chapter;
import com.triolabs.model.ListProgram;
import com.triolabs.social.Facebook;
import com.triolabs.social.Twitter;
import com.triolabs.tv_uaa_android.R;
import com.triolabs.tv_uaa_android.TVOnlineActivity;
import com.triolabs.util.Device;
import com.triolabs.util.Font;
import com.triolabs.util.Image;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
* ProgramDetailAdapter devuelve las celdas de la lista de los capitulos
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class ProgramDetailAdapter extends BaseAdapter {
 
    private static Context context;  
    ArrayList<Chapter> listChapter;
    public static boolean playVideo=false;
    String idProgram;
    
    /**Se crea una instancia de ProgramDetailAdapter
	 * @param context context
	 * @param listProgram lista de los programas
	 * @param programName nombre del programa actual
     */
	public ProgramDetailAdapter(Context context,ArrayList<Chapter> listChapter,String idProgram){
		ProgramDetailAdapter.context=context;
		this.listChapter=listChapter;
		this.idProgram=idProgram;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listChapter.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(context==null)
			return null;
		
		if(convertView==null){
		    LayoutInflater view = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    convertView = view.inflate(R.layout.program_detail_list_view, parent, false);
		}
		final Chapter chapter = listChapter.get(position);
		final Button showSocial =(Button)convertView.findViewById(R.id.program_detail_item_button);
		final RelativeLayout layoutSocial =(RelativeLayout)convertView.findViewById(R.id.program_detail_item_social);
		final RelativeLayout layoutName =(RelativeLayout)convertView.findViewById(R.id.program_detaill_item_click);	
		final RelativeLayout layoutDate=(RelativeLayout)convertView.findViewById(R.id.program_detail_item_date_layout);
		final RelativeLayout layoutTriangle=(RelativeLayout)convertView.findViewById(R.id.program_detail_item_triangle);
		final RelativeLayout layoutShow =(RelativeLayout)convertView.findViewById(R.id.program_detail_item_show);
		ImageView imageChapter =(ImageView)convertView.findViewById(R.id.program_detail_item_image);
		ImageView imageFace =(ImageView)convertView.findViewById(R.id.image_facebook);
		ProgressBar progressBar =(ProgressBar)convertView.findViewById(R.id.progress_image);
		Image image = new Image();
		Bitmap bmChapter = image.getBitmapFromMemCache(chapter.getPhotoChapter(),Device.getWidth()/2,Device.getHeigth()/6);
		if(bmChapter==null){
			ImageAsyncTask task = new ImageAsyncTask(context,imageChapter,progressBar,chapter.getPhotoChapter(),Device.getWidth()/2,Device.getHeigth()/7);
			ImageAsyncTask taskFace = new ImageAsyncTask(context,imageFace,progressBar,chapter.getPhotoChapter(),Device.getWidth()/2,Device.getHeigth()/7);
			taskFace.execute();
			task.execute();
		}else{
			imageFace.setImageBitmap(bmChapter);
			imageChapter.setImageBitmap(bmChapter);
		}
		final Animation leftInAnimation =  AnimationUtils.loadAnimation(context, R.anim.left_in_animation);
		final Animation leftOutAnimation =  AnimationUtils.loadAnimation(context, R.anim.left_out_animation);
		leftInAnimation.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				showSocial.setEnabled(false);
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				layoutShow.setEnabled(true);
				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}});
		leftOutAnimation.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				showSocial.setEnabled(false);
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				layoutSocial.setVisibility(View.GONE);
				layoutShow.setEnabled(true);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}});
		layoutShow.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(layoutSocial.getVisibility()==View.GONE){
					layoutSocial.setVisibility(View.VISIBLE);
					layoutSocial.startAnimation(leftInAnimation);
				}else{
					layoutSocial.startAnimation(leftOutAnimation);
				}
			}});
		TextView textDateNumber = (TextView)convertView.findViewById(R.id.program_detail_item_date_number);
		TextView textDateMonth = (TextView)convertView.findViewById(R.id.program_detail_item_date_month);
		TextView textChapterName = (TextView)convertView.findViewById(R.id.program_detail_item_chapter_name);
		Font font = new Font();
		font.changeFontIntro(context, textDateNumber);
		font.changeFontIntro(context, textDateMonth);
		font.changeFontHelvetica(context, textChapterName);
		textDateNumber.setText(chapter.getDateNumberChapter());
		textDateMonth.setText(chapter.getDateMonthChapter());
		if(chapter.getNameChapter().length()<20)
			textChapterName.setText(chapter.getNameChapter());
		else
			textChapterName.setText(chapter.getNameChapter().substring(0, 17)+"...");
		layoutName.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				if(event.getAction() == MotionEvent.ACTION_DOWN){
	                //Button Pressed
					layoutDate.setBackgroundColor(context.getResources().getColor(R.color.orange_low));
					layoutTriangle.setBackgroundResource(R.drawable.triangle_orange);
	            }
				if(event.getAction() == MotionEvent.ACTION_CANCEL){
					layoutDate.setBackgroundColor(context.getResources().getColor(R.color.white));
	            	layoutTriangle.setBackgroundResource(R.drawable.triangle_white);
				}
	            if(event.getAction() == MotionEvent.ACTION_UP){
	                 //finger was lifted
	            	layoutDate.setBackgroundColor(context.getResources().getColor(R.color.white));
	            	layoutTriangle.setBackgroundResource(R.drawable.triangle_white);
	            	if(layoutSocial.getVisibility()==View.GONE)
						selectChapter(chapter);
	            }
	            return true;
			}
			
		});
		layoutDate.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				if(event.getAction() == MotionEvent.ACTION_DOWN){
	                //Button Pressed
					layoutDate.setBackgroundColor(context.getResources().getColor(R.color.orange_low));
					layoutTriangle.setBackgroundResource(R.drawable.triangle_orange);
	            }
				if(event.getAction() == MotionEvent.ACTION_CANCEL){
					layoutDate.setBackgroundColor(context.getResources().getColor(R.color.white));
	            	layoutTriangle.setBackgroundResource(R.drawable.triangle_white);
				}
	            if(event.getAction() == MotionEvent.ACTION_UP){
	                 //finger was lifted
	            	layoutDate.setBackgroundColor(context.getResources().getColor(R.color.white));
	            	layoutTriangle.setBackgroundResource(R.drawable.triangle_white);
	            	if(layoutSocial.getVisibility()==View.GONE)
						selectChapter(chapter);
	            }
	            return true;
			}
			
		});
		
		ImageView shareTwitter =(ImageView)convertView.findViewById(R.id.program_detail_item_share_twitter);
		final String urlFace="http://tv.uaa.mx/social/share.html?idProgram="+idProgram+"&idChapter="+chapter.getId();
		shareTwitter.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Twitter twitter = new Twitter(context);
				twitter.shareTwitterWithIntent(urlFace+" @tvuaaoficial");
				
			}});
		ImageView shareFacebook =(ImageView)convertView.findViewById(R.id.program_detail_item_share_facebook);
		shareFacebook.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Facebook facebook = new Facebook(context);
				facebook.shareFacebookWithIntent(urlFace,chapter.getNameChapter());
				
			}});
		ImageView heartLike =(ImageView)convertView.findViewById(R.id.program_detail_item_heart);
		final TextView textHeart = (TextView)convertView.findViewById(R.id.program_detail_item_like_text);
		textHeart.setText(chapter.getLikes());
		font.changeFontHelvetica(context, textHeart);
		heartLike.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences pref =context.getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
				boolean like = pref.getBoolean(""+chapter.getId(), false);
				if(!like){
					Kaltura kaltura = new Kaltura();
					kaltura.setLikeChapter(chapter.getId(),textHeart);
				}
			}});
		return convertView;
	}
	
	/**Metodo selectChapter asigna el capitulo actual y manda ala actividad de TV Online
	 * @param chapterParam capitulo que se asignara
     */
	private void selectChapter(Chapter chapterParam){
		if(!playVideo){
			playVideo=true;
			ListProgram.setChapterExists(true);
			ListProgram.setCurrentChapter(chapterParam);
			Intent intentTvOnline = new Intent(context,TVOnlineActivity.class);
			context.startActivity(intentTvOnline);
		}
	}
	
	/**Metodo setLikeHeart llamada de regreso al ansynctask likes devuele el resultado
	 * @param result resultado del request
	 * @param idChapter id del capitulo
	 * @param textHeart textview que se modificara
     */
	public static void setLikeHeart(String result,String idChapter,TextView textHeart){
		if(result.equals("null")){
			SharedPreferences pref =context.getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = pref.edit();
			editor.putBoolean(idChapter, true);
			editor.commit();
			Log.i("getText", textHeart.getText().toString());
			int likes = Integer.parseInt(textHeart.getText().toString());
			likes++;
			textHeart.setText(""+likes);
		}
	}

}
