package xmlio.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import xmlio.exceptions.XMLException;
import xmlio.exceptions.XMLExceptionType;

public abstract class FileXMLResource<T> extends StreamXMLResource<T> {

	protected abstract File getFile();

	@Override
	protected InputStream openIpnut() throws XMLException {
		try {
			return new FileInputStream(getFile());
		} catch (FileNotFoundException fnfe) {
			throw new XMLException(XMLExceptionType.UNDEFINED, fnfe);
		}
	}

	@Override
	protected OutputStream openOutput() throws XMLException {
		try {
			return new FileOutputStream(getFile());
		} catch (FileNotFoundException fnfe) {
			throw new XMLException(XMLExceptionType.UNDEFINED, fnfe);
		}
	}
}
