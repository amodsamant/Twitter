
package com.twitterclient.models;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.IdentityCollection;
import org.parceler.ParcelWrapper;
import org.parceler.ParcelerRuntimeException;

@Generated(value = "org.parceler.ParcelAnnotationProcessor", date = "2017-04-02T20:05-0700")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class Size$$Parcelable
    implements Parcelable, ParcelWrapper<com.twitterclient.models.Size>
{

    private com.twitterclient.models.Size size$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<Size$$Parcelable>CREATOR = new Creator<Size$$Parcelable>() {


        @Override
        public Size$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Size$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public Size$$Parcelable[] newArray(int size) {
            return new Size$$Parcelable[size] ;
        }

    }
    ;

    public Size$$Parcelable(com.twitterclient.models.Size size$$2) {
        size$$0 = size$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(size$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.twitterclient.models.Size size$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(size$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(size$$1));
            parcel$$1 .writeInt(size$$1 .w);
            parcel$$1 .writeInt(size$$1 .h);
            parcel$$1 .writeString(size$$1 .resize);
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.twitterclient.models.Size getParcel() {
        return size$$0;
    }

    public static com.twitterclient.models.Size read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.twitterclient.models.Size size$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            size$$4 = new com.twitterclient.models.Size();
            identityMap$$1 .put(reservation$$0, size$$4);
            size$$4 .w = parcel$$3 .readInt();
            size$$4 .h = parcel$$3 .readInt();
            size$$4 .resize = parcel$$3 .readString();
            com.twitterclient.models.Size size$$3 = size$$4;
            identityMap$$1 .put(identity$$1, size$$3);
            return size$$3;
        }
    }

}
