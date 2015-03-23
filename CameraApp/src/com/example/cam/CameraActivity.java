package com.example.cam;

/**
 * @author Elif BEÞLÝ *
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import android.hardware.Camera.AutoFocusCallback;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore.Images;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.Toast;


public class CameraActivity extends Activity implements SurfaceHolder.Callback {
    private static final String TAG = "CameraActivity";
	Preview preview;
	Button buttonTakePicture,back,saveFile,greyButton,bwButton,brightness,depth,invert,share;
	Bitmap bmp=null,m=null;
	ProgressDialog pg;
	ImageView iv;
	Camera camera;
	Activity act;
	Context ctx;
    LayoutInflater controlInflater = null;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    byte[] c;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;
		act = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);
		getWindow().setFormat(PixelFormat.UNKNOWN);
		iv=(ImageView)findViewById(R.id.img);
		bmp = BitmapFactory.decodeResource(getResources(), R.id.img);
		
        surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		preview = new Preview(this, (SurfaceView)findViewById(R.id.surfaceView));
		preview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		((FrameLayout) findViewById(R.id.layout)).addView(preview);
		preview.setKeepScreenOn(true);
		
		controlInflater = LayoutInflater.from(getBaseContext());
        View viewControl = controlInflater.inflate(R.layout.control, null);
        LayoutParams layoutParamsControl = new LayoutParams(LayoutParams.FILL_PARENT, 
        	                                                LayoutParams.FILL_PARENT);
        this.addContentView(viewControl, layoutParamsControl);
        saveFile=(Button)findViewById(R.id.but3);
        saveFile.setVisibility(View.GONE);
        greyButton=(Button)findViewById(R.id.button3);
        greyButton.setVisibility(View.GONE);
        bwButton=(Button)findViewById(R.id.button4);
        bwButton.setVisibility(View.GONE);
        brightness=(Button)findViewById(R.id.button5);
        brightness.setVisibility(View.GONE);
        depth=(Button)findViewById(R.id.button6);
        depth.setVisibility(View.GONE);
        invert=(Button)findViewById(R.id.button7);
        invert.setVisibility(View.GONE);
        share=(Button)findViewById(R.id.button8);
        share.setVisibility(View.GONE);
		buttonTakePicture = (Button)findViewById(R.id.button1);
		buttonTakePicture.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					camera.takePicture(shutterCallback, rawCallback, jpegCallback);		
					buttonTakePicture.setVisibility(View.GONE);
					back.setVisibility(View.VISIBLE);
					saveFile.setVisibility(View.VISIBLE);
					greyButton.setVisibility(View.VISIBLE);
					bwButton.setVisibility(View.VISIBLE);
					brightness.setVisibility(View.VISIBLE);
					depth.setVisibility(View.VISIBLE);
					invert.setVisibility(View.VISIBLE);
					share.setVisibility(View.VISIBLE);
	}}); 
	     
	     back = (Button)findViewById(R.id.button2);
	     back.setVisibility(View.GONE);
	     back.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
		            back.setVisibility(View.GONE);
					saveFile.setVisibility(View.GONE);
					greyButton.setVisibility(View.GONE);
					bwButton.setVisibility(View.GONE);
					brightness.setVisibility(View.GONE);
					depth.setVisibility(View.GONE);
					invert.setVisibility(View.GONE);
					share.setVisibility(View.GONE);
					iv.clearColorFilter();
					findViewById(R.id.img).setVisibility(View.GONE);
					 buttonTakePicture.setVisibility(View.VISIBLE);
		                findViewById(R.id.preview2).setVisibility(View.VISIBLE);
		                preview.mCamera.startPreview();
		   }}); 
	     final AutoFocusCallback myAutoFocusCallback = new AutoFocusCallback(){

		    		@Override
		    		public void onAutoFocus(boolean arg0, Camera arg1) {
		    			// TODO Auto-generated method stub
		    			buttonTakePicture.setEnabled(true);
		    		}};
		        
		        ShutterCallback myShutterCallback = new ShutterCallback(){

		    		@Override
		    		public void onShutter() {
		    			// TODO Auto-generated method stub
		    			
		    		}};
	        
	        RelativeLayout layoutBackground = (RelativeLayout)findViewById(R.id.background);
	        layoutBackground.setOnClickListener(new RelativeLayout.OnClickListener(){
	        	
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					 AudioManager mgr = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
					 mgr.setStreamMute(AudioManager.STREAM_SYSTEM, true);
					camera.autoFocus(myAutoFocusCallback);
				}});
		preview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				camera.takePicture(shutterCallback, rawCallback, jpegCallback);
			}
		});

		Toast.makeText(ctx, getString(R.string.take_photo_help), Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		int numCams = Camera.getNumberOfCameras();
		if(numCams > 0){
			try{
				camera = Camera.open(0);
				camera.startPreview();
				preview.setCamera(camera);
			} catch (RuntimeException ex){
				Toast.makeText(ctx, getString(R.string.camera_not_found), Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	protected void onPause() {
		if(camera != null) {
			camera.stopPreview();
			preview.setCamera(null);
			camera.release();
			camera = null;
		}
		super.onPause();
	}

	private void resetCam() {
		camera.startPreview();
		preview.setCamera(camera);
	}

	private void refreshGallery(File file) {
		Intent mediaScanIntent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		mediaScanIntent.setData(Uri.fromFile(file));
		sendBroadcast(mediaScanIntent);
	}

	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
			//			 Log.d(TAG, "onShutter'd");
		}
	};

	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			//			 Log.d(TAG, "onPictureTaken - raw");
			 if(data!=null){
	                bmp=BitmapFactory.decodeByteArray(data,0,data.length);
	                findViewById(R.id.img).setVisibility(View.VISIBLE);
	                iv= (ImageView)findViewById(R.id.img);
	                iv.setImageBitmap(bmp);
	                findViewById(R.id.preview2).setVisibility(View.GONE);
	 
	              if(pg!=null)
	                    pg.dismiss();
	            }
		}
	};
	
	PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(final byte[] data, Camera camera) {
			
			
			if(data!=null){
				c=data;
                bmp=BitmapFactory.decodeByteArray(data,0,data.length);//data bitmapa dönüþtürüldü
                findViewById(R.id.img).setVisibility(View.VISIBLE);//imgvew görünür hale getirildi
                iv= (ImageView)findViewById(R.id.img);
                iv.setImageBitmap(bmp);//imgviewe bitmapa dönüþtürülen data atandý
                findViewById(R.id.preview2).setVisibility(View.GONE);//önizleme gitti
                new SaveImageTask().execute(data);
    			resetCam();
    			Log.d(TAG, "onPictureTaken - jpeg");
              
        greyButton.setOnClickListener(new OnClickListener() {
        	int i=0;;
        	public void onClick(View v) {
        		i+=1;
				
				 Handler handler = new Handler();//click sayýsýný tutar.tek týklamada efektli,çift týklamada efektsiz halini verir
			        Runnable r = new Runnable() {

			            @Override
			            public void run() {
			                i = 0;
			            }
			        }; 
			        

			        if (i%2==0) {
			            c=data;
		                bmp=BitmapFactory.decodeByteArray(data,0,data.length);
		                findViewById(R.id.img).setVisibility(View.VISIBLE);
		                iv= (ImageView)findViewById(R.id.img);
		                iv.setImageBitmap(bmp);
		                findViewById(R.id.preview2).setVisibility(View.GONE);
			        } else if (i%2==1) {
			        	ImageFilters imgFilter = new ImageFilters();
						 m=imgFilter.applyGreyscaleEffect(bmp);//m efekt uygulanmýþ bitmaptir.
			             iv.setImageBitmap(m);//imageview efektli bitmapa atandý
			              
			             ByteArrayOutputStream baos = new ByteArrayOutputStream();
			             m .compress(Bitmap.CompressFormat.PNG, 100, baos);
			                		
			             byte[] b = baos.toByteArray(); //datanýn efektli þekilde kaydedilmesi için byte tipine dönüþtürüldü
			             //çünkü save metodu byte tipinde data istiyor.
			            c=data;
			            c=b;
			            
			            
			        }


			    }
			
		}); 
				bwButton.setOnClickListener(new OnClickListener() {
					int i=0;;
					public void onClick(View v) {
						i+=1;
						 Handler handler = new Handler();
					        Runnable r = new Runnable() {

					            @Override
					            public void run() {
					                i = 0;
					            }
					        }; 
					        

					        if (i%2==0) {
					        	if(iv.getDrawable() != null){
					            c=data;
				                bmp=BitmapFactory.decodeByteArray(data,0,data.length);
				                findViewById(R.id.img).setVisibility(View.VISIBLE);
				                iv= (ImageView)findViewById(R.id.img);
				                iv.setImageBitmap(bmp);
				                findViewById(R.id.preview2).setVisibility(View.GONE);}else {
				                	i+=1;
				                }
					        } else if (i%2==1) {
					        	
					          
					        	ImageFilters imgFilter = new ImageFilters();
								
								 m=imgFilter.applyContrastEffect(bmp, 70);
					             iv.setImageBitmap(m);
					              
					             ByteArrayOutputStream baos = new ByteArrayOutputStream();
					             m .compress(Bitmap.CompressFormat.PNG, 100, baos);
					                		
					             byte[] b = baos.toByteArray(); //datanýn efektli þekilde kaydedilmesi için byte tipine dönüþtürüldü
					            
					            c=data;
					            c=b;
					            
					        }


					    }
					
		});
				brightness.setOnClickListener(new OnClickListener() {
				int i=0;;
					public void onClick(View v) {
						i+=1;
						 Handler handler = new Handler();
					        Runnable r = new Runnable() {

					            @Override
					            public void run() {
					                i = 0;
					            }
					        }; 
					        

					        if (i%2==0) {
					            c=data;
				                bmp=BitmapFactory.decodeByteArray(data,0,data.length);
				                findViewById(R.id.img).setVisibility(View.VISIBLE);
				                iv= (ImageView)findViewById(R.id.img);
				                iv.setImageBitmap(bmp);
				                findViewById(R.id.preview2).setVisibility(View.GONE);
					        } else if (i%2==1) {
					        	ImageFilters imgFilter = new ImageFilters();
								m=imgFilter.applyBrightnessEffect(bmp, 80);
					             iv.setImageBitmap(m);
					              
					             ByteArrayOutputStream baos = new ByteArrayOutputStream();
					             m .compress(Bitmap.CompressFormat.PNG, 100, baos);
					                		
					             byte[] b = baos.toByteArray(); //datanýn efektli þekilde kaydedilmesi için byte tipine dönüþtürüldü
					            
					            c=data;
					            c=b;
					            
					        }


					    }
					
		});
				depth.setOnClickListener(new OnClickListener() {
					int i=0;
					public void onClick(View v) {
						i+=1;
						 Handler handler = new Handler();
					        Runnable r = new Runnable() {

					            @Override
					            public void run() {
					                i = 0;
					            }
					        }; 
					        
					        
					        if (i%2==0) {
					        	
					        	
					            c=data;
				                bmp=BitmapFactory.decodeByteArray(data,0,data.length);
				                findViewById(R.id.img).setVisibility(View.VISIBLE);
				                iv= (ImageView)findViewById(R.id.img);
				                iv.setImageBitmap(bmp);
				                findViewById(R.id.preview2).setVisibility(View.GONE);
				                

					        	
					        }else if (i%2==1) {
					        	
					        	ImageFilters imgFilter = new ImageFilters();
								
								 m=imgFilter.applyDecreaseColorDepthEffect(bmp, 32);
					             iv.setImageBitmap(m);
					              
					             ByteArrayOutputStream baos = new ByteArrayOutputStream();
					             m .compress(Bitmap.CompressFormat.PNG, 100, baos);
					                		//bMap is the bitmap object
					             byte[] b = baos.toByteArray(); //datanýn efektli þekilde kaydedilmesi için byte tipine dönüþtürüldü
					            
					             c=data;
					             c=b; 
					           
					            
					        }


					    }
									
			}); 
				invert.setOnClickListener(new OnClickListener() {
				 int i=0;;
					public void onClick(View v) {
						i+=1;
						 Handler handler = new Handler();
					        Runnable r = new Runnable() {

					            @Override
					            public void run() {
					                i = 0;
					            }
					        }; 
					        

					        if (i%2==0) {
					            c=data;
				                bmp=BitmapFactory.decodeByteArray(data,0,data.length);
				                findViewById(R.id.img).setVisibility(View.VISIBLE);
				                iv= (ImageView)findViewById(R.id.img);
				                iv.setImageBitmap(bmp);
				                findViewById(R.id.preview2).setVisibility(View.GONE);
					        } else if (i%2==1)   {
					        	ImageFilters imgFilter = new ImageFilters();
								
								 m=imgFilter.applyInvertEffect(bmp);
					             iv.setImageBitmap(m);
					              
					             ByteArrayOutputStream baos = new ByteArrayOutputStream();
					             m .compress(Bitmap.CompressFormat.PNG, 100, baos);
					                		//bMap is the bitmap object
					             byte[] b = baos.toByteArray(); //datanýn efektli þekilde kaydedilmesi için byte tipine dönüþtürüldü
					            
					            c=data;
					            c=b;
					            
					        }


					    }
						
					
		});
				
				share.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						
						BitmapFactory.Options options=new BitmapFactory.Options();//byte tipindeki data tekrar bitmapa dönüþtürüldü.
						options.inSampleSize = 8; 
						Bitmap b = BitmapFactory.decodeByteArray(c, 0, c.length);
						 String path = Images.Media.insertImage(ctx.getContentResolver(), b,null, null);//bu fonk bitmap þeklinde veri kabul ediyor
				         Uri image1= Uri.parse(path);
						 Intent intent = new Intent();
						 intent.setAction(Intent.ACTION_SEND);
						 intent.setType("image/*");
						 
						 intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
						 intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
						 intent.putExtra(Intent.EXTRA_STREAM, image1);
						 startActivity(Intent.createChooser(intent, "Share"));
				}
					
					
			});
                
                
                }
			 
			saveFile = (Button) findViewById(R.id.but3);
			
					saveFile.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							iv.setVisibility(View.VISIBLE);
							greyButton.setVisibility(View.VISIBLE);
							saveFile.setVisibility(View.VISIBLE);
							back.setVisibility(View.VISIBLE);
							buttonTakePicture.setVisibility(View.GONE);
							greyButton.setVisibility(View.VISIBLE);
							bwButton.setVisibility(View.VISIBLE);
							brightness.setVisibility(View.VISIBLE);
							depth.setVisibility(View.VISIBLE);
							invert.setVisibility(View.VISIBLE);
							share.setVisibility(View.VISIBLE);
							new SaveImageTask().execute(c);
							resetCam();
							Log.d(TAG, "onPictureTaken - jpeg");
							iv.clearColorFilter();
							
							
						}
				}); 
					
	}
	};
	
	private class SaveImageTask extends AsyncTask<byte[], Void, Void> {

		@Override
		protected Void doInBackground(byte[]... data) {
			FileOutputStream outStream = null;

			//SD Card'a yazma iþlemi
			try {
				File sdCard = Environment.getExternalStorageDirectory();
				File dir = new File (sdCard.getAbsolutePath() + "/Cheeze");
				dir.mkdirs();				

				String fileName = String.format("%d.jpg", System.currentTimeMillis());
				File outFile = new File(dir, fileName);

				outStream = new FileOutputStream(outFile);
				outStream.write(data[0]);
				outStream.flush();
				outStream.close();

				Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length + " to " + outFile.getAbsolutePath());

				refreshGallery(outFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			}
			return null;
		}

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}	
	 
	 
}

	