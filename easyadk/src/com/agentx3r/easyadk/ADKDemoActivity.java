package com.agentx3r.easyadk;

/*
 * Copyright (C) 2012 Paul Bovbel, paul@bovbel.com
 * 
 * This file is part of the EasyADK library (http://code.google.com/p/mover-bot/)
 * 
 * EasyADK is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This source code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this source code. If not, see http://www.gnu.org/licenses/
 */

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/* This is a demo activity for using the EasyADK library */

public class ADKDemoActivity extends Activity {
	Handler UIHandler = new Handler();
	EasyAdkConnection AdkConnection;
	TextView textbox;
	
	static final String TAG = "EasyADK";
	final static byte SYNC = 's';
	final static byte MESSAGE = 'm';
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		textbox = (TextView)findViewById(R.id.textbox);
		setupUSB();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		closeUSB();
		
		//Force close app, not 'good practice'
//		System.runFinalizersOnExit(true);
//		System.exit(0);
	}

	private void setupUSB(){

		toast("Starting USB...");
		AdkConnection = new EasyAdkConnection(UIHandler);

	}

	private void closeUSB(){
		toast("Closing USB...");
		AdkConnection.closeAccessory();
		AdkConnection.destroyReceiver();
	}
	
	//Customize control here
	public class EasyAdkConnection extends EasyADK{

		public EasyAdkConnection(Handler ui) {
			super(getApplicationContext(), ui);
		}

		//Write your own send-to-accessory method
		void sendValue(int value){
			byte[] msg = new byte[2];
			msg[0] = MESSAGE;
			msg[1] = (byte) value;
			this.send(msg);
		}
		
		//Process incoming bytes from accessory here
		@Override
		public void onReceive(byte[] msg) {
			
			switch (msg[0]) {
			case MESSAGE:
				int message = (int)msg[1];		
				
				textbox.setText(Byte.toString(msg[1]));
				
				break;
			}	
		}

		//Process debug messages from library
		@Override
		public void onNotify(String msg) {
			toast(msg);
		}

		@Override
		public void onConnected() {
			toast("ADK Connected!");
			//something to do when accessory is connected	
		}

		@Override
		public void onDisconnected() {
			//something to do when accessory is disconnected
			
			//not a bad idea to close the application
			finish();
		}


	}
	
	//Helper
	public void toast (final Object msg){
		UIHandler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), msg.toString(), Toast.LENGTH_SHORT).show();	
				Log.i(TAG, msg.toString());
			}
		});
	}
	
}