package no.runsafe.spooks.items;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;

public class Ectoplasm implements ISpookyItem
{
	public Ectoplasm(IServer server)
	{
		this.server = server;
	}

	@Override
	public Item getItem()
	{
		return Item.Miscellaneous.ExperienceBottle;
	}

	@Override
	public String getName()
	{
		return "Ectoplasm";
	}

	@Override
	public void onConsumed(IPlayer player)
	{
		IWorld world = server.getWorld("world");
		if (world != null)
		{
			player.removeItem(getItem(), 1);
			player.teleport(world, 3138, 143, 4433);
			player.sendMessage("Compelled to drink the ectoplasm, the world around you suddenly changes...");
		}
	}

	private final IServer server;
}
