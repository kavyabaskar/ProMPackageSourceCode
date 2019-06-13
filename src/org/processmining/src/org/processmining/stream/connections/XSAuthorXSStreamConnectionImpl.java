package org.processmining.stream.connections;

import org.processmining.framework.connections.impl.AbstractConnection;
import org.processmining.stream.core.interfaces.XSAuthor;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSStream;

public class XSAuthorXSStreamConnectionImpl<S extends XSStream<D>, D extends XSDataPacket<?, ?>>
		extends AbstractConnection {

	public final static String KEY_AUTHOR = "author";
	public final static String KEY_STREAM = "stream";
	public final static String KEY_TIMESTAMP = "timestamp";

	private final Long timestamp;

	public XSAuthorXSStreamConnectionImpl(S stream, XSAuthor<D> author) {
		super("Connection (Author:" + author.getName() + ", Stream:" + stream.getName() + ")@"
				+ System.currentTimeMillis());
		timestamp = System.currentTimeMillis();
		put(KEY_AUTHOR, author);
		put(KEY_STREAM, stream);
		put(KEY_TIMESTAMP, timestamp);
	}
}
