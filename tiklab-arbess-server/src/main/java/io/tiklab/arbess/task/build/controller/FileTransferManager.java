package io.tiklab.arbess.task.build.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileTransferManager {
    private static final Map<String, FileTransferSession> SESSION_MAP = new ConcurrentHashMap<>();

    public static void register(String requestId,FileTransferSession session) {
        SESSION_MAP.put(requestId, session);
    }

    public static FileTransferSession get(String requestId) {
        return SESSION_MAP.get(requestId);
    }

    public static void remove(String requestId) {
        SESSION_MAP.remove(requestId);
    }
}
