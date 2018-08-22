package michele.meninno.coolgifs.network.dto;

import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("total_count")
    private long totalCount;

    @SerializedName("count")
    private long count;

    @SerializedName("offset")
    private long offset;

    public long getTotalCount() {
        return totalCount;
    }

    public long getCount() {
        return count;
    }

    public long getOffset() {
        return offset;
    }
}
