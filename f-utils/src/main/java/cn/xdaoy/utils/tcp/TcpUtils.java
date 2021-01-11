package cn.xdaoy.utils.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpUtils {

	private static final Logger logger = LoggerFactory.getLogger(TcpUtils.class);

	/**
	 * tcp request,get String result
	 * 
	 * @param host
	 * @param port
	 * @param charset
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String getString(final String host, final int port, final String charset, String message)
			throws Exception {
		logger.info("==>tcp String request,host:{},port:{},message:{},charset:{}",host,port,message,charset);
		return new String(new TCPClient(host, port, message.getBytes(charset)).call(), charset);
	}

	/**
	 * tcp request, get byte result
	 * 
	 * @param host
	 * @param port
	 * @param charset
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static byte[] getByte(final String host, final int port, byte[] message) throws Exception {
		logger.info("==>tcp String request,host:{},port:{},message:{}",host,port,message);
		return new TCPClient(host, port, message).call();
	}

	/**
	 * tcp string callback
	 * 
	 * @param host
	 * @param port
	 * @param charset
	 * @param message
	 * @param callback
	 * @throws Exception
	 */
	public static void callback(final String host, final int port, final String charset, String message,
			StringCallback callback) throws Exception {
		callback.call(getString(host, port, charset, message));
	}

	/**
	 * tcp byte callback
	 * 
	 * @param host
	 * @param port
	 * @param message
	 * @param callback
	 * @throws Exception
	 */
	public static void callback(final String host, final int port, byte[] message, ByteCallback callback)
			throws Exception {
		callback.call(getByte(host, port, message));
	}

}
