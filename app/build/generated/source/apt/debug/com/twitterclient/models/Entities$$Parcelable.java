
package com.twitterclient.models;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.IdentityCollection;
import org.parceler.ParcelWrapper;
import org.parceler.ParcelerRuntimeException;

@Generated(value = "org.parceler.ParcelAnnotationProcessor", date = "2017-04-02T20:10-0700")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class Entities$$Parcelable
    implements Parcelable, ParcelWrapper<com.twitterclient.models.Entities>
{

    private com.twitterclient.models.Entities entities$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<Entities$$Parcelable>CREATOR = new Creator<Entities$$Parcelable>() {


        @Override
        public Entities$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Entities$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public Entities$$Parcelable[] newArray(int size) {
            return new Entities$$Parcelable[size] ;
        }

    }
    ;

    public Entities$$Parcelable(com.twitterclient.models.Entities entities$$2) {
        entities$$0 = entities$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(entities$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.twitterclient.models.Entities entities$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(entities$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(entities$$1));
            if (entities$$1 .urls == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(entities$$1 .urls.size());
                for (com.twitterclient.models.Url url$$0 : ((java.util.List<com.twitterclient.models.Url> ) entities$$1 .urls)) {
                    com.twitterclient.models.Url$$Parcelable.write(url$$0, parcel$$1, flags$$0, identityMap$$0);
                }
            }
            if (entities$$1 .media == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(entities$$1 .media.size());
                for (com.twitterclient.models.Media media$$0 : ((java.util.List<com.twitterclient.models.Media> ) entities$$1 .media)) {
                    com.twitterclient.models.Media$$Parcelable.write(media$$0, parcel$$1, flags$$0, identityMap$$0);
                }
            }
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.twitterclient.models.Entities getParcel() {
        return entities$$0;
    }

    public static com.twitterclient.models.Entities read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.twitterclient.models.Entities entities$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            entities$$4 = new com.twitterclient.models.Entities();
            identityMap$$1 .put(reservation$$0, entities$$4);
            int int$$0 = parcel$$3 .readInt();
            java.util.ArrayList<com.twitterclient.models.Url> list$$0;
            if (int$$0 < 0) {
                list$$0 = null;
            } else {
                list$$0 = new java.util.ArrayList<com.twitterclient.models.Url>(int$$0);
                for (int int$$1 = 0; (int$$1 <int$$0); int$$1 ++) {
                    com.twitterclient.models.Url url$$1 = com.twitterclient.models.Url$$Parcelable.read(parcel$$3, identityMap$$1);
                    list$$0 .add(url$$1);
                }
            }
            entities$$4 .urls = list$$0;
            int int$$2 = parcel$$3 .readInt();
            java.util.ArrayList<com.twitterclient.models.Media> list$$1;
            if (int$$2 < 0) {
                list$$1 = null;
            } else {
                list$$1 = new java.util.ArrayList<com.twitterclient.models.Media>(int$$2);
                for (int int$$3 = 0; (int$$3 <int$$2); int$$3 ++) {
                    com.twitterclient.models.Media media$$1 = com.twitterclient.models.Media$$Parcelable.read(parcel$$3, identityMap$$1);
                    list$$1 .add(media$$1);
                }
            }
            entities$$4 .media = list$$1;
            com.twitterclient.models.Entities entities$$3 = entities$$4;
            identityMap$$1 .put(identity$$1, entities$$3);
            return entities$$3;
        }
    }

}
