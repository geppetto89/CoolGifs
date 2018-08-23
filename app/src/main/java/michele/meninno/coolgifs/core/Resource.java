package michele.meninno.coolgifs.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Resource<T> {

    @NonNull
    private Status status;
    @Nullable
    private Throwable throwable;
    @Nullable
    private T data;

    public Resource(@NonNull Status status, Throwable throwable, T data) {
        this.status = status;
        this.throwable = throwable;
        this.data = data;
    }

    public void setStatus(@NonNull Status status) {
        this.status = status;
    }

    public void setThrowable(@Nullable Throwable throwable) {
        this.throwable = throwable;
    }

    public void setData(@Nullable T data) {
        this.data = data;
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public Throwable getThrowable() {
        return throwable;
    }

    @Nullable
    public T getData() {
        return data;
    }

    public enum Status {
        SUCCESS, ERROR, LOADING, EMPTY
    }
}
