package no.runsafe.spooks.items;

import no.runsafe.framework.minecraft.Buff;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class SugarApple implements ISpookyItem
{
	@Override
	public Item getItem()
	{
		return Item.Food.Plant.Apple;
	}

	@Override
	public String getName()
	{
		return "Sugar-coated Apple";
	}

	@Override
	public void onConsumed(RunsafePlayer player)
	{
		player.removeItem(getItem(), 1);
		Buff.Utility.Movement.IncreaseSpeed.duration(20).amplification(1).applyTo(player);
		player.sendMessage("Suddenly ... sugar rush!");
	}
}
