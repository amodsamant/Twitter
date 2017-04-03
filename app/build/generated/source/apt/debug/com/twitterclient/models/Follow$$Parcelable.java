
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
public class Follow$$Parcelable
    implements Parcelable, ParcelWrapper<com.twitterclient.models.Follow>
{

    private com.twitterclient.models.Follow follow$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<Follow$$Parcelable>CREATOR = new Creator<Follow$$Parcelable>() {


        @Override
        public Follow$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Follow$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public Follow$$Parcelable[] newArray(int size) {
            return new Follow$$Parcelable[size] ;
        }

    }
    ;

    public Follow$$Parcelable(com.twitterclient.models.Follow follow$$2) {
        follow$$0 = follow$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(follow$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.twitterclient.models.Follow follow$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(follow$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(follow$$1));
            if (follow$$1 .users == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(follow$$1 .users.size());
                for (com.twitterclient.models.User user$$0 : ((List<com.twitterclient.models.User> ) follow$$1 .users)) {
                    com.twitterclient.models.User$$Parcelable.write(user$$0, parcel$$1, flags$$0, identityMap$$0);
                }
            }
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.twitterclient.models.Follow getParcel() {
        return follow$$0;
    }

    public static com.twitterclient.models.Follow read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.twitterclient.models.Follow follow$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            follow$$4 = new com.twitterclient.models.Follow();
            identityMap$$1 .put(reservation$$0, follow$$4);
            int int$$0 = parcel$$3 .readInt();
            ArrayList<com.twitterclient.models.User> list$$0;
            if (int$$0 < 0) {
                list$$0 = null;
            } else {
                list$$0 = new ArrayList<com.twitterclient.models.User>(int$$0);
                for (int int$$1 = 0; (int$$1 <int$$0); int$$1 ++) {
                    com.twitterclient.models.User user$$1 = com.twitterclient.models.User$$Parcelable.read(parcel$$3, identityMap$$1);
                    list$$0 .add(user$$1);
                }
            }
            follow$$4 .users = list$$0;
            com.twitterclient.models.Follow follow$$3 = follow$$4;
            identityMap$$1 .put(identity$$1, follow$$3);
            return follow$$3;
        }
    }

}
