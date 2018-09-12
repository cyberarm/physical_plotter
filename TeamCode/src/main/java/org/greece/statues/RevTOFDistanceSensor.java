package org.greece.statues;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.I2cSensor;
import com.qualcomm.robotcore.util.TypeConversion;

@I2cSensor(name = "REV Distance Sensor", description = "REV TOF Distance Sensor", xmlTag = "RevTOFDistanceSensor")
public class RevTOFDistanceSensor extends I2cDeviceSynchDevice<I2cDeviceSynch> {
  public RevTOFDistanceSensor(I2cDeviceSynch i2cDeviceSynch, boolean deviceClientIsOwned) {
    super(i2cDeviceSynch, deviceClientIsOwned);

    this.deviceClient.setI2cAddress(I2cAddr.create7bit(0x52));

    super.registerArmingStateCallback(false);
    this.deviceClient.engage();
  }

  @Override
  protected boolean doInitialize() {
    return true;
  }

  @Override
  public Manufacturer getManufacturer() {
    return Manufacturer.Adafruit;
  }

  @Override
  public String getDeviceName() {
    return "REV 2 Meter Distance Sensor";
  }

  public enum Register {
    WHO_AM_I(0xC0);

    public int bVal;
    Register(int bVal) {
      this.bVal = bVal;
    }
  }

  protected long readLong(Register reg) {
    return TypeConversion.byteArrayToLong((deviceClient.read(reg.bVal, 8)));
  }

  public long getWhoAmIRAW() {
    return readLong(Register.WHO_AM_I);
  }
}
