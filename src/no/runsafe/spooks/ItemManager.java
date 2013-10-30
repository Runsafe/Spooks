package no.runsafe.spooks;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.event.plugin.IPluginDisabled;
import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.framework.minecraft.RunsafeWorld;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.spooks.items.ISpookyItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemManager implements IConfigurationChanged, IPluginDisabled
{
	public ItemManager(IScheduler scheduler, ISpookyItem[] items)
	{
		ItemManager.scheduler = scheduler;
		this.items = items;
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
		{
			ISpookyItem randomItem = items[random.nextInt(items.length)];
			RunsafeMeta item = randomItem.getItem().getItem();
			item.setDisplayName(randomItem.getName()); // Name the item.

			spawnWorld.dropItem(location, randomItem.getItem().getItem());
		}
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
	private ISpookyItem[] items;
	private Random random = new Random();
	public static IScheduler scheduler;
}
