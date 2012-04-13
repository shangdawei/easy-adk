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

/* Demo firmware for accessory */

#include <Max3421e.h>
#include <Usb.h>
#include <AndroidAccessory.h>
#define  LED_PIN  13

//Protocol Messages
#define MESSAGE 'm'

//ADK Communication Frequency (Hz)
#define COMM_FREQ 200
#define MSG_FREQ 0.5

// ADK Config
AndroidAccessory acc("Demo",
					 "Accessory",
					 "EasyADK Demo Accessory",
					 "0.1",
					 "http://code.google.com/p/easy-adk/",
					 "0000000012345678");

//Main Timer
long timer_comm = millis();
long timer_msg = millis();

void setup()
{
	// Serial Communication
	Serial.begin(115200);
	Serial.print("Hello World!\n");
	pinMode(LED_PIN, OUTPUT);

	// ADK Config
	acc.powerOn();
}

void loop()
{
	byte msg[2];

	//ADK Communication
	if (acc.isConnected() && millis()-timer_comm >= 1000/COMM_FREQ) {
		timer_comm = millis();
		//Always Read
		int len = acc.read(msg, sizeof(msg), 1); // read data into msg variable
		if (len > 0) {
			if (msg[0] == MESSAGE){
				Serial.print(msg[1]);
				}
			}else{
				Serial.print("Unknown Message");
			}
		}
	}

	//Message update, 0.5 Hz loop
	if (acc.isConnected() && millis()-timer_batt >= 1000/BATT_FREQ) {
		timer_batt = millis();
		//Serial.print("b");
		msg[0] = MESSAGE;
		msg[1] = 'a';
		acc.write(msg, 2);

	}

	//What to do if not connected
	if (!acc.isConnected()){

	}
}