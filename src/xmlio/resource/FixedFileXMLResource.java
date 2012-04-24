package xmlio.resource;

import java.io.File;

public abstract class FixedFileXMLResource<T> extends FileXMLResource<T> {

	private File	target;

	public FixedFileXMLResource(File target) {
		this.target = target;
	}

	@Override
	protected File getFile() {
		return target;
	}

}
