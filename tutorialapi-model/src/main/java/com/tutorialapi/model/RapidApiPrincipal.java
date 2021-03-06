package com.tutorialapi.model;

import java.security.Principal;
import java.util.Objects;

@SuppressWarnings("PMD.UselessParentheses")
public class RapidApiPrincipal implements Principal {

	private final String proxySecret;
	private final String user;
	private final Subscription subscription;

	public RapidApiPrincipal(String proxySecret, String user, Subscription subscription) {
		this.user = user;
		this.subscription = subscription;
		this.proxySecret = proxySecret;
	}

	@Override
	public String getName() {
		return user;
	}

	public String getUser() {
		return user;
	}

	public String getProxySecret() {
		return proxySecret;
	}

	public Subscription getSubscription() {
		return subscription;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		RapidApiPrincipal that = (RapidApiPrincipal) obj;
		return (
			Objects.equals(proxySecret, that.proxySecret) &&
			Objects.equals(user, that.user) &&
			subscription == that.subscription
			);
	}

	@Override
	public int hashCode() {
		return Objects.hash(proxySecret, user, subscription);
	}

	@Override
	public String toString() {
		return (
			"RapidApiPrincipal{" +
			"proxySecret='" +
			proxySecret +
			'\'' +
			", " +
			"user='" +
			user +
			'\'' +
			", subscription=" +
			subscription +
			'}'
			);
	}
}
