
package com.twitterclient.models;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.IdentityCollection;
import org.parceler.ParcelWrapper;
import org.parceler.ParcelerRuntimeException;

@Generated(value = "org.parceler.ParcelAnnotationProcessor", date = "2017-04-02T19:13-0700")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class Statuses$$Parcelable
    implements Parcelable, ParcelWrapper<com.twitterclient.models.Statuses>
{

    private com.twitterclient.models.Statuses statuses$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<Statuses$$Parcelable>CREATOR = new Creator<Statuses$$Parcelable>() {


        @Override
        public Statuses$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Statuses$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public Statuses$$Parcelable[] newArray(int size) {
            return new Statuses$$Parcelable[size] ;
        }

    }
    ;

    public Statuses$$Parcelable(com.twitterclient.models.Statuses statuses$$2) {
        statuses$$0 = statuses$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(statuses$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.twitterclient.models.Statuses statuses$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(statuses$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(statuses$$1));
            if (statuses$$1 .tweets == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(statuses$$1 .tweets.size());
                for (com.twitterclient.models.Tweet tweet$$0 : ((List<com.twitterclient.models.Tweet> ) statuses$$1 .tweets)) {
                    com.twitterclient.models.Tweet$$Parcelable.write(tweet$$0, parcel$$1, flags$$0, identityMap$$0);
                }
            }
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.twitterclient.models.Statuses getParcel() {
        return statuses$$0;
    }

    public static com.twitterclient.models.Statuses read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.twitterclient.models.Statuses statuses$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            statuses$$4 = new com.twitterclient.models.Statuses();
            identityMap$$1 .put(reservation$$0, statuses$$4);
            int int$$0 = parcel$$3 .readInt();
            ArrayList<com.twitterclient.models.Tweet> list$$0;
            if (int$$0 < 0) {
                list$$0 = null;
            } else {
                list$$0 = new ArrayList<com.twitterclient.models.Tweet>(int$$0);
                for (int int$$1 = 0; (int$$1 <int$$0); int$$1 ++) {
                    com.twitterclient.models.Tweet tweet$$1 = com.twitterclient.models.Tweet$$Parcelable.read(parcel$$3, identityMap$$1);
                    list$$0 .add(tweet$$1);
                }
            }
            statuses$$4 .tweets = list$$0;
            com.twitterclient.models.Statuses statuses$$3 = statuses$$4;
            identityMap$$1 .put(identity$$1, statuses$$3);
            return statuses$$3;
        }
    }

}
