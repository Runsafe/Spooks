package no.runsafe.spooks.items;

import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.RunsafeWorld;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class Ectoplasm implements ISpookyItem
{
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
	public void onConsumed(RunsafePlayer player)
	{
		RunsafeWorld world = RunsafeServer.Instance.getWorld("world");
		if (world != null)
		{
			player.teleport(world, 3138, 143, 4433);
			player.sendMessage("Compelled to drink the ectoplasm, the world around you suddenly changes...");
		}
	}
}
