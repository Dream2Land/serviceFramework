package cn.xdaoy.utils.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import cn.xdaoy.utils.StringUtils;

/**
 * nio file util
 * 
 * @author xdtand
 *
 */
public class FileUtils {

	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * create file
	 * 
	 * @param fileName
	 * @param recreate
	 * @throws Exception
	 */
	public static void createFile(final String fileName, final boolean recreate) throws Exception {
		Path path = Paths.get(fileName);
		createFile(path, recreate);
	}

	/**
	 * create file
	 * 
	 * @param path
	 * @param recreate
	 * @throws Exception
	 */
	public static void createFile(final Path path, final boolean recreate) throws Exception {
		if (Files.notExists(path)) {
			createDirectory(path.getParent());
			Files.createFile(path);
		} else if (recreate) {
			Files.deleteIfExists(path);
			Files.createFile(path);
		}
	}

	/**
	 * create directory
	 * 
	 * @param directory name
	 * @throws Exception
	 */
	public static void createDirectory(final String directory) throws Exception {
		Path path = Paths.get(directory);
		createDirectory(path);
	}

	/**
	 * create directory
	 * 
	 * @param directory path
	 * @throws Exception
	 */
	public static void createDirectory(final Path path) throws Exception {
		if (Files.exists(path.getParent())) {
			if (!Files.exists(path))
				Files.createDirectory(path);
		} else {
			Files.createDirectories(path);
		}
	}

	/**
	 * read file bytes
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static byte[] readFile(String fileName) throws Exception {
		Path path = Paths.get(fileName);
		return Files.readAllBytes(path);
	}

	/**
	 * read file lines
	 * 
	 * 
	 * @param fileName
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static List<String> readFile(String fileName, String charset) throws Exception {
		Path path = Paths.get(fileName);
		return Files.readAllLines(path, Charset.forName(charset));
	}

	/**
	 * read file async
	 * 
	 * @param path
	 * @param encode
	 * @param fileReader
	 * @return
	 * @throws Exception
	 */
	public static long readFileAsync(String path, String encode, AsyncFileReader fileReader) throws Exception {
		Path filePath = Paths.get(path);
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(filePath, StandardOpenOption.READ);
		long size = fileChannel.size();
		long cnt = size / 1024;
		if (size % 1014 != 0) {
			cnt++;
		}
		long ccnt = 0;
		while (ccnt < cnt) {
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			final long cg = ccnt;
			fileChannel.read(buffer, ccnt * 1024, buffer, new CompletionHandler<Integer, ByteBuffer>() {
				@Override
				public void completed(Integer result, ByteBuffer attachment) {
					logger.debug("async complete result:{}", result);
					attachment.flip();
					byte[] data = new byte[attachment.limit()];
					attachment.get(data);
					fileReader.read(cg, data);
					attachment.clear();
				}

				@Override
				public void failed(Throwable exc, ByteBuffer attachment) {
					logger.error("read file error,file:{}", path);
				}
			});
			ccnt++;
		}
		return cnt;
	}

	public interface AsyncFileReader {
		void read(long group, byte[] data);
	}

	/**
	 * write file
	 * 
	 * @param fileName
	 * @param lines
	 * @param encode
	 * @throws Exception
	 */
	public static void writeFile(String fileName, List<String> content, String encode) throws Exception {
		Path path = Paths.get(fileName);
		Files.write(path, content, Charset.forName(encode));
	}

	/**
	 * file write 
	 * 
	 * @param fileName
	 * @param content
	 * @param charset
	 * @param append
	 * @throws IOException
	 */
	public static void write(File fileName, String content, String charset, boolean append) throws IOException {
		FileOutputStream fos = null;
		FileChannel fc = null;
		byte[] bts = content.getBytes(charset);

		try {
			fos = new FileOutputStream(fileName, append);
			fc = fos.getChannel();
			ByteBuffer bb = ByteBuffer.allocate(bts.length);
			bb.put(bts);
			bb.flip();
			fc.write(bb);
		} finally {
			try {
				fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * write file async
	 * 
	 * @param path
	 * @param handler
	 * @throws Exception
	 */
	public static void fileWriteAsync(String path, String encode, AsyncFileWriter fileWriter) throws Exception {
		Path filePath = Paths.get(path);
		if (!Files.exists(filePath)) {
		    Files.createFile(filePath);
		}
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(filePath, StandardOpenOption.WRITE);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		fileChannel.write(buffer, 0L, buffer, new CompletionHandler<Integer, ByteBuffer>() {
			@Override
			public void completed(Integer result, ByteBuffer attachment) {
				logger.debug("async complete result:{}", result);
				logger.debug("async complete attachment:{}", attachment);
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				logger.error("write file error,file:{}", path);
			}
		});
	}

	public interface AsyncFileWriter {
		void write(byte[] line);
	}

	/**
	 * copy file(replace exists)
	 * 
	 * @param source
	 * @param target
	 * @throws Exception
	 */
	public static void copy(String source, String target) throws Exception {
		Path sourcePath = Paths.get(source);
		Path targetPath = Paths.get(target);
		Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
	}

	/**
	 * move file(replace exists)
	 * 
	 * @param source
	 * @param target
	 * @throws Exception
	 */
	public static void move(String source, String target) throws Exception {
		Path sourcePath = Paths.get(source);
		Path targetPath = Paths.get(target);
		Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
	}

	/**
	 * delete file or directory
	 * 
	 * @param path
	 */
	public static void delete(String path) throws Exception {
		Files.delete(Paths.get(path));
	}

	/**
	 * 遍历所有目录
	 * 
	 * @param path
	 * @param visitor
	 * @throws Exception
	 */
	public static void walkFileTree(String path, FileVisitor<Path> visitor) throws Exception {
		Files.walkFileTree(Paths.get(path), visitor);
	}

	/**
	 * 判断上传文件是否可用
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isMultiFileEnable(MultipartFile file) {
		if (null != file && file.getSize() > 0 && !StringUtils.isEmpty(file.getOriginalFilename())) {
			return true;
		}
		return false;
	}

	/**
	 * 获取文件类型
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileType(MultipartFile file) {
		try {
			String header = bytesToHexString(Arrays.copyOfRange(file.getBytes(), 0, 6));
			header = header.toUpperCase();
			FileType[] fileTypes = FileType.values();
			for (FileType type : fileTypes) {
				if (header.startsWith(type.getValue())) {
					return type.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 将文件头转换成16进制字符串
	 * 
	 * @param 原生byte
	 * @return 16进制字符串
	 */
	private static String bytesToHexString(byte[] src) {

		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

}
