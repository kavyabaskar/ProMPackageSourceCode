package org.processmining.stream.core.factories;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.processmining.stream.core.annotations.XSStreamSignature;
import org.processmining.stream.core.annotations.XSStreamTopic;
import org.processmining.stream.core.enums.CommunicationType;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSSignature;
import org.processmining.stream.core.interfaces.XSStream;
import org.reflections.Reflections;

public class XSStreamFactory {

	@SuppressWarnings("unchecked")
	public static <T extends XSDataPacket<?, ?>> XSStream<T> createStream(final Class<?> topic,
			final Class<?> signature, final CommunicationType communicationType) {

		Class<?> targetStreamImpl = findCorrespondingStreamClass(topic);
		Class<?> targetSignature = findCorrespondingSignatureClass(signature);

		XSSignature xsSignature = null;
		XSStream<T> xsStream = null;
		try {
			xsSignature = (XSSignature) targetSignature.getConstructor().newInstance();
			xsStream = (XSStream<T>) targetStreamImpl
					.getConstructor(new Class<?>[] { signature, CommunicationType.class })
					.newInstance(xsSignature, communicationType);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return xsStream;
	}

	private static Class<?> findCorrespondingStreamClass(final Class<?> topic) {
		Set<Class<?>> typedStreamImpls = new Reflections("org.processmining")
				.getTypesAnnotatedWith(XSStreamTopic.class);
		Class<?> result = null;
		streamLoop: for (Class<?> typedStreamImpl : typedStreamImpls) {
			if (typedStreamImpl.getAnnotation(XSStreamTopic.class).value().isAssignableFrom(topic)) {
				result = typedStreamImpl;
				break streamLoop;
			}
		}
		return result;
	}

	private static Class<?> findCorrespondingSignatureClass(final Class<?> signature) {
		Set<Class<?>> signatureImpls = new Reflections("org.processmining")
				.getTypesAnnotatedWith(XSStreamSignature.class);
		Class<?> result = null;
		signatureLoop: for (Class<?> signatureImpl : signatureImpls) {
			if (signatureImpl.getAnnotation(XSStreamSignature.class).value().isAssignableFrom(signature)) {
				result = signatureImpl;
				break signatureLoop;
			}
		}
		return result;
	}
}
