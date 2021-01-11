package cn.xdaoy.utils.tcp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TCPClient implements Callable<byte[]> {

	private transient final Logger logger = LogManager.getLogger(TCPClient.class);

	private String ip;

	private int port;

	private byte[] content;

	private int timeout = 5000;

	public TCPClient(String ip, int port, byte[] content) {
		super();
		this.ip = ip;
		this.port = port;
		this.content = content;
	}

	public TCPClient(String ip, int port, byte[] content, int timeout) {
		super();
		this.ip = ip;
		this.port = port;
		this.content = content;
		this.timeout = timeout;
	}

	@Override
	public byte[] call() throws Exception {
		byte[] result = null;
		Socket socket = null;
		OutputStream os = null;
		try {
			// 1.create socket
			socket = new Socket();
			logger.info("==>tcp server，ip:" + ip + ",port:" + port);
			SocketAddress socketAddress = new InetSocketAddress(ip, port);

			socket.connect(socketAddress, timeout);
			socket.setTcpNoDelay(true);
			socket.setSoTimeout(timeout);// read timeout
			// socket.setKeepAlive(true);
			logger.info("==>tcp connect success");

			// 2.send stream
			os = socket.getOutputStream();
			os.write(content);
			os.flush();

			logger.info("==>send success");

			// 3.read stream
			InputStream in = socket.getInputStream();
			// socket read block until finish
			int count = 0;
			while (count == 0 || count != in.available()) {
				count = in.available();
			}

			result = new byte[count];
			in.read(result);
			in.close();

		} finally {
			if (null != os) {
				os.close();
			}
			if (null != socket) {
				socket.close();
			}
		}
		return result;
	}

}
