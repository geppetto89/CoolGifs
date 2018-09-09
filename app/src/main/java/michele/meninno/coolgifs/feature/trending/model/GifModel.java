package michele.meninno.coolgifs.feature.trending.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model to dispatch to the view
 * @author Michele Meninno
 */
public class GifModel implements Parcelable {

    private String framePreviewUrl;
    private String gifUrl;
    private String id;

    public GifModel(String framePreviewUrl, String gifUrl, String id) {
        this.framePreviewUrl = framePreviewUrl;
        this.gifUrl = gifUrl;
        this.id = id;
    }


    public String getFramePreviewUrl() {
        return framePreviewUrl;
    }

    public void setFramePreviewUrl(String framePreviewUrl) {
        this.framePreviewUrl = framePreviewUrl;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.framePreviewUrl);
        dest.writeString(this.gifUrl);
    }

    protected GifModel(Parcel in) {
        this.framePreviewUrl = in.readString();
        this.gifUrl = in.readString();
    }

    public static final Parcelable.Creator<GifModel> CREATOR = new Parcelable.Creator<GifModel>() {
        @Override
        public GifModel createFromParcel(Parcel source) {
            return new GifModel(source);
        }

        @Override
        public GifModel[] newArray(int size) {
            return new GifModel[size];
        }
    };
}
