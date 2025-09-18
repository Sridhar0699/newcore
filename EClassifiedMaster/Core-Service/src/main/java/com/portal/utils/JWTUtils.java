package com.portal.utils;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtils {

	public String generateJwtToken(JSONObject payload, Map<String, Object> header, String secretKey) {
		try {
			Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
			String token = Jwts.builder().setPayload(payload.toString()).setHeader(header).signWith(key).compact();
			return token;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public  boolean verifyJwtToken(String token, String secretKey) {
		try {
			Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
			Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

			Claims body = claimsJws.getBody();

			body.getSubject();
			body.getIssuer();
		} catch (Exception e) {
			return false;
		}
		return true;

	}

	public static void main(String a[]) {
		try {
			JSONObject payload = new JSONObject();
			payload.put("mercid", "BDUAT2K194");
			payload.put("orderid", "order69952");
			payload.put("transactionid", "U1230000901187");

			Map<String, Object> header = new HashMap<>();
			header.put("alg", "HS256");
			header.put("typ", "JWT");
			header.put("clientid", "bduat2k194");
			header.put("kid", "HMAC");
			JWTUtils jWTUtils = new JWTUtils();
			String token = jWTUtils.generateJwtToken(payload, header, "D3tXPcYNXjkfMvlNUjU2Hq2t5gKXPMJn");
			System.out.println(token);
			System.out.println(jWTUtils.verifyJwtToken(token, "D3tXPcYNXjkfMvlNUjU2Hq2t5gKXPMJnaa"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
