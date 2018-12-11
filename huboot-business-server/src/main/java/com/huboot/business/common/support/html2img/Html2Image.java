package com.huboot.business.common.support.html2img;


import com.huboot.business.common.support.html2img.parser.HtmlParser;
import com.huboot.business.common.support.html2img.parser.HtmlParserImpl;
import com.huboot.business.common.support.html2img.imagemap.HtmlImageMap;
import com.huboot.business.common.support.html2img.imagemap.HtmlImageMapImpl;
import com.huboot.business.common.support.html2img.renderer.ImageRenderer;
import com.huboot.business.common.support.html2img.renderer.ImageRendererImpl;
import org.w3c.dom.Document;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.util.Map;

/**
 * @author Yoav Aharoni
 */
public class Html2Image {
	private HtmlParser parser = new HtmlParserImpl();
	private HtmlImageMap htmlImageMap;
	private ImageRenderer imageRenderer;

	public HtmlParser getParser() {
		return parser;
	}

	public HtmlImageMap getHtmlImageMap() {
		if (htmlImageMap == null) {
			htmlImageMap = new HtmlImageMapImpl(getImageRenderer());
		}
		return htmlImageMap;
	}



	public ImageRenderer getImageRenderer() {
		if (imageRenderer == null) {
			imageRenderer = new ImageRendererImpl(parser);
		}
		return imageRenderer;
	}

	public static Html2Image fromDocument(Document document) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().setDocument(document);
		return html2Image;
	}

	public static Html2Image fromHtml(String html,Map<String, String> placeholder) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().loadHtml(html,placeholder);
		return html2Image;
	}

	public static Html2Image fromURL(URL url,Map<String, String> placeholde) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(url,placeholde);
		return html2Image;
	}

	public static Html2Image fromURI(URI uri,Map<String, String> placeholde) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(uri,placeholde);
		return html2Image;
	}

	public static Html2Image fromFile(File file,Map<String, String> placeholde) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(file,placeholde);
		return html2Image;
	}

	public static Html2Image fromReader(Reader reader,Map<String, String> placeholde) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(reader,placeholde);
		return html2Image;
	}

	public static Html2Image fromInputStream(InputStream inputStream,Map<String, String> placeholde) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(inputStream,placeholde);
		return html2Image;
	}
}
