package android.examples.sampleyoutube.model;

import android.os.Parcel;
import android.os.Parcelable;

public class YouTubeDataModel implements Parcelable {
    private String mTitle;
    private String mDescription;
    private String mDatePublish;
    private String mThumbanail;
    private String mVideoId;

    public String getVideoId() {
        return mVideoId;
    }

    public void setVideoId(String mVideoId) {
        this.mVideoId = mVideoId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getDatePublish() {
        return mDatePublish;
    }

    public void setDatePublish(String mDatePublish) {
        this.mDatePublish = mDatePublish;
    }

    public String getThumbanail() {
        return mThumbanail;
    }

    public void setThumbanail(String mthumbanail) {
        this.mThumbanail = mthumbanail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mThumbanail);
        dest.writeString(mVideoId);
    }

    public YouTubeDataModel() {
        super();
    }


    protected YouTubeDataModel(Parcel in) {
        this();
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        this.mTitle = in.readString();
        this.mDescription = in.readString();
        this.mThumbanail = in.readString();
        this.mVideoId = in.readString();

    }

    public static final Creator<YouTubeDataModel> CREATOR = new Creator<YouTubeDataModel>() {
        @Override
        public YouTubeDataModel createFromParcel(Parcel in) {
            return new YouTubeDataModel(in);
        }

        @Override
        public YouTubeDataModel[] newArray(int size) {
            return new YouTubeDataModel[size];
        }
    };
}
