package demo;

import java.io.*;
import java.net.Socket;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2020-03-16 17:30
 */
public class SocketHandler implements Runnable {
    final static String CRLF = "\r\n";   // 1

    private Socket clientSocket;

    public SocketHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    private void handleSocket(Socket clientSocket) throws IOException {

        // 输入流，读Socket
        // 使用了转换流，可以将字节流操作转为字符流操作)
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
        );
        // 输出流，写到Socket
        // (使用了转换流，可以将字节流操作转为字符流操作)
        PrintWriter out = new PrintWriter(
                new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())),
                true
        );

        // 读取请求体
        String requestHeader = "";
        String s;
        while ((s = in.readLine()) != null) {
            // 2 很重要，默认情况下in.readLine的结果中`\r\n`被去掉了
            s += CRLF;
            requestHeader = requestHeader + s;
            // 请求头的分界
            if (s.equals(CRLF)) {
                break;
            }
        }
        System.out.println("客户端请求头：");
        System.out.println(requestHeader);

        //响应体
        String responseBody = "客户端的请求头是：\n" + requestHeader;

        //拼接响应头
        String responseHeader = "HTTP/1.0 200 OK\r\n" +
                "Content-Type: text/plain; charset=UTF-8\r\n" +
                "Content-Length: " + responseBody.getBytes().length + "\r\n" +
                "\r\n";

        System.out.println("响应头：");
        System.out.println(responseHeader);

        out.println(responseHeader);
        out.println(responseBody);

        // 关闭流
        out.close();
        in.close();
        // 关闭Socket
        clientSocket.close();
    }

    public void run() {
        try {
            handleSocket(clientSocket);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
