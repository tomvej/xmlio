package org.xmlio.resource;

import java.io.File;

/**
 * Stores and loads objects of designated type from and to a single fixed file.
 * 
 * @author <a href="mailto:xvejpust@fi.muni.cz">Tomáš Vejpustek</a>
 * 
 * @param <T>
 *            Type of objects for storing and loading.
 */
public abstract class FixedFileXMLResource<T> extends FileXMLResource<T> {

	private File	target;

	/**
	 * Creates an XML resource with specified file and transformation context.
	 * 
	 * @param target
	 *            File used for storage.
	 * @param context
	 *            Context of transformation between objects and XML
	 */
	public FixedFileXMLResource(File target, TransformationContext<T> context) {
		super(context);
		this.target = target;
	}

	@Override
	protected File getFile() {
		return target;
	}

}
