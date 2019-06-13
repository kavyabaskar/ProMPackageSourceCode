package org.processmining.stream.author.json.abstracts;

import java.io.IOException;
import java.io.Reader;

import org.processmining.stream.core.abstracts.AbstractXSAuthor;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSVisualization;

import com.google.gson.JsonStreamParser;

public abstract class AbstractJsonXSAuthor<D extends XSDataPacket<?, ?>, V> extends AbstractXSAuthor<D, V> {

	protected JsonStreamParser jsonStream;
	protected Reader externalStreamReader;

	public AbstractJsonXSAuthor(String name, XSVisualization<V> visualization) {
		super(name, visualization);
	}

	@Override
	public void startXSRunnable() {
		this.externalStreamReader = this.establishReaderConnectionToSource();
		this.jsonStream = new JsonStreamParser(this.externalStreamReader);
		super.startXSRunnable();
	}

	@Override
	public void stopXSRunnable() {
		super.stopXSRunnable();
		try {
			this.externalStreamReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected abstract Reader establishReaderConnectionToSource();

	protected void reConnect() {
		this.externalStreamReader = this.establishReaderConnectionToSource();
		this.jsonStream = new JsonStreamParser(this.externalStreamReader);
	}
}
