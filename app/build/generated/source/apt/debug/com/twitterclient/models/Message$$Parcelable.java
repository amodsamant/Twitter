
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
public class Message$$Parcelable
    implements Parcelable, ParcelWrapper<com.twitterclient.models.Message>
{

    private com.twitterclient.models.Message message$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<Message$$Parcelable>CREATOR = new Creator<Message$$Parcelable>() {


        @Override
        public Message$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Message$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public Message$$Parcelable[] newArray(int size) {
            return new Message$$Parcelable[size] ;
        }

    }
    ;

    public Message$$Parcelable(com.twitterclient.models.Message message$$2) {
        message$$0 = message$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(message$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.twitterclient.models.Message message$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(message$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(message$$1));
            parcel$$1 .writeString(message$$1 .createdAt);
            com.twitterclient.models.User$$Parcelable.write(message$$1 .sender, parcel$$1, flags$$0, identityMap$$0);
            com.twitterclient.models.User$$Parcelable.write(message$$1 .recipient, parcel$$1, flags$$0, identityMap$$0);
            parcel$$1 .writeLong(message$$1 .id);
            parcel$$1 .writeString(message$$1 .text);
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.twitterclient.models.Message getParcel() {
        return message$$0;
    }

    public static com.twitterclient.models.Message read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.twitterclient.models.Message message$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            message$$4 = new com.twitterclient.models.Message();
            identityMap$$1 .put(reservation$$0, message$$4);
            message$$4 .createdAt = parcel$$3 .readString();
            com.twitterclient.models.User user$$0 = com.twitterclient.models.User$$Parcelable.read(parcel$$3, identityMap$$1);
            message$$4 .sender = user$$0;
            com.twitterclient.models.User user$$1 = com.twitterclient.models.User$$Parcelable.read(parcel$$3, identityMap$$1);
            message$$4 .recipient = user$$1;
            message$$4 .id = parcel$$3 .readLong();
            message$$4 .text = parcel$$3 .readString();
            com.twitterclient.models.Message message$$3 = message$$4;
            identityMap$$1 .put(identity$$1, message$$3);
            return message$$3;
        }
    }

}
