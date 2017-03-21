/*
 * The MIT License
 *
 * Copyright (c) 2013-2016 reark project contributors
 *
 * https://github.com/reark/reark/graphs/contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.reark.reark.pojo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static io.reark.reark.pojo.NetworkRequestStatus.Status.COMPLETED_WITHOUT_VALUE;
import static io.reark.reark.pojo.NetworkRequestStatus.Status.COMPLETED_WITH_ERROR;
import static io.reark.reark.pojo.NetworkRequestStatus.Status.COMPLETED_WITH_VALUE;
import static io.reark.reark.pojo.NetworkRequestStatus.Status.NONE;
import static io.reark.reark.pojo.NetworkRequestStatus.Status.ONGOING;
import static io.reark.reark.utils.Preconditions.get;

public final class NetworkRequestStatus {

    @NonNull
    private final String uri;

    @NonNull
    private final Status status;

    private final int errorCode;

    @Nullable
    private final String errorMessage;

    public enum Status {
        NONE("none"),
        ONGOING("ongoing"),
        COMPLETED_WITH_VALUE("completedWithValue"),
        COMPLETED_WITHOUT_VALUE("completedWithoutValue"),
        COMPLETED_WITH_ERROR("completedWithError");

        private final String status;

        Status(@NonNull String value) {
            status = value;
        }

        String getStatus() {
            return status;
        }
    }

    private NetworkRequestStatus(@NonNull String uri,
                                 @NonNull Status status,
                                 int errorCode,
                                 @Nullable String errorMessage) {
        this.uri = uri;
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @NonNull
    public static NetworkRequestStatus none() {
        return new NetworkRequestStatus("", NONE, 0, null);
    }

    @NonNull
    public static NetworkRequestStatus ongoing(@NonNull String uri) {
        return new NetworkRequestStatus(get(uri), ONGOING, 0, null);
    }

    @NonNull
    public static NetworkRequestStatus error(@NonNull String uri, int errorCode, @Nullable String errorMessage) {
        return new NetworkRequestStatus(get(uri), COMPLETED_WITH_ERROR, errorCode, errorMessage);
    }

    @NonNull
    public static NetworkRequestStatus completed(@NonNull String uri, boolean withValue) {
        Status status = withValue ? COMPLETED_WITH_VALUE : COMPLETED_WITHOUT_VALUE;
        return new NetworkRequestStatus(get(uri), status, 0, null);
    }

    @NonNull
    public String getUri() {
        return uri;
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isSome() {
        return status != NONE;
    }

    public boolean isNone() {
        return status == NONE;
    }

    public boolean isOngoing() {
        return status == ONGOING;
    }

    public boolean isCompletedWithValue() {
        return status == COMPLETED_WITH_VALUE;
    }

    public boolean isCompletedWithoutValue() {
        return status == COMPLETED_WITHOUT_VALUE;
    }

    public boolean isCompletedWithError() {
        return status == COMPLETED_WITH_ERROR;
    }

    @Override
    public String toString() {
        return "NetworkRequestStatus(" + uri + ", " + status + ")";
    }
}
