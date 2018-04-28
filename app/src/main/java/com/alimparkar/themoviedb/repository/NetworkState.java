package com.alimparkar.themoviedb.repository;

/**
 * Created by alimparkar on 25/04/18.
 */

public class NetworkState {
    public enum STATUS {
        RUNNING,
        SUCCESS,
        FAILED
    }

    private STATUS status;
    private String message;

    public NetworkState(STATUS status, String message) {
        this.status = status;
        this.message = message;
    }

    public STATUS getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "NetworkState{" + "status=" + status.name() + ", message='" + message + '\'' + '}';
    }
}
