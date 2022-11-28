package ma.poc.gwt.domain;

import java.util.Date;

public class User {

	private String id;

	private String lastName;

	private String firstName;

	private String profession;

	private String genre;

	private String statutMartial;

	private Date dateCreation;
	
	private Integer version;
	

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getStatutMartial() {
		return statutMartial;
	}

	public void setStatutMartial(String statutMartial) {
		this.statutMartial = statutMartial;
	}

//	public static User getUserById(String id) {
//		return new User();
//	}

	@Override
	public String toString() {
		return super.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public void persist() {
//		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "http://localhost:8086/rest/users");
//		builder.setHeader("Content-type", "application/x-www-form-urlencoded");
//		System.out.println(this);
//		try {
//			Request request = builder.sendRequest(this.toString(), new RequestCallback() {
//				public void onError(Request request, Throwable exception) {
//					GWT.log("Erreur lors du WS", exception);
//				}
//
//				public void onResponseReceived(Request request, Response response) {
//					if (200 == response.getStatusCode()) {
////		            updateTable(JsonUtils.safeEval(response.getText()));
//					} else {
//					}
//				}
//			});
//
//		} catch (RequestException e) {
//			GWT.log("Erreur lors du WS", e);
//		}
//		return null
//		System.out.println("INIT PERSIST");
//		GWT.log("INIT");
//		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
//				"http://localhost:8086/rest/users/9c7e4957-51e0-4fa9-aae3-c1c9f412ad33");
//		try {
//			builder.send();
//		} catch (RequestException e) {
//			GWT.log("Erreur lors persist", e);
//		}
//		GWT.log("DONE");
//
//	}

}
