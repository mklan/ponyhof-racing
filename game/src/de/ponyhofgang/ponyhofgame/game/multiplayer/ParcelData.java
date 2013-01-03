package de.ponyhofgang.ponyhofgame.game.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelData implements Parcelable {

	public byte mapIsSelected; 
	public byte carIsSelected;
	public byte gameIsPaused;
	public float positionX ;
	public float positionY ;
	public float angle;
	public int whichMap; 
	public int whichCar;
	
	
	
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
		
		return 0;
	}



	public void writeToParcel(Parcel dest, int flags) {
	
		dest.writeByte(mapIsSelected);
		dest.writeByte(carIsSelected);
		dest.writeByte(gameIsPaused);
		dest.writeFloat(positionX);
		dest.writeFloat(positionY);
		dest.writeFloat(angle);
		dest.writeInt(whichCar);
		dest.writeInt(whichMap);
		
	}
	

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
	
    

    
	 public static final Parcelable.Creator<ParcelData> CREATOR = new Parcelable.Creator<ParcelData>() {
	       
		    public ParcelData createFromParcel(Parcel in) {
	            return new ParcelData(in);
	        }

			public ParcelData[] newArray(int size) {
				// TODO Auto-generated method stub
				return new ParcelData[size];
			}
	 	};
	 	
}
	
	
	
	

