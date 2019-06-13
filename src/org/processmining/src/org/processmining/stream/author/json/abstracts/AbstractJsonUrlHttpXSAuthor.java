package org.processmining.stream.author.json.abstracts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSVisualization;

public abstract class AbstractJsonUrlHttpXSAuthor<D extends XSDataPacket<?, ?>,V> extends AbstractJsonXSAuthor<D,V> {

	protected URL url;
	protected Charset charset;

	public AbstractJsonUrlHttpXSAuthor(String name, XSVisualization<V> visualization, URL url, Charset charset) {
		super(name, visualization);
		this.url = url;
		this.charset = charset;
	}

	@Override
	protected Reader establishReaderConnectionToSource() {
		try {
			HttpURLConnection connection = (HttpURLConnection) this.url.openConnection();
			//TODO: add catching of connection codes.
			//TODO: find out how to fix the User-Agent Issue
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0");
			connection.connect();
			return new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
