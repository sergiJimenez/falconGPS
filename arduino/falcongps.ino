#include <Wire.h>
#include <VMA430_GPS.h>
#include <SoftwareSerial.h>
SoftwareSerial ss(3, 2);
VMA430_GPS gps(&ss);

const int ID_Machine = 1;
const int MPU = 0x68;
int16_t GyX,GyY,GyZ;

void setup(){
  Wire.begin();
  Wire.beginTransmission(MPU);
  Wire.write(0x6B);
  Wire.write(0);
  Wire.endTransmission(true);
  Serial.println();
  gps.begin(9600);
  gps.setUBXNav();
  Serial.begin(9600);
}

void loop(){
  Wire.beginTransmission(MPU);
  Wire.write(0x3B);
  Wire.endTransmission(false);
  Wire.requestFrom(MPU,14,true);
  GyX = Wire.read()<<8 | Wire.read();
  GyY = Wire.read()<<8 | Wire.read();
  GyZ = Wire.read()<<8 | Wire.read();
  
  if(GyX < 1000){
    Serial.print(" | GyX = "); Serial.print(GyX);
    Serial.print(" | GyY = "); Serial.print(GyY);
    Serial.print(" | GyZ = "); Serial.println(GyZ);
  } else if(GyX > 1000 ){
    Serial.println("");
    Serial.print(" | ID_Machine@");
    Serial.println("Locating...");
    if (gps.getUBX_packet()){
      gps.parse_ubx_data();
      if (gps.utc_time.valid){
        Serial.println();
        Serial.print("UTC time: ");
        Serial.print(gps.utc_time.hour);
        Serial.print(":");
        Serial.print(gps.utc_time.minute);
        Serial.print(":");
        Serial.print(gps.utc_time.second);
        Serial.println();
      }
    Serial.println();
    Serial.print("location: ");
    Serial.print("Lat: ");
    Serial.print(gps.location.latitude, 8);
    Serial.print(" Long: ");
    Serial.print(gps.location.longitude, 8);
    }
  }
  delay(1000);
}