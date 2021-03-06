package no.runsafe.spooks;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.event.player.IPlayerPickupItemEvent;
import no.runsafe.framework.api.event.player.IPlayerRightClick;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.event.plugin.IPluginDisabled;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.entity.RunsafeItem;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerPickupItemEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.spooks.items.ISpookyItem;

import java.util.*;

public class ItemManager implements IConfigurationChanged, IPluginDisabled, IPlayerRightClick, IPlayerPickupItemEvent
{
	public ItemManager(IScheduler scheduler, ISpookyItem[] items)
	{
		ItemManager.scheduler = scheduler;
		this.items = items;

		scheduler.startSyncRepeatingTask(new Runnable()
		{
			@Override
			public void run()
			{
				wipeAllItems();
				spawnAllItems();
			}
		}, 1800, 1800);
	}

	@Override
	public void OnConfigurationChanged(IConfiguration config)
	{
		spawnWorld = config.getConfigValueAsWorld("world");
		if (spawnWorld == null) return;

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
		IPlayer player = event.getPlayer();
		if (player != null)
		{
			RunsafeItem item = event.getItem();
			if (item != null)
			{
				final int entityID = item.getEntityId();
				final ILocation location = respawn.get(entityID);

				if (respawn.containsKey(entityID))
				{
					ItemManager.scheduler.startSyncTask(new Runnable()
					{
						@Override
						public void run()
						{
							spawnItem(location);
						}
					}, random.nextInt(300) + 60);
					respawn.remove(entityID);
				}
			}
		}
	}

	private ILocation getLocationFromString(String locationString)
	{
		String[] parts = locationString.split(",");
		return spawnWorld.getLocation(
			Double.parseDouble(parts[0]),
			Double.parseDouble(parts[1]),
			Double.parseDouble(parts[2])
		);
	}

	private void spawnAllItems()
	{
		for (ILocation location : spawnPoints)
			spawnItem(location);
	}

	private void spawnItem(ILocation location)
	{
		ISpookyItem randomItem = items[random.nextInt(items.length)];
		RunsafeMeta item = randomItem.getItem().getItem();
		item.setDisplayName(randomItem.getName()); // Name the item.

		respawn.put(spawnWorld.dropItem(location, item).getEntityId(), location);
	}

	private void wipeAllItems()
	{
		for (Map.Entry<Integer, ILocation> entry : respawn.entrySet())
		{
			IEntity entity = spawnWorld.getEntityById(entry.getKey());
			if (entity != null)
				entity.remove();
		}
		respawn.clear();
	}

	@Override
	public void OnPluginDisabled()
	{
		wipeAllItems();
	}

	@Override
	public boolean OnPlayerRightClick(IPlayer player, RunsafeMeta usingItem, IBlock targetBlock)
	{
		if (player != null && usingItem != null)
		{
			String itemName = usingItem.getDisplayName();
			if (itemName != null)
			{
				ISpookyItem item = getItemByName(itemName);
				if (item != null)
				{
					IWorld playerWorld = player.getWorld();
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

	private IWorld spawnWorld;
	private List<ILocation> spawnPoints = new ArrayList<ILocation>();
	private HashMap<Integer, ILocation> respawn = new HashMap<Integer, ILocation>();
	private ISpookyItem[] items;
	private Random random = new Random();
	public static IScheduler scheduler;
}
