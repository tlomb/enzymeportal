package uk.ac.ebi.ep.sitemap.advanced;

import java.io.IOException;
import java.io.OutputStreamWriter;

public interface ISitemapUrlRenderer<T extends ISitemapUrl> {
	
	public Class<T> getUrlClass();
	public String getXmlNamespaces();
	public void render(T url, OutputStreamWriter out, W3CDateFormat dateFormat) throws IOException;
}
