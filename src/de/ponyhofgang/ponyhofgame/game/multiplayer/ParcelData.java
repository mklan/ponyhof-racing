package de.ponyhofgang.ponyhofgame.game.multiplayer;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelData implements Parcelable {

    public int playerId;
	public float positionX ;
	public float positionY ;
	public float angle;
	public int lap;
	public int inCollider;
	
	
	
	
	public ParcelData( int playerId, float positionX, float positionY, float angle, int lap, int inCollider){
		
		this.playerId = playerId;
		this.positionX = positionX;
		this.positionY = positionY;
		this.angle = angle;
		this.lap = lap;
		this.inCollider = inCollider;
		;
	}


	public int describeContents() {
		
		return 0;
	}



	public void writeToParcel(Parcel dest, int flags) {
	
		dest.writeInt(playerId);
		dest.writeFloat(positionX);
		dest.writeFloat(positionY);
		dest.writeFloat(angle);
		dest.writeInt(lap);
		dest.writeInt(inCollider);
		
		
		
	}
	

    private ParcelData(Parcel in) {
       
    	playerId = in.readInt();
        positionX = in.readFloat();
        positionY = in.readFloat();
        angle = in.readFloat();
        lap = in.readInt();
        inCollider = in.readInt();
        
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
	
	
	
	

