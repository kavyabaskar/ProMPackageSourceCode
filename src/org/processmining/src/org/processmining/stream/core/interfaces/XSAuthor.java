package org.processmining.stream.core.interfaces;

/**
 * An author takes some source and converts it to some data packet. An author is
 * a runnable entity that writes onto a socket. An author is visualizable and
 * has strong typing.
 *
 * @param <T>
 *            type of data packets (also known as "topic") of the author
 * @see org.processmining.stream.core.interfaces.XSDataPacket
 * @see org.processmining.stream.core.interfaces.XSRunnable
 * @see org.processmining.stream.core.interfaces.XSWriter
 * @see org.processmining.stream.core.interfaces.XSVisualizable
 * @see org.processmining.stream.core.interfaces.XSStronglyTyped
 */
public interface XSAuthor<T extends XSDataPacket<?, ?>>
		extends XSRunnable, XSWriter<T>, XSVisualizable, XSStronglyTyped<T> {
}
