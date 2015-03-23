package com.example.calculator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener{
	EditText etext;
	Button buton1,buton2,buton3,buton4,buton5,buton6,buton7,buton8,
	buton9,buton10,buton11,buton12,buton13,buton14,buton15,buton16,buton17;
	float sayi1=0,sayi2=0,sonuc=0;
	float sayiz=0;
	String islem,islem2;
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etext=(EditText)findViewById(R.id.editText1);
		tv=(TextView)findViewById(R.id.textView1);
	    buton1=(Button) findViewById(R.id.button1);
		buton1.setOnClickListener(this);
		buton2=(Button) findViewById(R.id.button2);
		buton2.setOnClickListener(this);
		buton3=(Button) findViewById(R.id.button3);
		buton3.setOnClickListener(this);
		buton4=(Button) findViewById(R.id.button4);
		buton4.setOnClickListener(this);
		buton5=(Button) findViewById(R.id.button5);
		buton5.setOnClickListener(this);
		buton6=(Button) findViewById(R.id.button6);
		buton6.setOnClickListener(this);
		buton7=(Button) findViewById(R.id.button7);
		buton7.setOnClickListener(this);
		buton8=(Button) findViewById(R.id.button8);
		buton8.setOnClickListener(this);
		buton9=(Button) findViewById(R.id.button9);
		buton9.setOnClickListener(this);
		buton10=(Button) findViewById(R.id.button10);
		buton10.setOnClickListener(this);
		buton11=(Button) findViewById(R.id.button11);
		buton11.setOnClickListener(this);
		buton12=(Button) findViewById(R.id.button12);
		buton12.setOnClickListener(this);
		buton13=(Button) findViewById(R.id.button13);
		buton13.setOnClickListener(this);
		buton14=(Button) findViewById(R.id.button14);
		buton14.setOnClickListener(this);
		buton15=(Button) findViewById(R.id.button15);
		buton15.setOnClickListener(this);
		buton16=(Button) findViewById(R.id.button16);
		buton16.setOnClickListener(this);
		buton17=(Button) findViewById(R.id.button17);
		buton17.setOnClickListener(this);
		
		
		
	
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.equals(buton1)){
	           etext.setText(etext.getText() + "7"); 
		}
		else if (v.equals(buton2)){
        etext.setText(etext.getText() + "8");
        }
	     
		else if (v.equals(buton3)){
	        etext.setText(etext.getText() + "9"); 
	        }
		else if (v.equals(buton4)){
			sayi1=Float.parseFloat((etext.getText().toString()));
			String sayi22=String.valueOf(sayi1);
			tv.setText(sayi22);
            etext.setText("");
            islem="/";
            tv.setText(tv.getText()+"÷");
             
	        }
		else if (v.equals(buton5)){
	        etext.setText(etext.getText() + "4"); 
	        }
		else if (v.equals(buton6)){
	        etext.setText(etext.getText() + "5"); 
	        }
		else if (v.equals(buton7)){
	        etext.setText(etext.getText() + "6"); 
	        }
		else if (v.equals(buton8)){
			sayi1=Float.parseFloat((etext.getText().toString()));
   			String sayi22=String.valueOf(sayi1);
   			tv.setText(sayi22);
	            etext.setText("");
	            islem="x";
	            tv.setText(tv.getText()+"x");

	        }
		else if (v.equals(buton9)){
	        etext.setText(etext.getText() + "1"); 
	        }
		else if (v.equals(buton10)){
	        etext.setText(etext.getText() + "2"); 
	        }
		else if (v.equals(buton11)){
	        etext.setText(etext.getText() + "3"); 
	        }
		else if (v.equals(buton12)){
			if(!(etext.getText().toString().equals(""))){
				sayi1=Float.parseFloat((etext.getText().toString()));
   			String sayi22=String.valueOf(sayi1);
   			tv.setText(sayi22);
	            etext.setText("");
	            islem="-";
	            tv.setText(tv.getText()+"-");
	            
              }
	       
	        }
		else if (v.equals(buton13)){
			if(!(etext.getText().toString().equals(""))){
			//sayi1=Integer.parseInt(etext.getText().toString());
	        etext.setText(etext.getText() + ".");
	        sayiz=Float.parseFloat((etext.getText().toString()));
			String sayik=String.valueOf(sayiz);
			
			}
	        }
		else if (v.equals(buton14)){
	        etext.setText(etext.getText() + "0"); 
	        }
		else if (v.equals(buton15)){
			sayi2=Float.parseFloat((etext.getText().toString()));
			String sayi=String.valueOf(sayi2);
			tv.setText(tv.getText()+sayi+"=");
        	if(islem.equals("+")){
        		sonuc = sayi1+sayi2;
        		sayi1=0;
                sayi2=0;
            
	        }
        	else if(islem.equals("-")){
        		sonuc = sayi1-sayi2;
                sayi1=0;
                sayi2=0;
               
        	} 
        	else if(islem.equals("/")){
        		sonuc = sayi1/sayi2;
                sayi1=0;
                sayi2=0;
               
        	}
        	else if(islem.equals("x")){
        		sonuc = sayi1*sayi2;
                sayi1=0;
                sayi2=0;
               
        	}
        	
              
        	etext.setText(Float.toString(sonuc));
        	} 
		else if (v.equals(buton16)){
			if(!(etext.getText().toString().equals(""))){
				sayi1=Float.parseFloat((etext.getText().toString()));
    			String sayi22=String.valueOf(sayi1);
    			tv.setText(sayi22);
	            etext.setText("");
	            islem="+";
	            tv.setText(tv.getText()+"+");
	            
	           
	        }}
		else if (v.equals(buton17)){
	        etext.setText(""); 
	        }
		
		     
		
	     }
	
	

	}
	

