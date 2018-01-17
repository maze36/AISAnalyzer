package model.vessel;

import java.util.ArrayList;

/**
 * Container object for a collection of vessels.
 * 
 * @author msteidel
 *
 */
public class VesselContainer {

	private ArrayList<Vessel> vesselContainer;

	public VesselContainer() {
		this.vesselContainer = new ArrayList<>();
	}

	public VesselContainer(Vessel vessel) {
		this.vesselContainer = new ArrayList<Vessel>();
		this.vesselContainer.add(vessel);
	}

	public VesselContainer(ArrayList<Vessel> vesselCollection) {
		this.vesselContainer = new ArrayList<Vessel>();
		this.vesselContainer.addAll(vesselCollection);
	}

	public Vessel getVessel(int index) {
		return this.vesselContainer.get(index);
	}

	public boolean addVessel(Vessel vessel) {
		return this.vesselContainer.add(vessel);
	}

	public boolean addVesselCollection(ArrayList<Vessel> vesselCollection) {
		return this.vesselContainer.addAll(vesselCollection);
	}

	public boolean vesselExists(Vessel vessel) {
		for (Vessel v : this.vesselContainer) {
			if (v.getMmsi() == vessel.getMmsi()) {
				return true;
			}
		}
		return false;
	}

	public boolean isEmpty() {
		return this.vesselContainer.isEmpty();
	}

	public boolean vesselExists(Integer mmsi) {
		for (Vessel v : this.vesselContainer) {
			if (v.getMmsi() == mmsi) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Vessel> getList() {
		return this.vesselContainer;
	}

}
