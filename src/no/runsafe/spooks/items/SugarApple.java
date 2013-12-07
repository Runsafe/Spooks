package no.runsafe.spooks.items;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Buff;
import no.runsafe.framework.minecraft.Item;

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
	public void onConsumed(IPlayer player)
	{
		player.removeItem(getItem(), 1);
		Buff.Utility.Movement.IncreaseSpeed.duration(20).amplification(5).applyTo(player);
		player.sendMessage("Suddenly ... sugar rush!");
	}
}
