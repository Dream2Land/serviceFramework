package cn.xdaoy.utils.file;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import cn.xdaoy.utils.StringUtils;


public class FileWriterHelper<T>{

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

	private FileWriterHelper() {
	}
	
	public void write(boolean recreate) throws Exception {
		Charset cs = Charset.defaultCharset();
		if(!StringUtils.isEmpty(encode)) {
			Charset.forName(encode);
		}
		Path path = Paths.get(fileName);
		FileUtils.createFile(path,recreate);
		long c = 0;
		int i=0;
		List<String> wlist = new ArrayList<>();
		for(list = reader.read(i);;list = reader.read(i)) {
			if(list.isEmpty()) break;
			for(T t :list) {
				try {
					if(c>=trunkSize) {
						c=0;
						Files.write(path, wlist, cs, StandardOpenOption.WRITE,StandardOpenOption.APPEND);
						wlist.clear();
					}
					wlist.add(this.writer.write(t));
				}catch (Exception e) {
					//异常中断
					if(!errorContinue) {
						break;
					}
				}
				c++;
			}
			i++;
		}
	}
	
	
	public interface Reader<T>{ List<T> read(final int index) throws Exception;}
	public interface Writer<T>{ String write(final T t) throws Exception;}
	
	public static class Builder<T>{
		private FileWriterHelper<T> helper = new FileWriterHelper<>();
		
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
		public FileWriterHelper<T> build() {
			return helper;
		}
	}
	
}
