package com.portal.utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExpressionEvaluatorUtil {

	public static void main(String a[]) throws Exception {
		int peopleAvg = 991;
		String exp = "{\"exp\":{\"cond\":\"AND\",\"rules\":[{\"sq\":1,\"operator\":\">=\",\"pp\":\"0\",\"operatorValue\":\"750\",\"result\":\"Sapphire\"},{\"sq\":2,\"operator\":\"<\",\"pp\":\"0\",\"operatorValue\":\"850\",\"result\":\"Sapphire\"},{\"sq\":3,\"cond\":\"OR\",\"rules\":[{\"sq\":1,\"operator\":\">=\",\"pp\":\"1\",\"operatorValue\":\"750\",\"result\":\"Sapphire\"},{\"sq\":2,\"operator\":\"<\",\"pp\":\"1\",\"operatorValue\":\"850\",\"result\":\"Sapphire\"}]}]}}";
		ExpressionEvaluatorUtil of = new ExpressionEvaluatorUtil();
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("Nashorn");
		LinkedHashMap<String, String> ruleMap = new LinkedHashMap<>();
		// ruleMap.put("R1", "{0} >= 850");
		// ruleMap.put("R2", "{0} >= 750 && {0} < 850");
		// ruleMap.put("R3", "{0} >= 650 && {0} < 750");
		ruleMap.put("R4", "{0} >= 750 && {0} < 1000 && ({1} >= 950 && {1} < 992)");
		for (Map.Entry<String, String> rule : ruleMap.entrySet()) {
			if ((Boolean) scriptEngine.eval(MessageFormat.format(rule.getValue(), peopleAvg, peopleAvg)))
				System.out.println(rule.getKey());
		}
		System.out.println(scriptEngine.eval(MessageFormat.format(of.parseExpression(exp), peopleAvg, peopleAvg)));

	}

	@SuppressWarnings("rawtypes")
	public String parseExpression(String expression) {
		StringBuilder sb = new StringBuilder();
		try {
			JSONObject expJson = new JSONObject(expression);
			if (expJson.has("exp")) {
				JSONObject exp = expJson.getJSONObject("exp");
				JSONArray rules = exp.getJSONArray("rules");
				ObjectMapper mapper = new ObjectMapper();
				List<LinkedHashMap> ruleList = mapper.readValue(rules.toString(),
						new TypeReference<List<LinkedHashMap>>() {
						});
				SortedMap<Integer, LinkedHashMap> sortedMap = new TreeMap<>();
				for (LinkedHashMap rule : ruleList) {
					sortedMap.put((Integer) rule.get("sq"), rule);
				}
				sortedMap.forEach((k, v) -> {
					if (!v.containsKey("cond")) {
						sb.append("{" + v.get("pp") + "} " + v.get("operator") + " " + v.get("operatorValue"));
						try {
							if ("AND".equals(exp.getString("cond"))) {
								sb.append(" && ");
							}
							if ("OR".equals(exp.getString("cond"))) {
								sb.append(" || ");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				sb.replace(sb.toString().length() - 4, sb.toString().length(), "");
				sortedMap.forEach((k, v) -> {
					if (v.containsKey("cond")) {
						sb.append(" && ");
						SortedMap<Integer, LinkedHashMap> sortedRules = getSortedRuleMap(v);
						sb.append("(");
						sortedRules.forEach((nk, nv) -> {
							if (!nv.containsKey("cond")) {
								sb.append(
										"{" + nv.get("pp") + "} " + nv.get("operator") + " " + nv.get("operatorValue"));
								try {
									if ("AND".equals(exp.getString("cond"))) {
										sb.append(" && ");
									}
									if ("OR".equals(exp.getString("cond"))) {
										sb.append(" || ");
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						sb.replace(sb.toString().length() - 4, sb.toString().length(), "");
						sb.append(")");
						System.out.println(sortedRules);
					}
				});
				System.out.println(sb);
			}
		} catch (Exception e) {
		}
		return sb.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SortedMap<Integer, LinkedHashMap> getSortedRuleMap(LinkedHashMap expObj) {
		ArrayList<LinkedHashMap> rules = (ArrayList<LinkedHashMap>) expObj.get("rules");
		SortedMap<Integer, LinkedHashMap> sortedMap = new TreeMap<>();
		for (LinkedHashMap rule : rules) {
			sortedMap.put((Integer) rule.get("sq"), rule);
		}
		return sortedMap;
	}
}