
package com.twitterclient.models;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import org.parceler.Generated;
import org.parceler.IdentityCollection;
import org.parceler.InjectionUtil;
import org.parceler.ParcelWrapper;
import org.parceler.ParcelerRuntimeException;

@Generated(value = "org.parceler.ParcelAnnotationProcessor", date = "2017-04-02T19:13-0700")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class Url$$Parcelable
    implements Parcelable, ParcelWrapper<com.twitterclient.models.Url>
{

    private com.twitterclient.models.Url url$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<Url$$Parcelable>CREATOR = new Creator<Url$$Parcelable>() {


        @Override
        public Url$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Url$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public Url$$Parcelable[] newArray(int size) {
            return new Url$$Parcelable[size] ;
        }

    }
    ;

    public Url$$Parcelable(com.twitterclient.models.Url url$$2) {
        url$$0 = url$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(url$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.twitterclient.models.Url url$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(url$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(url$$1));
            parcel$$1 .writeString(InjectionUtil.getField(java.lang.String.class, com.twitterclient.models.Url.class, url$$1, "displayUrl"));
            parcel$$1 .writeString(InjectionUtil.getField(java.lang.String.class, com.twitterclient.models.Url.class, url$$1, "expandedUrl"));
            parcel$$1 .writeString(InjectionUtil.getField(java.lang.String.class, com.twitterclient.models.Url.class, url$$1, "url"));
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.twitterclient.models.Url getParcel() {
        return url$$0;
    }

    public static com.twitterclient.models.Url read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.twitterclient.models.Url url$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            url$$4 = new com.twitterclient.models.Url();
            identityMap$$1 .put(reservation$$0, url$$4);
            InjectionUtil.setField(com.twitterclient.models.Url.class, url$$4, "displayUrl", parcel$$3 .readString());
            InjectionUtil.setField(com.twitterclient.models.Url.class, url$$4, "expandedUrl", parcel$$3 .readString());
            InjectionUtil.setField(com.twitterclient.models.Url.class, url$$4, "url", parcel$$3 .readString());
            com.twitterclient.models.Url url$$3 = url$$4;
            identityMap$$1 .put(identity$$1, url$$3);
            return url$$3;
        }
    }

}
