//Libraries:
#include <Wire.h>
#include <VMA430_GPS.h>     // Include the GPS module library
#include <SoftwareSerial.h> // Include the software serial library
SoftwareSerial ss(3, 2); // RX, TX
VMA430_GPS gps(&ss);     // Pass the softwareserial connection info the the GPS module library

//Variables and constants:
const int ID_Machine = 1; //ID Machine of the HYPOTHETICAL device
const int MPU = 0x68; //I2C address of the MPU-6050
int16_t GyX,GyY,GyZ;
int x, y, z;

//Set-up:
void setup(){
  Wire.begin();
  Wire.beginTransmission(MPU);
  Wire.write(0x6B); //PWR_MGMT_1 register
  Wire.write(0); //set to zero (wakes up the MPU-6050)
  Wire.endTransmission(true);
  Serial.println("Hello");
  gps.begin(9600); // Sets up the GPS module to communicate with the Arduino over serial at 9600 baud
  gps.setUBXNav(); // Enable the UBX mavigation messages to be sent from the GPS module
  Serial.begin(9600);
}

//Loop:
void loop(){
  Wire.beginTransmission(MPU);
  Wire.write(0x3B); //starting with register 0x3B (ACCEL_XOUT_H)
  Wire.endTransmission(false);
  Wire.requestFrom(MPU,14,true); //request a total of 14 registers
  GyX = Wire.read()<<8 | Wire.read(); //0x43 (GYRO_XOUT_H) & 0x44 (GYRO_XOUT_L)
  GyY = Wire.read()<<8 | Wire.read(); //0x45 (GYRO_YUT_H) & 0x46 (GYRO_YOUT_L)
  GyZ = Wire.read()<<8 | Wire.read(); // 0x47 (GYRO_ZOUT_H) & 0x48 (GYRO_ZOUT_L)
  
  if(GyX > 0 || GyY > 0 || GyZ > 0){
    Serial.print(" | GyX = "); Serial.print(GyX);
    Serial.print(" | GyY = "); Serial.print(GyY);
    Serial.print(" | GyZ = "); Serial.println(GyZ);
  } else if(GyX < 1000 || GyZ < 0){
    Serial.println("");
    Serial.print(" | ID_Machine@"); Serial.println(ID_Machine);Serial.println("@");
    Serial.println("Locating...");
    if (gps.getUBX_packet()){ // If a valid GPS UBX data packet is received...
      gps.parse_ubx_data(); // Parse the new data
      if (gps.utc_time.valid){ // If the utc_time passed from the GPS is valid...
        // Print UTC time hh:mm:ss
        Serial.println();
        Serial.print("UTC time: ");
        Serial.print(gps.utc_time.hour);
        Serial.print(":");
        Serial.print(gps.utc_time.minute);
        Serial.print(":");
        Serial.print(gps.utc_time.second);
        Serial.println();
      }
    // Print location (latitude/longitude)
    Serial.println();
    Serial.print("location: ");
    Serial.print("Lat: ");
    Serial.print(gps.location.latitude, 8); // to 8 decimals
    Serial.print(" Long: ");
    Serial.print(gps.location.longitude, 8); // to 8 decimals
    }
  }
  delay(1000);
}
