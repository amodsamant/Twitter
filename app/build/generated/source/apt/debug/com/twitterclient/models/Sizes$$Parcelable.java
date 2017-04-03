
package com.twitterclient.models;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.IdentityCollection;
import org.parceler.ParcelWrapper;
import org.parceler.ParcelerRuntimeException;

@Generated(value = "org.parceler.ParcelAnnotationProcessor", date = "2017-04-02T17:27-0700")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class Sizes$$Parcelable
    implements Parcelable, ParcelWrapper<com.twitterclient.models.Sizes>
{

    private com.twitterclient.models.Sizes sizes$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<Sizes$$Parcelable>CREATOR = new Creator<Sizes$$Parcelable>() {


        @Override
        public Sizes$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Sizes$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public Sizes$$Parcelable[] newArray(int size) {
            return new Sizes$$Parcelable[size] ;
        }

    }
    ;

    public Sizes$$Parcelable(com.twitterclient.models.Sizes sizes$$2) {
        sizes$$0 = sizes$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(sizes$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.twitterclient.models.Sizes sizes$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(sizes$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(sizes$$1));
            com.twitterclient.models.Size$$Parcelable.write(sizes$$1 .small, parcel$$1, flags$$0, identityMap$$0);
            com.twitterclient.models.Size$$Parcelable.write(sizes$$1 .large, parcel$$1, flags$$0, identityMap$$0);
            com.twitterclient.models.Size$$Parcelable.write(sizes$$1 .thumb, parcel$$1, flags$$0, identityMap$$0);
            com.twitterclient.models.Size$$Parcelable.write(sizes$$1 .medium, parcel$$1, flags$$0, identityMap$$0);
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.twitterclient.models.Sizes getParcel() {
        return sizes$$0;
    }

    public static com.twitterclient.models.Sizes read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.twitterclient.models.Sizes sizes$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            sizes$$4 = new com.twitterclient.models.Sizes();
            identityMap$$1 .put(reservation$$0, sizes$$4);
            com.twitterclient.models.Size size$$0 = com.twitterclient.models.Size$$Parcelable.read(parcel$$3, identityMap$$1);
            sizes$$4 .small = size$$0;
            com.twitterclient.models.Size size$$1 = com.twitterclient.models.Size$$Parcelable.read(parcel$$3, identityMap$$1);
            sizes$$4 .large = size$$1;
            com.twitterclient.models.Size size$$2 = com.twitterclient.models.Size$$Parcelable.read(parcel$$3, identityMap$$1);
            sizes$$4 .thumb = size$$2;
            com.twitterclient.models.Size size$$3 = com.twitterclient.models.Size$$Parcelable.read(parcel$$3, identityMap$$1);
            sizes$$4 .medium = size$$3;
            com.twitterclient.models.Sizes sizes$$3 = sizes$$4;
            identityMap$$1 .put(identity$$1, sizes$$3);
            return sizes$$3;
        }
    }

}
