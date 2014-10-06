package be.webfactor.sitecubes.faces.resourcehandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class CustomURLStreamHandler extends URLStreamHandler {

	private String content;

	public CustomURLStreamHandler(String content) {
		this.content = content;
	}

	@Override
	protected URLConnection openConnection(URL u) throws IOException {
		return new CustomURLConnection(u, content);
	}

	private static class CustomURLConnection extends URLConnection {

		private String content;

		public CustomURLConnection(URL url, String content) {
			super(url);
			this.content = content;
		}

		@Override
		public void connect() throws IOException {
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(content.getBytes("UTF-8"));
		}
	}

}
