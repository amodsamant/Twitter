
package com.twitterclient.models;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.IdentityCollection;
import org.parceler.ParcelWrapper;
import org.parceler.ParcelerRuntimeException;

@Generated(value = "org.parceler.ParcelAnnotationProcessor", date = "2017-04-02T19:40-0700")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class Variants$$Parcelable
    implements Parcelable, ParcelWrapper<com.twitterclient.models.Variants>
{

    private com.twitterclient.models.Variants variants$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<Variants$$Parcelable>CREATOR = new Creator<Variants$$Parcelable>() {


        @Override
        public Variants$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Variants$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public Variants$$Parcelable[] newArray(int size) {
            return new Variants$$Parcelable[size] ;
        }

    }
    ;

    public Variants$$Parcelable(com.twitterclient.models.Variants variants$$2) {
        variants$$0 = variants$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(variants$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.twitterclient.models.Variants variants$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(variants$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(variants$$1));
            parcel$$1 .writeInt(variants$$1 .bitRate);
            parcel$$1 .writeString(variants$$1 .contentType);
            parcel$$1 .writeString(variants$$1 .url);
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.twitterclient.models.Variants getParcel() {
        return variants$$0;
    }

    public static com.twitterclient.models.Variants read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.twitterclient.models.Variants variants$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            variants$$4 = new com.twitterclient.models.Variants();
            identityMap$$1 .put(reservation$$0, variants$$4);
            variants$$4 .bitRate = parcel$$3 .readInt();
            variants$$4 .contentType = parcel$$3 .readString();
            variants$$4 .url = parcel$$3 .readString();
            com.twitterclient.models.Variants variants$$3 = variants$$4;
            identityMap$$1 .put(identity$$1, variants$$3);
            return variants$$3;
        }
    }

}
