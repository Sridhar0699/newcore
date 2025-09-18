package com.portal.clf.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ClassifiedDownloadService {

	public byte[] creteTablePdf(LinkedHashMap<String, List<Object>> dataMap);

	public byte[] createTableText(LinkedHashMap<String, List<Object>> dataMap);

	public byte[] generateAd(Map<String, Object> convertedData) throws IOException;

	public byte[] downloadDisplayAdsContent(LinkedHashMap<Integer, List<Object>> dataMap);
}
