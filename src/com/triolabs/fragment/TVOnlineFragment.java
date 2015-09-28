package com.triolabs.fragment;

import java.text.DecimalFormat;

import com.triolabs.kaltura.Constant;
import com.triolabs.model.Chapter;
import com.triolabs.model.ListProgram;
import com.triolabs.tv_uaa_android.R;
import com.triolabs.util.Font;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.PorterDuff.Mode;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.VideoView;

/**
* TVOnlineFragment es el fragmento para controlar y reproducir un video
* @author Triolabs
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class TVOnlineFragment extends Fragment implements OnClickListener,OnCompletionListener,OnErrorListener {
	
	TVOnlineFragment self;//contexto
	VideoView videoTV;
	SeekBar videoSeekBar; 
	private final Handler handler = new Handler();
	private int mediaFileLengthInMilliseconds;
	ImageView buttonPlayPause;
	String url;
	ListProgram listProgram;
	ProgressBar progressBar;
	TextView textStart;
	TextView textDuration;
	TextView nameProgram;
	RelativeLayout nameContent;
	
	/** Se crea una instancia de TVOnlineFragment se incializa self 
 	 */
	public TVOnlineFragment(){
		self=this;
	}
	
	
	/**
     * Metodo onCreateView si no existe una instancia salvada 
     * se inicializan los componentes del videoview a utilizar
     */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tv_online, container,
				false);
		videoSeekBar =(SeekBar)rootView.findViewById(R.id.tv_online_bar);
		videoSeekBar.getProgressDrawable().setColorFilter(this.getResources().getColor(R.color.orange_dark), Mode.SRC_IN);
		videoSeekBar.setEnabled(false);
		ImageView close =(ImageView)rootView.findViewById(R.id.tv_online_close);
		buttonPlayPause=(ImageView)rootView.findViewById(R.id.tv_online_playpuase);
		textDuration=(TextView)rootView.findViewById(R.id.tv_online_end);
		textStart=(TextView)rootView.findViewById(R.id.tv_online_start);
		nameProgram=(TextView)rootView.findViewById(R.id.tv_online_name_program);
		nameContent=(RelativeLayout)rootView.findViewById(R.id.tv_online_name_program_content);
		Font font = new Font();
		font.changeFontHelvetica(getActivity(), textDuration);
		font.changeFontHelvetica(getActivity(), textStart);
		font.changeFontHelvetica(getActivity(), nameProgram);
		buttonPlayPause.setOnClickListener(self);
		progressBar=(ProgressBar)rootView.findViewById(R.id.tv_online_progress);
		close.setOnClickListener(self);
		videoTV=(VideoView)rootView.findViewById(R.id.tv_online_video);
		videoTV.setOnCompletionListener(self);
		videoTV.setOnErrorListener(self);
		videoTV.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				onClickStat();
				return false;
			}
			
		});
		
		listProgram= new ListProgram();
		if(ListProgram.isChapterExists()){
			Chapter chapter = ListProgram.getCurrentChapter();
			url=chapter.getUrlChapter();
			videoTV.setVideoURI(Uri.parse(url));
			videoTV.requestFocus();
			if(Constant.KALTURA_STREAMING.equals(url))//si es streaming se desabilita el videoview
				videoTV.setEnabled(false);
			nameProgram.setText(chapter.getNameChapter());
		}
	
		videoSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				videoTV.seekTo(seekBar.getProgress());
				videoTV.start();
			}});
		return rootView;// regresa la vista
	}

	/**
     * Metodo onClick es el escuchador de eventos de click
     */
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()){
		
			case R.id.tv_online_close:
				self.getActivity().finish();
				break;
		
			case R.id.tv_online_playpuase:
				onClickStat();
				break;
		
		}
		
	}
	
	/**
     * Metodo onClickStat checa si esta en reproduccion el video lo pone en pausa,
     * si no,lo reproduce y se ejecuta el handler
     */
	private void onClickStat(){
		if(videoTV.isPlaying())
			puaseVideo();
		else
			playVideo();
		handler.post(seekBarProgressUpdater);
	}

	/**
     * Metodo onCompletion escucha cuando se acaba de reproducir el video y cierra la actividad
     */
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		buttonPlayPause.setVisibility(View.VISIBLE);
		self.getActivity().finish();
	}
	
	/**
     * Metodo onDestroy destruye la actividad y remueve el handler
     */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		handler.removeCallbacks(seekBarProgressUpdater);		
	}

	/**
     * Runnable hilo que ejecuta cada segundo para actualizar las seekbar si el video va avanzando,
     * y cambiar los textview
     */
	private final Runnable seekBarProgressUpdater = new Runnable(){
	    public void run(){
	        try {
	            //prepare and send the data here..
	        	mediaFileLengthInMilliseconds = videoTV.getDuration();
	        	textDuration.setText(convertMilliseconds(mediaFileLengthInMilliseconds));
	        	if(textDuration.getText().toString().equals("00:00"))
	        		progressBar.setVisibility(View.VISIBLE);
	        	videoSeekBar.setMax(mediaFileLengthInMilliseconds);
	        	if(videoTV.isPlaying()){
	        		videoSeekBar.setProgress(videoTV.getCurrentPosition());
	        		textStart.setText(convertMilliseconds(videoTV.getCurrentPosition()));
	        	}
	            handler.postDelayed(this, 1000);    
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }   
	    }
	};
	
	/**
     * Metodo playVideo  inicia la reproducion del video
     */
	private void playVideo(){
		Log.i("CLICK", "PLAY");
		nameContent.setVisibility(View.GONE);
		progressBar.setVisibility(View.VISIBLE);
		videoTV.start();
		buttonPlayPause.setVisibility(View.GONE);
		if(!Constant.KALTURA_STREAMING.equals(url)){
			videoSeekBar.setEnabled(true);
		}

	}
	
	/**
     * Metodo puaseVideo  pausa la reproducion del video
     */
	private void puaseVideo(){
		videoTV.pause();
		buttonPlayPause.setVisibility(View.VISIBLE);
		nameContent.setVisibility(View.VISIBLE);
		if(!Constant.KALTURA_STREAMING.equals(url)){
			videoSeekBar.setEnabled(false);
		}
	}

	/**
     * Metodo convertMilliseconds convierte los milesegundos en string de dos digitos
     * @param milliseconds milesegundos a convertir a string
     */
	private String convertMilliseconds(int milliseconds){
		String time="00:00";
		int seconds = (int) (milliseconds / 1000) % 60 ;
		int minutes = (int) ((milliseconds / (1000*60)) % 60);
		int hours   = (int) ((milliseconds / (1000*60*60)) % 24);
		DecimalFormat formatter = new DecimalFormat("00");
		if(hours!=0){
			time=formatter.format(hours)+":"+formatter.format(minutes)+":"+formatter.format(seconds);
		}else{
			time=formatter.format(minutes)+":"+formatter.format(seconds);
		}
		if(!time.equals("00:00"))
    		progressBar.setVisibility(View.GONE);
		return time;
	}

	/**
     * Metodo onError escucha si hay  un error en la reproducion del video,
     * si es asi muestra una alerta
     */
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(self.getActivity());
		builder.setMessage("Lo sentimos, no se puede reproducir este video ")
		        .setTitle("Error")
		        .setCancelable(false)
		        .setPositiveButton(self.getResources().getString(R.string.alert_acept),
		                new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.cancel();
								self.getActivity().finish();
							}
		                  
		                });
		AlertDialog alert = builder.create();
		alert.show();
		return true;
	}


}
