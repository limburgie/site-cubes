package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.ContentLocation;
import be.webfactor.sitecubes.service.test.ContentLocationServiceTestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContentLocationServiceImplDeleteLocationByIdTest extends ContentLocationServiceTestCase {

	@Before
	public void setup() {
		super.setup();

		createLocation("col1", 0);
		createLocation("col1", 1);
		createLocation("col1", 2);
		createLocation("col2", 0);
	}

	@Test
	public void locationIsEffectivelyDeleted() {
		ContentLocation locationToDelete = getLocation("col1", 1);

		deleteLocation("col1", 1);

		assertNull(contentLocationService.getLocation(locationToDelete.getId()));
	}

	@Test
	public void underlyingLocationsAreMovedUp() {
		ContentLocation locationBeforeDeleted = getLocation("col1", 0);
		ContentLocation locationAfterDeleted = getLocation("col1", 2);

		deleteLocation("col1", 1);

		assertEquals(0, contentLocationService.getLocation(locationBeforeDeleted.getId()).getPosition());
		assertEquals(1, contentLocationService.getLocation(locationAfterDeleted.getId()).getPosition());
	}

	@Test
	public void locationsInOtherColumnsAreLeftAlone() {
		ContentLocation locationInOtherColumn = getLocation("col2", 0);

		deleteLocation("col1", 1);

		assertEquals(0, contentLocationService.getLocation(locationInOtherColumn.getId()).getPosition());
	}

}
