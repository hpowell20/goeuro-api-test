package com.goeuro.test.model;

public class GeoPositionModel {
	private int id;
	private String name;
	private String type;
	private double latitude;
	private double longitude;
	
	protected GeoPositionModel(GeoPositionModelBuilder geoPositionModelBuilder) {
		id = geoPositionModelBuilder.id;
		name = geoPositionModelBuilder.name;
		type = geoPositionModelBuilder.type;
		latitude = geoPositionModelBuilder.latitude;
		longitude = geoPositionModelBuilder.longitude;
	}

	public static GeoPositionModelBuilder builder() {
		return new GeoPositionModelBuilder();
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public static class GeoPositionModelBuilder {
		private int id;
		private String name;
		private String type;
		private double latitude;
		private double longitude;
		
		public GeoPositionModelBuilder withId(int id) {
			this.id = id;
			return this;
		}
		
		public GeoPositionModelBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public GeoPositionModelBuilder withType(String type) {
			this.type = type;
			return this;
		}

		public GeoPositionModelBuilder withLatitude(double latitude) {
			this.latitude = latitude;
			return this;
		}

		public GeoPositionModelBuilder withLongitude(double longitude) {
			this.longitude = longitude;
			return this;
		}
		
		public GeoPositionModel build() {
			return new GeoPositionModel(this);
		}
	}
}
