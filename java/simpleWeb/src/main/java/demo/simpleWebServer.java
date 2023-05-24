package demo;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author DZB
 * @create 2020-03-16 17:26
 */
public class simpleWebServer {

    public static void main(String[] args) throws Exception {

        int port = 28000;
        // 创建一个Socket连接
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("启动服务，绑定端口： " + port);
        System.out.println("http://localhost:" + port);

        // 启动线程池
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(30);

        while (true) {
            // 聆听，等待客户端接入
            Socket clientSocket = serverSocket.accept();
            System.out.println("新的连接"
                    + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            try {
                // 一旦客户端接入，交由线程池处理，主线程继续监听
                fixedThreadPool.execute(new SocketHandler(clientSocket));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
