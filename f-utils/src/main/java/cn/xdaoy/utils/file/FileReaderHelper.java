package cn.xdaoy.utils.file;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import cn.xdaoy.utils.StringUtils;


public class FileReaderHelper<T>{

	private String fileName;
	
	private String encode;
	
	private Reader<T> reader;
	
	private Writer<T> writer;
	
	private int trunkSize=500;
	
	private int skipSize=0;
	
	private boolean errorContinue = true;
	
	private long length = 0;
	
	private List<T> list = new ArrayList<>();
	
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public Reader<T> getReader() {
		return reader;
	}

	public void setReader(Reader<T> reader) {
		this.reader = reader;
	}

	public Writer<T> getWriter() {
		return writer;
	}

	public void setWriter(Writer<T> writer) {
		this.writer = writer;
	}

	public int getTrunkSize() {
		return trunkSize;
	}

	public void setTrunkSize(int trunkSize) {
		this.trunkSize = trunkSize;
	}

	public int getSkipSize() {
		return skipSize;
	}

	public void setSkipSize(int skipSize) {
		this.skipSize = skipSize;
	}
	
	public boolean isErrorContinue() {
		return errorContinue;
	}

	public void setErrorContinue(boolean errorContinue) {
		this.errorContinue = errorContinue;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	private FileReaderHelper() {
	}
	
	public void read() throws Exception {
		BufferedReader reader = null;
		try {
			Charset cs = Charset.defaultCharset();
			if(!StringUtils.isEmpty(encode)) {
				Charset.forName(encode);
			}
			Path path = Paths.get(fileName);
			if(Files.notExists(path)) {
				throw new Exception("file not found");
			}
			reader = Files.newBufferedReader(path, cs);
			long c=0;
			String line = null;
			while((line = reader.readLine()) != null) {
				if(length<=skipSize) {
					length++;
					continue;
				}
				try {
					if(c>=trunkSize) {
						c=0;
						this.writer.write(list);
					}
					list.add(this.reader.read(length, line));
				}catch (Exception e) {
					//异常中断
					if(!errorContinue) {
						break;
					}
				}
				length++;
				c++;
			}
		}finally {
			if(null != reader) reader.close();
		}
	}
	
	
	public interface Reader<T>{ T read(final long index,final String line) throws Exception;}
	public interface Writer<T>{ void write(final List<T> list) throws Exception;}
	
	public static class Builder<T>{
		private FileReaderHelper<T> helper = new FileReaderHelper<>();
		
		public Builder<T> file(final String fileName) {
			helper.setFileName(fileName);
			return this;
		}
		public Builder<T> encode(final String encode) {
			helper.setEncode(encode);
			return this;
		}
		public Builder<T> skip(final int skip) {
			helper.setSkipSize(skip);
			return this;
		}
		public Builder<T> trunk(final int trunk) {
			helper.setTrunkSize(trunk);
			return this;
		}
		public Builder<T> reader(final Reader<T> reader) {
			helper.setReader(reader);
			return this;
		}
		public Builder<T> writer(final Writer<T> writer) {
			helper.setWriter(writer);
			return this;
		}
		public Builder<T> errorContinue(final boolean errorContinue) {
			helper.setErrorContinue(errorContinue);
			return this;
		}
		public FileReaderHelper<T> build() {
			return helper;
		}
	}
	
}
