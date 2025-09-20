package io.tiklab.arbess.task.build.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

public class FileTransferSession {

    private final String requestId;        // 唯一请求ID
    private final OutputStream out;        // HTTP 响应输出流
    private final CountDownLatch latch;    // 用来阻塞等待直到传输完成或出错
    private volatile Throwable error;      // 存储错误信息

    public FileTransferSession(String requestId, OutputStream out) {
        this.requestId = requestId;
        this.out = out;
        this.latch = new CountDownLatch(1); // 初始值为1，等待完成或出错
    }

    public String getRequestId() {
        return requestId;
    }

    /** WebSocket 收到一个分片后调用，把数据写入 HTTP 响应流 */
    public void write(byte[] data) throws IOException {
        out.write(data);
        out.flush(); // 及时推送给客户端，避免缓存太久
    }

    /** WebSocket 收到最后一个分片时调用，通知传输完成 */
    public void complete() {
        latch.countDown(); // 唤醒 awaitCompletion()
    }

    /** WebSocket 收到错误时调用 */
    public void error(Throwable e) {
        this.error = e;
        latch.countDown(); // 也要唤醒等待线程
    }

    public void cancel() {
        latch.countDown(); // 提前结束阻塞
    }

    /** HTTP 线程调用，阻塞直到传输完成或出错 */
    public void awaitCompletion() throws InterruptedException, IOException {
        latch.await(); // 等待 complete() 或 error()
        if (error != null) {
            if (error instanceof IOException) {
                throw (IOException) error;
            } else {
                throw new IOException("文件传输失败", error);
            }
        }
    }
}
