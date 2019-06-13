package org.processmining.stream.core.interfaces;

/**
 * The XSStream acts as a basis for any stream within ProM. An XSStream contains
 * of one publisher and a set of subscribers.
 * <p/>
 * <p>
 * It is possible to view a stream as a collection of {@link XSChannel} and each
 * of them is basically a one-to-one connection between the publisher and the
 * subscriber.
 *
 * @param <T>
 *            the type of data packet streamed, it must be a subtype of
 *            {@link XSDataPacket}
 * @see XSChannel
 */
public interface XSStream<T extends XSDataPacket<?, ?>> extends XSHub<T, T>, XSStronglyTyped<T>, XSVisualizable {

	public XSSignature getSignature();
}
