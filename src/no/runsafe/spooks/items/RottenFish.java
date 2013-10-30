package no.runsafe.spooks.items;

import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class RottenFish implements ISpookyItem
{
	@Override
	public Item getItem()
	{
		return Item.Food.Cooked.Fish;
	}

	@Override
	public String getName()
	{
 		return "Rotten Fish";
	}

	@Override
	public void onConsumed(RunsafePlayer player)
	{
		player.removeItem(getItem(), 1);
		player.damage(3D);
		player.sendMessage("That fish did not taste nice!");
	}
}
