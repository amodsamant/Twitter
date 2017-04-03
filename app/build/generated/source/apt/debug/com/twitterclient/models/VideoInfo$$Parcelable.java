
package com.twitterclient.models;

import java.util.ArrayList;
import java.util.List;
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
public class VideoInfo$$Parcelable
    implements Parcelable, ParcelWrapper<com.twitterclient.models.VideoInfo>
{

    private com.twitterclient.models.VideoInfo videoInfo$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Creator<VideoInfo$$Parcelable>CREATOR = new Creator<VideoInfo$$Parcelable>() {


        @Override
        public VideoInfo$$Parcelable createFromParcel(android.os.Parcel parcel$$2) {
            return new VideoInfo$$Parcelable(read(parcel$$2, new IdentityCollection()));
        }

        @Override
        public VideoInfo$$Parcelable[] newArray(int size) {
            return new VideoInfo$$Parcelable[size] ;
        }

    }
    ;

    public VideoInfo$$Parcelable(com.twitterclient.models.VideoInfo videoInfo$$2) {
        videoInfo$$0 = videoInfo$$2;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$0, int flags) {
        write(videoInfo$$0, parcel$$0, flags, new IdentityCollection());
    }

    public static void write(com.twitterclient.models.VideoInfo videoInfo$$1, android.os.Parcel parcel$$1, int flags$$0, IdentityCollection identityMap$$0) {
        int identity$$0 = identityMap$$0 .getKey(videoInfo$$1);
        if (identity$$0 != -1) {
            parcel$$1 .writeInt(identity$$0);
        } else {
            parcel$$1 .writeInt(identityMap$$0 .put(videoInfo$$1));
            if (videoInfo$$1 .variants == null) {
                parcel$$1 .writeInt(-1);
            } else {
                parcel$$1 .writeInt(videoInfo$$1 .variants.size());
                for (com.twitterclient.models.Variants variants$$0 : ((List<com.twitterclient.models.Variants> ) videoInfo$$1 .variants)) {
                    com.twitterclient.models.Variants$$Parcelable.write(variants$$0, parcel$$1, flags$$0, identityMap$$0);
                }
            }
        }
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public com.twitterclient.models.VideoInfo getParcel() {
        return videoInfo$$0;
    }

    public static com.twitterclient.models.VideoInfo read(android.os.Parcel parcel$$3, IdentityCollection identityMap$$1) {
        int identity$$1 = parcel$$3 .readInt();
        if (identityMap$$1 .containsKey(identity$$1)) {
            if (identityMap$$1 .isReserved(identity$$1)) {
                throw new ParcelerRuntimeException("An instance loop was detected whild building Parcelable and deseralization cannot continue.  This error is most likely due to using @ParcelConstructor or @ParcelFactory.");
            }
            return identityMap$$1 .get(identity$$1);
        } else {
            com.twitterclient.models.VideoInfo videoInfo$$4;
            int reservation$$0 = identityMap$$1 .reserve();
            videoInfo$$4 = new com.twitterclient.models.VideoInfo();
            identityMap$$1 .put(reservation$$0, videoInfo$$4);
            int int$$0 = parcel$$3 .readInt();
            ArrayList<com.twitterclient.models.Variants> list$$0;
            if (int$$0 < 0) {
                list$$0 = null;
            } else {
                list$$0 = new ArrayList<com.twitterclient.models.Variants>(int$$0);
                for (int int$$1 = 0; (int$$1 <int$$0); int$$1 ++) {
                    com.twitterclient.models.Variants variants$$1 = com.twitterclient.models.Variants$$Parcelable.read(parcel$$3, identityMap$$1);
                    list$$0 .add(variants$$1);
                }
            }
            videoInfo$$4 .variants = list$$0;
            com.twitterclient.models.VideoInfo videoInfo$$3 = videoInfo$$4;
            identityMap$$1 .put(identity$$1, videoInfo$$3);
            return videoInfo$$3;
        }
    }

}
