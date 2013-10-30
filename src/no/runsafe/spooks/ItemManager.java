package no.runsafe.spooks;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.event.player.IPlayerPickupItemEvent;
import no.runsafe.framework.api.event.player.IPlayerRightClick;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.event.plugin.IPluginDisabled;
import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.framework.minecraft.RunsafeWorld;
import no.runsafe.framework.minecraft.block.RunsafeBlock;
import no.runsafe.framework.minecraft.entity.RunsafeItem;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerPickupItemEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.spooks.items.ISpookyItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ItemManager implements IConfigurationChanged, IPluginDisabled, IPlayerRightClick, IPlayerPickupItemEvent
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

	@Override
	public void OnPlayerPickupItemEvent(RunsafePlayerPickupItemEvent event)
	{
		RunsafePlayer player = event.getPlayer();
		if (player != null)
		{
			RunsafeItem item = event.getItem();
			if (item != null)
			{
				int entityID = item.getEntityId();
				if (respawn.containsKey(entityID))
				{
					spawnItem(respawn.get(entityID));
					respawn.remove(entityID);
				}
			}
		}
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
			respawn.put(spawnItem(location), location);
	}

	private int spawnItem(RunsafeLocation location)
	{
		ISpookyItem randomItem = items[random.nextInt(items.length)];
		RunsafeMeta item = randomItem.getItem().getItem();
		item.setDisplayName(randomItem.getName()); // Name the item.

		return spawnWorld.dropItem(location, item).getEntityId();
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

	@Override
	public boolean OnPlayerRightClick(RunsafePlayer player, RunsafeMeta usingItem, RunsafeBlock targetBlock)
	{
		if (player != null && usingItem != null)
		{
			String itemName = usingItem.getDisplayName();
			if (itemName != null)
			{
				ISpookyItem item = getItemByName(itemName);
				if (item != null)
				{
					RunsafeWorld playerWorld = player.getWorld();
					if (playerWorld != null && playerWorld.getUniverse().getName().equalsIgnoreCase("survival"))
						item.onConsumed(player);
					else
						player.sendColouredMessage("&cYou cannot use that here.");

					return false;
				}
			}
		}
		return true;
	}

	private ISpookyItem getItemByName(String name)
	{
		for (ISpookyItem item : items)
			if (item.getName().equals(name))
				return item;

		return null;
	}

	private RunsafeWorld spawnWorld;
	private List<RunsafeLocation> spawnPoints = new ArrayList<RunsafeLocation>();
	private HashMap<Integer, RunsafeLocation> respawn = new HashMap<Integer, RunsafeLocation>();
	private ISpookyItem[] items;
	private Random random = new Random();
	public static IScheduler scheduler;
}
