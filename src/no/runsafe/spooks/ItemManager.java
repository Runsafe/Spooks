package no.runsafe.spooks;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.event.plugin.IPluginDisabled;
import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.RunsafeWorld;

import java.util.ArrayList;
import java.util.List;

public class ItemManager implements IConfigurationChanged, IPluginDisabled
{
	public ItemManager(IScheduler scheduler)
	{
		ItemManager.scheduler = scheduler;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration config)
	{
		spawnWorld = config.getConfigValueAsWorld("world");
		if (spawnWorld == null)	return;

		spawnPoints.clear();
		List<String> locationStrings = config.getConfigValueAsList("locations");

		for (String locationString : locationStrings)
			spawnPoints.add(getLocationFromString(locationString));

		wipeAllItems();
		spawnAllItems();
	}

	private RunsafeLocation getLocationFromString(String locationString)
	{
		String[] parts = locationString.split(",");
		return new RunsafeLocation(
				spawnWorld,
				Double.parseDouble(parts[0]),
				Double.parseDouble(parts[1]),
				Double.parseDouble(parts[2])
		);
	}

	private void spawnAllItems()
	{
		for (RunsafeLocation location : spawnPoints)
			RunsafeServer.Instance.broadcastMessage("Spawning something at: " + location.toString());
	}

	private void wipeAllItems()
	{
		// ToDo: implement
	}

	@Override
	public void OnPluginDisabled()
	{
		// ToDo: Timer clean-up.
		wipeAllItems();
	}

	private RunsafeWorld spawnWorld;
	private List<RunsafeLocation> spawnPoints = new ArrayList<RunsafeLocation>();
	public static IScheduler scheduler;
}
