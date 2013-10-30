package no.runsafe.spooks.items;

import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class TestApple implements ISpookyItem
{
	@Override
	public Item getItem()
	{
		return Item.Food.Plant.Apple;
	}

	@Override
	public String getName()
	{
		return "The Apple of Testing";
	}

	@Override
	public void onConsumed(RunsafePlayer player)
	{
		player.damage(100D); // Carrrrrrrl, that kills people.
	}
}
