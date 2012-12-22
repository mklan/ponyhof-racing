package de.ponyhofgang.ponyhofgame.game.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelData implements Parcelable {

	public byte mapIsSelected, carIsSelected,  gameIsPaused;
	public float positionX, positionY, angle;
	public int whichMap, whichCar;
	
	
	
	public ParcelData(byte mapIsSelected, byte carIsSelected, byte gameIsPaused, float positionX, float positionY, float angle, int whichMap, int whichCar){

		this.mapIsSelected = mapIsSelected;
		this.carIsSelected = carIsSelected;
		this.gameIsPaused = gameIsPaused;
		this.positionX = positionX;
		this.positionY = positionY;
		this.angle = angle;
		this.whichCar = whichCar;
		this.whichMap = whichMap;
	}


	public int describeContents() {
		// TODO Auto-generated method stub
		return 3;
	}



	public synchronized void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
		dest.writeByte(mapIsSelected);
		dest.writeByte(carIsSelected);
		dest.writeByte(gameIsPaused);
		dest.writeFloat(positionX);
		dest.writeFloat(positionY);
		dest.writeFloat(angle);
		dest.writeInt(whichCar);
		dest.writeInt(whichMap);
		
	}
	
	 public static final Parcelable.Creator<ParcelData> CREATOR = new Parcelable.Creator<ParcelData>() {
	        public ParcelData createFromParcel(Parcel in) {
	            return new ParcelData(in);
	        }

			public ParcelData[] newArray(int size) {
				// TODO Auto-generated method stub
				return new ParcelData[size];
			}
	 	};

        private ParcelData(Parcel in) {
            mapIsSelected = in.readByte();
            carIsSelected = in.readByte();
            gameIsPaused = in.readByte();
            positionX = in.readFloat();
            positionY = in.readFloat();
            angle = in.readFloat();
            whichMap = in.readInt();
            whichCar = in.readInt();
        }
	
	
	
	
}
