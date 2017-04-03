
package com.twitterclient.models;

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
public class Media$$Parcelable
    implements Parcelable, ParcelWrapper<com.twitterclient.models.Media>
{

    private com.twitterclient.models.Media media$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<Media$$Parcelable>CREATOR = new Creator<Media$$Parcelable>() {


        @Override
        public Media$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new Media$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public Media$$Parcelable[] newArray(int size) {
            return new Media$$Parcelable[size] ;
        }

    }
    ;

    public Media$$Parcelable(com.twitterclient.models.Media media$$2) {
        media$$0 = media$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(media$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.twitterclient.models.Media media$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(media$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(media$$1));
            parcel$$1 .writeString(media$$1 .idStr);
            parcel$$1 .writeString(media$$1 .displayUrl);
            parcel$$1 .writeString(media$$1 .mediaUrl);
            com.twitterclient.models.Sizes$$Parcelable.write(media$$1 .sizes, parcel$$1, flags$$0, identityMap$$0);
            parcel$$1 .writeString(media$$1 .mediaUrlHttps);
            com.twitterclient.models.VideoInfo$$Parcelable.write(media$$1 .videoInfo, parcel$$1, flags$$0, identityMap$$0);
            parcel$$1 .writeLong(media$$1 .id);
            parcel$$1 .writeString(media$$1 .expandedUrl);
            parcel$$1 .writeString(media$$1 .type);
            parcel$$1 .writeString(media$$1 .url);
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.twitterclient.models.Media getParcel() {
        return media$$0;
    }

    public static com.twitterclient.models.Media read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.twitterclient.models.Media media$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            media$$4 = new com.twitterclient.models.Media();
            identityMap$$1 .put(reservation$$0, media$$4);
            media$$4 .idStr = parcel$$3 .readString();
            media$$4 .displayUrl = parcel$$3 .readString();
            media$$4 .mediaUrl = parcel$$3 .readString();
            Sizes sizes$$0 = com.twitterclient.models.Sizes$$Parcelable.read(parcel$$3, identityMap$$1);
            media$$4 .sizes = sizes$$0;
            media$$4 .mediaUrlHttps = parcel$$3 .readString();
            VideoInfo videoInfo$$0 = com.twitterclient.models.VideoInfo$$Parcelable.read(parcel$$3, identityMap$$1);
            media$$4 .videoInfo = videoInfo$$0;
            media$$4 .id = parcel$$3 .readLong();
            media$$4 .expandedUrl = parcel$$3 .readString();
            media$$4 .type = parcel$$3 .readString();
            media$$4 .url = parcel$$3 .readString();
            com.twitterclient.models.Media media$$3 = media$$4;
            identityMap$$1 .put(identity$$1, media$$3);
            return media$$3;
        }
    }

}
