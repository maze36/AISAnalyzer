package model.vessel;

public enum ShipType {

	ANTI_POLLUTION("antipollution"), DIVING_SHIP("divingship"), DREDGER("dredger"), DRY_CARGO("drycargo"), FISHING(
			"fishing"), GENERAL_CARGO("generalcargo"), HIGH_SPEED_CRAFT("highspeedcraft"), NAVAL_MILITARY_SHIP(
					"navalmilitaryship"), OTHER(
							"other"), PASSENGER("passenger"), PILOT_SHIP("pilotship"), PORT_POLICE_LAW_ENFORCE(
									"portpolicelawenforce"), RESCUE_SALVAGE_SHIP("resucesalvageship"), SAILING_VESSEL(
											"sailingvessel"), TANKER("tanker"), TUG("tug"), WINGIN_GROUND(
													"winginground"), YACHT_PLEASURE_CRAFT("yachtpleasurecraft");

	private final String name;

	private ShipType(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		// (otherName == null) check is not needed because name.equals(null)
		// returns false
		return name.equals(otherName);
	}

	public String toString() {
		return this.name;
	}

}
